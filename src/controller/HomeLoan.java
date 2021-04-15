package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class HomeLoan extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();
			
			// Get logged-in accNumber from the existing session created in login.
			HttpSession hs = x.getSession();
			int accNumber = (int)hs.getAttribute("accNumber");
			
			// Set accNumber instance variable & retrieve name of logged-in account holder.
			m.setAccNumber(accNumber);
			m.retrieveName();
			String name = m.getName();
			
			// Create a new session and store attribute for name of logged-in account holder.
			HttpSession hs2 = x.getSession(true);
			hs2.setAttribute("name", name);
			
			y.sendRedirect("HomeLoan.jsp");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}