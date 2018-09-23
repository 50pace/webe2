package porucivanjeHrane.dto;

import porucivanjeHrane.model.Restoran;

public class RestoranDTO {
	
	
	private int id;
	
	private String naziv;
	
	private String adresa;
	
	private porucivanjeHrane.model.Restoran.Kategorija kategorija;
	
	private boolean omiljen;

	public RestoranDTO(Restoran restoran , boolean omiljen) {
		this.id = restoran.getId();
		this.naziv = restoran.getNaziv();
		this.adresa = restoran.getAdresa();
		this.kategorija = restoran.getKategorija();
		this.omiljen = omiljen;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public porucivanjeHrane.model.Restoran.Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(porucivanjeHrane.model.Restoran.Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public boolean isOmiljen() {
		return omiljen;
	}

	public void setOmiljen(boolean omiljen) {
		this.omiljen = omiljen;
	}
	
	
	
}
