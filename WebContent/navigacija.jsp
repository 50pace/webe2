<!DOCTYPE html>
<%@page import="porucivanjeHrane.model.Korisnik.Uloga"%>
<html>
<head>
<meta charset="UTF-8">
<title>Porucivanje hrane</title>
<script type="text/javascript" src="/PorucivanjeHrane/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/PorucivanjeHrane/js/navigacija.js"></script>
<link rel="stylesheet" href="/PorucivanjeHrane/bootstrap/css/bootstrap.min.css" >

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	       <li class="nav-item active">
	   		<a class="nav-link" href="/PorucivanjeHrane">Porucivanje hrane</a>
	  	   </li>
	       <li class="nav-item active">
	        <a class="nav-link" href="/PorucivanjeHrane">Home <span class="sr-only">(current)</span></a>
	       </li>
	       <li class="nav-item">
	        <a class="nav-link " href="/PorucivanjeHrane/restorani" >Restorani</a>
	       </li>
	      
	      <%@ page import="porucivanjeHrane.model.Korisnik" %>
	      <% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
	      <% if(korisnik == null){ %>
	      <li class="nav-item">
	        <a class="nav-link" href="/PorucivanjeHrane/login">Logovanje</a>
	      </li>
	      
	      <li class="nav-item">
	        <a class="nav-link " href="/PorucivanjeHrane/register">Registrovanje</a>
	      </li>
	      <% }else{%>
	      
	     
	      <li class="nav-item">
	        <a class="nav-link disabled" href="/PorucivanjeHrane/profili/<%= korisnik.getKorisnickoIme() %>" ><%= korisnik.getKorisnickoIme() %></a>	
	      </li>
	      
	      <% if(korisnik.getUloga() != Uloga.Kupac){ %>
	      <li class="nav-item">
	        <a class="nav-link disabled" href="/PorucivanjeHrane/porudzbine" >Porudzbine</a>	
	      </li>
	         
	      <%} }%>
	    </ul>
	    <% if(korisnik != null){ %>
	    <button class="btn btn-primary"  onClick="logout()">Izloguj se</button>   
	    <%} %>
	  </div>
	</nav>
</body>
</html>