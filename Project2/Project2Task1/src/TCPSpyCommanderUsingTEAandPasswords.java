/*
 * @author Jason Yang
 * Course: Distributed Sytems
 * Project2Task1: transfer information using symmertric key cryptography
 * Target: provide a server for Sean to recieve info from spy
 */

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPSpyCommanderUsingTEAandPasswords {
    static String publicKey; //get sean's symmetric key
    static int logCount;    //count spy log times
    
    //code from TCPServer example
    public static void main (String args[]) {
        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            
            //get symmetric key
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            Scanner reader = new Scanner(System.in);
            publicKey = reader.nextLine().trim();
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
    byte[] userId, userPW, userLocation;
    String password;
      

    //initialize spy info   
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
    
    
    public void run(){
        try { 
                       
            // use server's symmetric key to decrypt the encrypted data got from client
            TEA tea = new TEA(TCPSpyCommanderUsingTEAandPasswords.publicKey.getBytes());
            
            // get username
            userId = new byte[100];
            in.read(userId);
            String username = new String(tea.decrypt(userId));
//            System.out.println(username);
            
            // get password
            userPW = new byte[100];
            in.read(userPW);
            String pw = new String(tea.decrypt(userPW));
//          System.out.println(pw);
            PasswordHash s = new PasswordHash();
            password = s.encrpt(username, pw);
//          System.out.println(password);
            
            // get location
            userLocation = new byte[100];
            in.read(userLocation);
            String location = new String(tea.decrypt(userLocation));
//            System.out.println(location);
 
            //count spy log attempts        
            String output = "Got visit " + TCPSpyCommanderUsingTEAandPasswords.logCount;
            TCPSpyCommanderUsingTEAandPasswords.logCount++;

            // determine whether the decrypted userId is legal ascii string
            CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
            boolean isAscii = asciiEncoder.canEncode(username);
            
            final String successMessage = "Thank you. Your location was securely transmitted to Intelligence Headquarters";
            
            if (isAscii){
                //checking if the suer is valid - if so updating their location
                if(username.equals(spy1.getId()) && password.equals(spy1.getPassword())) {
                    output = output + " from mikem";
                    spy1.setLocation(location);
                    out.write(successMessage.getBytes());
                    createKml();
                } else if(username.equals(spy2.getId()) && password.equals(spy2.getPassword())) {
                    output = output + " from joem";
                    spy2.setLocation(location);
                    out.write(successMessage.getBytes());
                    createKml();
                } else if(username.equals(spy3.getId()) && password.equals(spy3.getPassword())) {
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
                //closes the socket
                clientSocket.close();
            }
            
            // print out the result to Sean
            System.out.println(output);

        } catch (EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {
            System.out.println("readline:"+e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } finally{ 
            try {
                clientSocket.close();
            } catch (IOException e){
            /*close failed*/}
        }
    }
    
    
    //creat kml file for google pro
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

