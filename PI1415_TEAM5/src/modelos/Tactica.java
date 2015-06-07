package modelos;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controladores.IdiomaController;

public class Tactica {
	
	
	/*tactica actualizada*/
	private int id;
	private String nombre;
	private ArrayList<TacticaJugador> tacticasJugadores = new ArrayList<TacticaJugador>();
	IdiomaController traduccion = IdiomaController.getInstance();
	
	public Tactica() {

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

	public static ArrayList<Tactica> load(Equipo equipo){
		MySql conexion = MySql.getInstance();
		int idEquipo = equipo.getId();
		ResultSet resultado = conexion.query("select id_tactica, nombre FROM tacticas WHERE id_equipo="+idEquipo);
		
		
		ArrayList<Tactica> tacticas = new ArrayList<Tactica>();
		try{
			while(resultado.next()){
				
				Tactica tactica = new Tactica();
				tactica.setId((int)resultado.getObject("id_tactica"));
				tactica.setNombre((String)resultado.getObject("nombre"));
				tacticas.add(tactica);
				TacticaJugador.load(tactica, equipo);
				
			}
		}
		catch(SQLException errorCarga){
			errorCarga.printStackTrace();
		}
		equipo.setTacticas(tacticas);
		return tacticas;
	}
	
	public boolean save(Equipo equipo){
		MySql conexion = MySql.getInstance();

		
		int idEquipo = equipo.getId();

		
		
		if(this.getId() < 1){
			conexion.modifyQuery("INSERT INTO tacticas SET nombre='"+this.getNombre()+"', id_equipo='"+idEquipo+"';");
			ResultSet result = conexion.query("SELECT LAST_INSERT_ID() AS last_id;"); 
			try{
				while(result.next()){
					this.setId(((BigInteger) result.getObject("last_id")).intValue());
				}
			}catch (SQLException e){
				System.out.println("ERROR ASIGNACION DE ULTIMO ID A TACTICA NUEVA");
			}
			for(int i=0; i<this.getTacticasJugadores().size(); i++){
				this.getTacticasJugadores().get(i).save(this.getId(), true);
			}
			JOptionPane.showMessageDialog(null, traduccion.getTraduccion("tactica_guardada_correctamente"));

		}
		else{
			
				conexion.modifyQuery("UPDATE tacticas SET nombre='"+this.getNombre()+"' WHERE id_tactica='"+this.getId()+"';");
				for(int i=0; i<this.getTacticasJugadores().size(); i++){
					this.getTacticasJugadores().get(i).save(this.getId(), false);
				}
				JOptionPane.showMessageDialog(null, traduccion.getTraduccion("tactica_modificada_correctamente"));

			
		}
			
		return true;
	}
	

	
	public String toString(){
		if(this.id==-1)
			return this.nombre+" (default)";
		else return this.nombre;
	}

	public void setTacticaJugador(ArrayList<TacticaJugador> tacticasJugadores) {
		this.tacticasJugadores = tacticasJugadores;
	}
	
	public ArrayList<TacticaJugador> getTacticasJugadores(){
		return tacticasJugadores;
	}
}
