/*
 * 
 * @author Jason Yang
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to SMBC web site and then screen scraping the HTML that is
 * returned in order to fabricate an image URL.
 */

package SMBC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SearchPicturesModel {

    private String pictureTag; // The user input of search
    private String pictureURL; // The URL of image
    private String photographerTag;//The photographer of the picture

    /**
     * This fucntion will return a img url for the user search on their devices
     * 
     * @param searchTag  get user input
     * @param deviceType determine user interface
     */
    public String searchPictureUrl(String searchTag, boolean deviceType) {

        pictureTag = searchTag;
        String response;
        String imageId;
        String searchname = "";
        String imageList;
        int imageSize;
        
        //get web tag
        String[]splited = searchTag.split("\\s+");
        for (int i = 0; i < splited.length; i++){
           //when searchTag contains "'", the sever need transformed to "%27"
            if(splited[i].contains("'")){
                splited[i] = splited[i].replace("'", "%27");
            }
            //change from xxx xxx to xxx+xxx
            searchname += splited[i];
            searchname += "+";
        }
        searchname = searchname.substring(0, searchname.length()-1);
        
        // Create a URL for the desired page
        String smbcURL = "http://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + searchname;    
        response = fetch(smbcURL);
        
        //Get image list  
        if (response.contains("images/bigpic/")){
            int cutLeft = response.indexOf("images/bigpic/");
            String urlRight = response.substring(cutLeft);
            int cutRight = urlRight.indexOf(".jpg",14 );
            imageList = urlRight.substring(14,cutRight);


            //Get total size of pics
            //imageList = "acwo35"
            imageSize = Integer.valueOf(imageList.substring(4));
            //create a random number of the pics
            int id =  1 + (int)(Math.random()*(imageSize - 1));
            String ids = String.valueOf(id);
            //Get image id
            imageId =imageList.substring(0, 4) + ids;
            //Get photographer name
            int cutPhoto = response.indexOf(imageId);
            String photoRight = response.substring(cutPhoto);
            int cutPhotoLeft = photoRight.indexOf("&copy;");
            String photoRightTwo = photoRight.substring(cutPhotoLeft);
            int cutPhotoRight = photoRightTwo.indexOf("</a>",0);
            String photographer = photoRightTwo.substring(7, cutPhotoRight);
            photographerTag = photographer;

            // Setting the picture URL according to device type
            if (deviceType) {
                // set the 250 piexel wide for image
                pictureURL = "https://ids.si.edu/ids/deliveryService?max_w=250&amp;id=http%3A%2F%2Fnationalzoo.si.edu/scbi/migratorybirds/featured_photo/images/bigpic/" +imageId +".jpg";

            } else {
                // set the 500 piexel wide for image
                 pictureURL = "https://ids.si.edu/ids/deliveryService?max_w=500&amp;id=http%3A%2F%2Fnationalzoo.si.edu/scbi/migratorybirds/featured_photo/images/bigpic/" +imageId +".jpg";
            }
        }else {
            //if no image, send back to error page
            pictureURL = null;
        }
        
        return pictureURL;
      
    }



    /*
     * Return the picture tag.  I.e. the search string.
     * 
     * @return The tag that was used to search for the current picture.
     */
    public String getPictureTag() {
        return (pictureTag);
    }
    
     /*
     * Return the photographer tag.  I.e. the search string.
     * 
     * @return The tag that was used to show the photogragher of picture.
     */
    public String getPhotographerTag() {
        return (photographerTag);
    }
    

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try ( 
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                String str;
                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                }
            }
        } catch (IOException e) {
            System.out.println("Eeek, an exception: " + e.getMessage());
        }
        return response;
    }
}
