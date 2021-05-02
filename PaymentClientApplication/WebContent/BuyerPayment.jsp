<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import = "com.Payment" %>
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

		<div class="container"><div class="row"><div class="col-6">
		<h1>Buyer Payment</h1>
		<form id="formBuyer" name="formBuyer">
		Payment Type:
		<input id="PaymentType" name="PaymentType" type="text"
		class="form-control form-control-sm">
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
		ProductID:
		<input id="pName" name="pName" type="text"
		class="form-control form-control-sm">
		<br>
		ConsumerID:
		<input id="cName" name="cName" type="text"
		class="form-control form-control-sm">
		<br>
		<input id="btnSave" name="btnSave" type="button" value="Save"
		class="btn btn-primary">
		<input type="hidden" id="hidItemIDSave"
		name="hidItemIDSave" value="">
		</form>
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		<div id="divItemsGrid">
		<%
		Payment payObj = new Payment();
		out.print(payObj.readPayments());
		%>
		</div>
		</div> </div> </div>

</body>
</html>