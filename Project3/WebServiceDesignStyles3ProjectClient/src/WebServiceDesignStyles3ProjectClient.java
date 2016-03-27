/*
 * Course: Distributed System
 * Project 3 Task 3
 * @author Jason Yang
 * Target: send spy requests to sever and recieve server responses
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceDesignStyles3ProjectClient {
    
    public static void main(String[] args) {
    
        System.out.println("Begin main");
        Spy spy1 = new Spy("mikem","spy", "Pittsburgh","sesame");
        Spy spy2 = new Spy("joem","spy", "Philadelphia","obama");
        Spy spy3 = new Spy("seanb","spy commander", "Adelaide","pirates"); 
        Spy spy4 = new Spy("jamesb","007", "Boston","queen");
        
        System.out.println(doPut(spy1));  // 201
        System.out.println(doPut(spy2));  // 201
        System.out.println(doPut(spy3));  // 201
        System.out.println(doPut(spy4));  // 201
        
        System.out.println(doDelete("joem")); // 200 
        spy1.setPassword("Doris"); 
        System.out.println(doPost(spy1)); // 200
        
        System.out.println(doGetListAsXML()); // display xml 
        System.out.println(doGetListAsText()); // display text
        
        System.out.println(doGetSpyAsXML("mikem")); // display xml 
        System.out.println(doGetSpyAsText("joem")); // 404
        
        System.out.println(doGetSpyAsXML("mikem")); // display xml 
        System.out.println(doPut(spy2)); // 201 
        System.out.println(doGetSpyAsText("joem")); // display text 
        System.out.println("End main");

    }
    
    
    // send addSpy request to server
    // 201 is OK 405 means spy was already in the list.
    private static int doPut(Spy spy) {
        int status = 0;
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/" + spy.getName());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(spy.toXML());
            out.close();
            // get response code
            status = conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return status;
    }


    // send delete request to server
    // 200 is OK. 404 is not found.
    private static int doDelete(String name) {
        int status = 0;
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/" +name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("DELETE");
            // get response code
            status = conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    
    // send update request to server
    // 200 is OK. 404 is not found. 
    private static int doPost(Spy spy) {
        int status = 0;
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(spy.toXML());
            out.close();
            // get response code
            status = conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return status;
    }

    // send spyList request
    // return each spy info in a String
    private static String doGetListAsText() {
        String result = "";
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("GET");
            // set string for sever to know response type
            conn.setRequestProperty("Accept", "text/plain");
            // get response code
            int status = conn.getResponseCode();
            // if not OK(200), return respose code
            if (status != 200) { 
                return Integer.toString(status);
            }
            // read response and concatinate to a string
            String line="";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return result;
    }
    
    // send spyList request
    // return each spy info in a xml format
    private static String doGetListAsXML() {
        String result = "";
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("GET");
            // set xml for sever to know response type
            conn.setRequestProperty("Accept", "text/xml");
            // get response code
            int status = conn.getResponseCode();
            // if not OK(200), return respose code
            if (status != 200) { 
                return Integer.toString(status);
            }
            // read response and concatinate to a string
            String line="";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return result;
    }
    
    // send spyXML info request to server with spy name
    // return spyXML info
    private static String doGetSpyAsXML(String name) {
        String result = "";
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/" +name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("GET");
            // set xml for sever to know response type
            conn.setRequestProperty("Accept", "text/xml");
            // get response code
            int status = conn.getResponseCode();
            // if not OK(200), return respose code
            if (status != 200) { 
                return Integer.toString(status);
            }
            // read response and concatinate to a string
            String line="";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return result;
    }

    
    // send spy info request to server with spy name
    // return spy info
    private static String doGetSpyAsText(String name) {
        String result = "";
        try {
            // set up url path
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection/" +name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set request method
            conn.setRequestMethod("GET");
            // set string for sever to know response type
            conn.setRequestProperty("Accept", "text/plain");
            // get response code
            int status = conn.getResponseCode();
            // if not OK(200), return respose code
            if (status != 200) { 
                return Integer.toString(status);
            }
            // read response and concatinate to a string
            String line="";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }
        return result;
    }


}

