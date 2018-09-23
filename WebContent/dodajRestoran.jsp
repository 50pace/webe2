<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Restorani</title>
<jsp:include page="/navigacija.jsp" />
<script type="text/javascript" src="/PorucivanjeHrane/js/dodajRestoran.js"></script>
</head>

<body>
<%@ page import="porucivanjeHrane.model.Korisnik" %>
<% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
<% if(korisnik == null || korisnik.getUloga()!=Korisnik.Uloga.Administrator){ %>
	  <p>Ne mozete pristupiti datoj stranici</p>
<% return;}%>

<h3 align="center">Dodavanje restorana</h3>
<br>
<form style="width:300px;margin:5% 10%" >

	<div class="form-group row">
   	 <label for="naziv" class="col-sm-2 col-form-label ml-0 pl-0"><b>Naziv</b></label>
     <div class="col-sm-10">
       <input type="text" class="form-control" id="naziv" style="margin:0% 15%" >
     </div>
  	</div>

	<div class="form-group row">
   	 <label for="naziv" class="col-sm-2 col-form-label ml-0 pl-0"><b>Adresa</b></label>
     <div class="col-sm-10">
       <input type="text" class="form-control" id="adresa" style="margin:0% 15%" >
     </div>
  	</div>
	
	<div class="form-group row ">
    <label for="tip" class="col-sm-2 col-form-label ml-0 pl-0"><b>Tip</b></label>
     <div class="col-sm-10">
      <select class="form-control" id="kategorija" style="margin:0% 15%">
      	<option value="Domaca_kuhinja">Domaca kuhinja</option>
		<option value="Rostilj">Rostilj</option>
		<option value="Kineski_restoran">Kineski restoran</option>
		<option value="Indijski_restoran">Indijski restoran</option>
		<option value="Poslasticarnica">Poslasticarnica</option>
		<option value="Picerija">Picerija</option>
      </select>
     </div>
    </div>

	<br/>
	
 <div class="form-group row">
    <div class="col-sm-10 ">
      <button type="button" style="margin:0% 38%" id="button" class="btn btn-primary" onClick="addRestaurant()">Dodaj restoran</button>
    </div>
  </div>

</form>

</body>
</html>