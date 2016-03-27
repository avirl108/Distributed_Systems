/*
 * Course: Distributed System
 * Project 3 Task 1
 * @author Jason Yang
 * Target: send spy requests to sever and recieve server responses
 */

package webservicedesignstyles1projectclient;

public class WebServiceDesignStyles1ProjectClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(getList());
        System.out.println(getListAsXML());
        addSpy("mikem","spy","Pittsburgh","sesame");
        addSpy("joem","spy","North Hills","xyz"); 
        addSpy("seanb","spy commander","South Hills","abcdefg"); 
        addSpy("jamesb","spy","Adelaide","sydney"); 
        addSpy("adekunle","spy","Pittsburgh","secret"); 
        addSpy("Michael Jordan", "spy", "North Caroline", "23");
        System.out.println(getList()); 
        System.out.println(getListAsXML());
        updateSpy("mikem", "super spy", "Pittsburgh","sesame"); 
        System.out.println(getListAsXML());
        String result = getSpy("jamesb"); 
        System.out.println(result);
        deleteSpy("jamesb"); result = getSpy("jamesb"); 
        System.out.println(result);
    }

    private static String addSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.addSpy(name, title, location, password);
    }

    private static String deleteSpy(java.lang.String name) {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.deleteSpy(name);
    }

    private static String getList() {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.getList();
    }

    private static String getListAsXML() {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.getListAsXML();
    }

    private static String getSpy(java.lang.String name) {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.getSpy(name);
    }

    private static String updateSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        cmu.edu.mingjiey.CoolSpy_Service service = new cmu.edu.mingjiey.CoolSpy_Service();
        cmu.edu.mingjiey.CoolSpy port = service.getCoolSpyPort();
        return port.updateSpy(name, title, location, password);
    }
    
}
