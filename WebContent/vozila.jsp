<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="/PorucivanjeHrane/js/vozila.js"></script>
</head>
<body>
<jsp:include page="/navigacija.jsp" />
 <%@ page import="porucivanjeHrane.model.Korisnik" %>
 <% Korisnik korisnik = (Korisnik)session.getAttribute("korisnik"); %>
 <% if(korisnik != null){ %>
<input type="hidden" id="uloga" value="<%=korisnik.getUloga()%>">
<% }%>
<ul id="list"></ul>

</body>
</html>