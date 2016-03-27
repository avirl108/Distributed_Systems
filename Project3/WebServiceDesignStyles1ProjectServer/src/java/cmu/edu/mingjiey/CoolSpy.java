/*
 * Course: Distributed System
 * Project 3 Task 1
 * @author Jason Yang
 * Target: recieve spyOperation requests from client and return responses back to client
 */

package cmu.edu.mingjiey;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "CoolSpy")
public class CoolSpy {

    SpyList spyList = SpyList.getInstance();
    /**
     * add spy with name, title, location and password
     */
    @WebMethod(operationName = "addSpy")
    public String addSpy(@WebParam(name = "name") String name, @WebParam(name = "title") String title, @WebParam(name = "location") String location, @WebParam(name = "password") String password) {
        
        if (spyList.get(name) == null) {
            Spy spyInfo = new Spy(name,title, location, password); 
            spyList.add(spyInfo);
            return "Spy " + name + " was added to the list.";
        } else {
            return "the spy already exists in the spy list, no change is made to the spy list";
        }
        

    }

    /**
     * if spy exist, update spy with name, title, location and password
     */
    @WebMethod(operationName = "updateSpy")
    public String updateSpy(@WebParam(name = "name") String name, @WebParam(name = "title") String title, @WebParam(name = "location") String location, @WebParam(name = "password") String password) {
        
        if (spyList.get(name) != null) {
            Spy newSpy = new Spy (name, title, location, password);
            spyList.add(newSpy);
            return "spy info updated";
        } else {
            return "spy does not already exists in the spy list, no change is made";
        }
        
    }

    /**
     * check spy in spyList, if yes return spy name
     */
    @WebMethod(operationName = "getSpy")
    public String getSpy(@WebParam(name = "name") String name) {
        
        if (spyList.get(name) != null) {          
            
            return name;
        } else {
            return "No such spy";
        }
    }

    /**
     * delete spy in spyList
     */
    @WebMethod(operationName = "deleteSpy")
    public String deleteSpy(@WebParam(name = "name") String name) {
        
        if (spyList.get(name) != null) {          
            spyList.delete(spyList.get(name));
            return "Spy " + name + " was deleted from the list.";
        } else {
            return "No such spy";
        }
        
    }

    /**
     * get spyList in plain text
     * @return 
     */
    @WebMethod(operationName = "getList")
    public String getList() {
       
        return spyList.toString();
    }

    /**
     * get spyList in xml format
     * @return xml info
     */
    @WebMethod(operationName = "getListAsXML")
    public String getListAsXML() {
        

        return spyList.toXML();
    }


}
