package controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class IdiomaController  {

	private static IdiomaController idioma=null;
	private static String idiomaKey="es"; //Idioma por defecto

	
	private Properties prop;
	
	
	public IdiomaController(String key) throws IOException {
		prop = new Properties();
		String propFileName = key+"_lang.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("conf/"+propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
 
	}
	
	
	
	public static IdiomaController getInstance(String key) {
		if(idioma!=null && idiomaKey==key) {
			return idioma;
		}
		
		try {
			return new IdiomaController(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static IdiomaController getInstance() {
		
		return getInstance(idiomaKey);
		
	}
	
	public static void setAppLang(String key) {
		idiomaKey=key;
	}
	
	public String getTraduccion(String property){
		if(prop.getProperty(property)!=null) {
			return prop.getProperty(property);
		}
		else return property;
	}



	public static String getAppLang() {
		// TODO Auto-generated method stub
		return idiomaKey;
	}
	
}
