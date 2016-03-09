/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coolclient;

/**
 *
 * @author JY
 */
public class CoolClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(foo(900.00));
        System.out.println(purchase(900, "kayak", 4));
    }

    private static double foo(double x) {
        edu.cmu.andrew.mingjiey.CoolService_Service service = new edu.cmu.andrew.mingjiey.CoolService_Service();
        edu.cmu.andrew.mingjiey.CoolService port = service.getCoolServicePort();
        return port.foo(x);
    }

    private static String purchase(int pid, java.lang.String name, int qty) {
        edu.cmu.andrew.mingjiey.CoolService_Service service = new edu.cmu.andrew.mingjiey.CoolService_Service();
        edu.cmu.andrew.mingjiey.CoolService port = service.getCoolServicePort();
        return port.purchase(pid, name, qty);
    }
    
}
