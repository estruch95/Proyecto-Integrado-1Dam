package ventanas;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagen extends JLabel {
	
	private int widthIco, heightIco;
	private ImageIcon icono;
	private ImageIcon iconoSelected;
	

	
	public Imagen(String src, int w, int h) {
		this.icono =new ImageIcon(getClass().getResource(src));
		
		this.setIcon(icono);
		
		this.setBounds(0, 0, w, h);
		
		this.widthIco=w;
		this.heightIco=h;
	}
	
	public Imagen(String src, String srcSelected, int w, int h) {
		
		this.icono =new ImageIcon(getClass().getResource(src));
		this.iconoSelected=new ImageIcon(getClass().getResource(srcSelected));
		
		this.setIcon(icono);
		
		this.setBounds(0, 0, w, h);
		
		this.widthIco=w;
		this.heightIco=h;
		

	}

	public void cambiarPosicion(int x, int y) {
		
		x= x - this.widthIco / 2;
		y= y - this.heightIco / 2;
		this.setLocation((int) (x), (int) (y));
		
		//this.setLocation((int) (x), (int) (y));
		
		this.repaint();
	}
	
	public void seleccionar() {
		this.setIcon(iconoSelected);
	}
	
	public void deseleccionar() {
		this.setIcon(icono);
	}
	

	

	

	
}
