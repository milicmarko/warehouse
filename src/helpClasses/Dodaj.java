package helpClasses;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Component;
import javax.swing.JButton;

public class Dodaj extends JPanel {
	public Dodaj() {
		setLayout(new MigLayout("", "[][][227.00]", "[][][][][][][][][]"));
		
		JLabel lblUnesiteArtikal = new JLabel("Unos novog artikla");
		lblUnesiteArtikal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(lblUnesiteArtikal, "cell 2 2,alignx center");
		
		JLabel lblSifraArtikla = new JLabel("Sifra:");
		lblSifraArtikla.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSifraArtikla, "cell 1 3,alignx trailing");
		
		txtSifra = new JTextField();
		add(txtSifra, "cell 2 3,growx");
		txtSifra.setColumns(10);
		txtSifra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNazivArtikla = new JLabel("Naziv:");
		lblNazivArtikla.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblNazivArtikla, "cell 1 4,alignx trailing");
		
		txtNaziv = new JTextField();
		add(txtNaziv, "cell 2 4,growx");
		txtNaziv.setColumns(10);
		txtNaziv.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblCena = new JLabel("Cena:");
		lblCena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblCena, "cell 1 5,alignx trailing");
		
		txtCena = new JTextField();
		add(txtCena, "cell 2 5,growx");
		txtCena.setColumns(10);
		txtCena.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblKomada = new JLabel("Komada:");
		lblKomada.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblKomada, "cell 1 6,alignx trailing");
		
		txtKomada = new JTextField();
		add(txtKomada, "cell 2 6,growx");
		txtKomada.setColumns(10);
		txtKomada.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnDodaj = new JButton("Dodaj");
		add(btnDodaj, "cell 2 8");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6127770025393869133L;
	private JTextField txtSifra;
	private JTextField txtNaziv;
	private JTextField txtCena;
	private JTextField txtKomada;

	
	
}
