/*
 * @author Jason Yang
 * Class: 95-702 Distributed Systems
 * Assignment: Project 1 Task 3
 * Object: Create a web app to check whether a string is palindrome
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Palin", urlPatterns = {"/Palin"})
public class Palin extends HttpServlet {
    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               
        // get the subject parameter if it exists
        String input= (String) request.getParameter("userInput");
        //determine 
        String result;      
        if (isPalindrome(input)) {
            result = "a palindrome";
        } else {
            result = "not a palindrome";
        }
      
        //print out the result
        PrintWriter out = response.getWriter();
        // if user use android mobile, provide an appropriate doctype
        String ua = request.getHeader("User-Agent");
                
        if (ua != null && ua.indexOf("Android") != -1) {
            out.println("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }          
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Result</title>");            
        out.println("</head>");
        out.println("<body>"); 
        out.println("<h1>\"" + input + "\" is " +result + "</h1>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }  
        
    public boolean isPalindrome(String s) {
        //empty is palindrome
        if (s == null || s.length() == 0) {
            return true;
        }
        //compare the first and last char one by one, 
        int front = 0;
        int end = s.length() - 1;
        while (front < end) {   
            
            if (front == s.length()) {   
                return true; 
            }
            //disregard punctuation and white space
            while (front < s.length() && !isvalid(s.charAt(front))){ 
                front++;
            }
            //disregard punctuation and white space
            while (end >= 0 && ! isvalid(s.charAt(end))) {               
                end--;
            }
            //disregard case
            if (Character.toLowerCase(s.charAt(front)) != Character.toLowerCase(s.charAt(end))) {
                break;
            } else {
                front++;
                end--;
            }
        }

        return end <= front; 
    }
    //only letter or number are counted
    private boolean isvalid (char c) {
        return Character.isLetter(c) || Character.isDigit(c);
    }
                
}
        
        
      

    

