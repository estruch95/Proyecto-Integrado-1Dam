package ventanas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import modelos.Autentificacion;
import modelos.Demarcacion;
import modelos.Equipo;
import modelos.Joption;
import modelos.Jugador;
import Other.Fuente;
import controladores.EquipoController;
import controladores.IdiomaController;
import controladores.MainController;

import javax.swing.UIManager;



public class CreacionEquipos extends JPanel {
	
	private IdiomaController traduccion = IdiomaController.getInstance(); 
	private JButton btnCerrarSesion, btnAddJugador, btnEliminarJugador, btnGuardarEquipo, btnEliminarEquipo, btnIrATacticas, btnCancelar, btnEditarJugador;
	private JTextField nombreEquipo,nombre,dorsal;
	private JFormattedTextField fecha_nacimiento;
	private JLabel lblNombreEquipo,lblEquipos,lblNombre,lblPosicion,lblSegundaPosicion,lblDorsal,lblFechaNacimiento;
	private JComboBox<Equipo> comboBoxEquipo;
	private JComboBox comboBoxPosicion, comboBox2Posicion;
	private ImageIcon fondoPanel;
	private Equipo equipoNuevo;
	private JTable tablaJugadores;
	private JScrollPane scrollPane;
	private int rowSelected;
	private int rowEditedJugador;
	private boolean btnJugador  = false;
	private String posicion, segundaPosicion;
	private JTextField camposTexto[] = new JTextField[3];
	private Demarcacion demarcaciones[]={
			new Demarcacion("delantero"),
			new Demarcacion("defensa"),
			new Demarcacion("lateral"),
			new Demarcacion("mediocentro"),
			new Demarcacion("portero") 
	};
	
	private Equipo equipoSeleccionado;
	private EquipoController equipoController=new EquipoController();
	private MainController mc = new MainController();
	private Fuente fuente = new Fuente();
	private Dimension dimensionPantalla;
	private JPanel panelCentrado;
	private Login loginPanel;

	
	private static int INICIOFILA=0;
	private static int FINALFILA=11;
	private JPanel panelCentral;

	/**
	 * Create the panel.
	 */
	public CreacionEquipos(Login loginPanel) {
		
		this.loginPanel = loginPanel;
		//if(Autentificacion.isAutentificado()==true){
			setLayout(null);
			//this.setBounds(0,0,1149,720);
			fondoPanel = new ImageIcon(getClass().getResource("/img/Fondodifuminado.jpg"));
			dimensionPantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			
			etiquetas();
			tablaJugadores();
			comboBox();
			jTextField();
			botones();
			JFormattedTextField();
			logoApp();
			panelCentrado();
		//}
		//else{
			//System.out.println("ERROR DE AUTENTIFICACION");
		//}
	}
	
	@Override
	public void paintComponent(Graphics g){
		Rectangle r = g.getClipBounds();
		   g.setColor(this.getBackground());
		   g.fillRect (r.x, r.y, r.width, r.height);
		   super.paintComponent(g);
		   g.drawImage (fondoPanel.getImage(), 0, 0, this.getWidth(),
				   this.getHeight(), this.getBackground(), this);
	}
	
	private void etiquetas(){
		lblNombreEquipo = new JLabel(traduccion.getTraduccion("nombre_equipo"));
		lblNombreEquipo.setBounds(490, 18, 110, 14);
		lblNombreEquipo.setFont(fuente.getFuente());
		lblNombreEquipo.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblNombreEquipo);
		
