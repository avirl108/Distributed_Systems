/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.mingjiey;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author JY
 */
@WebService(serviceName = "CoolService")
public class CoolService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "purchase")
    public String purchase(@WebParam(name = "pid") int pid, @WebParam(name = "name") String name, @WebParam(name = "qty") int qty) {
        //TODO write your implementation code here:
        System.out.println("purchase orperation hit");
        return "you purchased " + name;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "foo")
    public double foo(@WebParam(name = "x") double x) {
        //TODO write your implementation code here:
        return x * 2.0;
    }


}
