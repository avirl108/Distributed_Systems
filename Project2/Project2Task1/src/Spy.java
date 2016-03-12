/*
 * Record Spy information
 */

/**
 *
 * @author JY
 */
public class Spy{
    private String id;
    private String password;
    private String title;
    private String location;
    
    //constructor
    Spy(String id, String password, String title, String location){
        this.id = id;
        this.password = password;
        this.title = title;
        this.location = location;
    }

    //getter and setter for each parameters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
        


}