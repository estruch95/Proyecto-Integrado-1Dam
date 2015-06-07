package Other;

import java.awt.Font;
import java.awt.Color;

public class Fuente {

	private Font fuente;
	private Color colorFuente;
	
	public Fuente() {
		fuente = new Font("Georgia",Font.BOLD,12);
		colorFuente = new Color(224,224,224);
	}
	
	public Font getFuente(){
		return fuente;
	}
	
	public Color getFontColor(){
		return colorFuente;
	}

}