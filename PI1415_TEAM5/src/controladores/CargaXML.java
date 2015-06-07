package controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class CargaXML{
	


public CargaXML(){
	
cargaXml();	
}

public ArrayList<String[][][]> cargaXml(){
	   //Se crea un SAXBuilder para poder parsear el archivo
    SAXBuilder builder = new SAXBuilder();
    URL url = getClass().getResource("/conf/CargaPosicionesFinal.xml");
    File xmlFile = new File(url.getPath());
	ArrayList<String[][][]> tacticas = new ArrayList<String[][][]>();

    try{
        //Se crea el documento a traves del archivo
        Document document = (Document) builder.build( xmlFile );
 
        //Se obtiene la raiz 'tacticas'
        Element rootNode = document.getRootElement();
 
        //Se obtiene la lista de hijos de la raiz 'tacticas'
        List list = rootNode.getChildren( "tactica" );
 
        //Se recorre la lista de hijos de 'tacticas'
        for ( int i = 0; i < list.size(); i++ ){
            //Se obtiene el elemento 'tactica'
        	 Element tactica = (Element) list.get(i);
 
            //Se obtiene el atributo 'nombre' que esta en el tag 'tacticas'
        	 String nombreTactica = tactica.getAttributeValue("nombre");
        
            //System.out.println( "Tactica: " + nombreTactica );
 
            //Se obtiene la lista de hijos del tag 'tactica'
        	 List lista_posiciones = tactica.getChildren();
	 
           // System.out.println( "\tDemarcacion\t\t\t\tTipo\t\t\tValor" );
        	String[][][] tacticaAttr=new String[2][7][3];
            //Se recorre la lista de posiciones
	         	for ( int j = 0; j < lista_posiciones.size(); j++ ){
                //Se obtiene el elemento 'posiciones'
	         		Element posiciones = (Element)lista_posiciones.get( j );
	         		
	         		List lista_pos = posiciones.getChildren();
	         		//Se recorre la lista de pos
	         			for ( int k = 0; k < lista_pos.size(); k++ ){
			            	//Se obtiene el elemento 'pos'
			                Element pos = (Element)lista_pos.get( k );
			 
			               	//Se obtienen los valores que estan entre los tags '<demarcacion></demarcacion>'
              
			                String demarcacion = pos.getChildTextTrim("demarcacion");
			                //Se obtiene el valor que esta entre los tags '<x></x>'
			                String posX = pos.getChildTextTrim("x");
			                //Se obtiene el valor que esta entre los tags '<y></y>'
			                String posY = pos.getChildTextTrim("y");
			                String tacticaJugador[] = new String[3];

			                tacticaAttr[1][k][0] = demarcacion;
			                tacticaAttr[1][k][1] = posX;
			                tacticaAttr[1][k][2] = posY; 
			               
			              
			               
			              
			               			                
	         			}
	         			tacticaAttr[0][0][0]=nombreTactica;
	         			tacticas.add(tacticaAttr);
	         			
        	
	         	}
	         	tacticas.get(i)[0][0][0]=nombreTactica;
        }    
    }catch ( IOException io ) {
        System.out.println( io.getMessage() );
    }catch ( JDOMException jdomex ) {
        System.out.println( jdomex.getMessage() );
    }
	return tacticas;

}
}