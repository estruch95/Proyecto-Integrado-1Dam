package modelos;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TacticaJugador {
	
	private int X;
	private int Y;
	private Jugador jugador;
	private boolean titular;
	
	public boolean isTitular() {
		return titular;
	}

	public void setTitular(boolean titular) {
		this.titular = titular;
	}

	public TacticaJugador(){

	}

	public void setPosicionX(int posX) {
		this.X = posX;
	}

	public void setPosicionY(int posY) {
		this.Y = posY;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	public static ArrayList<TacticaJugador> load(Tactica tactica, Equipo equipo){
		MySql conexion = MySql.getInstance();
		int idTactica = tactica.getId();
		int idEquipo = equipo.getId();
		ResultSet resultado = conexion.query("select tj.*, j.id_jugador as id_jugador_tact from tacticas_jugadores tj inner join jugadores j on tj.id_jugador=j.id_jugador inner join tacticas t on tj.id_tactica=t.id_tactica where j.id_equipo ='" +idEquipo+ "' and tj.id_tactica = '" +idTactica+ "';");
		
		ArrayList<TacticaJugador> tacticasJugadores = new ArrayList<TacticaJugador>();
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		
		Jugador jugador = new Jugador();
		//Cargo en el arraylist todos los jugadores del equipo pasado por parametro.
		jugadores = equipo.getJugadores();
		int indice = 0;
		try{
			while(resultado.next()){		
				try{
					TacticaJugador nuevo = new TacticaJugador();
					nuevo.setPosicionX((int) resultado.getObject("posX"));
					nuevo.setPosicionY((int) resultado.getObject("posY"));
					nuevo.setTitular((boolean)resultado.getObject("titular"));
					
					/*Bucle para recorrer los jugadores del equipo. Si el id del jugador coincide
					 * con el id de la consulta sql, metemos ese jugador en nuevo
					 */
					//Seteo el array jugadores con los valores del array que me devuelve el metodo load() de jugadores.
					jugadores = equipo.getJugadores();
					for(int i=0; i<jugadores.size(); i++){
						if(jugadores.get(i).getId()==(int)resultado.getObject("id_jugador_tact")) {
							nuevo.setJugador(jugadores.get(i));
						}
					}
					
					tacticasJugadores.add(nuevo);
					
					
				} catch (SQLException errorObtenerDatos) {
					// CAPTURA DE EXCEPCION EN CASO DE FALLO
					errorObtenerDatos.printStackTrace();
				}
			}
			tactica.setTacticaJugador(tacticasJugadores);
		} catch (SQLException e) {
			// CAPTURA DE EXCEPCION EN CASO DE FALLO
			e.printStackTrace();
		}
		//equipo.setTacticas(tacticasJugadores);
		
		return tacticasJugadores;
		
	}
	
	
	public void save(int id_tactica, boolean crear) {
		
		MySql mysql = MySql.getInstance();
		if(crear) { 
			mysql.modifyQuery("INSERT INTO tacticas_jugadores set"
					+ " id_tactica="+id_tactica
					+ ", id_jugador="+this.getJugador().getId()
					+ ", posX="+this.X
					+ ", posY="+this.Y
					+ ", titular="+this.titular
			);
		}
		else { //Modificación
			mysql.modifyQuery("UPDATE tacticas_jugadores set"
					+ " posX="+this.X
					+ ", posY="+this.Y
					+ ", titular="+this.titular
					+ " WHERE "
					+ " id_tactica="+id_tactica
					+ " and id_jugador="+this.getJugador().getId()
			);
		}
		
	}

	
	
	public void cambiarXY(int x, int y) {
		this.X=x;
		this.Y=y;
	}
	


	
}
