<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Success</title>
</head>
<body>

<h3>Registration Successfully Completed!</h3>
<h4>Here are your login details : </h4>

<%

HttpSession hs = request.getSession();
String password = (String)hs.getAttribute("password");
Integer accNumber = (Integer)hs.getAttribute("accNumber");

out.println("Your Password is : " + password + "<br><br>");
out.println("Your Account Number is : " + accNumber);

%>

<br><br>
<a href ="Login.html">Click Here to LOGIN</a>
 
</body>
</html>