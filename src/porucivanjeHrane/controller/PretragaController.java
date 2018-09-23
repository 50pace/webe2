package porucivanjeHrane.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import porucivanjeHrane.dao.RestoranDAO;
import porucivanjeHrane.dto.ArtiklDTO;
import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Restoran;

@Path("/search")
public class PretragaController {

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
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtiklDTO> searchArticles(@QueryParam(value="nazivRestorana") String nazivRestorana,
										   @QueryParam(value="adresa") String adresa,
										   @QueryParam(value="kategorija") String kategorija,
										   @QueryParam(value="nazivArtikla") String nazivArtikla,
										   @QueryParam(value="pocetnaCijena") String pocetnaCijena,
										   @QueryParam(value="krajnjaCijena") String krajnjaCijena,
										   @QueryParam(value="tip") String tip){
		
		RestoranDAO dao = (RestoranDAO)ctx.getAttribute("restoranDAO");
		List<ArtiklDTO> artikli = new ArrayList<>();
		
		if(nazivRestorana == null) 
			nazivRestorana = "";
		if(adresa == null) 
			adresa= "";
		if(kategorija == null) 
			kategorija = "";
		if(nazivArtikla == null) 
			nazivArtikla = "";
		if(pocetnaCijena == null) 
			pocetnaCijena = "";
		if(krajnjaCijena == null) 
			krajnjaCijena = "";
		if(tip == null) 
			tip= "";
		
		int pocetnaCena= Integer.MIN_VALUE;
		try{
			pocetnaCena = Integer.parseInt(pocetnaCijena);
		}catch (Exception e) {
		}
		int krajnjaCena = Integer.MAX_VALUE;
		try{
			krajnjaCena = Integer.parseInt(krajnjaCijena);
		}catch (Exception e) {
			
		}

		for(Restoran restoran : dao.findAll()){
			if(!restoran.getNaziv().toLowerCase().contains(nazivRestorana.toLowerCase()) ||
			   !restoran.getAdresa().toLowerCase().contains(adresa.toLowerCase()) ||
			   !restoran.getKategorija().toString().contains(kategorija)){
				
				continue;
			}
			for(Artikl artikl : restoran.getArtikli()){
				double cena = artikl.getJedinicnaCena() * artikl.getKolicina();
				if(artikl.getNaziv().toLowerCase().contains(nazivArtikla.toLowerCase()) &&
				   cena >= pocetnaCena &&
				   cena <= krajnjaCena &&
				   artikl.getTip().toString().contains(tip)){
							
					artikli.add(new ArtiklDTO(artikl, restoran));
				}
			}
			
			
		}
		return artikli;
		
	}
}
