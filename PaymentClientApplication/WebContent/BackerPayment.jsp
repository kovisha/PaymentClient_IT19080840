<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import = "com.Payment" %>
     <%@page import="java.sql.*" %>
     <%@page import="util.DBConnection" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Backer Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/BackerPayment.js"></script>
</head>
<body>

		<div class="container"><div class="row"><div class="col-6">
		<h1>Backer Payment</h1>
		<form id="formBacker" name="formBacker">
		
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
		
		ConceptName:
      	<select class="form-control input-lg" class="custom-select" id="Concept" placeholder="Concept" name = "Concept" required>
      	<option value = "-1" >Select Concept</option>
      	<%
      	
    	//Connection object creation.
  		DBConnection dbConnect = new DBConnection();
      	
      	try{
      		
			Connection con = dbConnect.connect();
			
      		String Query= "select * from concept_service.concept";
      		  		
      		Statement stm = con.createStatement();
      		ResultSet rs = stm.executeQuery(Query);
      		
      		while(rs.next()){
      			
      			%> 
      			<option value="<%=rs.getString("conceptCode")%>"><%=rs.getString("conceptName") %></option>
      			<%
      		}
      	}catch(Exception ex){
      		ex.printStackTrace();
      		out.println("Error" + ex.getMessage());
      	}
      	
      	%>
       
      </select>
		<br>
		Consumer ID:
		<input id="cName" name="cName" type="text"
		class="form-control form-control-sm">
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		
		<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value = "">
		</form>
		
		
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		
		
		<div id="divItemsGrid">
		<%
		Payment payObj = new Payment();
		out.print(payObj.readBackerPayments());
		%>
		</div>
		
		</div> </div> </div>
<br><br>

</body>
</html>