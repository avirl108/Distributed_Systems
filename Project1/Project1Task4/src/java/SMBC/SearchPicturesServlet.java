/*
 *
 * @author Jason Yang
 * Class: 95-702 Distributed Systems
 * Assignment: Project 1 Task 4
 * Object: Create a web app that can show the bird picture of user's search
 *
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet.  So it is also the starting place for the web
 * application.
 *
 * This is the controller part of MVC model.
 * It will receive user input from welcome.jsp and call methods in model
 * Finally, it will show the results on views(result.jsp, error.jsp)
 * The model is provided by SearchPictureModel.
 */
package SMBC;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * The following WebServlet annotation gives instructions to the web container.
 */
@WebServlet(name = "SearchPicturesServlet", 
        urlPatterns = {"/searchPictures"})
public class SearchPicturesServlet extends HttpServlet {

    SearchPicturesModel spm = null;  // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        spm = new SearchPicturesModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the subject parameter if it exists
        String subject = request.getParameter("subject");

        // determine what type of device is
        String ua = request.getHeader("User-Agent");

        String deviceType;
        String imageUrl;
        boolean mobile;
        
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.contains("Android")) || (ua.contains("iPhone")))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            // This is the XHTM Webpage doctype.
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the subject parameter is present.
         * If not, then give the user instructions and prompt for a subject string.
         * If there is a subject parameter, then do the subject and return the image sourde url.
         */
        if (subject != null) {
            // use model to get the image url and choose the result view
            imageUrl = spm.searchPictureUrl(subject, mobile);
          
            if (imageUrl != null) {
                request.setAttribute("pictureURL", imageUrl);
                // Pass the user search string (pictureTag) also to the view.
                request.setAttribute("pictureTag", spm.getPictureTag());
                // Pass the user search string (photographerTag) also to the view.
                request.setAttribute("photographerTag", spm.getPhotographerTag());
                nextView = "result.jsp";
            } else {
                //if no image url, give them an error view
                nextView = "error.jsp";
                request.setAttribute("error", "Cannot found image for this bird. Please try again.");
            }
        } else {
            // no search parameter so choose the prompt view
            if (ua != null && ((ua.contains("Android")) || (ua.contains("iPhone")))) {
                request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            } else {
                request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            }
            nextView = "prompt.jsp";
        }
        // Transfer control over the the correct view
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
