/*
 * @author Jason Yang
 * Class: 95-702 Distributed Systems
 * Assignment: Project 1 Task 1
 * Object: Create a web app to calculate hash value
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;


@WebServlet(urlPatterns = {"/ComputeHashes"})
public class ComputeHashes extends HttpServlet {
     // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        //get user input
        String userInput = request.getParameter("userInput");
        String hashChoice = request.getParameter("hashChoice");
        String hex = null;
        String base = null;
        
        try{
            MessageDigest md;
            if(hashChoice == "MD5"){
                md = MessageDigest.getInstance("MD5");
            }else {
                md = MessageDigest.getInstance("SHA1");
            }
            // calculate both MD5 or SHA encryption
            byte[] hashBytes = userInput.getBytes();  
            byte[] thedigest = md.digest(hashBytes);
            //tranfer the encrypted byte value into base 64 string
            base = new BASE64Encoder().encode(thedigest);
            hex = getHexString(thedigest).toUpperCase();
           
          
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComputeHashes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ComputeHashes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //print out the result
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Result</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hashes of the String: " + userInput + "</h1>");
        out.println("<h1>" + hashChoice +" (Hex): " + hex + "</h1>");
        out.println("<h1>" + hashChoice +" (Base 64): " + base + "</h1>");
        out.println("</body>");
        out.println("</html>");

    }

    //return a hex string from the web site "Reals's how to"
    public String getHexString(byte[] b) throws Exception{   
      String result = "";
      for (int i=0;i<b.length;i++){
      result+=Integer.toString((b[i] &0xff)+0x100,16).substring(1);
      }
      return result;
    }    

}
