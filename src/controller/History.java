package controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

public class History extends HttpServlet 
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

			// Set accNumber instance variable of the Model object & retrieve transaction history.
			m.setAccNumber(accNumber);
			ArrayList<Float> al = m.history();

			// Create a new session and store attribute for the transaction history.
			HttpSession hs2 = x.getSession(true);
			hs2.setAttribute("al", al);

			y.sendRedirect("HistorySuccess.jsp");		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}