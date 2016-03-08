<!--
  @author Jason Yang
  Class: 95-702 Distributed Systems
  Assignment: Project 1 Task 2
  Object: Create a web app to do calculations
 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Math Operation</title>
    </head>
    <body>
        <form action="BigCalc" method="GET">
            <h1>Enter Two Numbers</h1>
            Number x<input type="text" name="x"><br>
            Number y<input type="text" name="y"><br>
            <select name="operation">
                            <option value="add">Add</option>
                            <option value="multiply">Multiply</option>
                            <option value="relativelyPrime">Relatively Prime?</option>
                            <option value="mod">Mod</option>
                            <option value="modInverse">Modular Inverse</option>
                            <option value="power">Power</option>
            <input type="submit">
        </form>
    </body>
</html>
