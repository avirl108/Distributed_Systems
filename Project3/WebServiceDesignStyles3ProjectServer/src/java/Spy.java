/**
 * Spy class to store spy info and spy methods
 * @author JY
 */
class Spy {

    private String name; 
    private String title; 
    private String location; 
    private String password;

    public String getPassword() { 
        return password;
    }
    
    public void setPassword(String password) { 
        this.password = password;
    }
    
    public String getLocation() { 
        return location;
    }
    
    public String getName() { 
        return name;
    }
    
    public String getTitle() { 
        return title;
    }
    
    public void setLocation(String location) { 
        this.location = location;
    }
    
    public void setName(String name) { 
        this.name = name;
    }
    
    public void setTitle(String title) { 
        this.title = title;
    }
    
    public Spy(String name, String title, String location, String password) { 
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = password; 
    }
    
    public Spy(String name, String title, String location) { 
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = ""; 
    }
    
    public Spy(String name) { 
        this.name = name; this.title = ""; this.location = ""; this.password = "";
    }
    
    public Spy() { 
        this.name = ""; this.title = ""; this.location = ""; this.password = "";
    }
    
    // return spy info in xml format
    public String toXML() {
        StringBuffer xml = new StringBuffer();
        xml.append("<spy>");
        xml.append("<name>" + name + "</name>"); xml.append("<spyTitle>" + title + "</spyTitle>"); xml.append("<location>" + location + "</location>"); xml.append("<password>" + password + "</password>"); xml.append("</spy>");
        return xml.toString();
    }
    
    // return spy info in plain text
    public String toResult() {
        String result = "Name: " + this.name+" Title: " + this.title+ " Location: " + this.location;
        return result;
    }
    
    public static void main(String args[]) {
        Spy s = new Spy("james","spy", "Pittsburgh", "james"); 
        System.out.println(s);
    } 
}
