
import java.math.BigInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JY
 */
public class JAXCLient {
    
    public static void main(String[] args) {
        
        BigInteger ctr = new BigInteger("0");
        BigInteger n = new BigInteger("10000");

        
         //calculate time
        long start = System.currentTimeMillis();
        while (!ctr.equals(n)) {
            bump();
            ctr = ctr.add(BigInteger.valueOf(1));
            
            System.out.println(ctr);
        }
        long stop = System.currentTimeMillis();



        System.out.println("Time Consumed: " + (stop - start));
    }

    private static boolean bump() {
        cmu.edu.mingjiey.BumpServer_Service service = new cmu.edu.mingjiey.BumpServer_Service();
        cmu.edu.mingjiey.BumpServer port = service.getBumpServerPort();
        return port.bump();
    }

    private static BigInteger get() {
        cmu.edu.mingjiey.BumpServer_Service service = new cmu.edu.mingjiey.BumpServer_Service();
        cmu.edu.mingjiey.BumpServer port = service.getBumpServerPort();
        return port.get();
    }
    
}
