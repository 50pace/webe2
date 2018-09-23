package porucivanjeHrane.model;

import java.util.ArrayList;
import java.util.List;

public class Restoran {

	public enum Kategorija{Domaca_kuhinja,Rostilj,Kineski_restoran,Indijski_restoran,Poslasticarnica,Picerija};
	
	private int id;
	
	private String naziv;
	
	private String adresa;
	
	private Kategorija kategorija;
	
	private List<Artikl> artikli;
	
	private boolean obrisan;
	
	public Restoran(){
		artikli=new ArrayList<>();
		obrisan = false;
	}

	public Restoran(int id, String naziv, String adresa, Kategorija kategorija,List<Artikl> artikli,boolean obrisan) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.kategorija = kategorija;
		this.artikli=artikli;
		this.obrisan = obrisan;
	}


	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}
	
	public List<Artikl> getArtikli(){
		return artikli;
	}
	
	public void setArtikli(List<Artikl> artikli){
		this.artikli=artikli;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public boolean isObrisan(){
		return obrisan;
	}
	
	public void setObrisan(boolean obrisan){
		this.obrisan=obrisan;
	}
	
	@Override
	public String toString() {
		String rez=id + ";" + naziv + ";" + adresa + ";" + kategorija+ ";"+ obrisan
				+ ";";
		rez+=artikli.size()+";";
		for(Artikl artikl : artikli){
			rez+=artikl.toString();
		}
		return rez;
	}
}
