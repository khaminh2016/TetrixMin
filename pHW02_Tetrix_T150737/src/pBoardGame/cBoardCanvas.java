package pBoardGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vnam
 */
public class cBoardCanvas {
//    
    private static final int gridW = 20;
    public static final int canvasW = cBoardData.COL*gridW+1;
    public static final int canvasH = cBoardData.ROW*gridW+1;
    private static BufferedImage canvas = new BufferedImage(canvasW,canvasH,Image.SCALE_DEFAULT);
    private static Graphics2D gC = (Graphics2D)canvas.getGraphics();
    private static int x0 =0;
    private static int y0 =0;
    public static BufferedImage getCanvas(){
        return  canvas;
    }
    private static Color[] fillColors = {
    		 Color.WHITE, 
             Color.LIGHT_GRAY, 
             Color.GREEN, 
             Color.BLUE, 
             Color.MAGENTA, 
             Color.ORANGE, 
             Color.RED, 
             Color.CYAN	
    };
    public static void drawCanvas(){
        int i, j;
        int x = x0, y = y0;
//        for (i = 0; i < cBoardData.ROW; i++) {
        for (i =cBoardData.ROW-1; i>=0; i--) {
            x = x0;
            for (j = 0; j < cBoardData.COL; j++) {
                gC.setColor(fillColors[cBoardData.matrix[i][j]]);
                gC.fillRect(x, y, gridW, gridW);
              	x += gridW;
            }
            y = y + gridW;
        }
        gC.setColor(Color.GRAY);
        y = y0;
        for (i = 0; i <= cBoardData.ROW; i++) {
           gC.drawLine(0, y, canvasH, y);
            y = y + gridW;
        }
        x= x0;
        for (i = 0; i <= cBoardData.COL; i++) {
            gC.drawLine(x, 0, x, canvasH);
            x = x + gridW;
        }
    }
}
