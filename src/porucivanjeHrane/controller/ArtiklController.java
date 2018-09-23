package porucivanjeHrane.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import porucivanjeHrane.dao.RestoranDAO;
import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Restoran;
import porucivanjeHrane.model.Korisnik.Uloga;

@Path("/restaurants/{restaurantId}/articles")
public class ArtiklController {


	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("restoranDAO") == null) {
	    	String contextPutanja = ctx.getRealPath("");
			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPutanja));
		}
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Artikl getArtikli(@PathParam(value = "restaurantId") int id, @PathParam(value = "id") int artiklId) {
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran = dao.find(id);
		if(restoran == null) {
			return null;
		}
		Artikl artikl=null;
		for(Artikl a : restoran.getArtikli()){
			if(a.getId() == artiklId){
				artikl=a;
				break;
			}
		}
		if(artikl == null || artikl.isObrisan()){
			return null;
		}

		return artikl;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artikl> getArtikliByRestaurant(@PathParam(value = "restaurantId") int id) {
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran = dao.find(id);
		if(restoran == null) {
			return null;
		}
		List<Artikl> artikli = restoran.getArtikli().stream().filter(a -> !a.isObrisan()).collect(Collectors.toList());
		return artikli;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addArticleInRestaurant(@PathParam(value="restaurantId") int id,Artikl artikl,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao=(RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran=dao.find(id);
		if(restoran==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		artikl.setId(restoran.getArtikli().size());
		artikl.setRestoranId(id);
		restoran.getArtikli().add(artikl);
		dao.sacuvajRestorane();
		return Response.status(Status.CREATED).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateArticlByRestaurant(Artikl artikl,@PathParam(value="restaurantId") int restoranId,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran=dao.find(restoranId);
		if(restoran==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		for(int i=0;i<restoran.getArtikli().size();i++){
			if(id==restoran.getArtikli().get(i).getId()){
				artikl.setId(id);
				artikl.setRestoranId(restoranId);
				restoran.getArtikli().set(i,artikl);
				dao.sacuvajRestorane();
				return Response.status(Status.OK).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteArticlByRestaurant(@PathParam(value="restaurantId") int restoranId,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		RestoranDAO dao = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran restoran=dao.find(restoranId);
		if(restoran==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		for(int i=0;i<restoran.getArtikli().size();i++){
			if(id==restoran.getArtikli().get(i).getId()){
				restoran.getArtikli().get(i).setObrisan(true);
				dao.sacuvajRestorane();
				return Response.status(Status.OK).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
