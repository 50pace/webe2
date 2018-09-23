<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vozila</title>
<jsp:include page="/navigacija.jsp" />
<script type="text/javascript" src="/PorucivanjeHrane/js/dodajVozilo.js"></script>
</head>

<body>
<%@ page import="porucivanjeHrane.model.Korisnik" %>
<% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
<% if(korisnik == null || korisnik.getUloga()!=Korisnik.Uloga.Administrator){ %>
	  <p>Ne mozete pristupiti datoj stranici</p>
<% return;}%>

<h3 align="center">Dodavanje vozila</h3>
<br>
<form style="width:340px;margin:2% 10%">
  	
	  <div class="form-group row " >
	    <label for="marka" style="margin:0% 15%"><b>Marka:</b></label>
	    <input type="text" class="form-control" id="marka" style="margin:0% 15%">
	  </div>
	  
	  <div class="form-group row ">
	    <label for="model" style="margin:0% 15%"><b>Model:</b></label>
	    <input type="text" class="form-control" id="model" style="margin:0% 15%">
	  </div>
	  
	  
	  <div class="form-group row ">
	      <label for="regOznaka" style="margin:0% 15%"><b>Registarska oznaka:</b></label>
	      <input type="text" class="form-control" id="regOznaka" style="margin:0% 15%">
	  </div>
	  
      <div class="form-group row ">
	      <label for="tipovi" style="margin:0% 15%"><b>Tip:</b></label>
	      <select id="tipovi" class="form-control" style="margin:0% 15%">
	      	<option value="Bicikl">Bicikl</option>
			<option value="Skuter">Skuter</option>
			<option value="Automobil">Automobil</option>
	      </select>
      </div>
      
      <div class="form-group row ">
	      <label for="datum" style="margin:0% 15%"><b>Godina proizvodnje:</b></label>
	      <input type="date" class="form-control" id="datum" style="margin:0% 15%">
      </div>
     
	  <div class="form-group row">
	    <div class="form-check">
	      <input class="form-check-input" type="checkbox" id="upotreba" style="margin:20% 310%">
	       <label class="form-check-label" for="upotreba" style="margin:15% 50%">
	        <b>Upotreba</b>
	      </label>
	    </div>
	  </div>
  
  	  <div class="form-group row ">
	      <label for="dostavljaci" style="margin:0% 15%"><b>Dostavljac:</b></label>
	      <select id="dostavljaci" class="form-control" style="margin:0% 15%">
	      	
	      </select>
      </div>
  
  	  <div class="form-group row">
	     <label for="napomena" style="margin:0% 15%"><b>Napomena:</b></label>
	     <div class="col-sm-10">
	       <textarea class="form-control" id="napomena" rows="3" style="margin:0% 15%"></textarea>
	     </div>	
	  </div>
	 <div class="form-group row">
    	<div class="col-sm-10 "> 
	  		<button type="button" class="btn btn-primary" style="margin:5% 15%" id="button" onClick="dodajVozilo()">Dodaj vozilo</button>
	    </div>
  	 </div>
</form>

</body>
</html>