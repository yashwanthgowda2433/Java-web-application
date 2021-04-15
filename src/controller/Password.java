package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Password extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			// Get newPassword & confirmPassword from Front-End HTML code.
			String newPassword = x.getParameter("newPassword");
			String confirmPassword = x.getParameter("confirmPassword");
			
			if(newPassword.equals(confirmPassword))	// If both passwords match
			{
				Model m = new Model();
				
				// Get accNumber from the existing session created in login.
				HttpSession hs = x.getSession();
				int accNumber = (int)hs.getAttribute("accNumber");
				
				// Set accNumber & password instance variables of the Model object.
				m.setAccNumber(accNumber);
				m.setPassword(newPassword);
				
				boolean result = m.changePassword();	// Change password of account
				
				if(result == true)
				{
					y.sendRedirect("PasswordSuccess.html");
				}
				else
				{
					y.sendRedirect("PasswordFailure.html");
				}				
			}
			else	// If both passwords do not match
			{
				y.sendRedirect("PasswordMismatch.html");				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}