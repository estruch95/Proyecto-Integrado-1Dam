package mycoach;

import controladores.MainController;

public class MyCoach {
	
	static MainController controladorPrincipal;
	
	public static void main(String[] args) {
	    controladorPrincipal = new MainController();
		controladorPrincipal.run();
	}
}
