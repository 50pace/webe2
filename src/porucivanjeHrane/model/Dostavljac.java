package porucivanjeHrane.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Dostavljac extends Korisnik {
	
	private Vozilo vozilo;
	
	private List<Porudzbina> dodeljenePorudzbine;
	
	public Dostavljac(){
		dodeljenePorudzbine=new ArrayList<>();
	}
	
	public Dostavljac(String korisnickoIme, String lozinka, String ime, String prezime, String telefon, String email,Date datumRegistracije, Uloga uloga,
			Vozilo vozilo,List<Porudzbina> dodeljenePorudzbine){
		super(korisnickoIme,lozinka,ime,prezime,telefon,email,datumRegistracije,uloga);
		this.vozilo=vozilo;
		this.dodeljenePorudzbine=dodeljenePorudzbine;
		
	}
	
	public Dostavljac(Korisnik korisnik) {
		super(korisnik.getKorisnickoIme(),korisnik.getLozinka(),korisnik.getIme(),korisnik.getPrezime(),korisnik.getTelefon(),
				korisnik.getEmail(),korisnik.getDatumRegistracije(),korisnik.getUloga());
		dodeljenePorudzbine=new ArrayList<Porudzbina>();
	}
	
	public Vozilo getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vozilo vozilo) {
		this.vozilo = vozilo;
	}

	public List<Porudzbina> getDodeljenePorudzbine() {
		return dodeljenePorudzbine;
	}

	public void setDodeljenePorudzbine(List<Porudzbina> dodeljenePorudzbine) {
		this.dodeljenePorudzbine = dodeljenePorudzbine;
	}
	
	@Override
	public String toString() {
		String rez= super.toString();
		if(vozilo == null){
			rez += "null;";
		}else{
			rez+=vozilo.toString();
		}
		rez+=dodeljenePorudzbine.size()+";";
		for(Porudzbina p : dodeljenePorudzbine){
			rez+=p.toString();
		}
		return rez;
	}
}
