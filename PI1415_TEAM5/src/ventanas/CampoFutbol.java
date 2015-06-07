package ventanas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import modelos.Jugador;
import modelos.Tactica;
import modelos.TacticaJugador;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CampoFutbol extends JPanel {

	
	
	
	
	//InterfazApp iApp = new InterfazApp();

	private ImageIcon campoFutbol;
	
	private ArrayList<TacticaJugador> jugadoresEncampo = new ArrayList<TacticaJugador>();
	
	public final static int ANCHO=700;
	public final static int ALTO=450;

	
	public CampoFutbol() {
		setBorder(new LineBorder(new Color(139, 0, 0), 2));
		
		
		campoFutbol = new ImageIcon(getClass().getResource("/img/estadio3.jpg"));
		setSize(campoFutbol.getIconWidth(),campoFutbol.getIconHeight());
		
		//Añadimos listeners del mouse
		setLayout(null);
		
		//this.repaint();
		
		addMouseListener(new MouseListener() {
		
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			
				
				//System.out.println(":MOUSE_CLICK_EVENT:");
				
				//System.out.println(":MOUSE_CLICK_EVENT  Position X(): "+p.getX()+" Position Y(): "+p.getY());
				//img.cambiarPosicion((int)p.getX(), (int)p.getY());
				
				for(int i=0; i<jugadoresEncampo.size(); i++) {
					if(jugadoresEncampo.get(i).getJugador().isSelected()) {
						Point p=e.getPoint();
						jugadoresEncampo.get(i).cambiarXY((int)p.getX(), (int)p.getY());
						jugadoresEncampo.get(i).getJugador().cambiarPosicion((int)p.getX(), (int)p.getY());
						jugadoresEncampo.get(i).getJugador().setSelected(false);
						jugadoresEncampo.get(i).getJugador().deseleccionar();
						
					}
				}
			}
		});

	}


	@Override
	protected void paintComponent(Graphics g){
		
		Dimension d = getSize();
		g.drawImage(campoFutbol.getImage(), 0, 0, d.width, d.height, null);
		super.paintComponents(g);
		
	}
	
	public void redibujarFichas(Tactica tacticaCombobox){
		this.removeAll();
		this.jugadoresEncampo.clear();
		this.repaint();
		ArrayList<TacticaJugador> tacticasJug=tacticaCombobox.getTacticasJugadores();
		for(int i=0; i<tacticasJug.size();i++) {
			Jugador jugador=tacticasJug.get(i).getJugador();
			int x = tacticasJug.get(i).getX();
			int y = tacticasJug.get(i).getY();
			
			if(tacticasJug.get(i).isTitular()) {
				this.add(jugador); //Le metemos la imagen del jugador en el campo de fútbol.
				this.jugadoresEncampo.add(tacticasJug.get(i));
				jugador.cambiarPosicion(x, y);
				jugador.setCampoFutbol(this);
			}
		}
		this.repaint();
		
	}
	
	public  int numSelectedFichas() {
		int numSelected=0;
		for(int i=0; i<jugadoresEncampo.size(); i++) {
			if(jugadoresEncampo.get(i).getJugador().isSelected()) {
				numSelected++;
			}
		}
		return numSelected;
	}
	
	
	public void jugadorSeleccionado() {
		InterfazApp panelPadre = (InterfazApp) this.getParent().getParent();
		panelPadre.cambioSeleccionTabla();
	}


	
}
