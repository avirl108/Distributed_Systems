<!--
  @author Jason Yang
  Class: 95-702 Distributed Systems
  Assignment: Project 1 Task 3
  Object: Create a web app to check whether a string is palindrome
 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Palindrome?</title>
    </head>
    <body>
       <h1>Enter a String to check whether it is a palindrome</h1>
        <form action="Palin" method="GET">
            <input type="text" name="userInput">
            <input type="submit" value="Palindrome Check">
        </form>
    </body>
</html>
