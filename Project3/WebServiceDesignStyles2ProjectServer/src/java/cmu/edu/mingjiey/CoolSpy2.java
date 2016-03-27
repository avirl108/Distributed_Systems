/*
 * Course: Distributed System
 * Project 3 Task 2
 * @author Jason Yang
 * Target: recieve spyOperation requests from client and return responses back to client
 */
package cmu.edu.mingjiey;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.w3c.dom.*;

@WebService(serviceName = "CoolSpy2")
public class CoolSpy2 {
    SpyList spyList = new SpyList();

    /**
     * parse client xml request and return result to client
     */
    @WebMethod(operationName = "spyOperation")
    public String spyOperation(@WebParam(name = "argument") String argument) {
        String result ="";
        
        SpyMessage spm = new SpyMessage();
        
        Document spyDoc = spm.getDocument(argument);
        spyDoc.getDocumentElement().normalize();
        
        // parse xml 
        NodeList nl = spyDoc.getElementsByTagName("name"); 
        Node n = nl.item(0);
        String name = n.getTextContent();
        
        String operation = spyDoc.getElementsByTagName("operation").item(0).getTextContent(); 
        String title = spyDoc.getElementsByTagName("spyTitle").item(0).getTextContent();
        String location = spyDoc.getElementsByTagName("location").item(0).getTextContent();
        String password = spyDoc.getElementsByTagName("password").item(0).getTextContent();

        switch (operation) {
            case "addSpy" :
                if (spyList.get(name) == null) {
                    Spy spyInfo = new Spy(name,title, location, password); 
                    spyList.add(spyInfo);
                    result =  "Spy " + name + " was added to the list.";
                } else {
                    result = "the spy already exists in the spy list, no change is made to the spy list";
                }
                break;
                
            case "updateSpy" :
                if (spyList.get(name) != null) {
                    Spy newSpy = new Spy (name, title, location, password);
                    spyList.add(newSpy);
                    result = "spy info updated";
                } else {
                    result = "spy does not already exists in the spy list, no change is made";
                }
                break;
                
            case "getSpyAsXML":
                if (spyList.get(name) != null) {
                    result = spyList.get(name).toXML();
                } else {
                    result = "spy does not already exists in the spy list";
                }
                break;
                
            case "deleteSpy":
                if (spyList.get(name) != null) {          
                    spyList.delete(spyList.get(name));
                    result = "Spy " + name + " was deleted from the list.";
                } else {
                    result = "No such spy";
                }
                break;
                
            case "getList":
                result = spyList.toString();
                break;
                
            case "getListAsXML":
                result = spyList.toXML();
                break;
            
            default :
                result = "cannot recognize operation";
                                
        }

        return result;
    }

}

