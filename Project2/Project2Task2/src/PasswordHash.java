import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class PasswordHash {
    //store userid and their salts
    public static HashMap<String, String> salts = new HashMap<String, String>();

    //initialize 
    public PasswordHash(){
        salts.put("seanb", "salt");
        salts.put("mikem", "salt1");
        salts.put("joem", "salt2");
        salts.put("jamesb", "salt3");
        
    }
    
    //project1task1, use md5 to get hex value
    public String encrpt(String username, String pw) throws Exception {
        MessageDigest md;

        String hex = null;
        try{
            md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = (pw+ salts.get(username)).getBytes();  
            byte[] thedigest = md.digest(hashBytes);
//            System.out.println(pw + salts.get(username));

            hex = getHexString(thedigest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
        }
        
        return hex;
    }

    //return a hex string from the web site "Reals's how to"
    public static String getHexString(byte[] b) throws Exception{   
      String result = "";
      for (int i=0;i<b.length;i++){
      result+=Integer.toString((b[i] &0xff)+0x100,16).substring(1);
      }
      return result;
    } 
    
}