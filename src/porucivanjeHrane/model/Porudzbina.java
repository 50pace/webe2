package porucivanjeHrane.model;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class Porudzbina {
	
	public enum StatusPorudzbine{Poruceno,Dostava_u_toku,Otkazano,Dostavljeno,};
	
	private int id;
	
	private List<Artikl> stavke;
	
	private List<Integer> kolicine;
	
	private LocalDateTime datumVreme;
	
	private String napomena;
	
	private StatusPorudzbine status;
	
	private boolean obrisana;
	
	private String dostavljacUsername;

	private String kupacUsername;
	
	private int utrosenoBodova;
	
	public Porudzbina(){
		stavke=new ArrayList<>();
		kolicine = new ArrayList<>();
		status=StatusPorudzbine.Poruceno;
		obrisana=false;
		datumVreme=LocalDateTime.now();
	}
	
	public Porudzbina(int id,List<Artikl>  stavke,List<Integer> kolicine, LocalDateTime datumVreme,String napomena,StatusPorudzbine status,boolean obrisana,String kupacUsername,String dostavljacUsername, int utrosenoBodova){
		this.stavke=stavke;
		this.kolicine = kolicine;
		this.datumVreme=datumVreme;
		this.napomena=napomena;
		this.status=status;
		this.id=id;
		this.obrisana=obrisana;
		this.kupacUsername=kupacUsername;
		this.dostavljacUsername=dostavljacUsername;
		this.utrosenoBodova = utrosenoBodova;
	}
	
	public StatusPorudzbine getStatus() {
		return status;
	}

	public void setStatus(StatusPorudzbine status) {
		this.status = status;
	}

	public List<Artikl>  getStavke() {
		return stavke;
	}

	public void setStavke(List<Artikl> stavke) {
		this.stavke = stavke;
	}

	public LocalDateTime getDatumVreme() {
		return datumVreme;
	}

	public void setDatumVreme(LocalDateTime datumVreme) {
		this.datumVreme = datumVreme;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public boolean isObrisana(){
		return obrisana;
	}
	
	public void setObrisana(boolean obrisana){
		this.obrisana=obrisana;
	}
	
	public List<Integer> getKolicine() {
		return kolicine;
	}

	public void setKolicine(List<Integer> kolicine) {
		this.kolicine = kolicine;
	}
	
	public String getDostavljacUsername() {
		return dostavljacUsername;
	}

	public void setDostavljacUsername(String dostavljacUsername) {
		this.dostavljacUsername = dostavljacUsername;
	}

	public String getKupacUsername() {
		return kupacUsername;
	}

	public void setKupacUsername(String kupacUsername) {
		this.kupacUsername = kupacUsername;
	}
	
	
	public int getUtrosenoBodova() {
		return utrosenoBodova;
	}

	public void setUtrosenoBodova(int utrosenoBodova) {
		this.utrosenoBodova = utrosenoBodova;
	}

	public double price() {
		double sum=0;
		for(int i=0; i< stavke.size(); i++) {
			Artikl a = stavke.get(i);
			int kolicina = kolicine.get(i);
			sum += a.getJedinicnaCena() * a.getKolicina() * kolicina;
		}
		double price  = sum - 3.0/100.0*sum * utrosenoBodova;
		return price;
	}
	@Override
	public String toString() {
		String napomenaIspis = napomena;
		if(napomena == null || napomena.equals("") ){
			napomenaIspis = " "; 
		}
		
		String dostavljacIspis = dostavljacUsername;
		if(dostavljacUsername == null || dostavljacUsername.equals("")){
			dostavljacIspis= " "; 
		}
		String rez=id+";"+ datumVreme + ";" + status 
				+";" + napomenaIspis + ";" + obrisana + ";" + kupacUsername + ";" + dostavljacIspis+ ";" + utrosenoBodova + ";" + stavke.size() + ";";
		for(Artikl artikl: stavke){
			rez+=artikl.toString();
		}
		for(int kolicina: kolicine){
			rez+=kolicina + ";";
		}
		return rez;
	}
}
