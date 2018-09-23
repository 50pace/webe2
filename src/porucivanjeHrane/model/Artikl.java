package porucivanjeHrane.model;

public class Artikl {
	
	public enum TipArtikla{Jelo,Pice};
	
	private int id;
	
	private String naziv;
	
	private String opis;
	
	private double jedinicnaCena;
	
	private double kolicina;
	
	private TipArtikla tip;
	
	private boolean obrisan;
	
	private int restoranId;
	
	public Artikl(){
		obrisan=false;
	}

	public Artikl(int id, String naziv, String opis, double jedinicnaCena, double kolicina, TipArtikla tip,int restoranId, boolean obrisan) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.opis = opis;
		this.jedinicnaCena = jedinicnaCena;
		this.kolicina = kolicina;
		this.tip=tip;
		this.restoranId = restoranId;
		this.obrisan=obrisan;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getJedinicnaCena() {
		return jedinicnaCena;
	}

	public void setJedinicnaCena(double jedinicnaCena) {
		this.jedinicnaCena = jedinicnaCena;
	}

	public double getKolicina() {
		return kolicina;
	}

	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}
	
	public TipArtikla getTip(){
		return tip;
	}
	
	public void setTip(TipArtikla tip) {
		this.tip = tip;
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
	
	public int getRestoranId() {
		return restoranId;
	}

	public void setRestoranId(int restoranId) {
		this.restoranId = restoranId;
	}
	
	@Override
	public int hashCode() {
		int rezultat = ((id + restoranId)*(id + restoranId + 1))/2 + id;
		return rezultat;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Artikl)){
			return false;
		}
		Artikl artikl = (Artikl) obj;
		
		return this.id == artikl.id && this.restoranId == artikl.restoranId;
	}
	
	@Override
	public String toString() {
		return id+";"+naziv + ";" + opis + ";" + jedinicnaCena + ";" + kolicina + ";" + tip + ";" +restoranId+";"+ obrisan +";";
	}

	
}
