package porucivanjeHrane.controller;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import porucivanjeHrane.model.Porudzbina;
import porucivanjeHrane.model.Porudzbina.StatusPorudzbine;


@Path("/buyers/{buyerUsername}/orders")
public class PorudzbinaKupacController {

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
	public Porudzbina getOrder(@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Administrator){
			return null;
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Kupac){
			return null;
		}
		Kupac kupac=(Kupac)k;
		
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id){
				return kupac.getPorudzbine().get(i);
			}
		}
		return null;
		
	}
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderByAdmin(Porudzbina porudzbina,@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Administrator){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Kupac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Kupac kupac=(Kupac)k;
		
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id && kupac.getPorudzbine().get(i).getKupacUsername().equals(username)){
				Porudzbina staraPorudzbina = kupac.getPorudzbine().get(i);
				porudzbina.setId(id);
				kupac.getPorudzbine().set(i,porudzbina);
				if(!staraPorudzbina.getKupacUsername().equals(porudzbina.getKupacUsername())) {
					kupac.getPorudzbine().remove(i);
					Kupac noviKupac= (Kupac)dao.findUsername(porudzbina.getKupacUsername());
					noviKupac.getPorudzbine().add(porudzbina);
				}
				
				int j = 0;
				if(staraPorudzbina.getDostavljacUsername() != null && !staraPorudzbina.getDostavljacUsername().equals("") ) {
					Korisnik k1 =dao.findUsername(staraPorudzbina.getDostavljacUsername());
					if(k1 != null) {
						Dostavljac stariDostavljac= (Dostavljac)k1;
						for(;j<stariDostavljac.getDodeljenePorudzbine().size(); j++) {
							Porudzbina p = stariDostavljac.getDodeljenePorudzbine().get(j);
							if(p.getId() == id && p.getKupacUsername().equals(username)) {
								break;
							}
						}
						stariDostavljac.getDodeljenePorudzbine().set(j, porudzbina);
					}
				}
				
				if( !porudzbina.getDostavljacUsername().equals(staraPorudzbina.getDostavljacUsername())) {
					Korisnik k1 =dao.findUsername(staraPorudzbina.getDostavljacUsername());
					if(k1 != null) {
						Dostavljac stariDostavljac= (Dostavljac)k1;
						stariDostavljac.getDodeljenePorudzbine().remove(j);
					}
					Korisnik k2= dao.findUsername(porudzbina.getDostavljacUsername());
					if(k2 != null) {
						Dostavljac noviDostavljac = (Dostavljac)k2;
						noviDostavljac.getDodeljenePorudzbine().add(porudzbina);
					}
				}
				
				
				dao.sacuvajKorisnike();
				return Response.status(Status.OK).build();
			}
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/{id}")
	@DELETE
	public Response deleteOrder(@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik == null || korisnik.getUloga() != Uloga.Administrator) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Kupac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Kupac kupac=(Kupac)k;
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id && kupac.getPorudzbine().get(i).getKupacUsername().equals(username)){
				Porudzbina p = kupac.getPorudzbine().get(i);
				p.setObrisana(true);
				if(	p.getDostavljacUsername() != null && !p.getDostavljacUsername().trim().equals("") ){
					Dostavljac dostavljac = (Dostavljac)dao.findUsername(p.getDostavljacUsername());
					for(Porudzbina por : dostavljac.getDodeljenePorudzbine()){
						if(por.getId() == id && por.getKupacUsername().equals(username)){
							por.setObrisana(true);
						}
					}
				}
				dao.sacuvajKorisnike();
				return Response.status(Status.OK).build();
			}
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	@Path("/take/{id}")
	@PUT
	public Response updateOrderByDeliverer(@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Dostavljac){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Kupac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Kupac kupac=(Kupac)k;
		Dostavljac dostavljac = (Dostavljac)korisnik;
		for(Porudzbina p: dostavljac.getDodeljenePorudzbine()){
			if(p.getStatus() == StatusPorudzbine.Dostava_u_toku && !p.isObrisana()){
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
		}
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id && kupac.getPorudzbine().get(i).getKupacUsername().equals(username)){
				Porudzbina porudzbina = kupac.getPorudzbine().get(i);
				porudzbina.setStatus(StatusPorudzbine.Dostava_u_toku);
				porudzbina.setDostavljacUsername(korisnik.getKorisnickoIme());
				dostavljac.getDodeljenePorudzbine().add(porudzbina);
				dao.sacuvajKorisnike();
				return Response.status(Status.OK).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/finish/{id}")
	@PUT
	public Response finishOrder(@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Dostavljac){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik k=dao.findUsername(username);
		if(k==null || k.getUloga()!=Uloga.Kupac){
			return Response.status(Status.NOT_FOUND).build();
		}
		Kupac kupac=(Kupac)k;
		Dostavljac dostavljac = (Dostavljac)korisnik;
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id && kupac.getPorudzbine().get(i).getKupacUsername().equals(username)){
				Porudzbina porudzbina = kupac.getPorudzbine().get(i);
				porudzbina.setStatus(StatusPorudzbine.Dostavljeno);
				dostavljac.getDodeljenePorudzbine().stream().forEach(p -> { 
					if(p.getId() == id && p.getKupacUsername().equals(username)){
						p.setStatus(StatusPorudzbine.Dostavljeno);
					}
					
				});
				if(porudzbina.price() >= 500) {
					kupac.dodajBod();
				}
				dao.sacuvajKorisnike();
				return Response.status(Status.OK).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Path("/cancel/{id}")
	@PUT
	public Response cancelOrder(@PathParam(value="buyerUsername") String username,@PathParam(value="id") int id,@Context HttpServletRequest request){
		Korisnik korisnik=(Korisnik)request.getSession().getAttribute("korisnik");
		if(korisnik==null || korisnik.getUloga()!=Uloga.Kupac || !korisnik.getKorisnickoIme().equals(username)){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		KorisnikDAO dao = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		
		Kupac kupac=(Kupac)korisnik;
		for(int i=0;i<kupac.getPorudzbine().size();i++){
			if(kupac.getPorudzbine().get(i).getId()==id && kupac.getPorudzbine().get(i).getKupacUsername().equals(username)){
				Porudzbina porudzbina = kupac.getPorudzbine().get(i);
				if(porudzbina.getStatus() == StatusPorudzbine.Poruceno){
					porudzbina.setStatus(StatusPorudzbine.Otkazano);
					dao.sacuvajKorisnike();
					return Response.status(Status.OK).build();
				}
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}


}
