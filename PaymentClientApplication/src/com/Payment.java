package com;


//import statements.
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;



import util.DBConnection;
import Security.Hashing;
import model.PaymentModel;

/****************************SERVER MODEL CLASS IMPEMENTING SERVICE BUSINESS LOGIC.****************************************/

public class Payment{

	
	
	//Connection object creation.
	DBConnection dbConnect = new DBConnection();
	
	
	/*****Inserting backer payment.********/
	public String insertBackerPayment(String consumerID,String conceptID,String UserType,String paymentType, String bank, String paymentDate, String cardNo, 
			String nameOnCard, String cvv , 
			String cardExpMonth , String cardExpYear 
			)
	{
		//declaring variable to capture output message.
		
		String output = "";
		
		
		try
		{
			
			/*****************************************Checking for database connectivity.********/
			
			
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database.";
			}
			
			
			
			/*************Executing logic for Auto-generating payment id.******************************************************/	
			
			//Preparing a CallableStatement to call the implemented function.
          CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
          
          //Registering the out parameter of the function (return type).
         cstmt.registerOutParameter(1, Types.CHAR);
          
          //Executing the statement.
          cstmt.execute();
          
          //obtaining returned value of function(getPaymentID()).
          String PaymentCode = cstmt.getString(1);
			
			// create a prepared statement.
			
			String query = " insert into paymentservice.gb_payments(`PaymentID`,`paymentCode`,`UserType`,`PaymentType`,`bank`,`paymentDate`,`cardNo`,`NameOnCard`,`cvv`,`Buyerpayment`,`ProductID`,`ConsumerID`,`ConceptID`,`cardExpMonth`,`cardExpYear`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			
				// binding values.
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PaymentCode);
			preparedStmt.setString(3, UserType);
			preparedStmt.setString(4, paymentType);
			preparedStmt.setString(5, bank);
			preparedStmt.setString(6, paymentDate);
			preparedStmt.setString(7, cardNo);
			preparedStmt.setString(8,nameOnCard);
			preparedStmt.setString(9,cvv);
			preparedStmt.setDouble(10,0);
			preparedStmt.setString(11, "NA");
			preparedStmt.setString(12, consumerID);
			preparedStmt.setString(13, conceptID);
			preparedStmt.setString(14, cardExpMonth);
			preparedStmt.setString(15, cardExpYear);
			
			
			//execute the statement.
			preparedStmt.execute();
			
			
			
			con.close();
			
			String newBackerPayment = readBackerPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newBackerPayment + "\"}";
			
