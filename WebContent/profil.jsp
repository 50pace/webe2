<!DOCTYPE html>
<%@page import="porucivanjeHrane.model.Korisnik.Uloga"%>
<html>
<head>
<meta charset="UTF-8">
<title>Profil</title>
<jsp:include page="/navigacija.jsp" />
<script type="text/javascript" src="/PorucivanjeHrane/js/profil.js"></script>
</head>
<body>
 
	<%@ page import="porucivanjeHrane.model.Korisnik" %>
 	<% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
 	 <% if(korisnik != null){ %>
		<input type="hidden" id="uloga" value="<%=korisnik.getUloga()%>">
	<% }%>
 	<%if(korisnik != null && korisnik.getUloga()==Uloga.Kupac){ %>
		<h4>&nbsp;&nbsp;&nbsp;Istorija porudzbina</h4>
		<ul id= "listaPorudzbina"></ul>
		
		<h4>&nbsp;&nbsp;&nbsp;Omiljeni restorani</h4>
		<ul id= "listaRestorana"></ul>
	<%}else if(korisnik != null && korisnik.getUloga()==Uloga.Dostavljac) { %>
		<h4>&nbsp;&nbsp;&nbsp;Lista porudzbina</h4>
		<ul id="porudzbineDostavljaca"></ul>
		
		<br><h4>&nbsp;&nbsp;&nbsp;Vozilo</h4>
		
	<%}else if(korisnik != null && korisnik.getUloga()==Uloga.Administrator){ %>
	 	<br>	
		&nbsp;&nbsp;<a href="/PorucivanjeHrane/dodajRestoran"><button type="button" class="btn btn-primary">Dodaj restoran</button></a>
		&nbsp;&nbsp;<a href="/PorucivanjeHrane/dodajArtikl"><button type="button" class="btn btn-primary">Dodaj artikl</button></a>
		&nbsp;&nbsp;<a href="/PorucivanjeHrane/dodajVozilo"><button type="button" class="btn btn-primary">Dodaj vozilo</button></a>
		&nbsp;&nbsp;<a href="/PorucivanjeHrane/korisnici"><button type="button" class="btn btn-primary">Promijeni tip korisnika</button></a>
	<%} %>
</body>
</html>