		lblEquipos = new JLabel(traduccion.getTraduccion("equipos"));
		lblEquipos.setBounds(817, 18, 45, 14);
		lblEquipos.setFont(fuente.getFuente());
		lblEquipos.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblEquipos);
		
		lblNombre = new JLabel(traduccion.getTraduccion("nombre_equ"));
		lblNombre.setBounds(108, 57, 51, 14);
		lblNombre.setFont(fuente.getFuente());
		lblNombre.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblNombre);
		
		lblPosicion = new JLabel(traduccion.getTraduccion("posicion_tit"));
		lblPosicion.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosicion.setBounds(237, 57, 134, 14);
		lblPosicion.setFont(fuente.getFuente());
		lblPosicion.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblPosicion);
		
		lblSegundaPosicion = new JLabel(traduccion.getTraduccion("posicion_sup"));
		lblSegundaPosicion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegundaPosicion.setBounds(393, 57, 151, 14);
		lblSegundaPosicion.setFont(fuente.getFuente());
		lblSegundaPosicion.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblSegundaPosicion);
		
		lblDorsal = new JLabel(traduccion.getTraduccion("dorsal"));
		lblDorsal.setHorizontalAlignment(SwingConstants.CENTER);
		lblDorsal.setBounds(556, 57, 151, 14);
		lblDorsal.setFont(fuente.getFuente());
		lblDorsal.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblDorsal);
		
		lblFechaNacimiento = new JLabel(traduccion.getTraduccion("fecha_nacimiento"));
		lblFechaNacimiento.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaNacimiento.setBounds(719, 57, 167, 14);
		lblFechaNacimiento.setFont(fuente.getFuente());
		lblFechaNacimiento.setForeground(UIManager.getColor("Button.highlight"));
		//add(lblFechaNacimiento);
	}
	
	private void comboBox(){
		comboBoxEquipo = new JComboBox();


		comboBoxEquipo.setFont(new Font("Georgia", Font.PLAIN, 13));
		reloadComboBoxEquipo();
		comboBoxEquipo.setBounds(916, 12, 142, 27);
		comboBoxEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxEquipo.getSelectedIndex()>=0){
					btnEditarJugador.setEnabled(false);
					btnEliminarJugador.setEnabled(false);
				}
				nombreEquipo.setText(comboBoxEquipo.getSelectedItem().toString());
				equipoSeleccionado = (Equipo)comboBoxEquipo.getSelectedItem();
				tablaJugadores.setModel(equipoSeleccionado);
				equipoController.setEquipo(equipoSeleccionado);
				refrescarTabla();
			}
		});
		comboBoxEquipo.setToolTipText(traduccion.getTraduccion("seleccione_equipo"));
		comboBoxEquipo.setAlignmentX(CENTER_ALIGNMENT);
		
		comboBoxEquipo.setBorder(null);
		//add(comboBoxEquipo);
		
		
		comboBoxPosicion = new JComboBox(demarcaciones);
		comboBoxPosicion.setFont(new Font("Georgia", Font.PLAIN, 13));
		comboBoxPosicion.setBackground(Color.WHITE);
		comboBoxPosicion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		
			}
		});
		comboBoxPosicion.setBounds(230, 80, 151, 27);
		comboBoxPosicion.setToolTipText(traduccion.getTraduccion("seleccion_pos"));
		comboBoxPosicion.setAlignmentX(CENTER_ALIGNMENT);
		//add(comboBoxPosicion);

		
		comboBox2Posicion = new JComboBox(demarcaciones);
		comboBox2Posicion.setFont(new Font("Georgia", Font.PLAIN, 13));
		comboBox2Posicion.setBackground(Color.WHITE);
		comboBox2Posicion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		comboBox2Posicion.setBounds(393, 80, 151, 27);
		comboBox2Posicion.setToolTipText(traduccion.getTraduccion("seleccion_segunda_pos"));
		comboBox2Posicion.setAlignmentX(CENTER_ALIGNMENT);
		//add(comboBox2Posicion);
	}
	
	private void reloadComboBoxEquipo(){
		//RECOGIENDO LOS EQUIPOS DEL USUARIO
		ArrayList<Equipo> equiposUsuario = new ArrayList<Equipo>();
				
		//CREACION PRIMER ITEM "NUEVO EQUIPO"
		equipoNuevo=new Equipo();
		equipoNuevo.setId(0);
		equipoNuevo.setNombre(traduccion.getTraduccion("nombre_equipo"));
		equipoSeleccionado = equipoNuevo;
	
		equipoController.setEquipo(equipoSeleccionado);
		equiposUsuario.add(equipoNuevo);
		equiposUsuario.addAll(Autentificacion.getUsuario().getEquipos());
	
		DefaultComboBoxModel modeloEquipo = new DefaultComboBoxModel(equiposUsuario.toArray());
		comboBoxEquipo.setModel(modeloEquipo);
		comboBoxEquipo.setSelectedItem(equipoNuevo);
		comboBoxEquipo.repaint();
		tablaJugadores.setModel(equipoSeleccionado);
	}
	
	private void jTextField(){
		nombreEquipo = new JTextField();
		nombreEquipo.setForeground(new Color(139, 0, 0));
		nombreEquipo.setFont(new Font("Georgia", Font.PLAIN, 13));
		nombreEquipo.setHorizontalAlignment(SwingConstants.CENTER);
		nombreEquipo.setText(traduccion.getTraduccion("insertar_nombre"));
		nombreEquipo.setBounds(603, 15, 160, 19);
		nombreEquipo.setToolTipText(traduccion.getTraduccion("inserta_nombre"));
		nombreEquipo.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		//add(nombreEquipo);
		nombreEquipo.setColumns(10);
		
		nombre = new JTextField();
		nombre.setForeground(new Color(139, 0, 0));
		nombre.setFont(new Font("Georgia", Font.PLAIN, 13));
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setBounds(58, 83, 160, 19);
		nombre.setToolTipText(traduccion.getTraduccion("introduce_nombre_jugador"));
		nombre.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		//add(nombre);
		nombre.setColumns(10);
		camposTexto[0]=nombre;

		dorsal = new JTextField();
		dorsal.setForeground(new Color(139, 0, 0));
		dorsal.setFont(new Font("Georgia", Font.PLAIN, 13));
		dorsal.setHorizontalAlignment(SwingConstants.CENTER);
		dorsal.setBounds(556, 83, 151, 19);
		dorsal.setToolTipText(traduccion.getTraduccion("dorsal_jugador"));
		dorsal.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		//add(dorsal);
		dorsal.setColumns(10);
		camposTexto[1]=dorsal;
	}
	
	private void JFormattedTextField(){

		fecha_nacimiento = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
		fecha_nacimiento.setForeground(new Color(139, 0, 0));
		fecha_nacimiento.setFont(new Font("Georgia", Font.PLAIN, 13));
		fecha_nacimiento.setHorizontalAlignment(SwingConstants.CENTER);
		fecha_nacimiento.setValue(new Date(0));
		fecha_nacimiento.setColumns(10);
		fecha_nacimiento.setBounds(726, 83, 160, 19);
		fecha_nacimiento.setToolTipText(traduccion.getTraduccion("fecha_nacimiento_jugador"));
		fecha_nacimiento.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		//add(fecha_nacimiento);
		camposTexto[2]=fecha_nacimiento;
	}
	
	private void botones(){
		btnAddJugador = new JButton(traduccion.getTraduccion("crear_jugador"));
		btnAddJugador.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnAddJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/addJugador.png")));
		btnAddJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnJugador==false){
					accionBotonAddJugador();
				}
				else{
					accionBotonEditarJugador();
					btnAddJugador.setText(traduccion.getTraduccion("crear_jugador"));
					btnAddJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/addJugador.png")));
					nombre.setBackground(Color.WHITE);
					nombre.setForeground(Color.BLACK);
					dorsal.setBackground(Color.WHITE);
					dorsal.setForeground(Color.BLACK);
					fecha_nacimiento.setBackground(Color.WHITE);
					fecha_nacimiento.setForeground(Color.BLACK);
					activarBotones();
					btnCancelar.setVisible(false);
				}
			}
		});
		btnAddJugador.setBounds(916, 80, 142, 27);
		btnAddJugador.setToolTipText(traduccion.getTraduccion("pulse_crear_jug"));
		//add(btnAddJugador);
		
		btnEditarJugador = new JButton(traduccion.getTraduccion("editar_jugador"));
		btnEditarJugador.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnEditarJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/editar.png")));
		btnEditarJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionVolcarDatosJugador();
			}
		});
		btnEditarJugador.setBounds(916, 112, 140, 27);
		btnEditarJugador.setToolTipText(traduccion.getTraduccion("pulse_editar_jug"));
		//add(btnEditarJugador);
		btnEditarJugador.setEnabled(false);
		
		btnEliminarJugador = new JButton(traduccion.getTraduccion("eliminar_jug"));
		btnEliminarJugador.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnEliminarJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/eliminarJugador.jpeg")));
		btnEliminarJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBotonEliminarJugador();
			}
		});
		btnEliminarJugador.setBounds(916, 143, 142, 27);
		btnEliminarJugador.setToolTipText(traduccion.getTraduccion("pulse_eliminar_jug"));
		btnEliminarJugador.setEnabled(false);
		//add(btnEliminarJugador);
		
		
		btnGuardarEquipo = new JButton(traduccion.getTraduccion("guardar_equipo"));
		btnGuardarEquipo.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnGuardarEquipo.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/guardar.png")));
		btnGuardarEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBotonGuardarEquipo();
			}
		});
		btnGuardarEquipo.setBounds(436, 367, 142, 32);
		btnGuardarEquipo.setToolTipText(traduccion.getTraduccion("pulse_guargar_equipo"));
		//add(btnGuardarEquipo);
		
		
		btnEliminarEquipo = new JButton(traduccion.getTraduccion("elim_equipo"));
		btnEliminarEquipo.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnEliminarEquipo.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/eliminar.png")));
		btnEliminarEquipo.setBackground(Color.RED);
		btnEliminarEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBotonEliminarEquipo();
			}
		});
		btnEliminarEquipo.setBounds(590, 367, 142, 32);
		btnEliminarEquipo.setToolTipText(traduccion.getTraduccion("pulse_elim_equipo"));
		//add(btnEliminarEquipo);
		
		btnIrATacticas = new JButton(traduccion.getTraduccion("ir_tacticas"));
		btnIrATacticas.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnIrATacticas.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/entrenador.png")));
		btnIrATacticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				accionBotonIrAtacticas();
			}
		});
		btnIrATacticas.setBounds(744, 368, 142, 31);
		btnIrATacticas.setToolTipText(traduccion.getTraduccion("pulse_ir_tacticas"));
		//add(btnIrATacticas);
		
		btnCancelar = new JButton(traduccion.getTraduccion("cancelar"));
		btnCancelar.setFont(new Font("Georgia", Font.PLAIN, 13));
		btnCancelar.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/atras.png")));
		btnCancelar.setToolTipText(traduccion.getTraduccion("pulse_cancelar_edicion_jug"));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarBotones();
				vaciadoCampos();
				nombre.setBackground(Color.WHITE);
				nombre.setForeground(Color.BLACK);
				dorsal.setBackground(Color.WHITE);
				dorsal.setForeground(Color.BLACK);
				fecha_nacimiento.setBackground(Color.WHITE);
				fecha_nacimiento.setForeground(Color.BLACK);
				btnCancelar.setVisible(false);
				btnJugador=false;
				btnAddJugador.setText(traduccion.getTraduccion("crear_jugador"));
				btnAddJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/addJugador.png")));
			}
		});
		btnCancelar.setBounds(282, 368, 142, 31);
		//add(btnCancelar);
		btnCancelar.setVisible(false);
		//add(btnCerrarSesion);
		
		btnCerrarSesion = new JButton(traduccion.getTraduccion("cerrar_sesion"));
		btnCerrarSesion.setBounds(58, 369, 161, 29);
		btnCerrarSesion.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/logout.png")));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBotonCerrarSesion();
			}
		});
		btnCerrarSesion.setFont(new Font("Georgia", Font.PLAIN, 13));
	}
	
	private void activarBotones(){
		btnEliminarJugador.setEnabled(true);
		btnEditarJugador.setEnabled(true);
		btnGuardarEquipo.setEnabled(true);
		btnIrATacticas.setEnabled(true);
		btnEliminarEquipo.setEnabled(true);
		comboBoxEquipo.setEnabled(true);
		nombreEquipo.setEnabled(true);
	}
	
	private void desactivarBotones(){
		btnEliminarJugador.setEnabled(false);
		btnEditarJugador.setEnabled(false);
		btnGuardarEquipo.setEnabled(false);
		btnIrATacticas.setEnabled(false);
		btnEliminarEquipo.setEnabled(false);
		comboBoxEquipo.setEnabled(false);
		nombreEquipo.setEnabled(false);
	}
	
	private void accionBotonAddJugador(){
		equipoController.addJugador(nombre.getText(), ((Demarcacion)comboBoxPosicion.getSelectedItem()).getId(), ((Demarcacion)comboBox2Posicion.getSelectedItem()).getId(), dorsal.getText(), fecha_nacimiento.getText());
		refrescarTabla();
		vaciadoCampos();
	}
	
	private void accionVolcarDatosJugador(){
		//CAMBIADO DE TEXTO EN BOTON ADD JUGADOOR
		btnAddJugador.setText(traduccion.getTraduccion("guardar_cambios"));
		btnAddJugador.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/guardar.png")));
		btnCancelar.setVisible(true);
		//MODIFICACION DE BOOLEANO
		btnJugador=true;
		rowEditedJugador = rowSelected;
		Color azul = new Color(00,00,255);
		//VOLCADO DE DATOS EN JTEXTFIELD'S A PARTIR DE LOS DATOS DEL JUGADOR RECOGIDO
		Jugador jugador = equipoController.obtenerJugador(rowSelected);
		nombre.setText(jugador.getNombre());
		nombre.setBackground(azul);
		nombre.setForeground(Color.WHITE);
		for(int a=0;a<demarcaciones.length;a++){
			if(demarcaciones[a].getId().equals(jugador.getDemarcacion())){
				comboBoxPosicion.setSelectedItem(demarcaciones[a]);
				break;
			}
		}
		for(int b=0;b<demarcaciones.length;b++){
			if(demarcaciones[b].getId().equals(jugador.getSegundaDemarcacion())){
				comboBox2Posicion.setSelectedItem(demarcaciones[b]);
				break;
			}
		}
		dorsal.setText(String.valueOf(jugador.getDorsal()));
		dorsal.setBackground(azul);
		dorsal.setForeground(Color.WHITE);
		fecha_nacimiento.setText(jugador.getFechaNacimiento());
		fecha_nacimiento.setBackground(azul);
		fecha_nacimiento.setForeground(Color.white);
		//DESACTIVADO DE BOTONES RESTANTES, SOLO SE PERMITE OPCION GUARDAR CAMBIOS
		desactivarBotones();
	}
	
	private void accionBotonEditarJugador(){
		//LLAMADA AL METODO MODIFICAR JUGADOR EN EQUIPOCONTROLLER
		equipoController.modificarJugador(nombre.getText(),String.valueOf(comboBoxPosicion.getSelectedItem()),String.valueOf(comboBox2Posicion.getSelectedItem()),Integer.parseInt(dorsal.getText()),fecha_nacimiento.getText(),rowEditedJugador);
		refrescarTabla();
		vaciadoCampos();
		btnJugador=false;
	}
	
	private void accionBotonEliminarJugador(){
		equipoController.eliminarJugador(rowSelected);
		refrescarTabla();
	}
	
	private void accionBotonGuardarEquipo(){
		
		if(equipoController.guardarEquipo(nombreEquipo.getText())==true) {
			reloadComboBoxEquipo();
			
			for(int a=0;a<comboBoxEquipo.getItemCount();a++){
				if(comboBoxEquipo.getItemAt(a).getId()==0){
					comboBoxEquipo.setSelectedIndex(comboBoxEquipo.getItemCount()-1);
					//comboBoxEquipo.setSelectedItem(comboBoxEquipo.getItemAt(a));
				}
			}
			nombreEquipo.setText("");
			vaciadoCampos();
			Joption.showMesageDialog(traduccion.getTraduccion("equipo_guardado_exito"), new Object[]{"Ok"});
		}
		else{
			equipoNuevo=new Equipo();
			equipoNuevo.setId(0);
			equipoNuevo.setNombre(traduccion.getTraduccion("nombre_equipo"));
			equipoSeleccionado = equipoNuevo;
			tablaJugadores.setModel(equipoSeleccionado);
			Joption.showMesageDialog(traduccion.getTraduccion("equipo_no_guardado"), new Object[]{"Ok"});
		}
		
	}
	
	private boolean accionBotonEliminarEquipo(){
		equipoController.eliminarEquipo(equipoSeleccionado);
		reloadComboBoxEquipo();
		nombreEquipo.setText("");
		tablaJugadores.setModel(equipoNuevo);
		refrescarTabla();
		return true;
	}
	
	private void accionBotonIrAtacticas(){
		equipoController.irAtacticas(this, loginPanel);
	}
	
	private void accionBotonCerrarSesion(){
		loginPanel.clearFields();
		mc.cambiaPanel(loginPanel);
	}
	
	private void tablaJugadores(){
		tablaJugadores = new JTable();
		tablaJugadores.setRowSelectionAllowed(true);
		tablaJugadores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rowSelected = tablaJugadores.getSelectedRow();
				btnEditarJugador.setEnabled(true);
				btnEliminarJugador.setEnabled(true);
				//System.out.println(rowSelected);
			}
		});
		
		tablaJugadores.setGridColor(SystemColor.controlShadow);
		tablaJugadores.setFont(new Font("Tahoma", Font.BOLD, 11));
		tablaJugadores.setFillsViewportHeight(true);
		tablaJugadores.setBackground(Color.WHITE);
		tablaJugadores.setFont(fuente.getFuente());
		tablaJugadores.setBorder(new LineBorder(new Color(128, 128, 128)));
		tablaJugadores.setModel(new Equipo());
		tablaJugadores.getColumnModel().getColumn(0).setResizable(false);
		tablaJugadores.getColumnModel().getColumn(1).setResizable(false);
		tablaJugadores.getColumnModel().getColumn(2).setResizable(false);
		tablaJugadores.getColumnModel().getColumn(3).setResizable(false);
		tablaJugadores.getColumnModel().getColumn(4).setResizable(false);
		tablaJugadores.setBounds(50, 300, 715, 192);
		tablaJugadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
		scrollPane = new JScrollPane(tablaJugadores);
		scrollPane.setViewportBorder(new LineBorder(Color.GRAY, 2));
		scrollPane.setBounds(58, 124, 828, 231);
		tablaJugadores.setToolTipText(traduccion.getTraduccion("pulse_editar_eliminar_jug"));
		//add(scrollPane);
		alineacionCentradaCeldas();
	}
	
	public JTable getTablaJugadores(){
		return tablaJugadores;
	} 
	
	public void alineacionCentradaCeldas(){
		DefaultTableCellRenderer AlinearTablaJugadores = new DefaultTableCellRenderer();
		AlinearTablaJugadores.setHorizontalAlignment(SwingConstants.CENTER);//ALINEACION CENTRADA
		tablaJugadores.getTableHeader().getColumnModel().getColumn(0).setCellRenderer(AlinearTablaJugadores);
		tablaJugadores.getTableHeader().getColumnModel().getColumn(1).setCellRenderer(AlinearTablaJugadores);
		tablaJugadores.getTableHeader().getColumnModel().getColumn(2).setCellRenderer(AlinearTablaJugadores);
		tablaJugadores.getTableHeader().getColumnModel().getColumn(3).setCellRenderer(AlinearTablaJugadores);
		tablaJugadores.getTableHeader().getColumnModel().getColumn(4).setCellRenderer(AlinearTablaJugadores);
		
	}
	
	
	private void refrescarTabla(){
		tablaJugadores.repaint();
		alineacionCentradaCeldas();
	}
	
	
	public void vaciadoCampos(){
		//VACIADO DE JTextField
		JTextField arrayJText[] = new JTextField[3];
		arrayJText[0] = nombre;
		arrayJText[1] = dorsal;
		arrayJText[2] = fecha_nacimiento;
		
		for(int a=0;a<arrayJText.length;a++){
			arrayJText[a].setText("");
		}
		
		//ASIGNACIÓN POR DEFECTO DE JCOMBOBOX
		comboBoxPosicion.setSelectedIndex(0);
		comboBox2Posicion.setSelectedIndex(0);
	}
	
	public void logoApp(){
		
		JLabel logoExe = new JLabel("");
		logoExe.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/LOGO_EXE.png")));
		logoExe.setBounds(87, 65, 989, 70);
		add(logoExe);
		
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/MyCoach.png")));
		logo.setBounds((int)dimensionPantalla.getWidth()-305, 65, 300, 90);
		add(logo);
		
		JLabel silueta = new JLabel("");
		silueta.setIcon(new ImageIcon(CreacionEquipos.class.getResource("/img/Silueta.png")));
		silueta.setBounds(6, 0, 207, 135);
		add(silueta);
		
		ImageIcon imagenBarra = new ImageIcon(getClass().getResource("/img/barraroja.png"));
		JLabel barra = new JLabel(""){
			@Override
			public void paintComponent(Graphics g){
				Rectangle r = g.getClipBounds();
				   g.setColor(this.getBackground());
				   g.fillRect (r.x, r.y, r.width, r.height);
				   super.paintComponent(g);
				   g.drawImage (imagenBarra.getImage(), 0, 0, this.getWidth(),
						   this.getHeight(), this.getBackground(), this);
			}
		};
		barra.setBounds(0, 65, (int)dimensionPantalla.getWidth(), 70);
		add(barra);			
	}
	
	private void panelCentrado(){
		panelCentral = new JPanel();
		panelCentral.setBounds((int)dimensionPantalla.getWidth()/2-544,(int)dimensionPantalla.getHeight()/2-215,1088,429);
		panelCentral.setLayout(null);
		panelCentral.setOpaque(false);
		//panelCentral.setBackground(new Color(0, 0, 0, 150));
		panelCentral.add(lblNombreEquipo);
		panelCentral.add(lblEquipos);
		panelCentral.add(lblNombre);
		panelCentral.add(lblPosicion);
		panelCentral.add(lblSegundaPosicion);
		panelCentral.add(lblDorsal);
		panelCentral.add(lblFechaNacimiento);
		panelCentral.add(comboBoxEquipo);
		panelCentral.add(comboBoxPosicion);
		panelCentral.add(comboBox2Posicion);
		panelCentral.add(nombreEquipo);
		panelCentral.add(nombre);
		panelCentral.add(dorsal);
		panelCentral.add(fecha_nacimiento);
		panelCentral.add(btnAddJugador);
		panelCentral.add(btnEliminarJugador);
		panelCentral.add(btnGuardarEquipo);
		panelCentral.add(btnEliminarEquipo);
		panelCentral.add(btnIrATacticas);
		panelCentral.add(btnCancelar);
		panelCentral.add(btnEditarJugador);
		panelCentral.add(btnCerrarSesion);
		panelCentral.add(scrollPane);
		//panelCentral.setBorder(new LineBorder(new Color(153, 51, 51), 7));

		add(panelCentral);
		
		
	}
}
