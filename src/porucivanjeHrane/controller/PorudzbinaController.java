package porucivanjeHrane.controller;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import porucivanjeHrane.dao.KorisnikDAO;
import porucivanjeHrane.model.Dostavljac;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Kupac;
import porucivanjeHrane.model.Porudzbina;
import porucivanjeHrane.model.Porudzbina.StatusPorudzbine;
import porucivanjeHrane.model.Korisnik.Uloga;

@Path("/orders")
public class PorudzbinaController {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init(){
		if(ctx.getAttribute("korisnikDAO")==null){
			String contextPutanja=ctx.getRealPath("");
			System.out.println(ctx.getRealPath(""));
			ctx.setAttribute("korisnikDAO",new KorisnikDAO(contextPutanja));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Porudzbina> getPorudzbine( @Context HttpServletRequest request) {
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if(korisnik==null || korisnik.getUloga()==Uloga.Kupac){
			return null;
		}
		List<Kupac> kupci = dao.findBuyers();	
		List<Porudzbina> porudzbine=new ArrayList<>();
		for(Kupac kupac:kupci){
			for(Porudzbina p:kupac.getPorudzbine()){
				if(korisnik.getUloga()==Uloga.Administrator){
					if(!p.isObrisana())
						porudzbine.add(p);
				}else if(korisnik.getUloga()==Uloga.Dostavljac){
					if(p.getStatus()==StatusPorudzbine.Poruceno){
						if(!p.isObrisana())
							porudzbine.add(p);
					}
				}
			}
		}
		
		return porudzbine;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(Porudzbina porudzbina,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()==Uloga.Dostavljac){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if(korisnik.getUloga() == Uloga.Kupac){
			porudzbina.setKupacUsername(korisnik.getKorisnickoIme());	
			Kupac kupac=(Kupac)korisnik;
			porudzbina.setId(kupac.getPorudzbine().size());
			
			boolean umanjeno = kupac.umanjiBodove(porudzbina.getUtrosenoBodova());
			if(!umanjeno) {
				porudzbina.setUtrosenoBodova(0);
			}
			kupac.getPorudzbine().add(porudzbina);
			
			
		}else{
			if(porudzbina.getKupacUsername() == null || porudzbina.getKupacUsername().equals("")){
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			Kupac kupac  = (Kupac) dao.findUsername(porudzbina.getKupacUsername());
			porudzbina.setId(kupac.getPorudzbine().size());
			kupac.getPorudzbine().add(porudzbina);
			
			if(porudzbina.getDostavljacUsername() != null && !porudzbina.getDostavljacUsername().equals("")){
				Dostavljac dostavljac  = (Dostavljac) dao.findUsername(porudzbina.getDostavljacUsername());
				porudzbina.setStatus(StatusPorudzbine.Dostava_u_toku);
				dostavljac.getDodeljenePorudzbine().add(porudzbina);
			}
			
			
		}
		dao.sacuvajKorisnike();
		return  Response.status(Status.CREATED).build();
	}
	
	
}
