package porucivanjeHrane.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Kupac extends Korisnik{
	
	private int bodovi;
	
	private List<Porudzbina> porudzbine;
	
	private List<Restoran> omiljeniRestorani;
	
	public Kupac(){
		bodovi = 0;
		porudzbine=new ArrayList<Porudzbina>();
		omiljeniRestorani=new ArrayList<Restoran>();
	}
	
	public Kupac(String korisnickoIme, String lozinka, String ime, String prezime, String telefon, String email,Date datumRegistracije, Uloga uloga,
			int bodovi ,List<Porudzbina> porudzbine,List<Restoran> omiljeniRestorani){
		super(korisnickoIme,lozinka,ime,prezime,telefon,email,datumRegistracije,uloga);
		this.bodovi = bodovi;
		this.omiljeniRestorani=omiljeniRestorani;
		this.porudzbine=porudzbine;
		
	}

	public Kupac(Korisnik korisnik) {
		super(korisnik.getKorisnickoIme(),korisnik.getLozinka(),korisnik.getIme(),korisnik.getPrezime(),korisnik.getTelefon(),
				korisnik.getEmail(),korisnik.getDatumRegistracije(),korisnik.getUloga());
		porudzbine=new ArrayList<Porudzbina>();
		omiljeniRestorani=new ArrayList<Restoran>();
	}

	public List<Porudzbina> getPorudzbine() {
		return porudzbine;
	}

	public void setPorudzbine(List<Porudzbina> porudzbine) {
		this.porudzbine = porudzbine;
	}

	public List<Restoran> getOmiljeniRestorani() {
		return omiljeniRestorani;
	}

	public void setOmiljeniRestorani(List<Restoran> omiljeniRestorani) {
		this.omiljeniRestorani = omiljeniRestorani;
	}
	

	
	public int getBodovi() {
		return bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	
	public void dodajBod() {
		if(this.bodovi < 10) {
			this.bodovi +=1;
		}
	}
	
	public boolean umanjiBodove(int bodovi) {
		if(this.bodovi - bodovi >= 0) {
			this.bodovi -=bodovi;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String rez= getKorisnickoIme() + ";" + getLozinka() + ";" + getIme() + ";"+ getPrezime() + ";" + getTelefon() 
				+";" + getEmail() + ";" + getDatumRegistracije() + ";" + getUloga() + ";" +bodovi + ";" +porudzbine.size()+";";
		for(Porudzbina p : porudzbine){
			rez+=p.toString();
		}
		rez+=omiljeniRestorani.size()+";";
		for(Restoran restoran : omiljeniRestorani){
			rez+=restoran.toString();
		}
		
		return rez;
	}
}
