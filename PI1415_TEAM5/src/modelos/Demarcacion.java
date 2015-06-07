package modelos;

import controladores.IdiomaController;

public class Demarcacion {

	private String id;
	
	public Demarcacion(String indice) {
		this.id = indice;
	}

	public String toString(){
		return IdiomaController.getInstance().getTraduccion(this.id);
	}
	
	public String getId() {
		return id;
	}
}
