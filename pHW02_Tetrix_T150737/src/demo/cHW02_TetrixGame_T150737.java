/**
 * 
 */
package demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.print.attribute.standard.JobOriginatingUserName;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import demo.cHW02_TetrixGame_T150737.backgorunPAinr;
import pBoardGame.cBoardPanel;
import pTetrixGame.cBoardGame_Tetrix;

/**
 * @author KhaMinh T150737
 */
public class cHW02_TetrixGame_T150737 extends JFrame {
	cBoardPanel pGame = new cBoardPanel();

	int iNumberPieces = 0;
	Timer tAutoRunDown;
	// brick: TYPE, Dimension, status (moving or died), rPos, cPos
	KeyListener keyControl = null;
	int iDelayMove = 400;
	JButton btnStart = new JButton("Start");
	JButton btnPause = new JButton("||");
	JButton btnResume = new JButton(">");
	JButton btnReset = new JButton("Restart");
	int ix = 300, iy = 416, iw = 80, ih = 25, id = 10;
	JDialogStart pStartDL = new JDialogStart();
	boolean AutoRun;
	Timer t;
	/** Image **/
	backgorunPAinr nBackGorun = new backgorunPAinr();
	PaintMinh Pain = new PaintMinh();
	southPan pSouth = new southPan();
	int inameImage = 0;
	/** Score **/
	int iScoreMin = 0;
	int iMoveScoreSub = 0;
	int itDelayMinh;
	Timer tMinhTimer;
	boolean HightMin = false;
	LinkedList<HightScoreMin> listHs = new LinkedList<HightScoreMin>();
	JDialogFinish pFinishDL = new JDialogFinish();
	int icountScoreTime = 0; // So lan tu dong add vao arraylist
	int icountScoreTimeOver = 0; // in ra o Arraylist
	int input;
	String snamePlayer = null;
	/** Screen JDIALOG FINISH Hight Score **/
	boolean QuesTionEnd = false;
	boolean QuesHighEnd = false;
	int icountScoreComplete=0;
	public cHW02_TetrixGame_T150737() {
		setTitle(" T150737 – Tetrix Game");
		setLayout(null);
		setSize(450, 550);

		cBoardGame_Tetrix.initGame();

		Install();
		setLocationRelativeTo(this);
		this.setResizable(false);
	}

