package modelos;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.table.AbstractTableModel;

import controladores.IdiomaController;
import ventanas.CampoFutbol;

public class ModeloTablaAlineacion extends AbstractTableModel {

	private ArrayList<TacticaJugador> tacticaJugadores = new ArrayList<TacticaJugador>();
	private boolean titulares;
	private IdiomaController traduccion = IdiomaController.getInstance();

	

	public ModeloTablaAlineacion(Equipo equipo, Tactica tactica,
			boolean titulares) {

		this.titulares = titulares;
		ArrayList<TacticaJugador> tacticasJugadores = tactica
				.getTacticasJugadores();

		// Caso de tactica por defecto o tactica en bbdd

		if (tactica.getId() == -1 || tactica.getId() > 0) {

			// LLenamos el ArrayList que va utilizar la tabla

			for (int i = 0; i < tacticasJugadores.size()
					&& i < equipo.getJugadores().size(); i++) {

				if (tacticasJugadores.get(i).getJugador().getId() == -1) {

					Jugador jugador = tacticasJugadores.get(i).getJugador();

					tacticasJugadores.get(i).setJugador(
							equipo.getJugadores().get(i));

				}

				if (tacticasJugadores.get(i).isTitular() == titulares)
					tacticaJugadores.add(tacticasJugadores.get(i));
			}

			/*
			 * Completamos el ArrayList de TacticaJugadores en el caso de que
			 * haya m�s jugadores en un equipo que n�mero de jugadores en una
			 * t�ctica.
			 */

			for (int i = tacticasJugadores.size(); i < equipo.getJugadores()
					.size(); i++) {

				ArrayList<TacticaJugador> tactJug = tactica
						.getTacticasJugadores();

				TacticaJugador tacticaJugador = new TacticaJugador();

				tacticaJugador.setJugador(equipo.getJugador(i));
				tacticaJugador.setTitular(false);
				tacticaJugador.setX(0);
				tacticaJugador.setY(0);

				tactJug.add(tacticaJugador);
			}
		}

		// Caso tactica nueva

		if (tactica.getId() == 0) {

			Random randomX = new Random();
			Random randomY = new Random();

			if (tactica.getTacticasJugadores().size() < 1) { // Primera vez que
																// entra aquí

				for (int i = 0; i < equipo.getJugadores().size() && i < 7; i++) {

					TacticaJugador tacticaJugador = new TacticaJugador();
					tacticaJugador.setJugador(equipo.getJugador(i));
					tacticaJugador.setTitular(true);
					tacticaJugador.setX(randomX.nextInt(CampoFutbol.ANCHO));
					tacticaJugador.setY(randomY.nextInt(CampoFutbol.ALTO));

					tactica.getTacticasJugadores().add(tacticaJugador);

					if (titulares)
						tacticaJugadores.add(tacticasJugadores.get(i));
				}

				for (int i = 7; i < equipo.getJugadores().size(); i++) {

					TacticaJugador tacticaJugador = new TacticaJugador();
					tacticaJugador.setJugador(equipo.getJugador(i));
					tacticaJugador.setTitular(false);
					tacticaJugador.setX(randomX.nextInt(CampoFutbol.ANCHO));
					tacticaJugador.setY(randomY.nextInt(CampoFutbol.ALTO));
					tactica.getTacticasJugadores().add(tacticaJugador);

					if (!titulares)
						tacticaJugadores.add(tacticasJugadores.get(i));
				}
			}

			else { // Vuelve a seleccionar nueva táctica
				for (int i = 0; i < tactica.getTacticasJugadores().size(); i++) {
					if (tactica.getTacticasJugadores().get(i).isTitular() == titulares) {
						tacticaJugadores.add(tacticasJugadores.get(i));
					}
				}
			}
		}

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tacticaJugadores.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		Object value = "??";
		TacticaJugador tacticaJug = tacticaJugadores.get(rowIndex);

		switch (columnIndex) {
		case 0:
			value = tacticaJug.getJugador().getDorsal();
			break;
		case 2:
			value = traduccion.getTraduccion(tacticaJug.getJugador().getDemarcacion());
			break;
		case 1:
			value = tacticaJug.getJugador().getNombre();
			break;

		}
		return value;
	}

	public TacticaJugador getTacticaJugador(int row) {

		return tacticaJugadores.get(row);

	}

	public void setTacticaJugador(int row, TacticaJugador tacticaJug) {

		tacticaJugadores.set(row, tacticaJug);

	}

	public static void updateTitularidades(ModeloTablaAlineacion modeloTit,
			ModeloTablaAlineacion modeloSup) {

		TacticaJugador titAsup = null;
		TacticaJugador supAtit = null;

		int filaVaciaTit = 0;
		int filaVaciaSup = 0;

		for (int i = 0; i < modeloTit.getTacticasJugadores().size(); i++) {

			if (!modeloTit.getTacticasJugadores().get(i).isTitular()) {
				titAsup = modeloTit.getTacticasJugadores().get(i);
				filaVaciaTit = i;
				break;
			}
		}

		for (int i = 0; i < modeloSup.getTacticasJugadores().size(); i++) {

			if (modeloSup.getTacticasJugadores().get(i).isTitular()) {
				supAtit = modeloSup.getTacticasJugadores().get(i);
				filaVaciaSup = i;
				break;
			}
		}

		if (titAsup != null && supAtit != null) {
			modeloTit.setTacticaJugador(filaVaciaTit, supAtit);
			modeloSup.setTacticaJugador(filaVaciaSup, titAsup);
		}
	}

	public ArrayList<TacticaJugador> getTacticasJugadores() {
		return this.tacticaJugadores;
	}

}