			//output = "Backer payment Inserted successfully" + "\n Your payment ID is: " +  PaymentCode;
		}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the backer payment.\"}";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	
	/*****Inserting buyer payment********/
	public String insertBuyerPayment(String ConsumerID,String ProductID,String UserType,String paymentType, String bank, String paymentDate, String cardNo , 
			String NameOnCard, String cvv , 
			String cardExpMonth , String cardExpYear 
			)
	{
		//declare variable to hold output message.
		String output = "";
		
		//declaring variable to capture total payment amount.
		double totalBuyingAmt = 0.00;
		
		try
		{
			//checking for connectivity.
			
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database!";
			}
			
	
				
			
			/*************Executing logic for Auto-generating payment id.******************************************************/	
			
			//Preparing a CallableStatement to call the implemented function.
          CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
          
          //Registering the out parameter of the function (return type).
         cstmt.registerOutParameter(1, Types.CHAR);
          
          //Executing the statement.
          cstmt.execute();
          
          //obtaining returned value of function(getPaymentID()).
          String PaymentCode = cstmt.getString(1);
          
          
          
 /******************************BUYER PAYMENT CALCULATION.**************************************************************/         
        //contains implemented business logic to calculate total buying amount of buyer.
			 CallableStatement  cs = con.prepareCall("{? = call insertProductAmount(?,?)}");	
			
			//setting parameter to function.
			
			 cs.registerOutParameter(1, Types.DOUBLE);
			 cs.setString(2,ProductID);
			 cs.setString(3, ConsumerID);
		
			
			//call function.
			 cs.execute();
			
			//obtained returned value of function(insertProductAmount()).
		     totalBuyingAmt = cs.getDouble(1);
			
			// create a prepared statement.
			
			String query = " insert into paymentservice.gb_payments(`PaymentID`,`paymentCode`,`UserType`,`PaymentType`,`bank`,`paymentDate`,`cardNo`,`NameOnCard`,`cvv`,`Buyerpayment`,`ProductID`,`ConsumerID`,`ConceptID`,`cardExpMonth`,`cardExpYear`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			
				// binding values.
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PaymentCode);
			preparedStmt.setString(3, UserType);
			preparedStmt.setString(4, paymentType);
			preparedStmt.setString(5, bank);
			preparedStmt.setString(6, paymentDate);
			preparedStmt.setString(7, cardNo);
			preparedStmt.setString(8,NameOnCard);
			preparedStmt.setString(9, cvv);
			preparedStmt.setDouble(10,totalBuyingAmt);
			preparedStmt.setString(11, ProductID);
			preparedStmt.setString(12, ConsumerID);
			preparedStmt.setString(13, "NA");
			preparedStmt.setString(14, cardExpMonth);
			preparedStmt.setString(15, cardExpYear);
			
			
			//execute the statement.
			preparedStmt.execute();
			
			
			con.close();
			
			String newBuyerPayment = readBuyerPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newBuyerPayment + "\"}";
			
			//output = "Buyer payment Inserted successfully" + "\n Your payment ID is: "  + PaymentCode;
			
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the buyer payment.\"}";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	/*****************************************Method to read all backer payment details.********************************/
	public String readBackerPayments()
	{
		//declare variable to capture output message.
		
		String output = "";
		
	try
		{
		
		//invoking connection object.
		Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading!";
			}
			
			// Prepare the html table to be displayed.
			
			//Cannot view personal payment details.
			
				output = "<table border=�1�><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Name on card</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>ConceptID</th>"
				+ "<th>Update</th>"
				+ "<th>Remove</th></tr>";
				
				
				String query = "select * from paymentservice.gb_payments where UserType = 'Backer'";
				//String query = "select * from gb_payments p";
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set.
				
				while (rs.next())
				{
					String PaymentID = rs.getString("PaymentID");
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					String CardName= rs.getString("NameOnCard");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  rs.getString("ProductID");
					String consumerID =  rs.getString("ConsumerID");
					String conceptID =  rs.getString("ConceptID");
					//String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					//String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					
					
					// Add into the html table.
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + conceptID + "</td>";
					
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' "
					+ "class='btnUpdate btn btn-secondary' data-itemid='" + PaymentID + "'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' "
					+ "class='btnRemove btn btn-danger' data-itemid='" + PaymentID + "'></td></tr>";
					
						
					}
				
				con.close();
				
				
				// Complete the html table.
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the payments!";
				
				System.err.println(e.getMessage());
		}
	
		return output;
	}
	
	/*****************************************Method to read all buyer payment details.********************************/
	public String readBuyerPayments()
	{
		//declare variable to capture output message.
		
		String output = "";
		
	try
		{
		
		//invoking connection object.
		Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading!";
			}
			
			// Prepare the html table to be displayed.
			
			//Cannot view personal payment details.
			
				output = "<table border=�1�><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Name on card</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>BuyerAmount</th>"
				+ "<th>ProductID</th>"
				+ "<th>Update</th>"
				+ "<th>Remove</th></tr>";
				
				
				String query = "select * from paymentservice.gb_payments where UserType = 'Buyer'";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set.
				
				while (rs.next())
				{
					String PaymentID = rs.getString("PaymentID");
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					String CardName= rs.getString("NameOnCard");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  rs.getString("ProductID");
					String consumerID =  rs.getString("ConsumerID");
					String conceptID =  rs.getString("ConceptID");
					//String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					//String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					
					
					// Add into the html table.
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + buyerAmt + "</td>";
					output += "<td>" + productID + "</td>";
					
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' "
					+ "class='btnUpdate btn btn-secondary' data-itemid='" + PaymentID + "'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' "
					+ "class='btnRemove btn btn-danger' data-itemid='" + PaymentID + "'></td></tr>";
					
						
					}
				
				con.close();
				
				
				// Complete the html table.
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the payments!";
				
				System.err.println(e.getMessage());
		}
	
		return output;
	}
	

	/*****************************************Method to read specific user payment details********************************/
	public String readSpecificUserPayments(String name)
	{
		//Declare variable to capture output message.
		
		String output = "";
		
	try
		{
		//check for connectivity.
		Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading!";
			}
			
			// Prepare the html table to be displayed.
			
			//All user details visible to user.
				output = "<table border=�1�><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Card Number</th>"
				+ "<th>Name On Card</th>"
				+ "<th>CVV</th>"
				+ "<th>BuyerPayment</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>ConceptID</th>"
				+ "<th>ProductID</th>";
				
				
				String query = "select p.PaymentType , p.bank , p.paymentDate , ho.nKey AS cardNo ,   p.NameOnCard , hv.nKey AS cvv, p.Buyerpayment , p.ProductID , p.ConsumerID , p.ConceptID from gb_payments p ,  hcardno ho , hcvv hv where  p.cardNo = ho.nvalue AND p.cvv = hv.nvalue AND p.NameOnCard = '"+name+"'";
				
				
			
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set.
				
				while (rs.next())
				{
					
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					String cardNumber = rs.getNString("cardNo");
					String CardName= rs.getString("NameOnCard");
					String cvv = rs.getString("cvv");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  rs.getString("ProductID");
					String consumerID =  rs.getString("ConsumerID");
					String conceptID =  rs.getString("ConceptID");
					//String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					//String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					System.out.println(cardNumber);
					
					
					
					// Add into the html table.
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + cardNumber + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + cvv + "</td>";
					output += "<td>" + buyerAmt + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + conceptID + "</td>";
					output += "<td>" + productID + "</td>";
					
					
						
					}
				
				con.close();
				
				
				// Complete the html table.
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the payments!";
				
				System.err.println(e.getMessage());
		}
	
		return output;
	}
	
	
	
	
	
	
	/**************************Method to handle payment status depending on pledegAmount summation**********************/
	public String updatePaymentStatus(String ConceptID)
	{
		//Declare variable to capture output message.
		String output = "";
		
		try
		{
			//check for connectivity.
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating!"; 
			}
			
			//Preparing a CallableStatement to call a stored procedure containing business logic.
			
			//contains  business logic implemented to update concept status as pledgeAmount sums up.
			CallableStatement  cs = con.prepareCall("{call updateStatus(?)}");	
			
			//setting parameter to procedure
			cs.setString(1, ConceptID);/*pass this name to postman*/
			
			//call procedure
			cs.execute();
			
			cs.close();
			
			//verify procedure execution success
			System.out.println("Stored procedure called successfully!");
		
			output = "Concept payment status Updated successfully!";
		}
		catch (Exception e)
		{
			output = "Error while updating the concept payment status!";
			
			System.err.println(e.getMessage());
		}
		
		return output;
		
		}
	
	/***************************Method to update user payment details.****************************/
	
	public String updateBackerPaymentDetails(int PaymentID,String paymentType,String userType,String bank , String paymentDate,String cardNo , String NameOnCard ,String cvv, String cardExpMonth ,String cardExpYear,String conceptID , String consumerID)
	{
		//Declare variable to capture output message.
		String output = "";
		
		try
		{
			//check for connectivity.
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating!";
			}
			
			// create a prepared statement.
			
		String query = "UPDATE gb_payments SET PaymentType=?,UserType=?,bank=?,paymentDate=?,cardNo=?,NameOnCard=?,cvv=?,cardExpMonth=?,cardExpYear=?,ConceptID=?,ConsumerID=? WHERE PaymentID=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		
		
		// binding values.
		
		
		preparedStmt.setString(1, paymentType);
		preparedStmt.setString(2, userType);
		preparedStmt.setString(3, bank);
		preparedStmt.setString(4, paymentDate);
		preparedStmt.setString(5, cardNo);
		preparedStmt.setString(6, NameOnCard);
		preparedStmt.setString(7, cvv);
		preparedStmt.setString(8, cardExpMonth);
		preparedStmt.setString(9, cardExpYear);
		preparedStmt.setString(10, conceptID);
		preparedStmt.setString(11, consumerID);
		preparedStmt.setInt(12, PaymentID);
		
		
		// execute the statement.
		preparedStmt.execute();
		
		
		con.close();
		
		String payments = readBackerPayments();
		output = "{\"status\":\"success\", \"data\": \"" +
				payments + "\"}";
		
		
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
					System.err.println(e.getMessage());
		}
		
			return output;
		}
		
	
	
	
	/*Method to delete  backed funds for incomplete projects.*/
	public String deleteBackerPayment(String PaymentID) {
		
		//declare variabe to capure output message.
		String output = "";
		 
		//check for connectivity.
		Connection con = dbConnect.connect();
		
		//String sql = "delete from paymentdb.gb_payments p where p.PaymentID > 0 AND p.ConceptID IN (select c.conceptCode from concept_service.concept c where c.status = '"+status+"')" ;
		
		String sql = "delete from paymentservice.gb_payments p where p.PaymentID=?";
		try{
			
			PreparedStatement preparedStmt = con.prepareStatement(sql);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PaymentID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Payments Deleted Successfully!!";
			
			String newBackerPayment = readBackerPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newBackerPayment + "\"}";
		}
		catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the backer payment.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
}
