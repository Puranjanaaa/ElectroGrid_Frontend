<%@page import="com.PaymentRepository"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payment.js"></script>


</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Management</h1>
				<form id="formPayment" name="formPayment" method = "post" action = "payment.jsp">
					Account Number: <input id="accNo" name="accNo" type="text"
						class="form-control form-control-sm"> <br> 
						Units:
					<input id="units" name="units" type="text"
						class="form-control form-control-sm"> <br>
						 Additional Units : <input id="addtionalUnits" name="additionalUnits" type="text"
						class="form-control form-control-sm"> <br> 
						Amount : <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br> 
						Due Date : <input id="dueDate" name="dueDate" type="text"
						class="form-control form-control-sm"> <br> 
						<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidaccNoSave" name="hidaccNoSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPaymentGrid">
					<%
					PaymentRepository p1 = new PaymentRepository();
					out.print(p1.getPayments());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>