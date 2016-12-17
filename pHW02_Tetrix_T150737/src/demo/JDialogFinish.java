package demo;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JDialogFinish extends JDialog {
	JTextArea txaSrc = new JTextArea();
	JScrollPane sSrc = new JScrollPane(txaSrc); 
	public JDialogFinish(){
	
		setTitle("Hight Score");
		setLayout(null);
		setSize(300,400);
		setModal(true);
		add(sSrc);
		txaSrc.setEnabled(false);
		sSrc.setBounds(0,0,300,400);

	}
}
