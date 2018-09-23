package porucivanjeHrane.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Korisnik;
import porucivanjeHrane.model.Artikl.TipArtikla;
import porucivanjeHrane.model.Dostavljac;
import porucivanjeHrane.model.Korisnik.Uloga;
import porucivanjeHrane.model.Kupac;
import porucivanjeHrane.model.Porudzbina;
import porucivanjeHrane.model.Porudzbina.StatusPorudzbine;
import porucivanjeHrane.model.Restoran.Kategorija;
import porucivanjeHrane.model.Vozilo;
import porucivanjeHrane.model.Vozilo.TipVozila;
import porucivanjeHrane.model.Restoran;

public class KorisnikDAO {

	private Map<String, Korisnik> korisnici;
	private String contextPutanja;
	private String putanja =  "/data/korisnici.txt";
	
	
	public KorisnikDAO() {
		 korisnici = new HashMap<>();
	}
	

	public KorisnikDAO(String contextPutanja) {
		this();
		this.contextPutanja=contextPutanja;
		ucitajKorisnike();
	}
	
	public Korisnik findUsername(String korisnickoIme){
		if(!korisnici.containsKey(korisnickoIme)){
			return null;
		}
		Korisnik korisnik=korisnici.get(korisnickoIme);
		return korisnik;
	}
	
	public Korisnik find(String korisnickoIme, String lozinka) {
		if (!korisnici.containsKey(korisnickoIme)) {
			return null;
		}
		Korisnik korisnik = korisnici.get(korisnickoIme);
		if (!korisnik.getLozinka().equals(lozinka)) {
			return null;
		}
		return korisnik;
	}
	
	public Collection<Korisnik> findAll() {
		return korisnici.values();
	}
	
	public List<Kupac> findBuyers() {
		List<Kupac> kupci=new ArrayList<Kupac>();
		for(Korisnik korisnik:korisnici.values()){
			if(korisnik.getUloga()==Uloga.Kupac){
				kupci.add((Kupac) korisnik);
			}
		}
		return kupci;
	}
	
	public boolean update(String username,Korisnik korisnik) {
		if(!korisnici.containsKey(username)){
			return false;
		}
		Korisnik stariKorisnik = korisnici.get(username);
		Korisnik noviKorisnik=null;
		if(stariKorisnik.getUloga() != korisnik.getUloga()){
			if(korisnik.getUloga() == Uloga.Kupac){
				noviKorisnik = new Kupac(korisnik);
			}else if(korisnik.getUloga() == Uloga.Dostavljac){
				noviKorisnik = new Dostavljac(korisnik);
			}else{
				noviKorisnik = korisnik;
			}
		}
		korisnici.replace(username,noviKorisnik);
		sacuvajKorisnike();
		return true;
	}
	
	public void dodajKorisnika(Korisnik korisnik) {
		korisnici.put(korisnik.getKorisnickoIme(), korisnik);
		sacuvajKorisnike();
	}

