package controladores;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import ventanas.CreacionEquipos;
import modelos.Equipo;
import modelos.Jugador;
import modelos.Tactica;
import modelos.TacticaJugador;

public class TacticaControlador {
	CargaXML xml = new CargaXML();
	private ArrayList<String[][][]> tacticasDefecto;
	private Tactica tactica=null;
	IdiomaController traduccion= IdiomaController.getInstance();
	
	
	public TacticaControlador(){
		tacticasDefecto=xml.cargaXml();
	}
	
	public void setModel(Tactica tactica) {
		this.tactica=tactica;
	}
	
	public ArrayList<Tactica> getTacticasDefecto(){
		
		ArrayList<Tactica> tacticasAL=new ArrayList<Tactica>();
		for(int i=0; i<tacticasDefecto.size(); i++){
			Tactica tactica = new Tactica();
			tactica.setId(-1);
			tactica.setNombre(tacticasDefecto.get(i)[0][0][0]);
			tacticasAL.add(tactica);
			
			ArrayList<TacticaJugador> tacticasJugadores = new ArrayList<TacticaJugador>();
			
			for(int j = 0; j < tacticasDefecto.get(i)[1].length; j++){
				
				TacticaJugador tacticaJugador = new TacticaJugador();
				Jugador jugador=new Jugador();
				jugador.setDemarcacion(tacticasDefecto.get(i)[1][j][0]);
				jugador.setId(-1);
				tacticaJugador.setJugador(jugador);
				tacticaJugador.setPosicionX(Integer.parseInt(tacticasDefecto.get(i)[1][j][1]));
				tacticaJugador.setPosicionY(Integer.parseInt(tacticasDefecto.get(i)[1][j][2]));
				tacticaJugador.setTitular(true);
				tacticasJugadores.add(tacticaJugador);
				
			}
			tactica.setTacticaJugador(tacticasJugadores);
		}
		return tacticasAL;
	}
	
	
	public void intercambiarTitularidadJugadores(TacticaJugador tit, TacticaJugador sup) {
		
		sup.setX(tit.getX());
		sup.setY(tit.getY());
		tit.setTitular(false);
		sup.setTitular(true);
	}
	

	public void guardarTactica(String nombre, Equipo equipo) {
		if(this.tactica != null) {
			
			
			
			tactica.setNombre(nombre);
			
			if(tactica.getId()==0 || tactica.getId()==-1) {
				equipo.getTacticas().add(tactica);
			}
			
			
			tactica.save(equipo);
		}
		
	}
	
	public boolean eliminarTactica(Tactica tactica, Equipo equipo) {
		
		equipo.removeTactica(tactica);
		if(equipo.save())
			return true;
		return false;
	}
	
	public void volverAcreacionEquipos(CreacionEquipos creacionEquipos){
		MainController.cambiaPanel(creacionEquipos);
	}
	

}
	
