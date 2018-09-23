package porucivanjeHrane.model;


public class Vozilo {
	
	public enum TipVozila{Bicikl,Skuter,Automobil};
	
	private int id;
	
	private String marka;
	
	private String model;
	
	private String registarskaOznaka;
	
	private String godinaProizvodnje;
	
	private boolean voziloUupotrebi;
	
	private String napomena;
	
	private boolean obrisan;

	private TipVozila tip;
	
	public Vozilo(){
		obrisan=false;
	}

	public Vozilo(int id, String marka, String model, String registarskaOznaka, String godinaProizvodnje,
			boolean voziloUupotrebi,String napomena, TipVozila tip,boolean obrisan) {
		super();
		this.id=id;
		this.marka = marka;
		this.model = model;
		this.registarskaOznaka = registarskaOznaka;
		this.godinaProizvodnje = godinaProizvodnje;
		this.voziloUupotrebi = voziloUupotrebi;
		this.tip=tip;
		this.napomena=napomena;
		this.obrisan=obrisan;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegistarskaOznaka() {
		return registarskaOznaka;
	}

	public void setRegistarskaOznaka(String registarskaOznaka) {
		this.registarskaOznaka = registarskaOznaka;
	}

	public String getGodinaProizvodnje() {
		return godinaProizvodnje;
	}

	public void setGodinaProizvodnje(String godinaProizvodnje) {
		this.godinaProizvodnje = godinaProizvodnje;
	}

	public boolean isVoziloUupotrebi() {
		return voziloUupotrebi;
	}

	public void setVoziloUupotrebi(boolean voziloUupotrebi) {
		this.voziloUupotrebi = voziloUupotrebi;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	
	public TipVozila getTip(){
		return tip;
	}
	
	public void setTip(TipVozila tip) {
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
	
	@Override
	public String toString() {
		String napomenaIspis = napomena;
		if(napomena == null || napomena.equals("") ){
			napomenaIspis = " "; 
		}
		
		return  id + ";" + marka + ";" + model + ";" + registarskaOznaka
				+ ";" + godinaProizvodnje + ";" + voziloUupotrebi + ";"
				+ napomenaIspis + ";" + tip + ";" + obrisan + ";";
	}
	
}
