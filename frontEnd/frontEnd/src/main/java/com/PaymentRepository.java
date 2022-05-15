package com;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
//import java.sql.*;
import java.sql.Statement;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.WebResource;

import com.Payment;

import java.sql.Connection;

public class PaymentRepository {

	Connection con = null;

	public PaymentRepository() {

		// create db connection
		String url = "jdbc:mysql://localhost:3308/electrogrid";
		String driverName = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "";
		try {

			Class.forName(driverName);
			con = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			System.out.print(e);
		}

	}

	// retrieve all records from payment table
	public String getPayments() {

		String sql = "select * from payment";
		try {

			Statement st = con.createStatement();
			String output = "<table border=\"1\"><tr><th>Account Number</th>" + "<th>Number of Units</th> "
					+ "<th>Additional Units</th>" + "<th>Amount</th>" + "<th>Due Date</th></tr>";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				Payment p = new Payment();
				p.setAccNo(rs.getInt(1));
				p.setUnits(rs.getDouble(2));
				p.setAdditionalUnits(rs.getDouble(3));
				
				p.setAmount(rs.getDouble(4));
				p.setDueDate(rs.getString(5));

				output += "<tr><td>" + p.getAccNo() + "</td>";
				output += "<td>" + p.getUnits() + "</td>";
				output += "<td>" + p.getAdditionalUnits() + "</td>";
				output += "<td>" + p.getAmount() + "</td>";
				output += "<td>" + p.getDueDate() + "</td>";

				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-accno='" + p.getAccNo() + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-accno='" + p.getAccNo() + "'></td></tr>";
			}
			output += "</table>";
			return output;

		} catch (Exception e) {
			System.out.print(e);
			return "Error occured during retrieving data";
		}

	}

	// create payment record in payment table
	public String create(String accNo, String units, String additionalUnits, String amount, String dueDate) {
		String output = "";
		String sql = "insert into payment (`accNo`, `units`,`additionalUnits`, `amount`,`dueDate`)values(?,?,?,?,?)";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(accNo));
			st.setDouble(2, Double.parseDouble(units));
			st.setDouble(3, Double.parseDouble(additionalUnits));
			st.setDouble(4, Double.parseDouble(amount));
			st.setString(5, dueDate);
			st.executeUpdate();
			String newPayments = getPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.out.print(e);
		}
		return output;
	}

	// update payment record
	public String update(String accNo, String units, String additionalUnits, String amount, String dueDate) {
		String output = "";
		String sql = "update payment set units =?,additionalUnits=?,amount=?,dueDate=?  where accNo=? ";
		try {
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(5, Integer.parseInt(accNo));
			st.setDouble(1, Double.parseDouble(units));
			st.setDouble(2, Double.parseDouble(additionalUnits));
			st.setDouble(3, Double.parseDouble(amount));
			st.setString(4, dueDate);
			st.executeUpdate();

			String newItems = getPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// delete payment record
	public String delete(String accNo) {
		String output = "";
		String sql = "delete from payment where accNo=? ";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(accNo));
			st.executeUpdate();

			String newPayments = getPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
}
