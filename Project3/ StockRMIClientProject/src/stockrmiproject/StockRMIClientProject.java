package stockrmiproject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client side user interface for user to login and register for interested stocks
 * 
 * @author Gao
 */
public class StockRMIClientProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Read from the system in
        System.out.println("Enter user name:");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        
        StockRMI remoteServant;
        
        try {
            // find the remote server
            remoteServant = (StockRMI)Naming.lookup("//localhost:1099/Stock");
            System.out.println("Found server");
            
            // ask for the users to subscribe or unsubscribe stocks
            System.out.println("Enter S for subscribe or U for unsubscribe followed by the stock symbol of interest.\n" +
            "Enter ! to quit.");
        
            scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            
            while (!inputString.equals("!")) {

                String[] inputStringList = inputString.split(" ");
                String isSubscribe = inputStringList[0];
                String stockSym = inputStringList[1];
                
                // call the remote functions of the server side to subscribe and unsubscribe stocks for the user
                if (isSubscribe.equals("S")) {
                    remoteServant.subscribe(userName, stockSym);
                } else if (isSubscribe.equals("U")) {
                    remoteServant.unSubscribe(userName, stockSym);
                } else {
                    System.out.println("Please follow the correct input format");
                }
                
                System.out.println("Enter stock symbol and price or ! to quit.");
                scanner = new Scanner(System.in);
                inputString = scanner.nextLine();
            }
            
            // exit the client interface after the user typed "!"
            if (inputString.equals("!")) {
                
                remoteServant.deRegisterCallBack(userName); // call remote functions from the server side to exit the client
                System.exit(1);
          
            }          

        } catch (NotBoundException ex) {
            Logger.getLogger(StockRMIClientProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StockRMIClientProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(StockRMIClientProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
