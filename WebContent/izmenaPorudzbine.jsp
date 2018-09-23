<!DOCTYPE html>
<%@page import="porucivanjeHrane.model.Korisnik.Uloga"%>
<html>
<head>
<meta charset="UTF-8">
<title>Izmena porudzbine</title>
<jsp:include page="/navigacija.jsp" />
<script type="text/javascript" src="/PorucivanjeHrane/js/izmenaPorudzbine.js"></script>
</head>
<body>
 <%@ page import="porucivanjeHrane.model.Korisnik" %>
 <% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
 <% if(korisnik != null){ %>
<% }%>
	
<br/>
	<div class="form-row">
	
		<div class="form-group col-md-2">
	      <input type="text" class="form-control" id="nazivPretraga" placeholder="Naziv..." style="margin:0% 20%">
	    </div>
	    
	    <div class="form-group col-md-1 ">
	      <input type="text" class="form-control" id="pocetnaCijena" placeholder="Od..." style="margin:0% 50%">
	  	</div>
	  	
	  	<div class="form-group col-md-1 ">
	      <input type="text" class="form-control" id="krajnjaCijena" placeholder="Do..." style="margin:0% 55%">
	  	</div>
	  	
	  	<div class="form-group col-md-2">
	      <select id="tipPretraga" class="form-control" style="margin:0% 30%">
	        <option value = "">Tip..</option>
	      	<option value = "Pice">Pice</option>
	      	<option value = "Jelo">Jelo</option>
	      </select>
    	</div>
    	
    	<div class="form-group ">
	    	<div class="col-sm-10 "> 
		  		<button type="button" class="btn btn-primary"  id="button" style="margin:0% 75%" onClick="pretraziArtikle()" >Pretrazi artikle</button>
		    </div>
  	 	</div>
  	 	
	</div> 
 	
<br/>
	<ul id="list"></ul>
		<br>
		<h3>&nbsp;&nbsp;&nbsp;Korpa</h3>
		<ul id="korpa"> </ul>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="napomena">Napomena</label>
		<textarea id="napomena"></textarea>
			<label for="kupac">Kupac</label>
			<select id="kupac"></select>
			<label for="dostavljac">Dostavljac</label>
			<select id="dostavljac"><option value=""></option></select>
			<label for="dostavljac">Status porudzbine</label>
			<select id="statusPorudzbine">
				<option value="Poruceno">Poruceno</option>
				<option value="Dostava_u_toku">Dostava u toku</option>
				<option value="Dostavljeno">Dostavljeno</option>
				<option value="Otkazano">Otkazano</option>
				
			</select>
		&nbsp;&nbsp;<button class="btn btn-success" onClick="izmeni()">Izmeni</button>
		<br><br>
	
</body>
</html>