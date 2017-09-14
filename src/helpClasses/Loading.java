package helpClasses;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice.WindowTranslucency;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class Loading extends JWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3686923313081563872L;
	private JProgressBar progressBar;

	
	public Loading() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setSize(new Dimension(300, 100));
		getContentPane().setLayout(new MigLayout("", "[]", "[21.00][grow]"));

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar, "cell 0 0,grow");

		JLabel lblOcitavamBazu = new JLabel("Ocitavam bazu....");
		getContentPane().add(lblOcitavamBazu, "cell 0 1,alignx center");
		pack();
		repaint();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    if (gd.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT)) {
            setBackground(new Color(0,0,0,0));
        }
	}

	public void setProgress(int progress) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				progressBar.setValue(progress);
				progressBar.repaint();
			}
		});
	}

}
