/*
 * Target: Create a client server for spies to send encryptied information
 */

/**
 *
 * @author JY
 */ 
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPSpyUsingTEAandPasswords {
        
    public static void main (String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try{
            
            //code from TCPClient example
            int serverPort = 7896;
            InetAddress aHost = InetAddress.getLocalHost();
            s = new Socket(aHost, serverPort);    
            DataInputStream in = new DataInputStream( s.getInputStream());
            DataOutputStream out = new DataOutputStream( s.getOutputStream());
            Scanner sc = new Scanner(System.in);

            // read spy symmetric key 
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            String spyKey = sc.nextLine().trim();

            // encrypt spy info by using Tea instance
            TEA spyTea = new TEA(spyKey.getBytes());

            // read spy Id
            System.out.println("Enter your ID:");
            String spyId = sc.nextLine();
            byte[] encryptedId = spyTea.encrypt(spyId.getBytes());
            out.write(encryptedId);

            // read spy password
            System.out.println("Enter your Password:");
            String spyPassword = sc.nextLine();
            byte[] encryptedPassword = spyTea.encrypt(spyPassword.getBytes());
            out.write(encryptedPassword);

            // read spy location
            System.out.println("Enter your location:");
            String spyLocation = sc.nextLine();
            byte[] encryptedLocation = spyTea.encrypt(spyLocation.getBytes());
            out.write(encryptedLocation);

            // recieve sever response
            byte[] resultByte = new byte[100];
            in.read(resultByte);
            System.out.println(new String(resultByte)) ;

        }catch (UnknownHostException e){
            System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){
            System.out.println("readline:"+e.getMessage());
        }finally {
            if(s!=null){ 
                try {
                    s.close();
                }catch (IOException e){
                    System.out.println("close:"+e.getMessage());
                }
            }
        }   
    }


}


