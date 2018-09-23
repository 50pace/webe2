<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikli</title>
<jsp:include page="/navigacija.jsp" />
<script type="text/javascript" src="/PorucivanjeHrane/js/dodajArtikl.js"></script>
</head>

<body>
<%@ page import="porucivanjeHrane.model.Korisnik" %>
<% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
<% if(korisnik == null || korisnik.getUloga()!=Korisnik.Uloga.Administrator){ %>
	  <p>Ne mozete pristupiti datoj stranici</p>
<% return;}%>

<h3 align="center">Dodavanje artikla</h3>
<br>
<form style="width:300px;margin:5% 10%">

  <div class="form-group row">
    <label for="naziv" class="col-sm-2 col-form-label ml-0 pl-0"><b>Naziv</b></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="naziv" style="margin:0% 15%" >
    </div>
  </div>
  
  <div class="form-group row ">
    <label for="cijena" class="col-sm-2 col-form-label ml-0 pl-0"><b>Jedinicna cijena</b></label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="cijena" style="margin:0% 15%" >
    </div>
  </div>
  
  <div class="form-group row ">
    <label for="opis" class="col-sm-2 col-form-label ml-0 pl-0"><b>Opis</b></label>
     <div class="col-sm-10">
       <textarea class="form-control" id="opis" rows="3" style="margin:0% 15%"></textarea>
     </div>
  </div>
  
  <div class="form-group row ">
    <label for="kolicina" class="col-sm-2 col-form-label ml-0 pl-0"><b>Kolicina</b></label>
    <div class="col-sm-10">
      <input type="number" class="form-control" id="kolicina" style="margin:0% 15%" >
    </div>
  </div>
  
   <div class="form-group row ">
    <label for="tip" class="col-sm-2 col-form-label ml-0 pl-0"><b>Tip</b></label>
    <div class="col-sm-10">
      <select class="form-control" id="tip" style="margin:0% 15%">
      	<option value = "Pice">Pice</option>
      	<option value = "Jelo">Jelo</option>
      </select>
    </div>
  </div>
  
   <div class="form-group row">
    <label for="restoranSelect" class="col-sm-2 col-form-label ml-0 pl-0"><b>Restoran</b></label>
    <div class="col-sm-10">
      <select class="form-control" id="restoranSelect" style="margin:0% 15%" ></select>
    </div>
  </div>
  
  <br/>
  
  <div class="form-group row">
    <div class="col-sm-10 ">
      <button type="button" style="margin:0% 38%" id="button" class="btn btn-primary" onClick="dodajArtikl()">Dodaj artikl</button>
    </div>
  </div>
  
</form>
 
</body>
</html>