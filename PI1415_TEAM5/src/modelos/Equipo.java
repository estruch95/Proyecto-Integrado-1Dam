package modelos;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import controladores.IdiomaController;

public class Equipo extends AbstractTableModel {

	public boolean isBorradoTacticas() {
		return borradoTacticas;
	}

	public void setBorradoTacticas(boolean borradoTacticas) {
		this.borradoTacticas = borradoTacticas;
	}

	private int id;
	private String nombre;
	private int disciplina;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Tactica> tacticas;
	private boolean modificado;
	private boolean borradoJugadores;
	private boolean borradoTacticas;
	private static Object[][] jugadoresTabla = new Object[0][6];
	private static String columnas[];
	private boolean borradoTactica;
	IdiomaController traduccion = IdiomaController.getInstance();


	public Equipo() {
		modificado = false;
		borradoJugadores = false;
		borradoTacticas = false;
		jugadores = new ArrayList<Jugador>();
		tacticas = new ArrayList<Tactica>();
		borradoTactica = false;
		columnas = new String[] { traduccion.getTraduccion("nombre"),
				traduccion.getTraduccion("posicion_tit"),
				traduccion.getTraduccion("posicion_sup"),
				traduccion.getTraduccion("dorsal"),
				traduccion.getTraduccion("fecha_nacimiento") };

	}

	public void setBorradoJugadores(boolean mod) {
		this.borradoJugadores = mod;
	}

	public boolean getBorradoJugadores() {
		return this.borradoJugadores;
	}

	public void setModificado(boolean mod) {
		this.modificado = mod;
	}

