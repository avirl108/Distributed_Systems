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
 * Client side interface for dealers to update prices for stocks
 * 
 * @author Gao
 */
public class StockRMIClientPriceUpdate extends UnicastRemoteObject {
    
    /**
     * Construct function
     * 
     * @throws RemoteException 
     */
    public StockRMIClientPriceUpdate() throws RemoteException {
    
    }
    
    public static void main(String[] args) {
        
        // Read from the system in
        System.out.println("Enter stock symbol and price or ! to quit.");
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        
        while (!inputString.equals("!")) {
            
            String[] inputStringList = inputString.split(" ");
            String inputStockSym = inputStringList[0];
            double inputPrice = Double.valueOf(inputStringList[1]);
            
            try {
                // find the server
                StockRMI remoteServant = (StockRMI)Naming.lookup("//localhost:1099/Stock");
                System.out.println("Found server");
                
                // call remote functions to update the price for the stock
                remoteServant.stockUpdate(inputStockSym, inputPrice);
                
            } catch (NotBoundException ex) {
                Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(StockRMIClientPriceUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Enter stock symbol and price or ! to quit.");
            scanner = new Scanner(System.in);
            inputString = scanner.nextLine();        
        }
            
    }
    
    
}
