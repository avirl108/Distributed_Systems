package stockrmiproject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client side callback class for listening remote stock updates and notifications
 * 
 * @author Gao
 */
public class StockPriceCallBack extends UnicastRemoteObject implements Notifiable {
    
    /**
     * Construct function
     */
    public StockPriceCallBack() throws RemoteException {
        
    }
    
    public static void main(String[] args) {
        
        System.out.println("Enter user name:");
        
        // get user name from the terminal
        Scanner scanner = new Scanner(System.in); 
        String userName = scanner.nextLine();
        
        try {
            System.out.println("Looking up the server in the registry.");
            StockRMI remoteServant = (StockRMI)Naming.lookup("//localhost:1099/Stock"); // find remote server
            
            System.out.println("Creating a callback object to handle calls from the server.");
            StockPriceCallBack callBackObject = new StockPriceCallBack(); // create a callback object for the user
            
            System.out.println("Registering the callback with a name at the server.");
            remoteServant.registerCallBack(callBackObject, userName); // register the user to the server with its callback object
            
            System.out.println("Callback handler for StockDealer ready");            

        } catch (NotBoundException ex) {
            Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * notify - implement the notify function from the Notifiable interface to notify user about the price update
     * 
     * @param stockSym stock name whose price has been updated
     * @param price the updated price
     * 
     * @throws RemoteException 
     */
    @Override
    public void notify(String stockSym, double price) throws RemoteException {
        
        System.out.println("An update for stock " + stockSym);
        System.out.println("The new price for stock " + stockSym + " is " + price);
    }

    /**
     * exit - implement the exit function from the Notifiable interface to exit from the callback client
     * 
     * @throws RemoteException 
     */
    @Override
    public void exit() throws RemoteException {
        
        try{
            
            // exit the client
            UnicastRemoteObject.unexportObject(this, true);
            System.out.println("StockPriceCallBack exiting.");
            
        } catch(Exception e){ 
            
            System.out.println("Exception thrown" + e);
            
        }       
        
    }
    
}
