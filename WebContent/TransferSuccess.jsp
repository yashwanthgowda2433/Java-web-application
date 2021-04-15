<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transfer Success</title>
</head>
<body>

<h3>Transfer Successfully Completed! :)</h3>
<h4>Money Transfer Details : </h4>

<%

HttpSession hs = request.getSession();
Float amount = (Float)hs.getAttribute("amount");
Float balance = (Float)hs.getAttribute("balance");

out.println("Amount Transferred from your Account : Rs. " + amount + "<br><br>");
out.println("Your New Current Balance is : Rs. " + balance);

%>

</body>
</html>