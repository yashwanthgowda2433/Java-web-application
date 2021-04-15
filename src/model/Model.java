package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleDriver;

public class Model 
{
	float balance = 100.0f;		// Initial Balance = 100.0
	float principal;			// Principal Loan Amount
	float tempBalance = -1.0f;
	float toBalance;
	float amount;
	String name;
	String password;
	String branch;
	String email;
	int accNumber;	// Current Account Number
	int accNumber2;	// Updated / Next Account Number
	int toAccNumber;
	String ifsc;

	int result, result2, a;
	String p;

	Connection con = null;
	ResultSet resultSet = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;

	// Model() Constructor will load the OJDBC driver & establish the connection to the database.

	public Model()	
	{
		try
		{
			DriverManager.registerDriver(new OracleDriver());
			System.out.println("Driver Loaded Successfully!");

			con = DriverManager.getConnection("jdbc:oracle:thin:@SurajAcer:1522:xe", "system", "system");
			System.out.println("Connection Established Successfully!");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Generate account number by retrieving value of ACC_NO from ACC_TABLE.

	public void generateAccNumber()
	{
		try 
		{
			stmt = con.createStatement();
			resultSet = stmt.executeQuery("SELECT ACC_NO FROM ACC_TABLE");

			while(resultSet.next() == true)
			{
				accNumber = resultSet.getInt(1);
				accNumber2 = accNumber + 1;
				updateAccNumber();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	// Store the next account number by updating value of ACC_NO in ACC_TABLE.

	public void updateAccNumber()
	{
		try 
		{
			pstmt = con.prepareStatement("UPDATE ACC_TABLE SET ACC_NO = ? WHERE ACC_NO = ?");
			pstmt.setInt(1, accNumber2);
			pstmt.setInt(2, accNumber);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	// Generate IFSC code from the Bank's branch location.

	public void generateIFSC()
	{
		if(branch.equals("VIJ"))
		{
			ifsc = "UNBKVIJ001";
		}
		else if (branch.equals("YPR"))
		{
			ifsc = "UNBKYPR002";			
		}
		else
		{
			ifsc = "Invalid";
		}
	}

	/**
	 * Registers a new account into the database table of BANK_DATA.
	 * @return true, if the registration was successful. 
	 * @return false, if the registration was not successful.
	 */

	public boolean register()
	{
		generateIFSC();
		if(ifsc.equals("Invalid"))	// IFSC code is Invalid, Hence registration must fail.
		{
			return false;			// So directly return false.
		}

		generateAccNumber();

		try 
		{
			pstmt = con.prepareStatement("INSERT INTO BANK_DATA VALUES(?,?,?,?,?,?,?)");
			pstmt.setFloat(1, balance);
			pstmt.setString(2, name);
			pstmt.setString(3, password);
			pstmt.setString(4, branch);
			pstmt.setString(5, email);
			pstmt.setInt(6, accNumber);
			pstmt.setString(7, ifsc);
			result = pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		

		if(result == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Login for an existing account by taking it's accNumber & matching the password from the database.
	 * @return true, if the login was successful. 
	 * @return false, if the login was not successful.
	 */

	public boolean login()
	{
		try 
		{
			pstmt = con.prepareStatement("SELECT * FROM BANK_DATA WHERE ACC_NO = ?");

			pstmt.setInt(1, accNumber);		// Setting the accNumber given from Login servlet.
			resultSet = pstmt.executeQuery();		// Executing the query

			while(resultSet.next() == true)
			{
				a = resultSet.getInt("ACC_NO");			// Getting the accNumber from the Database
				p = resultSet.getString("PASSWORD");	// Getting the password from the Database
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		if(accNumber == a && password.equals(p))	// Matching the credentials
		{	return true;	}
		else
		{	return false;	}		
	}

	/**
	 * Check Balance for an already logged-in account.
	 * @return true, if the balance check was successful. 
	 * @return false, if the balance check was not successful.
	 */

	public boolean checkBalance()
	{
		try 
		{	
			tempBalance = -1.0f;	// First set a Negative, Invalid Balance value.

			pstmt = con.prepareStatement("SELECT BALANCE FROM BANK_DATA WHERE ACC_NO = ?");
			pstmt.setInt(1, accNumber);		// Setting the accNumber from Balance servlet.
			resultSet = pstmt.executeQuery();	// Executing the query

			while(resultSet.next() == true)
			{
				tempBalance = resultSet.getFloat("BALANCE");	// Getting balance from the Database
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		if(tempBalance > 0.0f)		// If valid Balance
		{
			balance = tempBalance;	// Store the valid Balance value.
			return true;
		}
		else						// If invalid Balance
		{
			return false;
		}		
	}

	/**
	 * Change Password for an already logged-in account.
	 * @return true, if the password change was successful. 
	 * @return false, if the password change was not successful.
	 */

	public boolean changePassword()
	{
		try 
		{
			pstmt = con.prepareStatement("UPDATE BANK_DATA SET PASSWORD = ? WHERE ACC_NO = ?");
			pstmt.setString(1, password);	// Setting password from Password servlet.
			pstmt.setInt(2, accNumber);		// Setting accNumber from Password servlet.
			result = pstmt.executeUpdate();		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	

		if(result == 1)
		{
			return true;
		}
		else
		{
			return false;
		}	
	}

	/**
	 * Transfer amount from an already logged-in account to another account.
	 * @return true, if the transfer was successful. 
	 * @return false, if the transfer was not successful.
	 */

	public boolean transfer()	
	{
		try
		{
			// First, get the current balance of the toAccNumber account.
			
			pstmt = con.prepareStatement("SELECT * FROM BANK_DATA WHERE ACC_NO = ?");
			pstmt.setInt(1, toAccNumber);
			resultSet = pstmt.executeQuery();

			while(resultSet.next() == true)
			{
				toBalance = resultSet.getFloat("BALANCE");
			}
			
			// Then, Credit (Add) the amount into the toAccNumber account.

			toBalance = toBalance + amount;	// New, Updated Balance of toAccNumber Account

			pstmt = con.prepareStatement("UPDATE BANK_DATA SET BALANCE = ? WHERE ACC_NO = ?");
			pstmt.setFloat(1, toBalance);
			pstmt.setInt(2, toAccNumber);	
			result = pstmt.executeUpdate();
			
			updateHistory();	// Update Transaction History in TRANSACTION Table of DB.
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(result == 1)
		{
			try
			{	// Now, Debit (Deduct) the same amount from logged-in account.

				balance = balance - amount;		// New, Updated Balance of Logged-in Account

				pstmt = con.prepareStatement("UPDATE BANK_DATA SET BALANCE = ? WHERE ACC_NO = ?");
				pstmt.setFloat(1, balance);
				pstmt.setInt(2, accNumber);	
				result2 = pstmt.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return true;
		}	
		else
		{
			return false;
		}
	}
	
	// Update Transaction History in TRANSACTION Table of DB.
	
	public void updateHistory()
	{
		try 
		{
			pstmt = con.prepareStatement("INSERT INTO TRANSACTIONS VALUES(?,?)");
			pstmt.setInt(1, accNumber);	// Logged-in Account Number
			pstmt.setFloat(2, amount);	// Transaction Amount
			pstmt.executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @return ArrayList containing Transaction History of logged-in account. 
	 */
	
	public ArrayList<Float> history()
	{
		ArrayList<Float> al = new ArrayList<Float>();
		
		try
		{		
			pstmt = con.prepareStatement("SELECT AMOUNT FROM TRANSACTIONS WHERE ACC_NO = ?");
			pstmt.setInt(1, accNumber);		// Logged-in Account Number
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next() == true)
			{
				al.add(resultSet.getFloat("AMOUNT"));	// Add all transaction amounts into the ArrayList.
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return al;
	}
	
	// Retrieve name of logged-in account holder, based on their accNumber.
	
	public void retrieveName()
	{
		try
		{
			pstmt = con.prepareStatement("SELECT NAME FROM BANK_DATA WHERE ACC_NO = ?");
			pstmt.setInt(1, accNumber);		// Logged-in Account Number
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next() == true)
			{
				name = resultSet.getString("NAME");	// Store name of logged-in account holder.
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Approve Loan & add principal loan amount into the logged-in account's balance.
	
	public void updateLoan()
	{
		try
		{
			// First, get the current balance of the logged-in accNumber account.
			pstmt = con.prepareStatement("SELECT BALANCE FROM BANK_DATA WHERE ACC_NO = ?");
			pstmt.setInt(1, accNumber);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next() == true)
			{
				balance = resultSet.getFloat("BALANCE");
				balance = balance + principal;	// Add principal loan amount into the balance.
			}
			
			// Then, update the new balance into the toAccNumber account.
			pstmt = con.prepareStatement("UPDATE BANK_DATA SET BALANCE = ? WHERE ACC_NO = ?");
			pstmt.setFloat(1, balance);
			pstmt.setInt(2, accNumber);
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	// Getters

	public float getBalance() 
	{	return balance;	}

	public String getName() 
	{	return name;	}

	public String getPassword() 
	{	return password;	}

	public String getBranch() 
	{	return branch;	}

	public String getEmail() 
	{	return email;	}

	public int getAccNumber() 
	{	return accNumber;	}

	public int getAccNumber2() 
	{	return accNumber2;	}

	public String getIfsc() 
	{	return ifsc;	}

	public int getToAccNumber() 
	{	return toAccNumber;	}

	// Setters

	public void setBalance(float balance) 
	{	this.balance = balance;	}
	
	public void setPrincipal(float principal) 
	{	this.principal = principal;	}

	public void setAmount(float amount) 
	{	this.amount = amount;	}

	public void setName(String name) 
	{	this.name = name;	}

	public void setPassword(String password)
	{	this.password = password;	}

	public void setBranch(String branch) 
	{	this.branch = branch;	}

	public void setEmail(String email) 
	{	this.email = email;	}

	public void setAccNumber(int accNumber) 
	{	this.accNumber = accNumber;	}

	public void setToAccNumber(int toAccNumber) 
	{	this.toAccNumber = toAccNumber;	}
}