<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/restorani.js"></script>
<title>Restorani</title>
</head>
<body>
<jsp:include page="/navigacija.jsp" />
 <%@ page import="porucivanjeHrane.model.Korisnik" %>
 <% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
 <% if(korisnik != null){ %>
<input type="hidden" id="uloga" value="<%=korisnik.getUloga()%>">
<% }%>

 <br>
  	<div class="form-row">
	    <div class="form-group col-md-2">
	      <input type="text" class="form-control" id="nazivPretraga" placeholder="Naziv..." style="margin:0% 20%">
	    </div>
   
	    <div class="form-group col-md-2">
		      <input type="text" class="form-control" id="adresaPretraga" placeholder="Adresa..." style="margin:0% 20%">
	    </div>
	    
	    <div class="form-group col-md-2">
	      <select id="kategorijaPretraga" class="form-control" style="margin:0% 20%">
	        <option value="">Kategorija...</option>
	      	<option value="Domaca_kuhinja">Domaca kuhinja</option>
			<option value="Rostilj">Rostilj</option>
			<option value="Kineski_restoran">Kineski restoran</option>
			<option value="Indijski_restoran">Indijski restoran</option>
			<option value="Poslasticarnica">Poslasticarnica</option>
			<option value="Picerija">Picerija</option>
	      </select>
    	</div>
    	
    	<div class="form-group ">
	    	<div class="col-sm-10 "> 
		  		<button type="button" class="btn btn-primary"  id="button" style="margin:0% 45%" onClick="pretraziRestorane()" >Pretrazi restorane</button>
		    </div>
  	 	</div>
  	</div>
   
<br>

<ul id="list"></ul>

</body>
</html>