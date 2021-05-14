$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
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
 url : "FundServlet", 
 type : type, 
 data : $("#formUser").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemSaveComplete(response.responseText, status); 
 } 
 }); 
});


function onItemSaveComplete(response, status)
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
	} else
	{ 
		$("#alertError").text("Unknown error while saving.."); 
		$("#alertError").show(); 
	}
	$("#hidItemIDSave").val(""); 
	$("#formItem")[0].reset(); 
}

$(document).on("click", ".btnUpdate", function(event)
{ 
	$("#hidItemIDSave").val($(this).data("fundid")); 
	$("#researchID").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#funderName").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#amount").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#fundingDate").val($(this).closest("tr").find('td:eq(4)').text());
	$("#fundStatus").val($(this).closest("tr").find('td:eq(5)').text());
	
});

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		url : "FundServlet", 
		type : "DELETE", 
		data : "fundID=" + $(this).data("fundid"),
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onItemDeleteComplete(response.responseText, status); 
		} 
	}); 
})

function onItemDeleteComplete(response, status)
{ 
if (status == "success") 
{ 
	var resultSet = JSON.parse(response); 
	if (resultSet.status.trim() == "success") 
	{ 
		$("#alertSuccess").text("Successfully deleted."); 
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
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} 
	else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	}
} 
function validateItemForm()
{
	//Research Name
	if ($("#researchID").val().trim() == "")
	{
		return "Insert Research ID.";
	}
	//Researcher Name
	if ($("#funderName").val().trim() == "")
	{
		return "Enter Funder Name.";
	}
	//Research Category
	if ($("#amount").val().trim() == "")
	{
		return "Enter Fund Amount.";
	}
	//Research Description
	if ($("#fundingDate").val().trim() == "")
	{
		return "Enter Funding Date.";
	}
	//Research Cost
	if ($("#fundStatus").val().trim() == "")
	{
		return "Enter Fund Status.";
	}


	return true;
}



//$(document).ready(function(){  
//    $('#search').keyup(function(){  
//         search_table($(this).val());  
//    });  
//    function search_table(value){  
//         $('#employee_table tr').each(function(){  
//              var found = 'false';  
//              $(this).each(function(){  
//                   if($(this).text().toLowerCase().indexOf(value.toLowerCase()) >= 0)  
//                   {  
//                        found = 'true';  
//                   }  
//              });  
//              if(found == 'true')  
//              {  
//                   $(this).show();  
//              }  
//              else  
//              {  
//                   $(this).hide();  
//              }  
//         });  
//    }  
//});  

