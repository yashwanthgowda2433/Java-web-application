package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Register extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();
			
			// Get name, password, branch & email from Front-End HTML code.
			String name = x.getParameter("name");
			String password = x.getParameter("password");
			String branch = x.getParameter("branch");
			String email = x.getParameter("email");

			// Set name, password, branch & email instance variables of the Model object.
			m.setName(name);
			m.setPassword(password);
			m.setBranch(branch);
			m.setEmail(email);

			boolean result = m.register();	// Perform the registration action.
			
			int accNumber = m.getAccNumber();	// Get the generated account number.
			
			if(result == true)
			{
				// Create a new session and store attributes for password & account number.
				HttpSession hs = x.getSession(true);
				hs.setAttribute("password", password);
				hs.setAttribute("accNumber", accNumber);
								
				y.sendRedirect("RegisterSuccess.jsp");
			}
			else
			{
				y.sendRedirect("RegisterFailure.html");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}