package modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controladores.IdiomaController;

public class Usuario {
	private int id;
	private ArrayList<Equipo> equipos = new ArrayList<Equipo>();
	private String nombre;
	private String apellidos;
	private String correo;
	IdiomaController traduccion = IdiomaController.getInstance();
	
	public Usuario(){
		
	}
	
	public static Usuario load(int id){
		MySql mysql = MySql.getInstance();
		ResultSet rs = mysql.query("SELECT nombre, apellidos, correo FROM usuarios WHERE id_usuario='" +id+ "'");
		Usuario usuario = new Usuario();
		
		try {
			while(rs.next()){
				usuario.setId(id);
				usuario.setNombre((String) rs.getObject("nombre"));
				usuario.setApellidos((String) rs.getObject("apellidos"));
				usuario.setCorreo((String) rs.getObject("correo"));
				Equipo.load(usuario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(ArrayList<Equipo> equipos) {
		this.equipos = equipos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void eliminarEquipo(Equipo equipo){
		this.eliminarEquipoBBDD(equipo);
	}
	
	public boolean eliminarEquipoBBDD(Equipo equipo) {
		
		int seleccion = JOptionPane.showOptionDialog(
				null,
				traduccion.getTraduccion("eliminar_equipo"),
			    traduccion.getTraduccion("elige_opcion"),
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] { "Si", "No"},
			    "No");
		
		if (seleccion != -1)
		{
		   if((seleccion + 1)==1)
		   {
			 //System.out.println("SI");
			   MySql conexion=MySql.getInstance();
			   conexion.modifyQuery("DELETE from jugadores WHERE id_equipo="+equipo.getId()+";");
			   conexion.modifyQuery("DELETE from equipos WHERE id_equipo="+equipo.getId()+";");
			   
			   for(int a=0;a<this.getEquipos().size();a++){
					if(this.getEquipos().get(a).getId()==equipo.getId()){
						equipos.remove(a);
					}
				}
			   return true;
		   }
		   else
		   {
			 //System.out.println("NO");
			   return false;
		   }
		}
		return false;
	}

}