	private void ucitajKorisnike() {

		BufferedReader in = null;
		try {
			File file = new File(contextPutanja + putanja);
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String korisnickoIme = st.nextToken().trim();
					String lozinka = st.nextToken().trim();
					String ime = st.nextToken().trim();
					String prezime = st.nextToken().trim();
					String telefon = st.nextToken().trim();
					String email = st.nextToken().trim();
					Date datumRegistracije = Date.valueOf(st.nextToken().trim());
					Uloga uloga = Uloga.valueOf( st.nextToken().trim());
					if(uloga==Uloga.Administrator){
						Korisnik korisnik = new Korisnik(korisnickoIme, lozinka, ime, prezime, telefon, email, datumRegistracije, uloga);
						korisnici.put(korisnickoIme, korisnik);
					}else if(uloga==Uloga.Kupac){
						int bodovi = Integer.parseInt(st.nextToken().trim());
						List<Porudzbina> porudzbine=new ArrayList<>();
						int brojPorudzbina=Integer.parseInt(st.nextToken().trim());
						for(int i=0;i<brojPorudzbina;i++){
							int idPorudzbine=Integer.parseInt(st.nextToken().trim());
							LocalDateTime datum=LocalDateTime.parse(st.nextToken().trim());
							StatusPorudzbine status=StatusPorudzbine.valueOf(st.nextToken().trim());
							String napomena=st.nextToken().trim();
							boolean obrisana=Boolean.valueOf(st.nextToken().trim());
							String kupacUsername=st.nextToken().trim();
							String dostavljacUsername=st.nextToken().trim();
							int utrosenoBodova=Integer.parseInt(st.nextToken().trim());
							int brojArtikala=Integer.parseInt(st.nextToken().trim());
							List<Artikl> stavke=new ArrayList<>();
							for(int j=0;j<brojArtikala;j++){
								int idArtikla=Integer.parseInt(st.nextToken().trim());
								String nazivArtikla=st.nextToken().trim();
								String opisArtikla=st.nextToken().trim();
								double jedinicnaCena=Double.parseDouble(st.nextToken().trim());
								double kolicina=Double.parseDouble(st.nextToken().trim());
								TipArtikla tipArtikla= TipArtikla.valueOf(st.nextToken().trim());
								int restoranId=Integer.parseInt(st.nextToken().trim());
								boolean obrisanArtikl=Boolean.valueOf(st.nextToken().trim());
								Artikl artikl = new Artikl(idArtikla,nazivArtikla,opisArtikla,jedinicnaCena,kolicina,tipArtikla,restoranId,obrisanArtikl);
								stavke.add(artikl);
							}
							List<Integer> kolicine=new ArrayList<>();
							for(int j=0;j<brojArtikala;j++){
								int kolicina=Integer.parseInt(st.nextToken().trim());
								kolicine.add(kolicina);
							}
				
							Porudzbina p=new Porudzbina(idPorudzbine,stavke,kolicine,datum,napomena,status,obrisana,kupacUsername,dostavljacUsername,utrosenoBodova);
							porudzbine.add(p);
						}
						List<Restoran> omiljeniRestorani=new ArrayList<>();
						int brojOmiljenihRestorana=Integer.parseInt(st.nextToken().trim());
						for(int i=0;i<brojOmiljenihRestorana;i++){
							int idRestorana=Integer.parseInt(st.nextToken().trim());
							String naziv=st.nextToken().trim();
							String adresa=st.nextToken().trim();
							Kategorija kategorija=Kategorija.valueOf(st.nextToken().trim());
							boolean obrisan=Boolean.valueOf(st.nextToken().trim());
						
							List<Artikl> artikli = new ArrayList<Artikl>();
							int brojArtikalaUrestoranu=Integer.parseInt(st.nextToken().trim());
							for(int j=0;j<brojArtikalaUrestoranu;j++){
								int idArtikla=Integer.parseInt(st.nextToken().trim());
								String nazivArtikla=st.nextToken().trim();
								String opisArtikla=st.nextToken().trim();
								double jedinicnaCena=Double.parseDouble(st.nextToken().trim());
								double kolicina=Double.parseDouble(st.nextToken().trim());
								TipArtikla tipArtikla= TipArtikla.valueOf(st.nextToken().trim());
								int restoranId=Integer.parseInt(st.nextToken().trim());
								boolean obrisanArtikl=Boolean.valueOf(st.nextToken().trim());
								Artikl artikl = new Artikl(idArtikla,nazivArtikla,opisArtikla,jedinicnaCena,kolicina,tipArtikla,restoranId,obrisanArtikl);
								artikli.add(artikl);
							}
							Restoran restoran=new Restoran(idRestorana,naziv,adresa,kategorija,artikli,obrisan);
							omiljeniRestorani.add(restoran);
						}
						Kupac kupac = new Kupac(korisnickoIme, lozinka, ime, prezime, telefon, email, datumRegistracije, uloga,bodovi,porudzbine,omiljeniRestorani);
						korisnici.put(korisnickoIme, kupac);
					}else if(uloga==Uloga.Dostavljac){
						Vozilo vozilo=null;
						String idV= st.nextToken().trim();
						if(!idV.equals("null")){
							int idVozila=Integer.parseInt(idV);
							String marka=st.nextToken().trim();
							String model=st.nextToken().trim();
							String registarskaOznaka=st.nextToken().trim();
							String datumProizvodnje=st.nextToken().trim();
							boolean upotreba=Boolean.valueOf(st.nextToken().trim());
							String napomena=st.nextToken().trim();
							TipVozila tip= TipVozila.valueOf(st.nextToken().trim());
							boolean obrisanoVozilo=Boolean.valueOf(st.nextToken().trim());
							vozilo=new Vozilo(idVozila,marka,model,registarskaOznaka,datumProizvodnje,upotreba,napomena,tip,obrisanoVozilo);
						}
						List<Porudzbina> dodeljenePorudzbine=new ArrayList<>();
						int brojPorudzbina=Integer.parseInt(st.nextToken().trim());
						for(int i=0;i<brojPorudzbina;i++){
							int idPorudzbine=Integer.parseInt(st.nextToken().trim());
							LocalDateTime datum=LocalDateTime.parse(st.nextToken().trim());
							StatusPorudzbine status=StatusPorudzbine.valueOf(st.nextToken().trim());
							String napomenaPorudzbine=st.nextToken().trim();
							boolean obrisana=Boolean.valueOf(st.nextToken().trim());
							String kupacUsername=st.nextToken().trim();
							String dostavljacUsername=st.nextToken().trim();
							int utrosenoBodova=Integer.parseInt(st.nextToken().trim());
							int brojArtikala=Integer.parseInt(st.nextToken().trim());
							List<Artikl> stavke=new ArrayList<>();
							for(int j=0;j<brojArtikala;j++){
								int idArtikla=Integer.parseInt(st.nextToken().trim());
								String nazivArtikla=st.nextToken().trim();
								String opisArtikla=st.nextToken().trim();
								double jedinicnaCena=Double.parseDouble(st.nextToken().trim());
								double kolicina=Double.parseDouble(st.nextToken().trim());
								TipArtikla tipArtikla= TipArtikla.valueOf(st.nextToken().trim());
								int restoranId=Integer.parseInt(st.nextToken().trim());
								boolean obrisanArtikl=Boolean.valueOf(st.nextToken().trim());
								Artikl artikl = new Artikl(idArtikla,nazivArtikla,opisArtikla,jedinicnaCena,kolicina,tipArtikla,restoranId,obrisanArtikl);
								stavke.add(artikl);
							}
							List<Integer> kolicine=new ArrayList<>();
							for(int j=0;j<brojArtikala;j++){
								int kolicina=Integer.parseInt(st.nextToken().trim());
								kolicine.add(kolicina);
							}
				
							Porudzbina p=new Porudzbina(idPorudzbine,stavke,kolicine,datum,napomenaPorudzbine,status,obrisana,kupacUsername,dostavljacUsername,utrosenoBodova);
							dodeljenePorudzbine.add(p);
						}
						Dostavljac dostavljac= new Dostavljac(korisnickoIme, lozinka, ime, prezime, telefon, email, datumRegistracije, uloga,vozilo,dodeljenePorudzbine);
						korisnici.put(korisnickoIme,dostavljac);
					}
					
				}
				
			}
		} catch (Exception ex) {
			if(!(ex instanceof FileNotFoundException))
				System.out.println("Greska prilikom ucitavanja korisnika!");
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	public void sacuvajKorisnike() {
		PrintWriter writer=null;
		
		try {
			File f = new File(contextPutanja+putanja);
			writer = new PrintWriter(f);
			for(String korisnickoIme : korisnici.keySet()) {
				writer.println(korisnici.get(korisnickoIme).toString());
			}
		}
		catch (Exception e) {
			System.out.println("Greska prilikom cuvanja korisnika!");
		}finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) { }
			}
		}
	}
}
