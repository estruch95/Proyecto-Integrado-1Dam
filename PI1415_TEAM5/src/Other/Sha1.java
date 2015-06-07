package Other;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sha1 {
	
	public static String encode(String msg) {
		java.security.MessageDigest d = null;
	    try {
			d = java.security.MessageDigest.getInstance("SHA-1");
			 d.reset();
			    d.update(msg.getBytes());
			    return byteArrayToHexString(d.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	   return null;
	}
	
	public static String byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
}
