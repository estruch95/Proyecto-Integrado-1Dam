package controladores;

import javax.swing.JPanel;

import ventanas.Login;
import ventanas.Ventana;

public class MainController {
	//private static JPanel panel = new Login();
	private static Ventana frame = new Ventana();
	
	public void run(){

		frame.setContentPane(new Login(this));
		frame.setVisible(true);
	}
	
	public static void cambiaPanel(JPanel name){
		frame.setVisible(false);
		//panel = name;
		frame.setContentPane(name);
		frame.setVisible(true);
	}
	
}
