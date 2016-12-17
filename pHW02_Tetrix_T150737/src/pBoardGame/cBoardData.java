package pBoardGame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vnam
 */
public class cBoardData{
  
    public final static int COL = 12;
    public final static int ROW = 21;
    
  
    public static int[][] matrix = new int[ROW+1][COL];
    static{
        initGame();
    }
    public static void initGame(){
        int i,j;
        for( i=0; i<ROW+1; i++){
            for( j=0; j<COL; j++){
                matrix[i][j]=0;
            }
        }
    }
}

