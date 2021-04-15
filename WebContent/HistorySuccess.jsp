<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction History Success</title>
</head>
<body>

<h3>Your Transaction History : </h3>

<%

HttpSession hs = request.getSession();

@SuppressWarnings("unchecked")
ArrayList<Float> al2 = (ArrayList<Float>) hs.getAttribute("al");

Iterator<Float> itr = al2.iterator();
int count = 0;

while(itr.hasNext() == true)
{
	out.println((++count) + ") Transfer Amount : Rs. " + itr.next() + "<br>");	
}

%>

</body>
</html>