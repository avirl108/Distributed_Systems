package mingjiey.cmu.edu.project4guessgender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;

/*
 * This class provides capabilities to search for an image on Flickr.com given a search term.  The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of an AsyncTask inner class that will do the network
 * operations in a separate worker thread.  However, any UI updates should be done in the UI thread so avoid any synchronization problems.
 * onPostExecution runs in the UI thread, and it calls the ImageView pictureReady method to do the update.
 *
 */
public class GetGender {
    GuessGender ip = null;

    /*
     * search is the public GetGender method.  Its arguments are the search term, and the GuessGender object that called it.  This provides a callback
     * path such that the pictureReady method in that object is called when the picture is available from the search.
     */
    public void search(String searchTerm, GuessGender ip) {
        this.ip = ip;
        new AsyncFlickrSearch().execute(searchTerm);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncFlickrSearch extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return search(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            ip.guessReady(result);
        }

        /*
         * Search Flickr.com for the searchTerm argument, and return a Bitmap that can be put in an ImageView
         */
        private String search(String searchTerm) {
            String pictureURL = null;

            try {
                pictureURL = "https://pumpkin-surprise-58556.herokuapp.com/GG/?name=" + searchTerm;
                //	pictureURL="https://gender-api.com/get?key=" + "ZtmJherAtFeQffskjZ" + "&name=" + searchTerm;
                URL u = new URL(pictureURL);
                return getRemoteImage(u);
            } catch (Exception e) {
                e.printStackTrace();
                return null; // so compiler does not complain
            }

        }



        /*
         * Given a URL referring to an image, return a bitmap of that image
         */
        private String getRemoteImage(final URL picurl) {
            String response = "";
            String urlString = "";
            HttpURLConnection conn;
//			URL url;
            int status;
            try {
//				url = new URL(picurl);
                conn = (HttpURLConnection) picurl.openConnection();

                conn.setRequestMethod("GET");
                // tell the server what format we want back
                conn.setRequestProperty("Accept", "text/plain");

                // wait for response
                status = conn.getResponseCode();
                System.out.println("response code: " + status);

                // If things went poorly, get the error status and message
                if (status != 200) {
                    // not using msg
                    String msg = conn.getResponseMessage();
                    return conn.getResponseCode() + " " + msg;
                }

                // get the response
                String output;
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                while ((output = br.readLine()) != null) {
                    response += output + "\n";
                }


                System.out.println("response: " + response);


                JSONObject json = new JSONObject(response);
                String gender = json.get("gender").toString();
                System.out.println("gender: " + gender);
                response = gender;
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;

        }
    }
}