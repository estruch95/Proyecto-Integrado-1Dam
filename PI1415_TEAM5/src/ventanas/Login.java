package ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import twitter.PanelTwitter;
import twitter.TwitterController;
import twitter4j.TwitterException;
import Other.Sha1;
import controladores.IdiomaController;
import controladores.LoginControlador;
import controladores.MainController;

import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Login extends JPanel {
	private JTextField emailField;
	private ImageIcon fondoPanel;
	private JPanel panelFormulario;
	private ImageIcon fondoPanelCentral;
	private JPasswordField passwordField;
	private URI url;
	JComboBox seleccionIdioma;
	JLabel lblIdioma;
	IdiomaController traduccion = IdiomaController.getInstance();
	private JLabel logoMyCoach;
	private JLabel label;
	private JLabel label_1;
	private JPanel panelCentral;
	private JButton btnLogin;
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	public Login(MainController main) {
		setBackground(Color.WHITE);
		setLayout(null);
		this.setBounds(0, 0, 1080, 720);
		
		seleccionIdioma = new JComboBox();
		
		seleccionIdioma.setModel(new DefaultComboBoxModel(new String[] {"Espa\u00F1ol", "English"}));
		
		if(IdiomaController.getAppLang()=="es") {
			seleccionIdioma.setSelectedIndex(0);
		}
		else {
			seleccionIdioma.setSelectedIndex(1);
		}
		
		seleccionIdioma.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(seleccionIdioma.getSelectedIndex()==0) {
					IdiomaController.setAppLang("es");
				}
				else {
					IdiomaController.setAppLang("en");
				}
				
				main.run();
			}
		});
		
		
		
		
		seleccionIdioma.setBounds((int)dimension.getWidth()-200, 45, 136, 20);
		add(seleccionIdioma);
		
		
		
		lblIdioma = new JLabel("Idioma/Language:");
		lblIdioma.setForeground(new Color(255, 255, 255));
		lblIdioma.setFont(new Font("Georgia", Font.BOLD, 18));
		lblIdioma.setBounds((int)dimension.getWidth()-380, 43, 174, 21);
		add(lblIdioma);
			TwitterController t = null;
		try {
			t = new TwitterController();
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fondoPanelCentral = new ImageIcon(getClass().getResource("/img/fondoPanelCentral.jpg"));

		panelCentral = new JPanel();
	
		panelCentral.setBounds((int)dimension.getWidth()/2-229, (int)dimension.getHeight()/2-300, 458, 500);
		add(panelCentral);
		panelCentral.setLayout(null);
		panelCentral.setOpaque(false);
	
		
		
		logoMyCoach = new JLabel("");
		logoMyCoach.setBounds(84, 27, 287, 97);
		panelCentral.add(logoMyCoach);
		logoMyCoach.setIcon(new ImageIcon(Login.class.getResource("/img/MyCoach.png")));
		//PanelTwitter panel = new PanelTwitter(t.getTwitter());
		//panel.setLocation(0, 0);
		//add(panel);
		
		panelFormulario = new JPanel() {
			/*@Override
			public void paintComponent(Graphics g){
				//super.paintComponent(g);
				//g.drawImage(fondoPanel.getImage(), 0, 0, null);
				
				Rectangle r = g.getClipBounds();
				   g.setColor(this.getBackground());
				   g.fillRect (r.x, r.y, r.width, r.height);
				   super.paintComponent(g);
				   g.drawImage (fondoPanelCentral.getImage(), 0, 0, this.getWidth(),
						   this.getHeight(), this.getBackground(), this);
				   
			}*/
		};
		panelFormulario.setBounds(0, 137, 458, 341);
		panelCentral.add(panelFormulario);
		panelFormulario.setBorder(new LineBorder(new Color(153, 51, 51), 7));
		panelFormulario.setLayout(null);
		panelFormulario.setBackground(new Color(0, 0, 0, 150));
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		passwordField.setBounds(213, 151, 185, 28);
		panelFormulario.add(passwordField);
		passwordField.setForeground(new Color(139, 0, 0));
		passwordField.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		btnLogin = new JButton(traduccion.getTraduccion("login"));
		btnLogin.setBounds(63, 221, 335, 39);
		panelFormulario.add(btnLogin);
		//btnLogin.setForeground(new Color(139, 0, 0));
		btnLogin.setFont(new Font("Georgia", Font.BOLD, 15));
		btnLogin.setIcon(new ImageIcon(Login.class.getResource("/img/login.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Llama al controlador para verificar usuario y contraseña.
				Login.this.checkLogin();

			}
		});
		btnLogin.setToolTipText(traduccion.getTraduccion("click_adelante"));
		btnLogin.setFocusable(false);
		//btnLogin.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		
		
		JLabel lblUsuario = new JLabel("Email:");
		lblUsuario.setBounds(63, 113, 115, 16);
		panelFormulario.add(lblUsuario);
		lblUsuario.setFont(new Font("Georgia", Font.BOLD, 20));
		lblUsuario.setForeground(new Color(255, 255, 255));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(63, 154, 120, 16);
		panelFormulario.add(lblPassword);
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Georgia", Font.BOLD, 20));
		
		emailField = new JTextField();
		emailField.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		emailField.setBounds(213, 110, 185, 28);
		panelFormulario.add(emailField);
		emailField.setForeground(new Color(139, 0, 0));
		emailField.setFont(new Font("Georgia", Font.PLAIN, 13));
		emailField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					Login.this.checkLogin();

				}
			}
		});
		emailField.setColumns(10);
		
				
				//Intento poner un enlace para que vaya a la pagina de registro.
				
				
		
				JLabel registroLabel = new JLabel(traduccion.getTraduccion("nuevo?_registrate_aqui"));
				registroLabel.setHorizontalAlignment(SwingConstants.CENTER);
				registroLabel.setBounds(73, 271, 335, 28);
				panelFormulario.add(registroLabel);
				registroLabel.setForeground(new Color(255, 255, 255));
				registroLabel.setFont(new Font("Georgia", Font.PLAIN, 26));
				
				JLabel lblInicioDeSesin = new JLabel(traduccion.getTraduccion("inicio_sesion"));
				lblInicioDeSesin.setHorizontalAlignment(SwingConstants.CENTER);
				lblInicioDeSesin.setForeground(Color.WHITE);
				lblInicioDeSesin.setFont(new Font("Georgia", Font.BOLD, 40));
				lblInicioDeSesin.setBounds(63, 22, 335, 45);
				panelFormulario.add(lblInicioDeSesin);
				
				try{
					url = new URI("http://auth-albafo.c9.io/Auth/public/registro");
				}catch(Exception e){
					
				}
				
				
				registroLabel.addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent evnt){
						 Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
					     setCursor(cursor);
					}
				});
				
				registroLabel.addMouseListener(new MouseAdapter(){
					public void mouseExited(MouseEvent evnt){
						 Cursor cursor = Cursor.getDefaultCursor(); 
					     setCursor(cursor);
					}
				});
				
				
				registroLabel.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent evnt){
					
						if(evnt.getClickCount()==1){
							
						    if (Desktop.isDesktopSupported()) {
						        try {
						          Desktop.getDesktop().browse(url);
						        } catch (IOException e) { System.out.println(e.getMessage()); }
						      }
						    
						}
					}
				});
		btnLogin.requestFocus();
		passwordField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					Login.this.checkLogin();
				}
			}
		});
		
		
		
		
		
		
		fondoPanel = new ImageIcon(getClass().getResource("/img/fondoPrincipal.jpg"));
		Ventana frame = new Ventana();
		

	}

	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		//g.drawImage(fondoPanel.getImage(), 0, 0, null);
		
		Rectangle r = g.getClipBounds();
		   g.setColor(this.getBackground());
		   g.fillRect (r.x, r.y, r.width, r.height);
		   super.paintComponent(g);
		   g.drawImage (fondoPanel.getImage(), 0, 0, this.getWidth(),
				   this.getHeight(), this.getBackground(), this);
		   
	}
	
	private void checkLogin() {
		
		
		LoginControlador controlador = new LoginControlador(emailField.getText(), passwordField.getPassword());
		controlador.checkLogin(emailField.getText(), passwordField.getPassword(), seleccionIdioma.getSelectedIndex(), this);
		

	}

	public void clearFields() {
		emailField.setText("");
		passwordField.setText("");
		
	}
}



	
	


	

