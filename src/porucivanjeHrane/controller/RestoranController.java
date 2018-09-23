package porucivanjeHrane.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import porucivanjeHrane.dao.RestoranDAO;
import porucivanjeHrane.dto.RestoranDTO;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Korisnik.Uloga;
import porucivanjeHrane.model.Kupac;
import porucivanjeHrane.model.Restoran;

@Path("/restaurants")
public class RestoranController {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restoranDAO") == null) {
	    	String contextPutanja = ctx.getRealPath("");
			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPutanja));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPutanja = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPutanja));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RestoranDTO> getRestorani( @Context HttpServletRequest request) {
		
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		List<RestoranDTO> restorani = new ArrayList<>();
		dao.findAll().stream().forEach(r -> restorani.add(new RestoranDTO(r, false)));
		
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik != null && korisnik.getUloga() == Uloga.Kupac) {
			Kupac kupac = (Kupac)korisnik;
			for(Restoran r : kupac.getOmiljeniRestorani()){
				for(RestoranDTO rDTO : restorani){
					if(rDTO.getId() == r.getId()){
						rDTO.setOmiljen(true);
					}
				}
			}
		}
		return restorani;
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Restoran getRestoran(@PathParam(value="id") int id) {
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran= dao.find(id);
		return restoran;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRestaurant(Restoran restoran, @Context HttpServletRequest request) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		dao.dodajRestoran(restoran);
		return Response.status(Status.CREATED).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRestaurant(Restoran restoran, @PathParam(value = "id") int id,@Context HttpServletRequest request){
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		restoran.setId(id);
		boolean updated=dao.update(id, restoran);
		if(updated){
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteRestaurant(@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		boolean deleted=dao.delete(id);
		if(deleted){
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/{id}")
	@POST
	public Response addFavoriteRestaurant(@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik logovaniKorisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(logovaniKorisnik == null || logovaniKorisnik.getUloga() != Uloga.Kupac) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		RestoranDAO daoR = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran=daoR.find(id);
		if(restoran==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		Kupac kupac=(Kupac)logovaniKorisnik;
		kupac.getOmiljeniRestorani().add(restoran);
		dao.sacuvajKorisnike();
		return Response.status(Status.OK).build();
	}
}
