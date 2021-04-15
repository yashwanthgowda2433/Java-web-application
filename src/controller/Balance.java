package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Balance extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();

			// Get accNumber from the existing session created in login.
			HttpSession hs = x.getSession();
			int accNumber = (int)hs.getAttribute("accNumber");
			
			// Set accNumber instance variables of the Model object.
			m.setAccNumber(accNumber);

			boolean result = m.checkBalance();	// Check balance in account.		

			if(result == true)
			{
				float balance = m.getBalance();	// Store that balance.

				// Create a new session and store attribute for balance.
				HttpSession hs2 = x.getSession(true);
				hs2.setAttribute("balance", balance);
				
				y.sendRedirect("BalanceSuccess.jsp");			
			}
			else
			{
				y.sendRedirect("BalanceFailure.html");			
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}