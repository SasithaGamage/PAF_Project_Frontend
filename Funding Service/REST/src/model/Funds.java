package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Funds {
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
	 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_fund_management", "root", "Sasitha@2020"); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return con; 
	} 
	public String insertItem(String researchID, String funderName, String amount, String fundingDate, String fundStatus) 
	{ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for inserting."; 
			} 
			// create a prepared statement
			String query = " insert into funds (`fundID`,`researchID`,`funderName`,`amount`,`fundingDate`,`fundStatus`)"
					+ " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setInt(2, Integer.parseInt(researchID)); 
			preparedStmt.setString(3, funderName); 
			preparedStmt.setDouble(4, Double.parseDouble(amount)); 
			preparedStmt.setString(5, fundingDate); 
			preparedStmt.setString(6, fundStatus); 
			// execute the statement3
			preparedStmt.execute(); 
			con.close(); 
//			output = "Inserted successfully"; 
			String newItems = readItems(); 
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	public String readItems() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 	output = "<table border='1'><tr><th>Fund ID</th><th>ResearchID</th>" +
	 	"<th>Funder Name</th>" +
	 	"<th>Amount</th>" + 
	 	"<th>Funding Date</th>" +
	 	"<th>Fund Status</th>" +
	 	"<th>Update</th><th>Remove</th></tr>"; 
	 
	 	String query = "select * from funds"; 
	 	Statement stmt = con.createStatement(); 
	 	ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
		 String fundID = Integer.toString(rs.getInt("fundID")); 
		 String researchID = Integer.toString(rs.getInt("researchID")); 
		 String funderName = rs.getString("funderName"); 
		 String amount = Double.toString(rs.getDouble("amount")); 
		 String fundingDate = rs.getString("fundingDate"); 
		 String fundStatus = rs.getString("fundStatus");
	 

	 	// Add into the html table
	 	output += "<tr><td>" + fundID + "</td>"; 
	 	output += "<td>" + researchID + "</td>"; 
	 	output += "<td>" + funderName + "</td>"; 
	 	output += "<td>" + amount + "</td>"; 
	 	output += "<td>" + fundingDate + "</td>"; 
	 	output += "<td>" + fundStatus + "</td>"; 
	 	// buttons
	 	output += "<td><input name='btnUpdate' type='button' value='Update' "
	 			+ "class='btnUpdate btn btn-secondary' data-fundid='" + fundID + "'></td>"
	 			+ "<td><input name='btnRemove' type='button' value='Remove' "
	 			+ "class='btnRemove btn btn-danger' data-fundid='" + fundID + "'></td></tr>"; 
	 } 
	 	con.close(); 
	 	// Complete the html table
	 	output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	} 
	public String updateItem(String ID, String researchID, String funderName, String amount, String fundingDate, String fundStatus)
	{ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for updating."; 
			} 
			// create a prepared statement
			String query = "UPDATE funds SET researchID=?,funderName=?,amount=?,fundingDate=?,fundStatus=? WHERE fundID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(researchID)); 
			preparedStmt.setString(2, funderName); 
			preparedStmt.setDouble(3, Double.parseDouble(amount)); 
			preparedStmt.setString(4, fundingDate);
			preparedStmt.setString(5, fundStatus);
			preparedStmt.setInt(6, Integer.parseInt(ID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Updated successfully"; 
			String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	public String deleteItem(String fundID) 
	{ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{
				return "Error while connecting to the database for deleting."; 
			} 
			// create a prepared statement
			String query = "delete from funds where fundID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newItems = readItems(); 
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
			System.err.println(e.getMessage());
		} 
		return output; 
	 } 
}
