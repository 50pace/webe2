package porucivanjeHrane.model;

import java.sql.Date;



public class Korisnik {

	public enum Uloga{Kupac,Dostavljac,Administrator};
	
	private String korisnickoIme;
	
	private String lozinka;
	
	private String ime;
	
	private String prezime;

	private String telefon;
	
	private String email;
	
	private Date datumRegistracije;
	
	private Uloga uloga;
	
	
	public Korisnik() {
		
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String telefon, String email,
			Date datumRegistracije, Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.telefon = telefon;
		this.email = email;
		this.datumRegistracije = datumRegistracije;
		this.uloga = uloga;
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDatumRegistracije() {
		return datumRegistracije;
	}
	public void setDatumRegistracije(Date datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	@Override
	public String toString() {
		return korisnickoIme + ";" + lozinka + ";" + ime + ";"+ prezime + ";" + telefon 
				+";" + email + ";" + datumRegistracije + ";" + uloga + ";";
	}
	
	
	
}
