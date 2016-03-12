/*
 * @author Jason Yang
 * Course: Distributed Sytems
 * Project2Task2: transfer information using symmertric key and asymmetric key cryptography
 * Target: provide a server for Sean to recieve info from spy
 */

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.math.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPSpyCommanderUsingTEAandPasswordsAndRSA {
    static String publicKey;
    static int logCount;
    //code from TCPServer example
    public static void main (String args[]) {
        try{
            int serverPort = 7886; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);           
            System.out.println("Waiting for spies to visit...");
            logCount = 1;
            while(true) {
                    Socket clientSocket = listenSocket.accept();
                    Connection c = new Connection(clientSocket);
            }
        } catch(IOException e) {
            System.out.println("Listen socket:"+e.getMessage());
       }
    }
    
}



class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    byte[] userKey, userId, userPW, userLocation;
    String password;
     
    //initialize spies info
    static Spy spy0 =new Spy("seanb","1A2A2BB2BCC5831B9B5E605D74104F0F","Commander","-79.945289, 40.44431, 0.00000");
    static Spy spy1 = new Spy("mikem","1F8E2D97453EEAF71999DF3ECE272537","spy","-79.945289, 40.44431, 0.00000");
    static Spy spy2 = new Spy("joem","0E91B4FE2A2F6BAA344F7F45AA3BAFC6","spy","-79.945289, 40.44431, 0.00000");
    static Spy spy3 = new Spy("jamesb","00FCB2BD2A239FEAAF4B4FAF6A7BE735","spy","-79.945289, 40.44431, 0.00000");

    
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());
            // initialized kml             
            createKml();
            this.start();
        } catch(IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }
    
    
    public void run() {
        try { 
             
            userKey = new byte[100];
            int keys = in.read(userKey);
            //d = private key, n = moduls, c = enrpted tea key
            BigInteger c = new BigInteger(buffer(keys));
            BigInteger n = new BigInteger ("1050858130373254826221557907879365506617750486996497194476003"); 
            BigInteger d = new BigInteger ("411174680518811869765161808317391045060812411068824975777553");
            
            //decrpt userKey
            BigInteger spyKey= c.modPow(d, n);
//            System.out.println(":c: " + c);
//            System.out.println(":clear: " + spyKey);
                       
            // use server's symmetric key to decrypt the encrypted data got from client
            TEA tea = new TEA(spyKey.toByteArray());

            // get username
            userId = new byte[100];
            in.read(userId);
            String username = new String(tea.decrypt(userId));
            
            // get password
            userPW = new byte[100];
            in.read(userPW);
            String pw = new String(tea.decrypt(userPW));
            PasswordHash s = new PasswordHash();
            password = s.encrpt(username, pw);
            
            // get location
            userLocation = new byte[100];
            in.read(userLocation);
            String location = new String(tea.decrypt(userLocation));

            // count spy log attempts        
            String output = "Got visit " + TCPSpyCommanderUsingTEAandPasswordsAndRSA.logCount;
            TCPSpyCommanderUsingTEAandPasswordsAndRSA.logCount++;

            // check whether key is ascii
            CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
            boolean isAscii = asciiEncoder.canEncode(username);
            
            final String successMessage = "Thank you. Your location was securely transmitted to Intelligence Headquarters";
            
            if (isAscii) {
                // check user id and password match
                if (username.equals(spy1.getId()) && password.equals(spy1.getPassword())) {
                    output = output + " from mikem";
                    spy1.setLocation(location);
                    out.write(successMessage.getBytes());
                    createKml();
                } else if (username.equals(spy2.getId()) && password.equals(spy2.getPassword())) {
                    output = output + " from joem";
                    spy2.setLocation(location);
                    out.write(successMessage.getBytes());
                    createKml();
                } else if (username.equals(spy3.getId()) && password.equals(spy3.getPassword())) {
                    output = output + " from jamesb";
                    spy3.setLocation(location);
                    out.write(successMessage.getBytes());
                    createKml();
                } else {
                    output = output + " from " + username +". Illegal Password attempt. This may be an attack.";
                    out.write("Not a valid user-id or password".getBytes());
                }
                
            } else {
                output = output +" illegal symmetric key used. This may be an attack.";
                // closes the socket
                clientSocket.close();
            }
            
            // print out the result to Sean
            System.out.println(output);

        } catch (EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:"+e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } finally { 
            try {
                clientSocket.close();
            } catch (IOException e){
            /*close failed*/}
        }
    }
    
    // change buffer into bytearray
    public byte[] buffer(int x) {
        byte[] tmp = new byte[x];
        for(int i = 0; i < x; i++) {
                tmp[i] = userKey[i];
        }
        return tmp;
    }
    
    // creat kml file for google pro
    public static void createKml() {
        try {
                PrintWriter writer = new PrintWriter("SecretAgents.kml", "UTF-8");
                writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                writer.println("<kml xmlns=\"http://earth.google.com/kml/2.2\">");
                writer.println("<Document>");
                writer.println("<Style id=\"style1\">");
                writer.println("<IconStyle>");
                writer.println("<Icon>");
                writer.println("<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>");
                writer.println("</Icon>");
                writer.println("</IconStyle>");
                writer.println("</Style><Placemark>");
                writer.println("<name>" + spy0.getId() + "</name>");
                writer.println("<description>" + spy0.getTitle() + "</description>");
                writer.println("<styleUrl>#style1</styleUrl>");
                writer.println("<Point> <coordinates>" + spy0.getLocation() + "</coordinates> </Point>");
                writer.println("</Placemark>");
                writer.println("<Placemark>");
                writer.println("<name>" + spy1.getId() + "</name>");
                writer.println("<description>" + spy1.getTitle() + "</description>");
                writer.println("<styleUrl>#style1</styleUrl>");
                writer.println("<Point> <coordinates>" + spy1.getLocation() + "</coordinates> </Point>");
                writer.println("</Placemark>");
                writer.println("<Placemark>");
                writer.println("<name>" + spy2.getId() + "</name>");
                writer.println("<description>" + spy2.getTitle() + "</description>");
                writer.println("<styleUrl>#style1</styleUrl>");
                writer.println("<Point> <coordinates>" + spy2.getLocation() + "</coordinates> </Point>");
                writer.println("</Placemark>");
                writer.println("<Placemark>");
                writer.println("<name>" + spy3.getId() + "</name>");
                writer.println("<description>" + spy3.getTitle() + "</description>");
                writer.println("<styleUrl>#style1</styleUrl>");
                writer.println("<Point> <coordinates>" + spy3.getLocation() + "</coordinates> </Point>");
                writer.println("</Placemark>");
                writer.println("</Document>");
                writer.println("</kml>");
                writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
        }
    }
    
    
}
