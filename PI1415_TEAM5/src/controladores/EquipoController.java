package controladores;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelos.Autentificacion;
import modelos.Equipo;
import modelos.Joption;
import modelos.Jugador;
import modelos.Tactica;
import modelos.Usuario;
import ventanas.CreacionEquipos;
import ventanas.InterfazApp;
import ventanas.Login;

public class EquipoController {
	
	private CreacionEquipos ce;
	private Equipo equipoSeleccionado;
	private IdiomaController traduccion = IdiomaController.getInstance();
	
	public EquipoController() {
		
	}

	public void setEquipo(Equipo equipo){
		this.equipoSeleccionado = equipo;
	}
	
	public Equipo getEquipo(){
		return this.equipoSeleccionado;
	}
	
	public void addJugador(String nombre, String posicion, String segundaPosicion, String dorsal, String edad){
		
		if(nombre.equals("") || dorsal.equals("") || edad.equals("")){
			//JOptionPane.showMessageDialog(null, traduccion.getTraduccion("no_campos_vacios"));
			Joption.showMesageDialog(traduccion.getTraduccion("no_campos_vacios"), new Object[]{"Ok"});
		}
		else if(isNumeric(dorsal)==false || dorsal.length()>2){
			//JOptionPane.showMessageDialog(null, traduccion.getTraduccion("dorsar_2_valores"));
			Joption.showMesageDialog(traduccion.getTraduccion("dorsar_2_valores"), new Object[]{"Ok"});
		}
		else{
			Jugador jugador = new Jugador();
			jugador.setId(0);
			jugador.setNombre(nombre);
			jugador.setDemarcacion(posicion);
			jugador.setSegundaDemarcacion(segundaPosicion);
			jugador.setDorsal(Integer.parseInt(dorsal));
			jugador.setFechaNacimiento(edad);
			this.equipoSeleccionado.addJugador(jugador);
		}
	}
	
	public void eliminarJugador(int row){
		this.equipoSeleccionado.removeJugador(row);
		this.equipoSeleccionado.setBorradoJugadores(true);
	}
	
	public void modificarJugador(String nombre, String demarcacion, String segundaDemarcacion, int dorsal, String fechaNacimiento, int row){
	Jugador jugador = this.equipoSeleccionado.getJugador(row);
	jugador.setNombre(nombre);
	jugador.setDemarcacion(demarcacion);
	jugador.setSegundaDemarcacion(segundaDemarcacion);
	jugador.setDorsal(dorsal);
	jugador.setFechaNacimiento(fechaNacimiento);
	this.equipoSeleccionado.setModificado(true);
	}
	
	public Jugador obtenerJugador(int row){
		return equipoSeleccionado.getJugador(row);
	}
	
	public void eliminarEquipo(Equipo equipo){
		Usuario usuario = Autentificacion.getUsuario();
		usuario.eliminarEquipo(equipo);
	}
	
	public boolean guardarEquipo(String nombreEquipo){
		this.equipoSeleccionado.setNombre(nombreEquipo);
		return this.equipoSeleccionado.save();
	}
	
	public void irAtacticas(CreacionEquipos creacionEquipos, Login loginPanel) {
		
		ArrayList<Equipo> equiposUsuario=Autentificacion.getUsuario().getEquipos();
		
		for(int i=0; i<equiposUsuario.size(); i++) {
			Tactica.load(equiposUsuario.get(i)); 
			Equipo equipo=equiposUsuario.get(i);
		}
		
		
		
		
		InterfazApp panelAppPrincipal = new InterfazApp(creacionEquipos, loginPanel);
		MainController.cambiaPanel(panelAppPrincipal);
		
	}
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} 
		catch (NumberFormatException nfe){
			return false;
		}
	}
}
