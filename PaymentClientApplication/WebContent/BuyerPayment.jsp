<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import = "com.Payment" %>
      <%@page import="java.sql.*" %>
     <%@page import="util.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Buyer Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/BuyerPayment.js"></script>
</head>
<body>
<br><br>

		<div class="container"><div class="row"><div class="col-6">
		<div class = "card text-dark special-card ">
		
		<!-- Form to get buyer payment data -->
		
		<div class = "card-body">
		
		<h1 class="text-info" class="card text-center" class="card-title">Buyer Payment</h1><br>
		<form id="formBuyer" name="formBuyer">
		
		
		Payment Type:
		<select class="form-control input-lg" class="custom-select" id="PaymentType" placeholder="PaymentType" name = "PaymentType" required>
		<option value = "-1" >Select Payment Type</option>
		<option value="Debit">Debit</option>
		<option value="Credit">Credit</option>
		</select>
		<br>
		
		
		User Type:
		<select class="form-control input-lg" class="custom-select" id="UserType" placeholder="UserType" name = "UserType" required>
		<option value = "-1" >Select User Type</option>
		<option value="Backer">Backer</option>
		<option value="Buyer">Buyer</option>
		</select>
		<br>
		
		Bank:
		<input id="bank" name="bank" type="text"
		class="form-control form-control-sm">
		<br> 
		
		Payment Date:
		<input id="paymentDate" name="paymentDate" type="text"
		class="form-control form-control-sm">
		<br> 
		
		Card Number:
		<input id="cardNo" name="cardNo" type="text"
		class="form-control form-control-sm">
		<br>
		
		Name on card:
		<input id="cardName" name="cardName" type="text"
		class="form-control form-control-sm">
		<br>
		
		cvv:
		<input id="cvv" name="cvv" type="text"
		class="form-control form-control-sm">
		<br>
		
		Card Expiry Month:
		<input id="cmonth" name="cmonth" type="text"
		class="form-control form-control-sm">
		<br>
		
		Card Expiry Year:
		<input id="cyear" name="cyear" type="text"
		class="form-control form-control-sm">
		<br>
		
		ProductName:
      	<select class="form-control input-lg" class="custom-select" id="Product" placeholder="Product" name = "Product" required>
      	<option value = "-1" >Select Product</option>
      	<%
      	
    	//Connection object creation.
  		DBConnection dbConnect = new DBConnection();
      	
      	try{
      		
			Connection con = dbConnect.connect();
			
      		String Query= "select * from productservice.product";
      		  		
      		Statement stm = con.createStatement();
      		ResultSet rs = stm.executeQuery(Query);
      		
      		while(rs.next()){
      			
      			%> 
      			<option value="<%=rs.getString("productCode")%>"><%=rs.getString("productName") %></option>
      			<%
      		}
      	}catch(Exception ex){
      		ex.printStackTrace();
      		out.println("Error" + ex.getMessage());
      	}
      	
      	%>
       
      </select>
		<br>
		
		ConsumerID:
		<input id="cName" name="cName" type="text"
		class="form-control form-control-sm">
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Add Buyer Payments" class="btn btn-info">
		<input type="hidden" id="hidBuyerIDSave" name="hidBuyerIDSave" value="">
		</form>
		
		
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		
		</div> </div> </div></div></div>
		
		<br><br>
		
		<div class="container"><div class="row"><div class="col-6">
		
		
		<div id="divItemsGrid">
		<%
		Payment payObj = new Payment();
		out.print(payObj.readBuyerPayments());
		%>
		</div>
		
		</div> </div> </div>
<br><br>
</body>
</html>