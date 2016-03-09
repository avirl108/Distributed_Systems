// CalculatorServant.java        
// A Remote object class that implements Calculator. 
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;
public class BumperServant extends UnicastRemoteObject implements Bumper {
    BigInteger big = new BigInteger("0");
    public BumperServant() throws RemoteException {
    }

    public boolean bump() throws RemoteException {
        
        big = big.add(BigInteger.valueOf(1));
        return true;
        
    }
    
    public BigInteger get() throws RemoteException {
        return big;
    }

}