$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});


// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidaccNoSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "PaymentAPI",
			type: type,
			data: $("#formPayment").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onPaymentSaveComplete(response.responseText, status);
			}
		});
});


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidaccNoSave").val($(this).closest("tr").find('td:eq(0)').text());
	$("#accNo").val($(this).closest("tr").find('td:eq(0)').text());
	$("#units").val($(this).closest("tr").find('td:eq(1)').text());
	$("#addtionalUnits").val($(this).closest("tr").find('td:eq(2)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(3)').text());
	$("#dueDate").val($(this).closest("tr").find('td:eq(4)').text());
});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "PaymentAPI",
			type: "DELETE",
			data: "accNo=" + $(this).data("accNo"),
			dataType: "text",
			complete: function(response, status) {
				onPaymentDeleteComplete(response.responseText, status);
			}
		});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.stringify(response);
		if (status == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (status == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}





// CLIENT-MODEL================================================================
function validatePaymentForm() {
	// Acc No
	if ($("#accNo").val().trim() == "") {
		return "Insert Account Number.";
	}
	// Units
	if ($("#units").val().trim() == "") {
		return "Insert Units.";
	}
	// additional Units-------------------------------
	if ($("#addtionalUnits").val().trim() == "") {
		return "Insert additional units.";
	}
	
	// amount-------------------------------
	if ($("#amount").val().trim() == "") {
		return "Insert amount.";
		}
	// is numerical value
	var tmpUnits = $("#units").val().trim();
	if (!$.isNumeric(tmpUnits)) {
		return "Insert a numerical value for Units.";
	}
	// convert to decimal price
	$("#units").val(parseFloat(tmpUnits).toFixed(2));
	
	var tmpAddUnits = $("#addtionalUnits").val().trim();
	if (!$.isNumeric(tmpAddUnits)) {
		return "Insert a numerical value for Additional Units.";
	}
	// convert to decimal price
	$("#addtionalUnits").val(parseFloat(tmpAddUnits).toFixed(2));
	
	var tmpAmount = $("#amount").val().trim();
	if (!$.isNumeric(tmpAmount)) {
		return "Insert a numerical value for Amount.";
	}
	// convert to decimal price
	$("#units").val(parseFloat(tmpAmount).toFixed(2));
	// DATE------------------------
	if ($("#dueDate").val().trim() == "") {
		return "Insert due Date.";
	}
	return true;
}


function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.stringify(response);
		if (status == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (status == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	} $("#hidaccNoSave").val("");
	$("#formPayment")[0].reset();
}
