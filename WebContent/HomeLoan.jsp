<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Loan Facility</title>
</head>
<body>

<h3>Home Loan Facility</h3>

<%

HttpSession hs = request.getSession();
String name = (String)hs.getAttribute("name");

out.println("Welcome " + name + "! <br><br>");
out.println("We provide home loans at an interest rate of 5 % per year.<br>");
out.println("Please Apply for Home Loans here.<br><br>");
%>

<form action = "http://localhost:8081/1_Sir's_Bank_App/HomeLoanApply">

<label> Enter Principal Loan Amount </label><input type="text" name="principal" required="required"/>
<br><br>
<label> Enter Time Period (Years) </label><input type="text" name="time" required="required"/>
<br><br>
<input type="submit" value="Request Loan Approval"/>

</form>

</body>
</html>