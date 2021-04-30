package com;
import model.Funds;

import java.sql.PreparedStatement;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Funds")
public class FundService {
	
	Funds funds1 = new Funds();
	
	RequestValidator rs = new RequestValidator();
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addFundDetails(String fundData) {
		JsonObject result = new JsonObject();
		result.addProperty("status", "error_1");
		int fundID;
		
		int researchID;
		String funderName;
		String amount;
		String fundingDate;
		String fundStatus;
		
		try {
			JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject();
			// request validation
			if (!rs.validate(fundObject.get("key").getAsString())) {
				return result.toString();
			}
			

			if (fundObject.has("funds")) {

				for (JsonElement singleItem : fundObject.get("funds").getAsJsonArray()) {
					JsonObject fundObj = singleItem.getAsJsonObject();
//					fundID = fundObj.get("fundID").getAsInt();
					researchID = fundObj.get("researchID").getAsInt();
					funderName = fundObj.get("funderName").getAsString();
					amount = fundObj.get("amount").getAsString();
					fundingDate = fundObj.get("fundingDate").getAsString();
					fundStatus = fundObj.get("fundStatus").getAsString();
					funds1.addFundPayment(researchID,funderName,amount,fundingDate,fundStatus);

			}
				result.addProperty("status", "done_all");

//				(fundObject.has("user_id"))
			} else  {
//				fundID = fundObject.get("fundID").getAsInt();
				researchID = fundObject.get("researchID").getAsInt();
				funderName = fundObject.get("funderName").getAsString();
				amount = fundObject.get("amount").getAsString();
				fundingDate = fundObject.get("fundingDate").getAsString();
				fundStatus = fundObject.get("fundStatus").getAsString();
				if (funds1.addFundPayment(researchID,funderName,amount,fundingDate,fundStatus)) {
					result.addProperty("status", "done");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.addProperty("status", "error_");
		}

		return result.toString();
	}
	//
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateFundPayment(String fundData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject itemObject = new JsonParser().parse(fundData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String fundID = itemObject.get("fundID").getAsString();
	 int researchID = itemObject.get("researchID").getAsInt(); 
	 String funderName = itemObject.get("funderName").getAsString(); 
	 String amount = itemObject.get("amount").getAsString(); 
	 String fundingDate = itemObject.get("fundingDate").getAsString(); 
	 String fundStatus = itemObject.get("fundStatus").getAsString(); 
	 String output = funds1.updateFundPayment(fundID, researchID, funderName, amount, fundingDate, fundStatus); 
	 return output; 
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeItem(String fundData) {
		String returnValue = "failed";
		JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject();
		String key = fundObject.get("key").getAsString();
		// request validation
		if (!rs.validate(key)) {
			JsonObject result = new JsonObject();
			result.addProperty("status", "error_unauthorized");
			return result.toString();
		}
		
		if (!fundObject.has("fundID")) {
			String fundID = fundObject.get("fundID").getAsString();
			// delete all
			returnValue = funds1.deleteFundPayment(fundID);
			return "{status:" + returnValue + "}";
		}
		String fundID = fundObject.get("fundID").getAsString();
		// delete one by one
		returnValue = funds1.deleteFundPayment(fundID);

		return "{status:" + returnValue + "}";
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllresearchProjects(@DefaultValue("") @QueryParam("key") String key)
	{
//		JsonObject fundObj = new JsonParser().parse(fundData).getAsJsonObject();
//		String key = fundObj.get("key").getAsString();
//		
		if(!rs.validate(key)) {
			JsonObject results = new JsonObject();
			results.addProperty("Status", "error_unauthorized");
			return results.toString();
		}
		
		
		return funds1.getAllFundDetails();
	}

	
	@GET
	@Path("/fundDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public String readItem(@DefaultValue("0") @QueryParam("fundID") Integer fundID,
			@DefaultValue("") @QueryParam("key") String key) {
		// request validation
//		JsonObject itemObj = new JsonParser().parse(fundData).getAsJsonObject();
//		String key = itemObj.get("key").getAsString();
//
//		JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject();
//		int fundID = fundObject.get("fundID").getAsInt();
		
		if(!rs.validate(key)) {
			JsonObject results = new JsonObject();
			results.addProperty("Status", "error_unauthorized");
			return results.toString();
		}
		
			
		return funds1.getFundDetails(fundID);
	}


}
