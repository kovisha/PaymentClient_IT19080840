$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}	
		$("#alertError").hide();
});


//SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	
	var status = validateBuyerForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
	{
	url : "BuyerAPI",
	type : type,
	data : $("#formBuyer").serialize(),
	dataType : "text",
	complete : function(response, status)
	{
		onBuyerPaymentSaveComplete(response.responseText, status);
	}
	});
});


function onBuyerPaymentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
	if (resultSet.status.trim() == "success")
	{
	$("#alertSuccess").text("Successfully saved.");
	$("#alertSuccess").show();
	$("#divItemsGrid").html(resultSet.data);
	} 
	else if (resultSet.status.trim() == "error")
	{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
		$("#hidItemIDSave").val("");
		$("#formBuyer")[0].reset();
}


// CLIENT-MODEL================================================================
function validateBuyerForm()
{

if ($("#bank").val().trim() == "")
{
	return "Insert Bank Name.";
}

if ($("#cardNo").val().trim() == "")
{
	return "Insert Card Number.";
}

if ($("#cardName").val().trim() == "")
{
return "Insert Name On Card.";
}

if ($("#cvv").val().trim() == "")
{
return "Insert cvv.";
}

if ($("#cmonth").val().trim() == "")
{
return "Insert expiry month.";
}

if ($("#cyear").val().trim() == "")
{
return "Insert expiry year.";
}

return true;
}



$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidItemIDSave").val($(this).data("itemid"));
	
	$("#PaymentType").val($(this).closest("tr").find('td:eq(0)').text());
	$("#UserType").val($(this).closest("tr").find('td:eq(1)').text());
	$("#bank").val($(this).closest("tr").find('td:eq(2)').text());
	$("#paymentDate").val($(this).closest("tr").find('td:eq(3)').text());
	$("#cardNo").val($(this).closest("tr").find('td:eq(4)').text());
	$("#cardName").val($(this).closest("tr").find('td:eq(5)').text());
	$("#cvv").val($(this).closest("tr").find('td:eq(6)').text());
	$("#cmonth").val($(this).closest("tr").find('td:eq(7)').text());
	$("#cyear").val($(this).closest("tr").find('td:eq(8)').text());
	$("#Product").val($(this).closest("tr").find('td:eq(10)').text());
	$("#cName").val($(this).closest("tr").find('td:eq(11)').text());
});



$(document).on("click", ".btnRemove", function(event)
{
$.ajax(
{
	url : "BuyerAPI",
	type : "DELETE",
	data : "PaymentID=" + $(this).data("itemid"),
	dataType : "text",
	complete : function(response, status)
{
	onBuyerPaymentDeleteComplete(response.responseText, status);
}
});
})


function onBuyerPaymentDeleteComplete(response, status)
{
if (status == "success")
{
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success")
	{
		$("#alertSuccess").text("Successfully deleted.");
		$("#alertSuccess").show();
		$("#divItemsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}




