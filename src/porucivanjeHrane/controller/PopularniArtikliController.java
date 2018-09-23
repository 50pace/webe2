package porucivanjeHrane.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import porucivanjeHrane.dao.KorisnikDAO;
import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Artikl.TipArtikla;
import porucivanjeHrane.model.Kupac;
import porucivanjeHrane.model.Porudzbina;

@Path("/popularArticles")

public class PopularniArtikliController {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPutanja = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPutanja));
		}
//		if (ctx.getAttribute("restoranDAO") == null) {
//	    	String contextPutanja = ctx.getRealPath("");
//			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPutanja));
//		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artikl> getPopularArticles() {
		KorisnikDAO korisnikDao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		List<Kupac> kupci  = korisnikDao.findBuyers();
		Map<Artikl,Integer> mapaJela = new HashMap<>();
		Map<Artikl,Integer> mapaPica = new HashMap<>();
		for(Kupac kupac  : kupci){
			for(Porudzbina porudzbina : kupac.getPorudzbine()){
				for(Artikl artikl : porudzbina.getStavke()){
					if(artikl.getTip() == TipArtikla.Jelo){
						if(mapaJela.containsKey(artikl)){
							int brojPojavljiva = mapaJela.get(artikl)+1;
							mapaJela.remove(artikl);
							mapaJela.put(artikl, brojPojavljiva);
						}else{
							mapaJela.put(artikl, 1);
						}
					}else{
						if(mapaPica.containsKey(artikl)){
							int brojPojavljiva = mapaPica.get(artikl)+1;
							mapaPica.remove(artikl);
							mapaPica.put(artikl, brojPojavljiva);
						}else{
							mapaPica.put(artikl, 1);
						}
					}
				}
			}
			
		}
		
		List<Artikl> popularniArtikli = new ArrayList<>();
		Map<Artikl, Integer> sortiranaMapaJela = sortByValue(mapaJela);
		int min = 0;
		if(sortiranaMapaJela.size() > 10){
			min = sortiranaMapaJela.size() -10;
		}
		for(int i =sortiranaMapaJela.size()-1; i >= min; i--){
			Artikl artikl = (Artikl) sortiranaMapaJela.keySet().toArray()[i];
			popularniArtikli.add(artikl);
		}
		
		Map<Artikl, Integer> sortiranaMapaPica= sortByValue(mapaPica);
		min = 0;
		if(sortiranaMapaPica.size() > 10){
			min = sortiranaMapaPica.size()-10;
		}
		for(int i = sortiranaMapaPica.size()-1; i >=min; i--){
			Artikl artikl = (Artikl) sortiranaMapaPica.keySet().toArray()[i];
			popularniArtikli.add(artikl);
			
		}
		
		return popularniArtikli;
	}
	
	 private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
	        list.sort(Entry.comparingByValue());

	        Map<K, V> result = new LinkedHashMap<>();
	        for (Entry<K, V> entry : list) {
	            result.put(entry.getKey(), entry.getValue());
	        }

	        return result;
	  }
	 
	 
//	@GET
//	@Path("/articles")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Artikl> getAllArticles() {
//		RestoranDAO restoranDao = (RestoranDAO) ctx.getAttribute("restoranDAO");
//		List<Restoran> restorani = restoranDao.findAll();
//		List<Artikl> artikli = new ArrayList<>();
//		for(Restoran r : restorani) {
//			artikli.addAll(r.getArtikli().stream().filter(a->!a.isObrisan()).collect(Collectors.toList()));
//		}
//		return artikli;
//
//	}
}
