package program;

public class Artikl {
	private String sifraArtikla;
	private String naziv;
	private float cena;
	private int komada;

	public Artikl(String sifraArtikla, String naziv, float cena, int komada) {
		super();
		this.sifraArtikla = sifraArtikla;
		this.naziv = naziv;
		this.cena = cena;
		this.komada = komada;
	}

	public String getSifraArtikla() {
		return sifraArtikla;
	}

	public void setSifraArtikla(String sifraArtikla) {
		this.sifraArtikla = sifraArtikla;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public int getKomada() {
		return komada;
	}

	public void setKomada(int komada) {
		this.komada = komada;
	}

}
