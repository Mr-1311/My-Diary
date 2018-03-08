package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha {

	public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	public static String hashNsalt(String userName, String password) throws NoSuchAlgorithmException {
		
		password = sha1(password) + userName;
		password = sha1(password);
				
		return sha1(password);
	}
	
}
