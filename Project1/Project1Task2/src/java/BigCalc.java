/*
 * @author Jason Yang
 * Class: 95-702 Distributed Systems
 * Assignment: Project 1 Task 2
 * Object: Create a web app to do calculations
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/BigCalc"})
public class BigCalc extends HttpServlet {

     // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String result = null;
        PrintWriter out = response.getWriter();
        try {
            //get user inputs
            BigInteger x = new BigInteger((String)request.getParameter("x"));
            BigInteger y = new BigInteger((String)request.getParameter("y"));
            String op = request.getParameter("operation");
            
            //calculate result
            switch(op){
                case "add":
                    result ="x + y = " + x.add(y).toString();
                    break;
                case "multiply":
                    result ="x * y = " + x.multiply(y).toString();
                    break;  
                case "relativelyPrime":
                    BigInteger gcd = x.gcd(y);
                    if (gcd.equals(new BigInteger("1"))){
                        result = "x and y are relatively prime";
                    } else {
                        result = "x and y are not relatively prime";
                    }
                    break;
                case "mod":
                    result = "x mod y = " + x.mod(y).toString();
                    break;  
                case "modInverse":
                    result = "x^-1 mod y = " + x.modInverse(y).toString();
                    break;
                case "power":
                    result = "x ^ y = " + x.pow(y.intValue()).toString();
                    break;                                                             
            }
            
            //print out the result
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Result</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + result + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } 
        catch (NumberFormatException e){
            out.println("<html><title>Error</title></html>");
            out.println("<h1>Pleae Enter Integer Only </h1>");
            out.println("</html>");
        }
        catch (ArithmeticException e){
            out.println("<html><title>Error</title></html>");
            out.println("<h1>Negative exponent</h1>");
            out.println("</html>");
        }
        catch (Exception e){
            out.println("<html><title>Error</title></html>");
            out.println("<h1> Please try again </h1>");
            out.println("</html>");
        }
        finally {            
            out.close();
        }
    }



}
