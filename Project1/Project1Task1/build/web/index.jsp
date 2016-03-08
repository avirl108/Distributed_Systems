<!--
 @author Jason Yang
 Class: 95-702 Distributed Systems
 Assignment: Project 1 Task 1
 Object: Create a web app to calculate hash value
 

-->
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hash Computation</title>
    </head>
    <body>
	<form action="ComputeHashes" method="GET">
         <h1>Enter a string and choose a hash function:</h1><br>
         <!--get user input-->
         <input type="text" name = "userInput"> <p>
         <!--get user choice-->
         <input type="radio" name="hashChoice" value="MD5" checked="checked">MD5<br>
         <input type="radio" name="hashChoice" value="SHA-1">SHA-1<br>
         <input type = "submit">
    	</form>
    </body>
</html>
