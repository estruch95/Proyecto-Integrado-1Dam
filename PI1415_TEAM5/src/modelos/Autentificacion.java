package modelos;

public class Autentificacion {
	
	private static boolean autentificado = false;
	private static int id = 0;
	private static Usuario usuario = null;

	
	public Autentificacion(int idUsuario){
		id = idUsuario;
		Usuario usuarioLogeado = Usuario.load(id);
		autentificado = true;
		usuario = usuarioLogeado;
	}
	
	public static Usuario getUsuario(){
		if(autentificado){
			return usuario;
		}else{
			return null;
		}
	}
	
	//Getters y setters generados por eclipse.

	public static boolean isAutentificado() {
		return autentificado;
	}

	public static void setAutentificado(boolean autent) {
		autentificado = autent;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int idUsuario) {
		id = idUsuario;
	}


}
