<%@ page import = "model.Funds" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/main.js"></script>
</head>
<body>

<form id="formUser" name="formUser">
  <div class="form-group">
    <label for="researchID">Research ID</label>
    <input type="text" class="form-control form-control-sm" id="researchID" name ="researchID" placeholder="Enter Research ID">
    
  </div>
  <div class="form-group">
    <label for="funderName">Funder Name</label>
    <input type="text" class="form-control form-control-sm" id="funderName" name = "funderName" placeholder="Enter Funder Name">
  </div>
  <div class="form-group">
    <label for="amount">Amount</label>
    <input type="text" class="form-control form-control-sm" id="amount" name = "amount" placeholder="Enter amount">
  </div>
  <div class="form-group">
    <label for="fundingDate">Funding Date</label>
    <input type="text" class="form-control form-control-sm" id="fundingDate" name = "fundingDate" placeholder="YYYY.MM.DD">
  </div>
  <div class="form-group">
    <label for="fundStatus">Fund Status</label>
    <input type="text" class="form-control form-control-sm" id="fundStatus" name = "fundStatus" placeholder="Enter Fund Status">
  </div>
 
  <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
  <input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
  
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divItemsGrid">
 <%
 Funds fundObj = new Funds(); 
 out.print(fundObj.readItems()); 
 %>
</div>


</body>
</html>