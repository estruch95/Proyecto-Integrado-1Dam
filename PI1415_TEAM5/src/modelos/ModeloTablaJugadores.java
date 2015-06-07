package modelos;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaJugadores extends DefaultTableModel{
	
	private static final int COLUMNAS = 5;
	private static final int FILAS = 12;
	
	private static String columnas[] = new String[]{"Nombre","Posicion","Segunda posicion","Dorsal","Fecha Nacimiento"};
	private boolean columnasEditables[] = new boolean[] {false, false, false, false, false};

	private static String jugadores[][] = new String[][]{
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
	};
		
	public boolean isCellEditable(int row, int column) {
		return columnasEditables[column];
	}
	
	public ModeloTablaJugadores() {
		super(jugadores,columnas);
	}
	
	private static void inicializaJugadores(){
		for(int a=1;a<FILAS;a++){
			for(int b=0;b<COLUMNAS;b++){
				jugadores[a][b] = null;
			}
		}
	}
	
	private static void contenidoJugadores(){
		for(int a=0;a<FILAS;a++){
			for(int b=0;b<COLUMNAS;b++){
				System.out.print(jugadores[a][b]+" ");
			}
			System.out.println();
		}
	}
	
	public String[][] getJugadores(){
		return jugadores;
	}
	
	/*
	public static void main(String[] args) {
		inicializaJugadores();
		contenidoJugadores();
	}
	*/
}
