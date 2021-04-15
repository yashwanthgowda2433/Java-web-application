package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();
			
			// Get accNumber & password from Front-End HTML code.
			String a = x.getParameter("accNumber");
			int accNumber = Integer.parseInt(a);
			String password = x.getParameter("password");

			// Set accNumber & password instance variables of the Model object.
			m.setAccNumber(accNumber);
			m.setPassword(password);

			boolean result = m.login();	// Perform the login() action

			if(result == true)
			{
				// Create a new session and store attribute for account number.
				HttpSession hs = x.getSession(true);
				hs.setAttribute("accNumber", accNumber);
		
				y.sendRedirect("LoginSuccess.html");
			}
			else
			{
				y.sendRedirect("LoginFailure.html");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}