/*
 * Course: Distributed System
 * Project 3 Task 3
 * @author Jason Yang
 * Target: recieve requests from client and return responses back to client
 */

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;


@WebServlet(name = "SpyListCollection", urlPatterns = {"/SpyListCollection/*"})
public class SpyListCollection extends HttpServlet {

    SpyList spyList = new SpyList();

    /**
     * Handles the HTTP <code>PUT</code> method.
     * receive client addSpy request and add spy to spyList
     * 201 is OK
     * 405 is already exist
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        // read client xml request
        BufferedReader br = new BufferedReader(request.getReader());  
        StringBuffer xmlBuffer = new StringBuffer();    
        String xmlString = "";          
        while((xmlString = br.readLine()) != null) {  
               xmlBuffer.append(xmlString);  
        }  
        xmlString = xmlBuffer.toString(); 

        // parse xml 
        SpyMessage sm = new SpyMessage();
        Document spyDoc = sm.getDocument(xmlString);
        spyDoc.getDocumentElement().normalize();
        String name = spyDoc.getElementsByTagName("name").item(0).getTextContent();
        
        // check spyList contain spy if yes 405, else add spy into spyList (201)
        if (spyList.get(name) != null) {
            response.setStatus(405);
        } else { 
            String title = spyDoc.getElementsByTagName("spyTitle").item(0).getTextContent();
            String location = spyDoc.getElementsByTagName("location").item(0).getTextContent();
            String password = spyDoc.getElementsByTagName("password").item(0).getTextContent();
            Spy s = new Spy(name, title, location, password);
            spyList.add(s);
            response.setStatus(201);
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * receive client updateSpy request and update spy in spyList
     * 200 is OK
     * 404 is not found
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // read client xml request
        BufferedReader br = new BufferedReader(request.getReader());  
        StringBuffer xmlBuffer = new StringBuffer();    
        String xmlString = "";          
        while((xmlString = br.readLine()) != null) {  
               xmlBuffer.append(xmlString);  
        }  
        xmlString = xmlBuffer.toString(); 
       
        // parse xml
        SpyMessage sm = new SpyMessage();
        Document spyDoc = sm.getDocument(xmlString);
        spyDoc.getDocumentElement().normalize();
        String name = spyDoc.getElementsByTagName("name").item(0).getTextContent();
        
        // check spyList contain spy. if not 404, else update spy into spyList (200)
        if (spyList.get(name) == null) {
            response.setStatus(404);
        } else { 
            String title = spyDoc.getElementsByTagName("spyTitle").item(0).getTextContent();
            String location = spyDoc.getElementsByTagName("location").item(0).getTextContent();
            String password = spyDoc.getElementsByTagName("password").item(0).getTextContent();
            Spy s = new Spy(name, title, location, password);
            spyList.add(s);
            response.setStatus(200);
        }
    }
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     * receive client deleteSpy request and delete spy in spyList
     * 200 is OK
     * 404 is not found
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // get spy name from ruqest
        String name = (request.getPathInfo()).substring(1);
        
        // check whether spy is in spyList, if yes delete(200), else not found(404)   
        if (spyList.get(name) != null) {
            spyList.delete(spyList.get(name));
            response.setStatus(200);
        } else {
            response.setStatus(404);
        }
        
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * receive client request of xml or plain text of spy info
     * 200 is OK
     * 404 is not found
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String result = "";
        // get spy name from ruqest
        String name = (request.getPathInfo()).substring(1);
        // get request header to decide result type
        String header = request.getHeader("Accept");

        // check whether to get spy info or spyList info 
        // if name is not null, get spy info
        if (!name.equals("")) {
            if (spyList.get(name) == null) {
                response.setStatus(404);
                return;
            } else {
                if (header.equals("text/xml")) {
                result = spyList.get(name).toXML();
                }
                if (header.equals("text/plain")) {
                result = spyList.get(name).toResult();
                }
            }
        // if name is null, get spyList info
        } else {
            if (header.equals("text/xml")) {
                result = spyList.toXML();
            }
            if (header.equals("text/plain")) {
                result = spyList.toString();
            }
        }
        // set OK (200) for response and result 
        response.setStatus(200);
        response.getWriter().write(result);
    }
}