package webservicedesignstyles2projectclient;

/**
 *
 * @author JY
 */


import java.io.StringReader;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;



public class SpyMessage {
    
    private Spy spy;
    private String command;
    
    
    public SpyMessage() {
        
    }
    
    public SpyMessage (Spy spy,String command ) {
       
        this.spy = spy;
        this.command = command;
             
    }
    
    public Document getDocument(String xmlString) {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document spyDoc = null;
        
        try {
            builder = factory.newDocumentBuilder();
            spyDoc = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch(Exception e) {            
            e.printStackTrace();           
        }
        return spyDoc;
              
    }
    
    // write request in XML format, according to command
    public String toXML() {
        String result = "";
        
        switch (command) {
            case "addSpy":
                result = "<operation>addSpy</operation>\n<spy>\n<name>" + spy.getName() + "</name>\n<spyTitle>"
                        + spy.getTitle() + "</spyTitle>\n<location>" + spy.getLocation() + "</location>\n<password>"
                        + spy.getPassword() + "</password>\n</spy>\n";
                break;
                
            case "updateSpy":
                result = "<operation>updateSpy</operation>\n<spy>\n<name>" + spy.getName() + "</name>\n<spyTitle>"
                        + spy.getTitle() + "</spyTitle>\n<location>" + spy.getLocation() + "</location>\n<password>"
                        + spy.getPassword() + "</password>\n</spy>\n";
                break;    
                
            case "getSpyAsXML":
                result = "<operation>getSpyAsXML</operation>\n<spy>\n<name>" + spy.getName() + "</name>\n<spyTitle>"
                        + spy.getTitle() + "</spyTitle>\n<location>" + spy.getLocation() + "</location>\n<password>"
                        + spy.getPassword() + "</password>\n</spy>\n";
                break;
                
            case "deleteSpy":
                result = "<operation>deleteSpy</operation>\n<spy>\n<name>" + spy.getName() + "</name>\n<spyTitle>"
                        + spy.getTitle() + "</spyTitle>\n<location>" + spy.getLocation() + "</location>\n<password>"
                        + spy.getPassword() + "</password>\n</spy>\n";
                break;
                
            case "getList":
                result = "<operation>getList</operation>\n<spy>\n<name></name>\n<spyTitle>"
                        + "</spyTitle>\n<location></location>\n<password></password>\n</spy>\n";
                break;
                
            case "getListAsXML":
                result = "<operation>getListAsXML</operation>\n<spy>\n<name></name>\n<spyTitle>"
                        + "</spyTitle>\n<location></location>\n<password></password>\n</spy>\n";

            default:
                break;
        }
        
        result = "<spyMessage>\n" +result +"</spyMessage>";
        return result;
    }

}
