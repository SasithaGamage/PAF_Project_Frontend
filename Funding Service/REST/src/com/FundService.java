package com;

import javax.swing.text.html.parser.Parser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.*;  
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

import model.Funds;

@Path("/Funds")
public class FundService {
	
	Funds fundObj = new Funds();

	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("researchID") String researchID, 
	@FormParam("funderName") String funderName,
	@FormParam("amount") String amount, 
	@FormParam("fundingDate") String fundingDate, 
	@FormParam("fundStatus") String fundStatus) 
	{ 
	 String output = fundObj.insertItem(researchID, funderName, amount,fundingDate,fundStatus); 
	 return output; 
	}
	
	@GET
	@Path("/funds") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
	 return fundObj.readItems(); 
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String fundID = itemObject.get("fundID").getAsString(); 
	 String researchID = itemObject.get("researchID").getAsString(); 
	 String funderName = itemObject.get("funderName").getAsString(); 
	 String amount = itemObject.get("amount").getAsString(); 
	 String fundingDate = itemObject.get("fundingDate").getAsString(); 
	 String fundStatus = itemObject.get("fundStatus").getAsString(); 
	 String output = fundObj.updateItem(fundID, researchID, funderName, amount, fundingDate, fundStatus); 
	 return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
		
		JsonObject fundObject = new JsonParser().parse(itemData).getAsJsonObject(); 
		//Read the value from the element <itemID>
		String fundID = fundObject.get("fundID").getAsString(); 
		String output = fundObj.deleteItem(fundID); 
		return output; 
	}



}
