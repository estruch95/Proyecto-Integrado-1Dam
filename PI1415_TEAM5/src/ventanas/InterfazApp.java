package ventanas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import modelos.Autentificacion;
import modelos.Equipo;
import modelos.ModeloTablaAlineacion;
import modelos.Tactica;
import modelos.TacticaJugador;
import Other.Fuente;
import controladores.IdiomaController;
import controladores.MainController;
import controladores.TacticaControlador;

public class InterfazApp extends JPanel {

	
	IdiomaController traduccion = IdiomaController.getInstance(); 
	private ImageIcon fondoPanel;
	private JTable tablaTitulares;
	private JTable tablaSuplentes;
	private String formacion;
	JComboBox cambiaEquipo;
	JComboBox cambiaFormacion;
	CampoFutbol cf = new CampoFutbol();
	TacticaControlador tacticaController=new TacticaControlador();
	Tactica tacticaSelected;
	Equipo equipoSelected;
	private JTextField nuevaTactica;
	ModeloTablaAlineacion modeloTablaTit;
	ModeloTablaAlineacion modeloTablaSup;
	private Fuente fuente;
	Dimension dimension = getToolkit().getDefaultToolkit().getScreenSize();
	JPanel panelCentral;
	private JButton btnCerrarSesion;
	private Login loginPanel;
	
	
	public InterfazApp(CreacionEquipos creacionEquipos, Login loginPanel) {
		
		this.loginPanel = loginPanel;
		setLayout(null);
		this.fuente = new Fuente();
		
		panelCentral = new JPanel();
		panelCentral.setBounds(138, 29, 1005, 642);
		add(panelCentral);
		panelCentral.setLayout(null);
		panelCentral.setOpaque(false);
		
		cambiaEquipo = new JComboBox(Autentificacion.getUsuario().getEquipos().toArray());
		cambiaEquipo.setBounds(6, 6, 140, 27);
		panelCentral.add(cambiaEquipo);
		equipoSelected=(Equipo) cambiaEquipo.getSelectedItem();
		cambiaEquipo.setFont(new Font("Georgia", Font.PLAIN, 11));
		cambiaEquipo.setBackground(new Color(255, 255, 255));
		cambiaEquipo.setToolTipText(traduccion.getTraduccion("cambiar_equipo"));
		
		
		//COMBOBOX FORMACIONES
		cambiaFormacion = new JComboBox();
		cambiaFormacion.setBounds(254, 8, 142, 20);
		panelCentral.add(cambiaFormacion);
		cambiaFormacion.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		
		
		
		cambiaFormacion.setToolTipText(traduccion.getTraduccion("elige_formacion"));
		cf.setBounds(6, 157, 700, 450);
		panelCentral.add(cf);
		
		cf.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel Formacion = new JLabel(traduccion.getTraduccion("formacion"));
		Formacion.setBounds(157, 10, 101, 14);
		panelCentral.add(Formacion);
		Formacion.setForeground(Color.WHITE);
		Formacion.setFont(new Font("Georgia", Font.BOLD, 15));
		
		
		//TABLA TITULARES
		tablaTitulares = new JTable();
		tablaTitulares.setBounds(735, 204, 264, 112);
		panelCentral.add(tablaTitulares);
		tablaTitulares.setRowSelectionAllowed(true);
		
		
		tablaTitulares.setGridColor(SystemColor.controlShadow);
		tablaTitulares.setFont(new Font("Tahoma", Font.BOLD, 11));
		tablaTitulares.setFillsViewportHeight(true);
		tablaTitulares.setBackground(Color.WHITE);
		tablaTitulares.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		tablaTitulares.setModel(new Equipo());
		
		JLabel lblJugadoresTitulares = new JLabel(traduccion.getTraduccion("titulares"));
		lblJugadoresTitulares.setBounds(735, 157, 215, 27);
		panelCentral.add(lblJugadoresTitulares);
		lblJugadoresTitulares.setForeground(Color.WHITE);
		lblJugadoresTitulares.setFont(new Font("Georgia", Font.BOLD, 25));
		
		
		
		//TABLA SUPLENTES
		tablaSuplentes = new JTable();
		tablaSuplentes.setBounds(735, 543, 264, 64);
		panelCentral.add(tablaSuplentes);
		tablaSuplentes.setRowSelectionAllowed(true);
		tablaSuplentes.setFont(fuente.getFuente());
		
		tablaSuplentes.setGridColor(SystemColor.controlShadow);
		tablaSuplentes.setFont(new Font("Tahoma", Font.BOLD, 11));
		tablaSuplentes.setFillsViewportHeight(true);
		tablaSuplentes.setBackground(Color.WHITE);
		tablaSuplentes.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		tablaSuplentes.setModel(new Equipo());
		
		JLabel lblSuplentes = new JLabel(traduccion.getTraduccion("suplentes"));
		lblSuplentes.setBounds(735, 485, 250, 49);
		panelCentral.add(lblSuplentes);
		lblSuplentes.setFont(new Font("Georgia", Font.BOLD, 25));
		lblSuplentes.setForeground(Color.WHITE);
		
		
		
		JButton CambiarJugador = new JButton(traduccion.getTraduccion("cambiar_jugador"));
		CambiarJugador.setBounds(735, 394, 264, 35);
		panelCentral.add(CambiarJugador);
		CambiarJugador.setForeground(new Color(139, 0, 0));
		CambiarJugador.setFont(new Font("Georgia", Font.BOLD, 15));
		
		nuevaTactica = new JTextField();
		nuevaTactica.setBounds(77, 103, 157, 21);
		panelCentral.add(nuevaTactica);
		nuevaTactica.setFont(fuente.getFuente());
		nuevaTactica.setColumns(10);
		
		//LABEL TACTICA;
		JLabel lblTactica = new JLabel(traduccion.getTraduccion("tactica"));
		lblTactica.setBounds(6, 105, 77, 14);
		panelCentral.add(lblTactica);
		lblTactica.setForeground(Color.WHITE);
		lblTactica.setFont(new Font("Georgia", Font.BOLD, 15));
		
		//BOTON GUARDAR TACTICA
		JButton guardarTactica = new JButton(traduccion.getTraduccion("guardar_tactica"));
		guardarTactica.setBounds(254, 103, 142, 22);
		panelCentral.add(guardarTactica);
		guardarTactica.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		//BOTON ELIMINAR FORMACION
		JButton btnEliminar = new JButton(traduccion.getTraduccion("eliminar"));
		btnEliminar.setBounds(408, 8, 124, 21);
		panelCentral.add(btnEliminar);
		btnEliminar.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		JButton btnAtrs = new JButton(traduccion.getTraduccion("volver_creacion"));
		btnAtrs.setBounds(6, 613, 181, 29);
		btnAtrs.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/atras.png")));
		btnAtrs.setFont(new Font("Georgia", Font.PLAIN, 13));
		panelCentral.add(btnAtrs);
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tacticaController.volverAcreacionEquipos(creacionEquipos);
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tacticaSelected.getId()==-1 || tacticaSelected.getId()==0) {
					JOptionPane.showMessageDialog(null, traduccion.getTraduccion("mensaje_error_tactica"), traduccion.getTraduccion("error"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(cambiaFormacion.getSelectedItem()!=null){
					int seleccion = JOptionPane.showOptionDialog(
							null,
							traduccion.getTraduccion("mensaje_eliminar_tactica"),
						    traduccion.getTraduccion("Seleccione_opcion"),
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    new Object[] { "Si", "No"},
						    "No");
					
					if(seleccion==0) {
						
						if(tacticaController.eliminarTactica(tacticaSelected, equipoSelected)) {
							reloadTacticas();
							reloadTablasAlineaciones();
							nuevaTactica.setText(tacticaSelected.getNombre());
							cf.redibujarFichas(tacticaSelected);
						}
					}
					
				}
			}
		});
		guardarTactica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tacticaController.guardarTactica(nuevaTactica.getText(), equipoSelected);
				reloadTacticas();
			}
		});
		
		CambiarJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filSelecTit = tablaTitulares.getSelectedRow();
				int filSelecSup= tablaSuplentes.getSelectedRow();
				
				TacticaJugador tacticaJugador = new TacticaJugador();
				
				

					if(filSelecTit!=(-1) && filSelecSup!=(-1)){						

					
						TacticaJugador titular= modeloTablaTit.getTacticaJugador(filSelecTit);
						TacticaJugador suplente= modeloTablaSup.getTacticaJugador(filSelecSup);
						
						tacticaController.intercambiarTitularidadJugadores(titular, suplente);
						ModeloTablaAlineacion.updateTitularidades(modeloTablaTit, modeloTablaSup);
						tablaTitulares.repaint();
						tablaTitulares.setFont(fuente.getFuente());
						tablaSuplentes.repaint();
						tablaTitulares.clearSelection();
						tablaSuplentes.clearSelection();
						cf.redibujarFichas(tacticaSelected);
						
			
					}
			} 
		});
		
		
		cambiaFormacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tacticaSelected=(Tactica) cambiaFormacion.getSelectedItem();
				
				if(tacticaSelected.getId()==-1) {
					guardarTactica.setText(traduccion.getTraduccion("nueva_tactica"));
				}
				else {
					guardarTactica.setText(traduccion.getTraduccion("guardar_tactica"));
				}
				tacticaController.setModel(tacticaSelected);
				nuevaTactica.setText(tacticaSelected.getNombre());
				reloadTablasAlineaciones();
				cf.redibujarFichas(tacticaSelected);
				cf.repaint();
			}
		});
		
		cambiaEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				equipoSelected=(Equipo)cambiaEquipo.getSelectedItem();
				
				reloadTacticas();
				reloadTablasAlineaciones();
				nuevaTactica.setText(tacticaSelected.getNombre());
				cf.redibujarFichas(tacticaSelected);
			}
		});
		
		btnCerrarSesion = new JButton(traduccion.getTraduccion("cerrar_sesion"));
		btnCerrarSesion.setBounds(199, 613, 161, 29);
		btnCerrarSesion.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/logout.png")));
		btnCerrarSesion.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBotonCerrarSesion();
			}

			
		});
		
		panelCentral.add(btnCerrarSesion);
		
		
		fondoPanel = new ImageIcon(getClass().getResource("/img/estadio.jpg"));
		setSize(fondoPanel.getIconWidth(),fondoPanel.getIconHeight());
		
		
		
		
		//Acciones para rellenar campos
		
		reloadTacticas();
		reloadTablasAlineaciones();
		cf.redibujarFichas(tacticaSelected);
		
		
		
		
		
}
	
	
	protected void accionBotonCerrarSesion() {
		loginPanel.clearFields();
		MainController.cambiaPanel(loginPanel);
	}
	
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		Dimension d = getSize();
		g.drawImage(fondoPanel.getImage(), 0, 0, d.width, d.height, null);
		
	}
	
	public String getFormacion(){
		return formacion;
	}
	
	public void setFormacion(JComboBox cambiaFormacion){
		formacion = (String)cambiaFormacion.getSelectedItem();
	}
	
	
	public void reloadTacticas() {
		
		ArrayList<Tactica> tacticaJugadores=tacticaController.getTacticasDefecto();
		tacticaJugadores.addAll(equipoSelected.getTacticas());
		//Tactica nuevaTactica = new Tactica();
		//nuevaTactica.setNombre("Nueva táctica");
		//nuevaTactica.setId(0);
		//nuevaTactica.setTacticaJugador(new ArrayList<TacticaJugador>());
		//tacticaController.setModel(nuevaTactica);
		//tacticaJugadores.add(0, nuevaTactica);
		
		DefaultComboBoxModel nuevoModelo = new DefaultComboBoxModel(tacticaJugadores.toArray());
		//tacticaSelected=nuevaTactica;
		
		tacticaController.setModel(tacticaJugadores.get(0));
		tacticaSelected=tacticaJugadores.get(0);
		
		cambiaFormacion.setModel(nuevoModelo);
		cambiaFormacion.repaint();
		
	}
	
	public void reloadTablasAlineaciones() {
		
		//Modelo de datos de las tablas
		modeloTablaTit=new ModeloTablaAlineacion(equipoSelected, tacticaSelected, true);
		modeloTablaSup=new ModeloTablaAlineacion(equipoSelected, tacticaSelected, false);
		
		tablaTitulares.setModel(modeloTablaTit);
		tablaSuplentes.setModel(modeloTablaSup);
		
		this.setJTableColumnsWidth(tablaTitulares, 264, 10, 45, 45);
		this.setJTableColumnsWidth(tablaSuplentes, 264, 10, 45, 45);

		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);

		for(int i=0; i<3; i++) {
			tablaSuplentes.getColumnModel().getColumn(i).setResizable(false);
			tablaTitulares.getColumnModel().getColumn(i).setResizable(false);
			tablaTitulares.getColumnModel().getColumn(i).setCellRenderer(centerRender);
			tablaSuplentes.getColumnModel().getColumn(i).setCellRenderer(centerRender);
		}
		

	}
	
	public void cambioSeleccionTabla() {
		for(int i=0; i<modeloTablaTit.getTacticasJugadores().size(); i++) {
			if(modeloTablaTit.getTacticasJugadores().get(i).getJugador().isSelected()) 
			{
				tablaTitulares.changeSelection(i, 0, false, false);
			}
		}
	}
	
	public void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
	        double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
}

