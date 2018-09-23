package porucivanjeHrane.dto;

import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Artikl.TipArtikla;
import porucivanjeHrane.model.Restoran;

public class ArtiklDTO {
	
	private int id;
	
	private String naziv;
	
	private String opis;
	
	private double jedinicnaCena;
	
	private double kolicina;
	
	private TipArtikla tip;
		
	private RestoranDTO restoran;
	
	
	public ArtiklDTO(Artikl artikl, Restoran restoran){
		this.restoran = new RestoranDTO(restoran, false);
		this.id = artikl.getId();
		this.naziv = artikl.getNaziv();
		this.opis = artikl.getOpis();
		this.jedinicnaCena = artikl.getJedinicnaCena();
		this.kolicina = artikl.getKolicina();
		this.tip = artikl.getTip();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public TipArtikla getTip() {
		return tip;
	}

	public void setTip(TipArtikla tip) {
		this.tip = tip;
	}


	public RestoranDTO getRestoran() {
		return restoran;
	}

	public void setRestoran(RestoranDTO restoran) {
		this.restoran = restoran;
	}
	
	
}
