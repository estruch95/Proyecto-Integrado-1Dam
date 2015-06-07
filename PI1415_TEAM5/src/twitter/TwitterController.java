package twitter;

import javax.swing.JOptionPane;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.Scanner;
 
public class TwitterController
{
	private Twitter twitter;
	
    public TwitterController() throws TwitterException
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        //Claves myCoachTeam; Tokens
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("jsp43rYgF44zC1FjyxhoR74v3")
            .setOAuthConsumerSecret("NDXEi8C3aN8epwtaT3kkIKsKGalwcJMa8D7V7M2MqjsekLLkri")
            .setOAuthAccessToken("3254008799-6Td7HbVH5Xgs8DCHoHmQi1YiPg6wG2MKkpN70I1")
            .setOAuthAccessTokenSecret("yzZK7eEkVuOW1FZwW1yDk96WAvIHeOow3aIu8VVhmQ6As");
        twitter = new TwitterFactory(cb.build()).getInstance();
         
        //Listado de ultimos tweets escritos
        //El paging sirve para decir el numero maximo de tweets que quieres recuperar
        //Paging pagina = new Paging();
        //pagina.setCount(50);
         
        //Recupera como maximo 50 tweets escritos por ti
        //ResponseList<Status> listado = twitter.getUserTimeline(pagina);
        //for (int i = 0; i < listado.size(); i++)
            //System.out.println(listado.get(i).getText());
         
        //Recupera como maximo los ultimos 50 tweets de tus tablon de novedades
        //listado = twitter.getHomeTimeline(pagina);
        //for (int i = 0; i < listado.size(); i++)
            //System.out.println(listado.get(i).getText());
         
        //Actualizar tu estado
        
        //String s;
        
        //Scanner entrada = new Scanner(System.in);
         
        //System.out.print("Escribe un Tweet: ");
         
       
        //s = entrada.nextLine();
         
        //System.out.println("Has escrito: "+s);
	
        
       //Status tweetEscrito = twitter.updateStatus(""+s); 
       
    }

	public Twitter getTwitter() {
		return twitter;
	}
   
    
}