	public boolean getModificado() {
		return this.modificado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(int disciplina) {
		this.disciplina = disciplina;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;

	}

	public ArrayList<Tactica> getTacticas() {
		return tacticas;
	}

	public void setTacticas(ArrayList<Tactica> tacticas) {
		this.tacticas = tacticas;
	}

	public static ArrayList<Equipo> load(Usuario usuario) {
		MySql conexion = MySql.getInstance();
		int idUser = usuario.getId();
		ResultSet resultado = conexion
				.query("select * from equipos where id_usuario = " + idUser);

		ArrayList<Equipo> equipos = new ArrayList<Equipo>();

		try {
			while (resultado.next()) {
				try {
					// RECOGIDA DE DATOS DE LA BBDD (TABLA EQUIPOS)

					Equipo nuevoEquipo = new Equipo();
					nuevoEquipo.setId((int) resultado.getObject("id_equipo"));
					nuevoEquipo.setNombre((String) resultado.getObject("nombre"));
					nuevoEquipo.setDisciplina((int) resultado.getObject("id_disciplina"));
					Jugador.load(nuevoEquipo);

					// AÑDIMOS EL NUEVO EQUIPO RECOGIDO DE LA BBDD EN EL
					// ARRAYLIST
					equipos.add(nuevoEquipo);

				} catch (SQLException errorObtenerDatos) {
					// CAPTURA DE EXCEPCION EN CASO DE FALLO
					errorObtenerDatos.printStackTrace();
					System.out.println("ERROR LOAD EQUIPO");
				}
			}
		} catch (SQLException e) {
			// CAPTURA DE EXCEPCION EN CASO DE FALLO (resultado.next())
			e.printStackTrace();
			System.out.println("ERROR LOAD EQUIPO");
		}

		// ASIGNAMOS EL EQUIPO AL CORRESPONDIENTE USUARIO SEGUN SU ID
		usuario.setEquipos(equipos);

		return equipos;
	}

	public boolean save() {

		MySql conexion = MySql.getInstance();

		if (this.jugadores.size() < 7) {
			/*
			JOptionPane.showMessageDialog(null,
					traduccion.getTraduccion("alerta_menos_7"));
					*/
			Joption.showMesageDialog(traduccion.getTraduccion("alerta_menos_7"), new Object[]{"Ok"});
			return false;
		}

		if (this.jugadores.size() > 11) {
			/*
			JOptionPane.showMessageDialog(null,
					traduccion.getTraduccion("un_equipo_no_puede_contener_mas_de_11_jugadores"));
			*/
			Joption.showMesageDialog(traduccion.getTraduccion("un_equipo_no_puede_contener_mas_de_11_jugadores"), new Object[]{"Ok"});
			return false;
		}

		if (this.getId() == 0) {

			int seleccion = JOptionPane.showOptionDialog(null,
					traduccion.getTraduccion("alerta_guardar"),
					traduccion.getTraduccion("selecion_opcion"),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Si",
							"No" }, "Si");

			if (seleccion != -1) {
				if ((seleccion + 1) == 1) {
					// System.out.println("SI");
					conexion.modifyQuery("INSERT INTO equipos set nombre='"
							+ this.getNombre() + "', " + "id_disciplina=1, "
							+ "id_usuario=" + Autentificacion.getId());

					ResultSet rs = conexion
							.query("SELECT LAST_INSERT_ID() as last_id");
					try {
						if (rs.next()) {
							this.setId(((BigInteger) rs.getObject("last_id"))
									.intValue());
						}
					} catch (SQLException e) {
						System.out
								.println("ERROR ASIGNACION DE ULTIMO ID A EQUIPO NUEVO");
					}
					for (int i = 0; i < this.jugadores.size(); i++) {
						jugadores.get(i).save(this.id);
					}
					Autentificacion.getUsuario().getEquipos().add(this);
				} else {
					// System.out.println("NO");
				}
			}

		} else {

			conexion.modifyQuery("UPDATE equipos set nombre='"
					+ this.getNombre() + "' WHERE id_equipo=" + this.getId()
					+ ";");

			for (int i = 0; i < this.jugadores.size(); i++) {
				jugadores.get(i).save(this.id);
			}
			/*
			JOptionPane
					.showMessageDialog(null, traduccion
							.getTraduccion("equipo_modificado_correctamente"));
			*/
			Joption.showMesageDialog(traduccion.getTraduccion("equipo_modificado_correctamente"), new Object[]{"Ok"});
		}

		if (this.getBorradoJugadores()) {
			ResultSet rs = conexion
					.query("SELECT id_jugador from jugadores where id_equipo="
							+ this.getId());
			try {
				while (rs.next()) {
					int id_jugador_bbdd = (int) rs.getObject("id_jugador");
					boolean borrarJugador = true;

					for (int i = 0; i < jugadores.size(); i++) {
						if (jugadores.get(i).getId() == id_jugador_bbdd) {
							borrarJugador = false;
							break;
						}
					}
					if (borrarJugador) {
						conexion.modifyQuery("DELETE from jugadores  WHERE id_jugador="
								+ id_jugador_bbdd);
					}

				}
				this.setBorradoJugadores(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR AL REALIZAR DELETES");
			}
		}

		if (this.isBorradoTacticas()) {
			ResultSet rs = conexion
					.query("SELECT id_tactica from tacticas where id_equipo="
							+ this.getId());
			try {
				while (rs.next()) {
					int id_tactica_bbdd = (int) rs.getObject("id_tactica");
					boolean borrarTactica = true;

					for (int i = 0; i < tacticas.size(); i++) {
						if (tacticas.get(i).getId() == id_tactica_bbdd) {
							borrarTactica = false;
							break;
						}
					}
					if (borrarTactica) {
						conexion.modifyQuery("DELETE from tacticas  WHERE id_tactica="
								+ id_tactica_bbdd);
					}

				}
				this.setBorradoTacticas(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR AL REALIZAR DELETES");
			}
		}
		return true;
	}

	public void setBorradoTactica(boolean mod) {
		this.borradoTactica = mod;
	}

	public boolean getBorradoTactica() {
		return this.borradoTactica;
	}

	public String toString() {
		return this.nombre;
	}

	public void addJugador(Jugador jugador) {

		jugadores.add(jugador);

	}

	public Jugador getJugador(int row) {
		return jugadores.get(row);
	}

	@Override
	public int getRowCount() {

		return jugadores.size();
	}

	@Override
	public int getColumnCount() {

		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		Object value = "??";
		Jugador jugador = jugadores.get(rowIndex);

		switch (columnIndex) {
		case 0:
			value = jugador.getNombre();
			break;
		case 1:
			value = traduccion.getTraduccion(jugador.getDemarcacion());
			break;
		case 2:
			value = traduccion.getTraduccion(jugador.getSegundaDemarcacion());
			break;
		case 3:
			value = jugador.getDorsal();
			break;
		case 4:
			value = jugador.getFechaNacimiento();
			break;

		}

		return value;

	}

	@Override
	public String getColumnName(int columnIndex) {

		return columnas[columnIndex];
	}

	public void removeJugador(int row) {
		jugadores.remove(row);
	}

	public void removeTactica(Tactica tactica) {
		this.setBorradoTacticas(true);
		tacticas.remove(tactica);
	}

}
