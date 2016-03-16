95-702 Distributed Systems
Android Application Lab

===============================================================================
Pre-Work:  You should download and install the Android Studio IDE **BEFORE**
your lab session.  It will take too long doing so during the lab itself.  You
should also create an Android Virtual Device emulator to test your Android 
projects.
===============================================================================

===============================================================================
Install Android Studio
===============================================================================

Android Studio is the supported IDE for creating Android applications.

To install:
1. Visit https://developer.android.com/sdk/index.html and download the Android
  studio for your operating system.
2. Follow the instructions to install Android Studio, accepting all defaults
3. Be sure to follow the directions to the "Adding SDK Packages".  As the site
  states, "By default, the Android SDK does not include everything you need to
  start developing. The SDK separates tools, platforms, and other components
  into packages you can download as needed using the Android SDK Manager. So
  before you can start, there are a few packages you should add to your Android
  SDK."

Notes for MacOS installation:  
  - In addition to dragging the application to the Application folder, you need
  to launch Android Studio to complete installation.
  - The "Installing Android Studio" instructions state that "If you need use
  the Android SDK tools from a command line, you can access them at:
  /Users/<user>/Library/Android/sdk/ .  This is not always the case and you
  might find that they have been installed in:
  /Applications/android-sdk-mac_x86/tools
  - View installation details to see where they have been put.

For all installations, you should add the Android tools directory to your path
to make them easy to access from the command line when you want them.

===============================================================================
Create an Android Virtual Device (AVD)
===============================================================================

Use the following steps to install a stable Android Virtual Device for use in DS
labs and projects.

Unless otherwise stated, all these steps are executed from within Android Studio

1. Launch the Android Virtual Device Manager via 
  Tools menu->Android->AVD Manager

2. Click "Create Virtual Device..."
3. Choose category "Phone" and name "Nexus 5" and Next
4. Choose a Target with API 23 Level for your platform and Next
5. Do not change default settings and Finish
6. Start your AVD by clicking on the green play triangle.
Once the emulated phone has started (this may take a couple minutes)...
7. Unlock the phone by swiping up from the padlock
8. Click on the browser (blue globe icon) and browse to some web site to confirm
everything is working.


===============================================================================
Phase 1 - Hello Android
===============================================================================
Create an Android Project in Android Studio
===============================================================================

1. From Welcome to Android Studio Wizard, "Start a new Android Studio Project"
2. Give an "Application Name" (e.g. "Hello Android")
3. Give a "Company Domain", e.g edu.cmu.yourAndrewID and click Next
4. Select "Phone and Tablet" and Minimum SDK API 23 and click Next
5. Select "Blank Activity" and Next
6. Leave defaults unchanged and click "Finish"

At this point you might have to wait a while.  It may take a couple minutes
until you see the code available in Android Studio.  Eventually you will see an
Android project in the IDE.  It should be a fully-working "Hello Android" app.

Test it by launching it in your AVD:
1) Click the green play triangle in the Android Studio menubar to run the app.
2) Choose the AVD you just created to run the app in.
3) Switch to the running AVD and verify that the Hello Android app has
  successfully launched.

===============================================================================
Exercises
===============================================================================

1. Explore the contents of the project's res directory. These are the static
  resource files that your Android app uses.  They include such things as
  menus, user interface (UI) definitions, icons, and strings. 
2. The file res/values/strings.xml defines static strings that are used in your
	application. Change the string named "app_name" to include your
	name (e.g. "Joe's App").
3. Save strings.xml
4. Edit res/layout/activity_main.xml. Notice that this is the UI definition of
  the main Activity.  It "includes" a layout for content_main.
5. Edit res/layout/content_main.xml.  This is the part of the screen layout
  that has the "Hello World" message.  Change the text from "Hello World" to
  "Welcome to my new app".
6. Notice the "Design" and "Text" tabs.  Toggle between them to understand what
  they allow you to do.
7. In the Design view, scroll down the Palette to find the "Text Fields".
  Drag a new "Plain Text" field onto your screen. (Make sure you are not using
  a "Plain TextView".  Scroll down further if you can't find "Plain Text" under
  "Text Fields".) 
8. In the Properties of this widget (Android calls it an editText element) set
  the "Hint" to "Your answer..."
9. Save content_main.xml
Test it by launching it in your AVD:
10. Click the green play triangle in the Android Studio menubar to run the app.
11. Choose the AVD you just created to run the app in.
12. Switch to the running AVD and verify that the Hello Android app has
  successfully launched and your changes have been successful.
  
** After completing this and Phase 2, show both to a TA **
  
===============================================================================
Phase 2 - Interesting Picture
===============================================================================

1. Download the zipped AndroidInterestingPictureLab from the course calendar
	into your Android Studio workspace and unzip it.
2. Open Android Studio
  If you already have a window open from Phase 1, close it
3. Select Open an existing Android Studio project
4. Browse to find the AndroidInterestingPictureLab folder and Choose it
  You may get a message to "Sync Android SDKs".  Click OK to modify
    local.properties for the location of your Android SDK.
5. Get a Flickr API key from:
	http://www.flickr.com/services/api/misc.api_keys.html
6. Edit the file GetPicture.java and put your API key where it says 
  "<<<put your Flickr api key here>>>" (replace the << and >> also!).
  Remember to save the file.
7. Click the green play triangle in the Android Studio menubar to run the app.
8. Choose the AVD you just created to run the app in.
9. Switch to the running AVD and verify that the InterestingPicture app has
  successfully launched.  Type in a keyword to search such as "boat" and
  click Submit and you should see a picture displayed.

Explore the project folders. The most important are the "java" and "res"
folders.  The java folder has the source code for the application, and
the res folder has the static resources as you saw in Phase 1.

Within the java folder, you will see two classes:
ds.cmu.edu.interestingpicture.InterestingPicture
ds.cmu.edu.interestingpicture.GetPicture

Study the InterestingPicture class.  Android applications are organized into
"activities" and this is the main activity for this application.  Read the
comments and the code and understand how it works.

The GetPicture class is used to first search Flickr for a pictures related to
a keyword, and then fetch a picture.  So that the phone user interface is not
frozen while these network activities take place, these network actions must
take place in a helper thread.  AsyncTask makes it easy to use a helper
thread.  Read the comments in GetPicture and review the AsyncTask API:
http://developer.android.com/reference/android/os/AsyncTask.html

10. The application is missing the feedback "Here is a picture of a ..." or 
	"Sorry, I could not find a picture of a..."
     a. Add a new TextView to res/layout/main.xml for this feedback
     b. In the pictureReady method, findViewById your new TextView
     c. Set the text of the TextView to the appropriate string (depending on
     		whether the picture is found or not).
       (Where can you get the search term from to add to this string?)
11. Run and test it.

12. Sort the following list of methods into the order they are invoked when
	"submit" is clicked, and indicate whether they are being run in the "UI" 
	thread or a helper thread. See for reference the AsyncTask API:
	http://developer.android.com/reference/android/os/AsyncTask.html

      AsyncFlickrSearch.search
      doInBackground
      GetPicture.search
      getRemoteImage
      getRemoteXML
      onClick
      onPostExecute
      pictureReady

===============================================================================
Show a TA
===============================================================================

For credit for the lab, show a TA:
	- Working Phase 1 
	- Working Phase 2 through step 11
	- Your sorted list from Phase 2 step 12
	  (BTW, a question regarding UI and helper threads is likely on the test.)
	
