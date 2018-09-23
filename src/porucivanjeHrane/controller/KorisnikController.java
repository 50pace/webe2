package porucivanjeHrane.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import porucivanjeHrane.dao.KorisnikDAO;
import porucivanjeHrane.model.Dostavljac;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Korisnik.Uloga;
import porucivanjeHrane.model.Kupac;

@Path("")
public class KorisnikController {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPutanja = ctx.getRealPath("");
	    	KorisnikDAO korisnikDAO= new KorisnikDAO(contextPutanja);
			ctx.setAttribute("korisnikDAO",korisnikDAO);
			Korisnik admin=new Korisnik("admin","admin","john","doe","1223-3446","admin@gmail.com",
					Date.valueOf(LocalDate.now()),Uloga.Administrator);
			Kupac kupac1=new Kupac("marko","1234","john","doe","1223-3446","admin@gmail.com",
					Date.valueOf(LocalDate.now()),Uloga.Kupac,0,new ArrayList<>(),new ArrayList<>());
			Kupac kupac2=new Kupac("djoka","1234","john","doe","1223-3446","admin@gmail.com",
					Date.valueOf(LocalDate.now()),Uloga.Kupac,3,new ArrayList<>(),new ArrayList<>());
			Dostavljac dos=new Dostavljac("baki","admin34","john","doe","1223-3446","admin@gmail.com",
					Date.valueOf(LocalDate.now()),Uloga.Dostavljac,null,new ArrayList<>());
			korisnikDAO.dodajKorisnika(admin);
			korisnikDAO.dodajKorisnika(dos);
			korisnikDAO.dodajKorisnika(kupac1);
			korisnikDAO.dodajKorisnika(kupac2);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public Response register(Korisnik korisnik) {
		korisnik.setUloga(Uloga.Kupac);
		korisnik.setDatumRegistracije(Date.valueOf(LocalDate.now()));
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Kupac kupac = new Kupac(korisnik);
		dao.dodajKorisnika(kupac);
		return Response.status(Status.CREATED).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response login(Korisnik korisnik, @Context HttpServletRequest request) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik logovaniKorisnik = dao.find(korisnik.getKorisnickoIme(), korisnik.getLozinka());
		if(logovaniKorisnik == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		request.getSession().setAttribute("korisnik", logovaniKorisnik);
		return Response.status(Status.OK).build();
	}
	
	@POST
	@Path("/logout")
	public Response login(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.status(Status.OK).build();
	}
	
	
	@PUT
	@Path("/korisnici/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeType(Korisnik korisnik, @PathParam(value="username") String username,  @Context HttpServletRequest request) {
		Korisnik kor = (Korisnik)request.getSession().getAttribute("korisnik");
		if(kor == null || kor.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		boolean updated=dao.update(username,korisnik);
		if(updated){
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/korisnici")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getAllUsers(){
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		List<Korisnik> listaKorisnika=new ArrayList<>(dao.findAll());
		return listaKorisnika;
	}
	
	@GET
	@Path("/kupci/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Kupac getKupac(@PathParam(value="username") String username,  @Context HttpServletRequest request) {
		Korisnik kor = (Korisnik)request.getSession().getAttribute("korisnik");
		if(kor == null || !kor.getKorisnickoIme().equals(username) || kor.getUloga() != Uloga.Kupac) {
			return null;
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Kupac kupac = (Kupac)dao.findUsername(username);
		return kupac;
	}
	
	@GET
	@Path("/deliverers/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Dostavljac getDeliverer(@PathParam(value="username") String username,  @Context HttpServletRequest request) {
		Korisnik kor = (Korisnik)request.getSession().getAttribute("korisnik");
		if(kor == null || !kor.getKorisnickoIme().equals(username) || kor.getUloga() != Uloga.Dostavljac) {
			return null;
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Dostavljac dostavljac = (Dostavljac)dao.findUsername(username);
		return dostavljac;
	}
	
	
	@Path("/deliverers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dostavljac> getDeliverers(){
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findAll().stream().filter( k -> k.getUloga() == Uloga.Dostavljac ).map(k-> (Dostavljac)k).collect(Collectors.toList());
	}
}
