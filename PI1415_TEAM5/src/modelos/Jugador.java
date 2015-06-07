
package modelos;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JLabel;

import ventanas.CampoFutbol;
import ventanas.Imagen;

public class Jugador extends Imagen{
	
	private int id;
	private String nombre;
	private int dorsal;
	private String demarcacion;
	private String segundaDemarcacion;
	private String fechaNacimiento;
	private CampoFutbol campoFutbol=null;
	private boolean selected=false;
	

	public Jugador() {
		super("/img/ficha-roja.png" , "/img/ficha-seleccionada.png", 30, 30);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setForeground(Color.WHITE);
		
		this.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(Jugador.this.selected!=true && campoFutbol!=null) {
					if(campoFutbol.numSelectedFichas()<1) {
						Jugador.this.selected=true;
						Jugador.this.seleccionar();
						campoFutbol.jugadorSeleccionado();
					}
				}
				else {
					Jugador.this.selected=false;
					Jugador.this.deseleccionar();
					

				}
					
				

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	
	public CampoFutbol getCampoFutbol() {
		return campoFutbol;
	}


	public void setCampoFutbol(CampoFutbol campoFutbol) {
		this.campoFutbol = campoFutbol;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
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



	public int getDorsal() {
		return dorsal;
	}



	public void setDorsal(int dorsal) {
		this.setText(""+dorsal);
		this.dorsal = dorsal;
	}



	public String getDemarcacion() {
		return demarcacion;
	}



	public void setDemarcacion(String demarcacion) {
		this.demarcacion = demarcacion;
	}



	public String getFechaNacimiento() {
		return fechaNacimiento;
	}



	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}



	public String getSegundaDemarcacion() {
		return segundaDemarcacion;
	}



	public void setSegundaDemarcacion(String segundaDemarcacion) {
		this.segundaDemarcacion = segundaDemarcacion;
	}
	
	
	
	public static ArrayList<Jugador> load(Equipo equipo){
		MySql conexion = MySql.getInstance();
		int idEquipo = equipo.getId();
		ResultSet resultado = conexion.query("select id_jugador, nombre, dorsal, demarcacion, segunda_demarcacion, fecha_nacimiento FROM jugadores WHERE id_equipo ="+idEquipo);
		
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		try{
			while(resultado.next()){
				Jugador jugador = new Jugador();
				jugador.setDemarcacion((String)resultado.getObject("demarcacion"));
				jugador.setDorsal((int)resultado.getObject("dorsal"));
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
				String fechaNacimientoString = formatter.format(resultado.getObject("fecha_nacimiento")); 
				jugador.setFechaNacimiento(fechaNacimientoString);
				jugador.setId((int)resultado.getObject("id_jugador"));
				jugador.setNombre((String)resultado.getObject("nombre"));
				jugador.setSegundaDemarcacion((String)resultado.getObject("segunda_demarcacion"));
				jugadores.add(jugador);
			}
		}
		catch(SQLException errorCargaDatos){
			errorCargaDatos.printStackTrace();
			System.out.println("ERROR LOAD JUGADOR");
		}
		equipo.setJugadores(jugadores);
		return jugadores;
	}

	public boolean save(int id_equipo){
		MySql conexion = MySql.getInstance();
		
		final String OLD_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT = "yyyy/MM/dd";

		// August 12, 2010
		String oldDateString = this.getFechaNacimiento();
		String newDateString="";

		SimpleDateFormat newDateFormat = new SimpleDateFormat(OLD_FORMAT);
		java.util.Date d;
		try {
			d = newDateFormat.parse(oldDateString);
			newDateFormat.applyPattern(NEW_FORMAT);
			newDateString = newDateFormat.format(d);
		} 
		catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(this.getId()==0){
			conexion.modifyQuery("insert INTO jugadores set"
					+ "  nombre='"+this.getNombre()+"' "
					+ ", dorsal="+this.getDorsal()+" "
					+ ", demarcacion='"+this.getDemarcacion()+"' "
					+ ", segunda_demarcacion='"+this.getSegundaDemarcacion()+"' "
					+ ", fecha_nacimiento='"+newDateString+"'"
					+ ", id_equipo="+id_equipo	);
			//System.out.println("INSERTS REALIZADOS CORRECTAMENTE");
			
			ResultSet rs = conexion.query("SELECT LAST_INSERT_ID() as last_id");
			try {
				if (rs.next()) {
					this.setId(((BigInteger) rs.getObject("last_id")).intValue());
				}
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR SAVE JUGADOR");
			}
			
		}
		else{
			
			conexion.modifyQuery("UPDATE jugadores set"
					+ "  nombre='"+this.getNombre()+"' "
					+ ", dorsal="+this.getDorsal()+" "
					+ ", demarcacion='"+this.getDemarcacion()+"' "
					+ ", segunda_demarcacion='"+this.getSegundaDemarcacion()+"' "
					+ ", fecha_nacimiento='"+this.getFechaNacimiento()+"'"
					+ ", id_equipo="+id_equipo+" WHERE id_jugador="+this.getId()+";");
		}
		return true;
	}
}
