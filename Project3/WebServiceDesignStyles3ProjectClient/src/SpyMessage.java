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
    
    public SpyMessage(Spy spy) {
        this.spy = spy;
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
    


}
