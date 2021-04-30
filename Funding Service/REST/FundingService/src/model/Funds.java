package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Funds 
{ //A common method to connect to the DB
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_fund_management", "paf_user", "^paf_user_pw_000"); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	} 
	
	
	public boolean addFundPayment(int researchID, String funderName, String amount, String fundingDate,String fundStatus) 
	{ 
//		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{return false; } 
			// create a prepared statement
			String query = " insert into funds (`fundID`,`researchID`,`funderName`,`amount`,`fundingDate`,`fundStatus`)"
					+ " values (?, ?, ?, ?, ?, ?)"; 
//			(`researchID`,`funderName`,`amount`,`fundingDate`,`fundStatus`)
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, researchID); 
			preparedStmt.setString(3, funderName); 
			preparedStmt.setDouble(4, Double.parseDouble(amount)); 
			preparedStmt.setString(5, fundingDate);
			preparedStmt.setString(6, fundStatus); 
			// execute the statement3
			preparedStmt.execute(); 
			con.close(); 
//			output = "Inserted successfully"; 
		} 
		catch (Exception e) 
		{ 
//			output = "Error while inserting the fund details."; 
			System.err.println(e.getMessage()); 
			return false;
		} 
		return true; 
	} 
	
	
	public String updateFundPayment(String fundID, int researchID, String funderName, String amount, String fundingDate, String fundStatus)
	{ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{return "Error while connecting to the database for updating."; } 
			// create a prepared statement
			String query = "UPDATE funds SET researchID=?,funderName=?,amount=?,fundingDate=?,fundStatus=? WHERE fundID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, researchID);
			preparedStmt.setString(2, funderName); 
			preparedStmt.setDouble(3,Double.parseDouble(amount)); 
			preparedStmt.setString(4,fundingDate); 
			preparedStmt.setString(5, fundStatus); 
			preparedStmt.setInt(6, Integer.parseInt(fundID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Updated successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while updating the fund details."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	
	public String deleteFundPayment(String fundID) 
	{ 
		String output = ""; 
		try
		{ 
			Connection con = connect(); 
			if (con == null) 
			{return "Error while connecting to the database for deleting."; } 
			// create a prepared statement
			String query = "delete from funds where fundID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Deleted successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while deleting the fund details."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	public String getAllFundDetails()
	{
		JsonObject fundDetails = new JsonObject();

		String output = "error";

		try {

			Connection con = connect();
			if(con == null)
			{
				return fundDetails.toString();
			}

			String readSql = "SELECT * FROM funds";
			PreparedStatement statement = con.prepareStatement(readSql);
			ResultSet results = statement.executeQuery(readSql);

			JsonArray fundArray = new JsonArray();
			

			while(results.next())
			{
				JsonObject fundObject = new JsonObject();
				fundObject.addProperty("fundID", results.getInt(1));
				fundObject.addProperty("researchID", results.getString(2));
				fundObject.addProperty("funderName", results.getString(3));
				fundObject.addProperty("amount", results.getDouble(4));
				fundObject.addProperty("fundingDate", results.getString(5));
				fundObject.addProperty("fundStatus", results.getString(6));
				
				fundArray.add(fundObject);
			}

			fundDetails.add("funds", fundArray);

			con.close();

		}catch(Exception e)
		{
			System.out.print("Error While Reading from the Database");
			System.out.print(e);
			fundDetails.addProperty("status", "error");

		}

		return fundDetails.toString();

	}
	
	public String getFundDetails(int fundID) {
		JsonObject fundData = new JsonObject();

		try {

			Connection con = connect();
			if (con == null) {
				return fundData.toString();
			}
			// create a prepared statement
			String query = " select * from funds where fundID = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, fundID);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();
			JsonObject fundItems = new JsonObject();
			while (rs.next()) {

//				fundItems.addProperty("fundID", rs.getInt(1));
				fundItems.addProperty("researchID", rs.getInt(2));
				fundItems.addProperty("funderName", rs.getString(3));
				fundItems.addProperty("amount", rs.getDouble(4));
				fundItems.addProperty("fundingDate", rs.getString(5));
				fundItems.addProperty("fundStatus", rs.getString(6));
				array.add(fundItems);

			}
			fundData.add("funds", array);

		} catch (Exception e) {

			e.printStackTrace();
			fundData.addProperty("status", "error");
			return fundData.toString();

		}

		return fundData.toString();
	}
	
	
	
	
} 
