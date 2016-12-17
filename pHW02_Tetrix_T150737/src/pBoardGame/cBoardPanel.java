package pBoardGame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class cBoardPanel extends JPanel{
	private Timer tRefreshScreen = new Timer(50, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cBoardCanvas.drawCanvas();
			repaint();
		}
	});
	public cBoardPanel(){
		this.setSize(cBoardCanvas.canvasW+1, cBoardCanvas.canvasH+1);
		tRefreshScreen.start();
		System.err.println("Warning: the minimum required width and heigh of PANEL: ("+ cBoardCanvas.canvasW+", "+ cBoardCanvas.canvasH+")" );
	}
	
	public void paint(Graphics g){
		g.drawImage(cBoardCanvas.getCanvas(), 0, 0, this);
	}
	
}
