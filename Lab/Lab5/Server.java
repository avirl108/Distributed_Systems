
// Server side SSL 
import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.security.*;

public class Server {
    
    // hold the name of the keystore containing public and private keys
    static String keyStore = "myCoolkeystore.jks";
    
    // password of the keystore (same as the alias)
    static char keyStorePass[] = "sesame".toCharArray();
    public static void main(String args[]) {
        
        int port = 6502;
        SSLServerSocket server;
        
        try {
            // get the keystore into memory
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keyStore), keyStorePass);
            
            // initialize the key manager factory with the keystore data
            KeyManagerFactory kmf = 
            KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks,keyStorePass);
            
            // initialize the SSLContext engine
            // may throw NoSuchProvider or NoSuchAlgorithm exception
            // TLS - Transport Layer Security most generic
            
            SSLContext sslContext = SSLContext.getInstance("TLS");
            
            // Inititialize context with given KeyManagers, TrustManagers, 
            // SecureRandom defaults taken if null
            
            sslContext.init(kmf.getKeyManagers(), null, null);
            
            // Get ServerSocketFactory from the context object
            ServerSocketFactory ssf = sslContext.getServerSocketFactory();
            //  Now like programming with normal server sockets               
            ServerSocket serverSocket = ssf.createServerSocket(port);
            
            System.out.println("Accepting secure connections");
            
            Socket client = serverSocket.accept();
            System.out.println("Got connection");
            
            BufferedWriter out = new BufferedWriter(
                                                    new OutputStreamWriter(
                                                                           client.getOutputStream()));
            BufferedReader in = new BufferedReader(
                                                   new InputStreamReader(
                                                                         client.getInputStream()));
            String msg = in.readLine();
            System.out.println("Got message " + msg);
            out.write("Hello client\n");
            out.flush();
            in.close();
            out.close();     
        }
        catch(Exception e) {
            System.out.println("Exception thrown " + e);
        }
    }
}
