package porucivanjeHrane.controller;

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
import porucivanjeHrane.model.Dostavljac;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Korisnik.Uloga;
import porucivanjeHrane.model.Vozilo;

@Path("/deliverers/{delivererUsername}/cars")
public class VoziloController {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init(){
		if(ctx.getAttribute("korisnikDAO")==null){
			String contextPutanja=ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO",new KorisnikDAO(contextPutanja));
		}
	}
	
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Vozilo getCarsByDeliverer(@PathParam(value="id") int id,@PathParam(value="delivererUsername") String username){
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Dostavljac){
			return null;
		}
		Dostavljac dostavljac=(Dostavljac)k;
		if(dostavljac.getVozilo() == null || dostavljac.getVozilo().getId()!=id || dostavljac.getVozilo().isObrisan()){
			return null;
		}
		return dostavljac.getVozilo();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCarToDeliverer(Vozilo vozilo,@PathParam(value="delivererUsername") String username,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Administrator){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Dostavljac){
			return Response.status(Status.NOT_FOUND).build();
		}
		vozilo.setId(0);
		Dostavljac dostavljac=(Dostavljac)k;
		dostavljac.setVozilo(vozilo);
		dao.sacuvajKorisnike();
		return  Response.status(Status.CREATED).build();
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCarByDeliver(Vozilo vozilo,@PathParam(value="delivererUsername") String username,@PathParam(value="id")int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Administrator){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Dostavljac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Dostavljac dostavljac=(Dostavljac)k;
		if(dostavljac.getVozilo().getId()!=id){
			return Response.status(Status.NOT_FOUND).build();
		}
		vozilo.setId(id);
		dostavljac.setVozilo(vozilo);
		dao.sacuvajKorisnike();
		return Response.status(Status.OK).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteCarByDeliver(@PathParam(value="delivererUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Dostavljac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Dostavljac dostavljac=(Dostavljac)k;
		if(dostavljac.getVozilo().getId()!=id){
			return Response.status(Status.NOT_FOUND).build();
		}
		dostavljac.getVozilo().setObrisan(true);
		dao.sacuvajKorisnike();
		return Response.status(Status.OK).build();
	}

}
