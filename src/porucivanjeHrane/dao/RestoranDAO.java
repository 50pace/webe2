package porucivanjeHrane.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import porucivanjeHrane.model.Artikl;
import porucivanjeHrane.model.Artikl.TipArtikla;
import porucivanjeHrane.model.Restoran;
import porucivanjeHrane.model.Restoran.Kategorija;;

public class RestoranDAO {
	private Map<Integer,Restoran> restorani;
	private String contextPutanja;
	private String putanja= "/data/restorani.txt";
	
	public RestoranDAO(){
		restorani=new HashMap<>();
	}
	
	public RestoranDAO(String contextPutanja) {
		this();
		this.contextPutanja=contextPutanja;
		ucitajRestorane();
	}
	
	private void ucitajRestorane(){
		BufferedReader in=null;
		try {
			File file=new File(contextPutanja+putanja);
			in=new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while((line=in.readLine())!=null){
				line=line.trim();
				if(line.equals("") || line.indexOf('#')==0)
					continue;
				st=new StringTokenizer(line,";");
				int id=Integer.parseInt(st.nextToken().trim());
				String naziv=st.nextToken().trim();
				String adresa=st.nextToken().trim();
				Kategorija kategorija=Kategorija.valueOf(st.nextToken().trim());
				boolean obrisan=Boolean.valueOf(st.nextToken().trim());
				
				List<Artikl> artikli = new ArrayList<Artikl>();
				int brojArtikala=Integer.parseInt(st.nextToken().trim());
				for(int i=0;i<brojArtikala;i++){
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
					Restoran restoran=new Restoran(id,naziv,adresa,kategorija,artikli,obrisan);
					restorani.put(id,restoran);
			}
		}catch(Exception ex){
			if(!(ex instanceof FileNotFoundException))
				System.out.println("Greška prilikom učitavanja restorana!");
		}finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public List<Restoran> findAll() {
		List<Restoran> restorani = new  ArrayList<>(this.restorani.values());
		List<Restoran> filtriraniRestorani = restorani.stream().filter(r->!r.isObrisan()).collect(Collectors.toList());
		return filtriraniRestorani;
	}
	
	public Restoran find(int id){
		return restorani.get(id);
	}
	
	public void dodajRestoran(Restoran restoran){
		int id=restorani.size();
		restoran.setId(id);
		restorani.put(restoran.getId(),restoran);
		sacuvajRestorane();
	}
	
	public void sacuvajRestorane() {
		PrintWriter writer=null;
		
		try {
			File f = new File(contextPutanja+putanja);
			writer = new PrintWriter(f);
			for(int id : restorani.keySet()) {
				writer.println(restorani.get(id).toString());
			}
		}
		catch (Exception e) {
			System.out.println("Greska prilikom cuvanja restorana!");
		}finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) { }
			}
		}
	}

	public boolean update(int id, Restoran restoran) {
		if(!restorani.containsKey(id)){
			return false;
		}
		restorani.replace(id, restoran);
		sacuvajRestorane();
		return true;
	}

	public boolean delete(int id) {
			if(!restorani.containsKey(id)){
				return false;
			}
			restorani.get(id).setObrisan(true);
			restorani.get(id).getArtikli().forEach(a -> a.setObrisan(true));
			sacuvajRestorane();
			return true;
	}
	
	public int findRestoranByArtikl(Artikl artikl) {
		
		for(Restoran restoran : restorani.values()){
			for(Artikl a : restoran.getArtikli()){
				if(a.equals(artikl)){
					return restoran.getId();
				}
			}
		}
		return -1;
}

}
