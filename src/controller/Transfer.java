package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class Transfer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();
			
			// Get toAccNumber & amount from Front-End HTML code.
			String to = x.getParameter("toAccNumber");
			int toAccNumber = Integer.parseInt(to);
			String a = x.getParameter("amount");
			float amount = Float.valueOf(a);
			
			// Get logged-in accNumber from the existing session created in login.
			HttpSession hs = x.getSession();
			int accNumber = (int)hs.getAttribute("accNumber");
			
			// Set accNumber instance variable of the Model object & check current Balance.
			m.setAccNumber(accNumber);
			m.checkBalance();
			float balance = m.getBalance();
			
			if((balance - amount) >= 100.0f)	// If Sufficient Balance in Logged-In Account...
			{				
				// Set toAccNumber & amount instance variables of the Model object
				m.setToAccNumber(toAccNumber);
				m.setAmount(amount);
				
				boolean result = m.transfer();	// Transfer Money
				
				if(result == true)
				{
					balance = m.getBalance();		// Get the new balance in account
					
					// Create a new session and store attributes for this new balance & amount.
					HttpSession hs2 = x.getSession(true);
					hs2.setAttribute("balance", balance);
					hs2.setAttribute("amount", amount);
					
					y.sendRedirect("TransferSuccess.jsp");				
				}
				else
				{
					y.sendRedirect("TransferFailure.html");					
				}
			}
			else
			{
				y.sendRedirect("TransferNotEnoughBalance.html");	// If Insufficient Balance				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}