	public void Install() {

		add(btnStart);
		add(btnResume);
		add(btnPause);
		add(pGame);
		add(btnReset);
		add(Pain);
		add(nBackGorun);
		add(pSouth);
		Insets s = new Insets(1, 1, 1, 1);

		btnStart.setMargin(s);
		btnPause.setMargin(s);
		btnResume.setMargin(s);
		btnReset.setMargin(s);
		nBackGorun.setBounds(0, 0, 450, 550);
		Pain.setBounds(ix - 20, 20, 150, 350);
		pGame.setBounds(20, 20, 241, 421);
		btnStart.setBounds(ix - 30, iy, iw, ih);
		btnPause.setBounds(ix - 30, iy, iw - 50, ih);
		btnResume.setBounds(ix - 30 + iw - 50 + id, iy, iw - 50, ih);
		btnReset.setBounds(ix - 30 + iw - 50 + id + iw - 50 + id, iy, iw, ih);
		pSouth.setBounds(20, 20 + 421 + 10, 400, 50);
		btnResume.setVisible(false);
		btnPause.setVisible(false);
		btnReset.setVisible(false);

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Sound.play("./Sound/MerryJava.wav");
				pStartDL.setLocationRelativeTo(cHW02_TetrixGame_T150737.this);
				pStartDL.setFocusable(true);
				pStartDL.setResizable(false);
				pStartDL.setVisible(true);
				timeStart();
				icountScoreTime = 0;
				System.out.println(pStartDL.inputName);

			}
		});
		// Acation
		ActionListener bAc = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnPause) {
					Pause();
				} else if (e.getSource() == btnResume) {
					Resume();
				} else if (e.getSource() == btnReset) {
					Restart();
				}
			}
		};
		btnPause.addActionListener(bAc);
		btnResume.addActionListener(bAc);
		btnReset.addActionListener(bAc);
		keyControl = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					// move left
					if (cBoardGame_Tetrix.checkMoveLeft()) {
						cBoardGame_Tetrix.moveLeft();
					}
				}
				if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					// move right
					if (cBoardGame_Tetrix.checkMoveRight()) {
						cBoardGame_Tetrix.moveRight();
					}
				}

				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					if (cBoardGame_Tetrix.checkRotate()) {
						cBoardGame_Tetrix.rotate();

					}

				}
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					if (cBoardGame_Tetrix.checkMoveDown()) {
						cBoardGame_Tetrix.moveDown();
					}
				}
			}
		};
		pGame.addKeyListener(keyControl);
		this.addKeyListener(keyControl);

		tAutoRunDown = new Timer(iDelayMove, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				boolean bl = cBoardGame_Tetrix.checkMoveDown();
				if (bl) {
					// can move more
					cBoardGame_Tetrix.moveDown();
					
				} else {

					// died, could not move
					int iStoppedX = cBoardGame_Tetrix.getActiveRow();
					int nMoveScore = cBoardGame_Tetrix.clearFullRow();
					if(nMoveScore==cBoardGame_Tetrix.clearFullRow()){
						Sound.play("./Sound/iphone.wav");
					}
					System.out.println("Collapsed " + nMoveScore + " Line(s).");
				
					// tinh diem
					
					if (nMoveScore == 1) {
						iScoreMin = nMoveScore + iScoreMin;
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					} else if (nMoveScore == 2) {
						iScoreMin = (int) (nMoveScore * 1.5 + iScoreMin);
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					} else if (nMoveScore == 3) {
						iScoreMin = (int) (nMoveScore * 2 + iScoreMin);
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					} else if (nMoveScore == 4) {
						iScoreMin = (int) (nMoveScore * 2.5 + iScoreMin);
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					} else if (nMoveScore == 5) {
						iScoreMin = (int) (nMoveScore * 3 + iScoreMin);
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					} else if (nMoveScore == 6) {
						iScoreMin = (int) (nMoveScore * 3.5 + iScoreMin);
						iMoveScoreSub = iMoveScoreSub + iScoreMin;
						iScoreMin = 0;
					}

					// scoring and set level here
					if (cBoardGame_Tetrix.isGameOver()) {

						// end of game
						tAutoRunDown.stop();
						QuesHighEnd = true;

					} else {
						iNumberPieces++;
						cBoardGame_Tetrix.newBrick();
						System.out.println("Next: " + cBoardGame_Tetrix.getNextBrickName());
					}
				}

				for (int i = 0; i < iMoveScoreSub; i++) {
					tAutoRunDown.setInitialDelay(iDelayMove - i * 10);

					tAutoRunDown.restart();
				}
				OverHightScore();
			}
		});

	}

	// Cho timer chay de xet Dieu Kien ben Class Phu
	public void timeStart() {
		if (pStartDL.AssisStart == true) {

			tAutoRunDown.setInitialDelay(iDelayMove);
			tAutoRunDown.start();
			pGame.requestFocus();
			AutoRun = true;
			btnStart.setVisible(false);
			btnPause.setVisible(true);
			btnResume.setVisible(true);
			btnReset.setVisible(true);
			btnResume.setEnabled(false);

		}

	}

	public void Pause() {

		tAutoRunDown.stop();
		btnPause.setEnabled(false);
		pGame.setVisible(false);
		btnResume.setEnabled(true);
		pGame.requestFocus();

	}

	public void Resume() {
		btnPause.setEnabled(true);
		btnResume.setEnabled(false);
		tAutoRunDown.start();
		pGame.setVisible(true);
		pGame.requestFocus();
	}

	public void Restart() {
		cBoardGame_Tetrix.initGame();

		pStartDL.setLocationRelativeTo(cHW02_TetrixGame_T150737.this);
		pStartDL.setFocusable(true);
		pStartDL.setVisible(true);
		HightMin = true;
		if (pStartDL.AssisStart == true) {
			btnPause.setEnabled(true);
			tAutoRunDown.setInitialDelay(iDelayMove);
			tAutoRunDown.start();
			pStartDL.txtSrc.requestFocus();
			btnStart.setVisible(false);
			btnPause.setVisible(true);
			btnResume.setVisible(true);
			btnReset.setVisible(true);
			icountScoreTime = 0;
			iMoveScoreSub = 0;
			pGame.requestFocus();
			System.out.println(pStartDL.inputName);// ahihi
		}
	}

	public class PaintMinh extends JPanel {
		String linkImages[] = { "./Imgaes/I.jpg", "./Imgaes/J.jpg", "./Imgaes/L.jpg", "./Imgaes/O.png",
				"./Imgaes/S.jpg", "./Imgaes/T.jpg", "./Imgaes/Z.jpg", };
		ImageIcon ChooseImage[] = new ImageIcon[7];
		int xIMA = 0, yIMA = 260, w = 80;

		public PaintMinh() {
			for (int i = 0; i < 7; i++) {
				ChooseImage[i] = new ImageIcon(linkImages[i]);
			}

		}

		public void paint(Graphics g) {
			super.paint(g);
			setBackground(Color.BLACK);
			Font fontName = new Font("Name", Font.BOLD, 20);
			g.setFont(fontName);
			g.setColor(Color.WHITE);
			if (pStartDL.inputName != null) {
				g.drawString(pStartDL.inputName, xIMA, yIMA - 200);

			}

			Font fontmeo = new Font("Tao Font", Font.BOLD, 50);
			g.setFont(fontmeo);
			g.setColor(Color.GREEN);
			g.drawString("Score ", xIMA, yIMA - 120);
			g.setColor(Color.RED);
			g.drawString(iMoveScoreSub + "", xIMA + 50, yIMA - 50);

			repaint();
			if (cBoardGame_Tetrix.getNextBrickName().equals("I")) {
				inameImage = 0;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("J")) {
				inameImage = 1;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("L")) {
				inameImage = 2;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("O")) {
				inameImage = 3;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("S")) {
				inameImage = 4;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("T")) {
				inameImage = 5;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			} else if (cBoardGame_Tetrix.getNextBrickName().equals("Z")) {
				inameImage = 6;
				g.drawImage(ChooseImage[inameImage].getImage(), xIMA + 20, yIMA, w, w, this);
			}

			repaint();
		}
	}

	public class backgorunPAinr extends JPanel {
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 450, 550);
			g.setColor(Color.GREEN);
			Font fontmeo = new Font("Tao Font", Font.BOLD, 50);
			g.setFont(fontmeo);

		}

	}

	public class southPan extends JPanel {
		ImageIcon iConStar = new ImageIcon("./Imgaes/star.jpg");
		int x = 0, y = 0, w = 50;

		public void paint(Graphics g) {
			super.paint(g);
			setBackground(Color.BLACK);
			if (iMoveScoreSub >= 5 && iMoveScoreSub < 15) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
			} else if (iMoveScoreSub >= 15 && iMoveScoreSub < 20) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
			} else if (iMoveScoreSub >= 20 && iMoveScoreSub < 30) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w, y, w, w, this);
			} else if (iMoveScoreSub >= 30 && iMoveScoreSub < 35) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w, y, w, w, this);
			} else if (iMoveScoreSub >= 35 && iMoveScoreSub < 45) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w, y, w, w, this);
			} else if (iMoveScoreSub >= 45 && iMoveScoreSub < 60) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w + w, y, w, w, this);

			} else if (iMoveScoreSub >= 60 && iMoveScoreSub < 80) {
				g.drawImage(iConStar.getImage(), x, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w + w, y, w, w, this);
				g.drawImage(iConStar.getImage(), x + w + w + w + w + w + w, y, w, w, this);
			}
			repaint();

		}

	}

	public void OverHightScore() {
		if (QuesHighEnd == true) {
			QuesHighEnd = false;
			btnPause.setEnabled(false);
			System.err.println("Game Overs");
			input = iMoveScoreSub;
			tAutoRunDown.stop();
			snamePlayer = pStartDL.inputName;
			Font font = new Font("Current", Font.BOLD, 20);
			pFinishDL.txaSrc.setFont(font);
			pFinishDL.txaSrc.setDisabledTextColor(Color.RED);
			System.out.println(icountScoreTime);
			pFinishDL.txaSrc.setText("");
			icountScoreTimeOver = icountScoreTimeOver + 1;
			icountScoreTime = 1;
			HightScoreMin hs = new HightScoreMin();
			if (icountScoreTime == 1) {
				for (int i = 0; i < icountScoreTime; i++) {
					hs.setName(snamePlayer);
					hs.setScore(iMoveScoreSub);
					listHs.add(hs);
				}
				icountScoreComplete=icountScoreComplete+1;

			}
			if (icountScoreComplete != 1) {// de no khong bi
																// trung Score
																// khi ra ket
																// qua diem
				for (int i = 0; i < icountScoreTimeOver; i++) {
					if (listHs.size() < 10) {
						if (Math.max(iMoveScoreSub, listHs.get(i).getScore()) == iMoveScoreSub) {
							listHs.add(i, hs);
							break;
						} else {
							listHs.add(hs);
						}
					} else {
						if (Math.max(iMoveScoreSub, listHs.get(i).getScore()) == iMoveScoreSub) {
							listHs.add(i, hs);
							listHs.removeLast();
							break;
						} else {
							listHs.add(hs);
						}
					}

				}
			}
			if (icountScoreTimeOver > 10) {
				icountScoreTimeOver = 10;
				for (int i = 0; i < icountScoreTimeOver; i++) {
					pFinishDL.txaSrc.append(listHs.get(i).getName() + " " + listHs.get(i).getScore() + "\n");
				}
			} else {
				for (int i = 0; i < icountScoreTimeOver; i++) {
					pFinishDL.txaSrc.append(listHs.get(i).getName() + " " + listHs.get(i).getScore() + "\n");
				}
			}

			pFinishDL.setLocationRelativeTo(cHW02_TetrixGame_T150737.this);
			pFinishDL.setFocusable(true);
			pFinishDL.setResizable(false);
			pFinishDL.setVisible(true);
			pFinishDL.setDefaultCloseOperation(pFinishDL.DISPOSE_ON_CLOSE);
		}

	}

	/**
	 * x
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		cHW02_TetrixGame_T150737 f = new cHW02_TetrixGame_T150737();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
