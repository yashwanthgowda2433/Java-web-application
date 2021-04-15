package controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class HomeLoanApply extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest x, HttpServletResponse y)
	{
		try
		{
			Model m = new Model();
			
			// Get principal loan amount & time period from Front-End JSP code.
			String p = x.getParameter("principal");
			float principal = Float.valueOf(p);
			String t = x.getParameter("time");
			int time = Integer.parseInt(t);
			
			// Get logged-in accNumber from the existing session created in login.
			HttpSession hs = x.getSession();
			int accNumber = (int)hs.getAttribute("accNumber");
			
			// Calculate simple interest with Home Loan Interest Rate of 5 % p.a.
			
			float simpleInterest = (principal * 5.0f * time) / 100.0f;

			// Set toAccNumber & principal loan amount instance variables of the Model object
			m.setAccNumber(accNumber);
			m.setPrincipal(principal);	
			
			m.updateLoan();	// Add principal loan amount into the logged-in account's balance.
			
			// Create a new session and store attributes for si, p, r & t.
			HttpSession hs2 = x.getSession(true);
			hs2.setAttribute("simpleInterest", simpleInterest);
			hs2.setAttribute("principal", principal);
			hs2.setAttribute("rate", 5.0f);
			hs2.setAttribute("time", time);
			
			y.sendRedirect("HomeLoanSuccess.jsp");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}