package program;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import helpClasses.DbConn;
import helpClasses.Loading;
import net.miginfocom.swing.MigLayout;

public class Gui extends JFrame implements ActionListener, PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2291712392982215395L;
	private JTable table;
	private JButton btnDodaj;
	private JButton btnIzbrisi;
	private JButton btnIzvestaj;
	private JTextField txtSifra;
	private JTextField txtNaziv;
	private JTextField txtCena;
	private JButton btnIzmeni;
	private JTextField txtTrazi;
	private DefaultTableModel model;
	private JSpinner spinner;

	private Connection conn;
	private Statement st;
	private ResultSet rs;

	private static Loading loading;

	private int redniBroj;
	private boolean zavrseno = false;
	private JButton btnOsvezi;

	class Taskic extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			int ukupno = 0;
			int trenutno = 0;
			int gresaka = 0;

			try {
				conn = DbConn.getConnection();
				st = conn.createStatement();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Konekcija sa bazom nije uspostavljena, program ce se sada ugasiti.");
				System.exit(0);
			}

			rs.last();
			ukupno = rs.getRow();
			rs.beforeFirst();

			rs = st.executeQuery("select * from Artikli");

			setProgress(0);

			while (rs.next()) {
				System.out.println(rs.getString(1));
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
				setProgress(trenutno * 100 / ukupno);
				if (trenutno == ukupno) {
					zavrseno = true;
				}

			}
			return null;
		}

		@Override
		protected void done() {
			setProgress(100);
			super.done();
		}
	}

	public Gui() {
		loading.setLocationRelativeTo(this);
		loading.setVisible(true);
		loading.setProgress(0);

		setSize(new Dimension(600, 400));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/resources/logo.gif")));
		setTitle("Magacin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[399.00][grow]", "[196.00,fill][156.00,grow][]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		getContentPane().add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[][257.00,grow]", "[8.00][][31.00][][30.00][38.00]"));

		JLabel lblUnesiteNoviArtikl = new JLabel("Unesite novi artikl:");
		lblUnesiteNoviArtikl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblUnesiteNoviArtikl, "cell 1 0,alignx center");

		JLabel lblSifra = new JLabel("Sifra:");
		panel_1.add(lblSifra, "cell 0 1,alignx trailing");

		txtSifra = new JTextField();
		panel_1.add(txtSifra, "cell 1 1,growx,aligny top");
		txtSifra.setColumns(10);
		// txtSifra.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNaziv = new JLabel("Naziv:");
		panel_1.add(lblNaziv, "cell 0 2,alignx trailing");

		txtNaziv = new JTextField();
		panel_1.add(txtNaziv, "cell 1 2,growx");
		txtNaziv.setColumns(10);
		// txtNaziv.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblCena = new JLabel("Cena:");
		panel_1.add(lblCena, "cell 0 3,alignx trailing");

		txtCena = new JTextField();
		panel_1.add(txtCena, "cell 1 3,growx");
		txtCena.setColumns(10);
		// txtCena.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblKomada = new JLabel("Komada:");
		panel_1.add(lblKomada, "cell 0 4,alignx trailing");

		loading.setProgress(20);

		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(40, 20));
		spinner.setSize(new Dimension(60, 20));
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_1.add(spinner, "cell 1 4");

		btnDodaj = new JButton("Dodaj");
		panel_1.add(btnDodaj, "cell 1 5,alignx right,growy");
		btnDodaj.addActionListener(this);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, "cell 1 0 1 2,grow");
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_2.setLayout(new MigLayout("", "[grow]", "[][135.00,grow][]"));

		JLabel lblNewLabel = new JLabel("Tabela artikala\r\n");
		panel_2.add(lblNewLabel, "cell 0 0,alignx center");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("");
		panel_2.add(scrollPane, "cell 0 1,grow");

		String[] columNames = { "Redni Broj", "Sifra", "Naziv", "Cena", "Komada" };
		// String[][] data = { { "1", "12", "Avion", "15000", "2" }, { "2",
		// "14", "Komp", "10000", "10" } };
		model = new DefaultTableModel(columNames, 0);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int a = table.getSelectedRow();
				txtCena.setText(model.getValueAt(a, 3).toString().trim());
				txtNaziv.setText(model.getValueAt(a, 2).toString().trim());
				txtSifra.setText(model.getValueAt(a, 1).toString().trim());
				int sp = Integer.parseInt(model.getValueAt(a, 4).toString());
				spinner.setValue(sp);
			}
		});

		loading.setProgress(40);

		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				int red = e.getFirstRow();

			}
		});

		JLabel lblUnesiteRecZa = new JLabel("Unesite rec za pretragu:");
		panel_2.add(lblUnesiteRecZa, "flowx,cell 0 2,alignx left");

		txtTrazi = new JTextField();
		panel_2.add(txtTrazi, "cell 0 2,growx");
		txtTrazi.setColumns(10);

		table.addMouseListener(new MouseAdapter() {

		});
		txtTrazi.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				trazi(txtTrazi.getText());

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				trazi(txtTrazi.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				trazi(txtTrazi.getText());
			}
		});

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, "cell 0 1,grow");
		panel_3.setLayout(new MigLayout("", "[grow][][][][][]", "[grow][]"));

		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 1 2,grow");
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setLayout(new MigLayout("", "[][72.00,grow][72.00,grow][72.00,grow]", "[61.00]"));

		btnOsvezi = new JButton("Osvezi");
		panel.add(btnOsvezi, "cell 0 0");
		btnOsvezi.addActionListener(this);

		btnIzmeni = new JButton("Izmeni");
		panel.add(btnIzmeni, "cell 1 0,grow");

		btnIzbrisi = new JButton("Izbrisi");
		panel.add(btnIzbrisi, "cell 2 0,grow");
		btnIzbrisi.addActionListener(this);

		btnIzvestaj = new JButton("Izvestaj");
		panel.add(btnIzvestaj, "cell 3 0,grow");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		// Taskic task = new Taskic();
		// task.execute();
		loading.setProgress(80);

		ucitaj();

		loading.setProgress(100);
		loading.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDodaj) {

			try {
				st.executeUpdate("insert into Artikli (SifraArtikla, Naziv, Cena, Komada) values (1223, 'eeeee', 14444, 15)");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			try {
				spinner.commitEdit();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			int value = (Integer) spinner.getValue();
			model.addRow(new Object[] { redniBroj, txtSifra.getText().trim(), txtNaziv.getText().trim(),
					Integer.parseInt(txtCena.getText()), value });
			redniBroj++;
			try {
				st.executeUpdate("insert into Artikli (SifraArtikla, Naziv, Cena, Komada) values ("+ txtSifra.getText() +", '"+txtNaziv.getText()+"', "+txtCena.getText()+", "+value+")");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnIzbrisi) {
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(Gui.this, "Tabela je prazna.");
				} else
					JOptionPane.showMessageDialog(Gui.this, "Morate selektovati artikl.");
			} else {
				int row = table.getSelectedRow();
				String sifra = model.getValueAt(row, 1).toString();
				model.removeRow(row);
				clearFields();
				deleteFromDatabase(sifra);
			}
		} else if (e.getSource() == btnIzvestaj) {

		} else if (e.getSource() == btnIzmeni) {

		} else if (e.getSource() == btnOsvezi) {
			osvezi(rs);
		}
		System.out.println();
	}

	/**Method for search the database*/
	public void trazi(String rec) {
		try {
			model.setRowCount(0);
			rs = st.executeQuery("select * from Artikli");
			while (rs.next()) {
				if (rs.getString(1).trim().toLowerCase().contains(rec.toLowerCase()) || rs.getString(2).toLowerCase().trim().contains(rec.toLowerCase()) || rs.getString(3).trim().toLowerCase().contains(rec.toLowerCase()) || rs.getString(4).trim().toLowerCase().equals(rec.toLowerCase()) || rs.getString(5).trim().toLowerCase().equals(rec.toLowerCase()))
					model.addRow(new Object[] { rs.getString(1).trim(), rs.getString(2).trim(), rs.getString(3).trim(), rs.getString(4).trim(),
							rs.getString(5).trim() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**Delete from Database*/
	public void deleteFromDatabase(String sifra) {
		try {
			boolean a = st.execute("delete from Artikli where SifraArtikla = "+sifra+"");
			System.out.println(a);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearFields() {
		txtCena.setText("");
		txtNaziv.setText("");
		txtSifra.setText("");
		spinner.setValue(0);
	}
	/* Load values from databse*/
	public void ucitaj() {
		try {
			conn = DbConn.getConnection();
			// conn.setNetworkTimeout(null, 10000);
			st = conn.createStatement();
			rs = st.executeQuery("select * from Artikli");
			osvezi(rs);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Gui.this,
					"Konekcija ka bazi nije upostavljena.\nProgram ce se sada ugasiti.");
			System.exit(0);

		}
	}

	public void osvezi(ResultSet rs) {
		try {
			rs = st.executeQuery("select * from Artikli");
			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1).trim(), rs.getString(2).trim(), rs.getString(3).trim(), rs.getString(4).trim(),
						rs.getString(5).trim() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		loading = new Loading();
		Gui frame = new Gui();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "progress") {

		}

	}
}
