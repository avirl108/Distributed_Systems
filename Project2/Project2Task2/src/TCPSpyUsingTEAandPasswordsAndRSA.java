/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JY
 */
import java.io.*;
import java.math.BigInteger;
import javax.net.ssl.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import javax.net.*;

public class TCPSpyUsingTEAandPasswordsAndRSA {
        
	public static void main (String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		try{
            
            //code from TCPClient example
			int serverPort = 7886;
            InetAddress aHost = InetAddress.getLocalHost();
			s = new Socket(aHost, serverPort);    
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out = new DataOutputStream( s.getOutputStream());
            Scanner sc = new Scanner(System.in);                     

            //randominze a key for tea
            Random rnd = new Random();           
            BigInteger key = new BigInteger(16*8,rnd);   // this WILL be > 0

            // RSA to encrpty , n = moduls, e = public key
            BigInteger n = new BigInteger ("1050858130373254826221557907879365506617750486996497194476003"); 
            BigInteger e = new BigInteger ("65537");
        
            //encrpty tea key using public key and send to server
            BigInteger c = key.modPow(e, n);     
            out.write(c.toByteArray()); 
         
            // encrypt spy info by using Tea instance
            TEA spyTea = new TEA(key.toByteArray());
    
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
