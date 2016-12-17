package demo;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.text.ParseException;

import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;





public class JDialogStart extends JDialog {
	  JButton btnOk = new JButton("Ok");
	  JButton btnCan =  new JButton("Cancle");
	  JLabel lbllInput = new JLabel("Name:");
	  JFormattedTextField txtSrc ;
	  boolean AssisStart=false;
	  String inputName;
	  int e=0;
	  int x=20,y=50,w=60,h=30;
	    public JDialogStart(){
	    	setTitle("Demo");
	    	setLayout(null);
	    	setSize(300,200);
	    	setModal(true);
	    	INstallMIh();
	    	
	    }
	    public void INstallMIh(){
	    	try {
				MaskFormatter formatter = new MaskFormatter("**************");
				txtSrc=new JFormattedTextField(formatter);
				txtSrc.setColumns(20);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	add(btnOk);
	    	add(lbllInput);
	    	add(txtSrc);
	    	add(btnCan);
	    	
	    	Insets s =new Insets (1,1,1,1);
	    	lbllInput.setBounds(x,y,w,h);
	    	txtSrc.setBounds(x+w,y,w*3,h);
	    	btnCan.setMargin(s);
	    	btnOk.setBounds(x+w*3/2-40,y+2*h,w,h);
	    	btnCan.setBounds(x+w*3/2+40,y+2*h,w,h);
	    	btnCan.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					
				}
			});
	    	btnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent a) {
					// TODO Auto-generated method stub
					AssisStart=true;
					inputName=txtSrc.getText();
					txtSrc.setText("");
					setVisible(false);
					
				}
			});
	    	
	    }
	   
	    
}

