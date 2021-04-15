<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Balance Success</title>
</head>
<body>

<h3>Balance Check Successful!</h3>

<%

HttpSession hs = request.getSession();
float balance = (float)hs.getAttribute("balance");
out.println("Your Account Balance is : Rs. " + balance);

%>

</body>
</html>