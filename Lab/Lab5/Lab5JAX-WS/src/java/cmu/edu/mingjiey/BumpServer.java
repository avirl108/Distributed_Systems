/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu.edu.mingjiey;

import java.math.BigInteger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author JY
 */
@WebService(serviceName = "BumpServer")
public class BumpServer {

    BigInteger big = new BigInteger("0");


    /**
     * Web service operation
     */
    @WebMethod(operationName = "bump")
    public boolean bump() {
        //TODO write your implementation code here:
        big = big.add(new BigInteger("1"));
        return true;
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "get")
    public BigInteger get() {
        //TODO write your implementation code here:
        return big;
    }

    
}
