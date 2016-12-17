package pTetrixGame;

import javax.swing.JPanel;

import org.w3c.dom.CDATASection;

import pBoardGame.cBoardData;

/*
 * Update LOG:
 * Created by Micheal Vu (Jun 2013) 
 * Jun 20 2013: Micheal Vu create the class with all basic functions and support the T brick 
 * Oct 01 2014: Micheal Vu support the Z brick 
 * Oct 20 2014: Micheal Vu modified to support the O brick  
 * Oct 23 2014; Micheal Vu support the feature getNextBrickName();
 * Nov 2016: Add I brick Refactoring the code to sub-functions
 */
public class cBoardGame_Tetrix {

	// public final static int COL = 12;
	// public final static int ROW = 21;
	private static int N_BRICK_TYPE = 7;
	// this array is the list of BRICKs have been support by developer
	private static int[] aBrickType = { 1, 2, 3, 4, 5, 6, 7 };
	private static int[] aInitBrick = { 0, 3, 4, 5 };

	// This array to make reduce the duplicated brick as randomize
	private static int[] aLockedBrick = { 2, 1, 2, 1 };

	private static int N_TRIES = N_BRICK_TYPE;
	private static String[] sBrickName = { "", "T", "Z", "S", "J", "L", "I", "O" };

	/**
	 * Type of active brick brick type 1: T 2: Z- left 3:S (Z-right); 4: J
	 * (L-Left) 5:L (L-right) 6: I; 7: O (square) T Z S J L I O Type = -1 : not
	 * ready
	 */
	private static int iCurrentBrickType = -1;

	// 0: normal, 90: rot 90, 180: rot 180,
	// 270: rot 270
	private static int iCurrentBrickDim = 0;

	// True: ready to move
	// FALSE: not ready or died
	public static boolean bCurrentBrickStatus = false; // false:

	// rPos: row posiion of active brick
	// cPos: column position of active brick
	private static int rPos, cPos;
	private static int iNextBrick = -1;

	public static void initGame() {
		cBoardData.initGame();
		rPos = 0;
		cPos = 0;
		iCurrentBrickType = -1;
		bCurrentBrickStatus = true;
		iNextBrick = initBrick();
	}

	private static int initBrick() {

		int k = aInitBrick[((int) (Math.random() * 1000)) % aInitBrick.length];
		aLockedBrick[3] = k;
		return k;
	}

	private static int randomBrick() {

		int k = 0, i = 0;
		for (; i < N_TRIES; i++) {
			k = ((int) (Math.random() * 1000)) % N_BRICK_TYPE;
			if (k != aLockedBrick[0] && k != aLockedBrick[1] && k != aLockedBrick[2] && k != aLockedBrick[3]) {
				break;
			}
		}
		aLockedBrick[0] = aLockedBrick[1];
		aLockedBrick[1] = aLockedBrick[2];
		aLockedBrick[2] = aLockedBrick[3];
		aLockedBrick[3] = k;
		return k;
	}

	/*
	 * new brick There're 7 kind of brick: T, Z, S, J, L , I and O (Square),
	 * each brick has 4 grids @retunr a boolean value (true, false). Before add
	 * new brick, It needs to check the avaiable area for it, if there're no
	 * space for new brick the false value will be return. Otherwise, a true
	 * value will be return after creating new brick. Each grid of the brick
	 * will store the value equals type of brick (1-7). This values will effect
	 * into the color of the brick (grids)
	 */
	public static boolean newBrick() {
		iCurrentBrickType = aBrickType[iNextBrick];
		// random 1: T, 2:Z- left, 3:S , 4:J, 5: L, 6: I, 7: O square
		// SQUARE
		iNextBrick = randomBrick();
		iCurrentBrickDim = 0;
		bCurrentBrickStatus = true;
		rPos = cBoardData.ROW;
		cPos = cBoardData.COL / 2;
		if (iCurrentBrickType == 1) {
			// # $ #
			// #
			if (cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos - 1] + cBoardData.matrix[rPos][cPos + 1]
					+ cBoardData.matrix[rPos - 1][cPos] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
		}
		if (iCurrentBrickType == 2) {
			// # $
			// # #
			if (cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos - 1]
					+ cBoardData.matrix[rPos - 1][cPos + 1] + cBoardData.matrix[rPos - 1][cPos] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;

		}
		if (iCurrentBrickType == 3) {
			// $ #
			// # #
			if (cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos + 1]
					+ cBoardData.matrix[rPos - 1][cPos - 1] + cBoardData.matrix[rPos - 1][cPos] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
		}
		if (iCurrentBrickType == 4) {
			// J type
			// # $ #
			// #
			if (cBoardData.matrix[rPos][cPos - 1] + cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos + 1]
					+ cBoardData.matrix[rPos - 1][cPos + 1] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
		}
		if (iCurrentBrickType == 5) {
			// L type
			// # $ #
			// #
			if (cBoardData.matrix[rPos][cPos - 1] + cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos + 1]
					+ cBoardData.matrix[rPos - 1][cPos - 1] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
		}
		if (iCurrentBrickType == 6) {
			// I type
			// # $ # #
			if (cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos - 1] + cBoardData.matrix[rPos][cPos + 1]
					+ cBoardData.matrix[rPos][cPos + 2] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
		}
		if (iCurrentBrickType == 7) {
			// # $
			// # #
			if (cBoardData.matrix[rPos][cPos] + cBoardData.matrix[rPos][cPos - 1]
					+ cBoardData.matrix[rPos - 1][cPos - 1] + cBoardData.matrix[rPos - 1][cPos] > 0) {
				// there's no space for new brick
				bCurrentBrickStatus = false;
				return false;
			}
			cBoardData.matrix[rPos][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;

		}

		return true;

	}

	public static int clearFullRow() {
		int nFullRow = 0, i, j, k;
		for (i = cBoardData.ROW - 1; i >= 0; i--) {
			j = 0;
			while (j < cBoardData.COL && cBoardData.matrix[i][j] > 0) {
				j++; // count the number of NON ZERO
			}
			if (j == cBoardData.COL) {
				// row i is full
				nFullRow++;
				// remove the row i (full row)
				for (k = 0; k < cBoardData.COL; k++) {
					for (j = i; j < cBoardData.ROW; j++) {
						cBoardData.matrix[j][k] = cBoardData.matrix[j + 1][k];
					}
				}
			}
		}
		return nFullRow;
	}

	/*
	 * get the current row of active brick
	 */
	public static int getActiveRow() {
		return rPos;
	}// end function

	public static String getNextBrickName() {
		return sBrickName[aBrickType[iNextBrick]];
	}

	public static int getNextBrickType() {
		return aBrickType[iNextBrick];
	}

	public static boolean isGameOver() {
		return rPos == cBoardData.ROW;
	}

	public static boolean isGameStarted() {
		return iCurrentBrickType > -1;
	}

	public static boolean checkMoveLeft() {
		if (bCurrentBrickStatus == false) {
			return false;
		}
		switch (iCurrentBrickType) {
		case 1:
			return checkMoveLeftT();
		case 2:
			return checkMoveLeftZ();
		case 3:
			return checkMoveLeftS();
		case 4:
			return checkMoveLeftJ();
		case 5:
			return checkMoveLeftL();
		case 6:
			return checkMoveLeftI();
		case 7:
			return checkMoveLeftO();
		}
		return false;
	}

	public static boolean checkMoveRight() {
		if (bCurrentBrickStatus == false) {
			return false;
		}
		switch (iCurrentBrickType) {
		case 1:
			return checkMoveRightT();
		case 2:
			return checkMoveRightZ();
		case 3:
			return checkMoveRightS();
		case 4:
			return checkMoveRightJ();
		case 5:
			return checkMoveRightL();
		case 6:
			return checkMoveRightI();
		case 7:
			return checkMoveRightO();
		}
		return false;
	}

	public static boolean checkMoveDown() {
		if (bCurrentBrickStatus == false) {
			return false;
		}
		switch (iCurrentBrickType) {
		case 1:
			return checkMoveDownT();
		case 2:
			return checkMoveDownZ();
		case 3:
			return checkMoveDownS();
		case 4:
			return checkMoveDownJ();
		case 5:
			return checkMoveDownL();
		case 6:
			return checkMoveDownI();
		case 7:
			return checkMoveDownO();
		}
		return false;
	}

	public static boolean checkRotate() {
		if (bCurrentBrickStatus == false) {
			return false;
		}
		switch (iCurrentBrickType) {
		case 1:
			return checkRotateT();
		case 2:
			return checkRotateZ();
		case 3:
			return checkRotateS();
		case 4:
			return checkRotateJ();
		case 5:
			return checkRotateL();
		case 6:
			return checkRotateI();
		case 7:
			return checkRotateO();
		}
		return false;
	}

	public static void moveLeft() {
		if (!checkMoveLeft()) {
			return;
		}
		switch (iCurrentBrickType) {
		case 1:
			moveLeftT();
			break;
		case 2:
			moveLeftZ();
			break;
		case 3:
			moveLeftS();
			break;
		case 4:
			moveLeftJ();
			break;
		case 5:
			moveLeftL();
			break;
		case 6:
			moveLeftI();
			break;
		case 7:
			moveLeftO();
		}
		// return true;
		cPos--;
	}

	public static void moveRight() {
		if (!checkMoveRight()) {
			return;
		}
		switch (iCurrentBrickType) {
		case 1:
			moveRightT();
			break;
		case 2:
			moveRightZ();
			break;
		case 3:
			moveRightS();
			break;
		case 4:
			moveRightJ();
			break;
		case 5:
			moveRightL();
			break;
		case 6:
			moveRightI();
			break;
		case 7:
			moveRightO();
		}
		cPos++;
	}

	public static void moveDown() {
		if (!checkMoveDown()) {
			return;
		}
		switch (iCurrentBrickType) {
		case 1:
			moveDownT();
			break;
		case 2:
			moveDownZ();
			break;
		case 3:
			moveDownS();
			break;
		case 4:
			moveDownJ();
			break;
		case 5:
			moveDownL();
			break;
		case 6:
			moveDownI();
			break;
		case 7:
			moveDownO();
			break;
		}

		rPos--;
	}

	public static void rotate() {
		if (!checkRotate()) {
			return;
		}
		// anti - clock wise
		switch (iCurrentBrickType) {
		case 1:
			rotateT();
			break;
		case 2:
			rotateZ();
			break;
		case 3:
			rotateS();
			break;
		case 4:
			rotateJ();
			break;
		case 5:
			rotateL();
			break;
		case 6:
			rotateI();
			break;
		// case 7: // do nothing

		}

	}

	/*
	 * Private function to verify the status of the brick Check the moving of
	 * the current piece
	 */
	/*
	 * check move LEFT
	 */
	private static boolean checkMoveLeftT() {
		// T
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos - 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if ((cPos < 1) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos - 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) || (cBoardData.matrix[rPos][cPos - 2] > 0) // 1
																												// died
																												// cell
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveLeftZ() {
		// Z-lefl
		if (iCurrentBrickDim == 0) {
			//
			// # $
			// # #
			if (cPos < 2 || cBoardData.matrix[rPos][cPos - 2] > 0 || cBoardData.matrix[rPos - 1][cPos - 1] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			if (cPos < 2 || cBoardData.matrix[rPos][cPos - 2] > 0 || cBoardData.matrix[rPos - 1][cPos - 2] > 0
					|| cBoardData.matrix[rPos + 1][cPos - 1] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveLeftS() {
		if (iCurrentBrickDim == 0) {
			// $ #
			// # #
			if (cPos < 2 || cBoardData.matrix[rPos][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos - 2] > 0) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if (cPos < 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0 || cBoardData.matrix[rPos][cPos - 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveLeftJ() {
		// J
		if (iCurrentBrickDim == 0) {
			// J type
			// # $ #
			// #
			if (cPos < 2 // at the left-bound
					|| (cBoardData.matrix[rPos][cPos - 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			if ((cPos < 1) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 2] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos - 2] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveLeftL() {
		// J
		if (iCurrentBrickDim == 0) {
			// J type
			// # $ #
			// #
			if (cPos < 2 // at the left-bound
					|| (cBoardData.matrix[rPos][cPos - 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos - 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 2] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos][cPos - 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			if ((cPos < 1) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos - 1] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveLeftI() {
		// I type

		if (iCurrentBrickDim == 0) {
			// # $ # #
			if (cPos < 2 || cBoardData.matrix[rPos][cPos - 2] > 0) {
				// there's no space to LEFT
				return false;
			}

		}

		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			if (cPos < 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0 || cBoardData.matrix[rPos][cPos - 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 2][cPos - 1] > 0) {
				// there's no space to LEFT
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveLeftO() {
		// Square
		if (cPos < 2 || cBoardData.matrix[rPos][cPos - 2] > 0 || cBoardData.matrix[rPos - 1][cPos - 2] > 0) {
			return false;
		}
		return true;
	}

	/*
	 * Check Move Right for each kind of piece
	 */
	private static boolean checkMoveRightT() {
		// T
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((cPos >= cBoardData.COL - 2) // at the right-bound
					|| (cBoardData.matrix[rPos][cPos + 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos + 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos + 1][cPos + 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos + 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos + 1][cPos + 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			if ((cPos >= cBoardData.COL - 1) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) || (cBoardData.matrix[rPos + 1][cPos + 1] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveRightZ() {
		// Z-lefl
		if (iCurrentBrickDim == 0) {
			//
			// # $
			// # #
			if (cPos >= cBoardData.COL - 2 || cBoardData.matrix[rPos][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos + 2] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			if (cPos >= cBoardData.COL - 1 || cBoardData.matrix[rPos][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0 || cBoardData.matrix[rPos + 1][cPos + 1] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveRightS() {
		// Z-lefl
		if (iCurrentBrickDim == 0) {
			// $ #
			// # #
			if (cPos >= cBoardData.COL - 2 || cBoardData.matrix[rPos][cPos + 2] > 0
					|| cBoardData.matrix[rPos - 1][cPos + 1] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if (cPos >= cBoardData.COL - 2 || cBoardData.matrix[rPos + 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos][cPos + 2] > 0 || cBoardData.matrix[rPos + 1][cPos + 2] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveRightJ() {
		// J
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((cPos >= cBoardData.COL - 2) // at the right-bound
					|| (cBoardData.matrix[rPos][cPos + 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos + 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos + 2] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos][cPos + 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			if ((cPos >= cBoardData.COL - 1) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos + 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveRightL() {
		// L
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((cPos >= cBoardData.COL - 2) // at the right-bound
					|| (cBoardData.matrix[rPos][cPos + 2] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			if ((cPos >= cBoardData.COL - 1) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos + 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos + 2] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 2] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			if ((cPos >= cBoardData.COL - 2) // at the left-bound
					|| (cBoardData.matrix[rPos + 1][cPos + 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 2] > 0)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkMoveRightI() {
		// I type

		if (iCurrentBrickDim == 0) {
			// # $ # #
			if (cPos >= cBoardData.COL - 3 || cBoardData.matrix[rPos][cPos + 3] > 0) {
				// there's no space to RIGHT
				return false;
			}

		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			if (cPos >= cBoardData.COL - 1 || cBoardData.matrix[rPos][cPos + 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 2][cPos + 1] > 0) {
				// there's no space to RIGHT
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveRightO() {
		// Square
		if (cPos >= cBoardData.COL - 1 || cBoardData.matrix[rPos][cPos + 1] > 0
				|| cBoardData.matrix[rPos - 1][cPos + 1] > 0) {
			return false;
		}
		return true;
	}

	/*
	 * check move down
	 */
	private static boolean checkMoveDownT() {
		// T
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((rPos <= 1) // at the right-bound
					|| (cBoardData.matrix[rPos - 2][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 2][cPos] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((rPos <= 0) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 2][cPos] > 0)) {
				return false;
				// return rPos;
			}

		}
		return true;
	}

	private static boolean checkMoveDownZ() {
		// Z-lefl
		if (iCurrentBrickDim == 0) {
			//
			// # $
			// # #
			if (rPos < 2 || cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 2][cPos] > 0
					|| cBoardData.matrix[rPos - 2][cPos + 1] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			if (rPos < 2 || cBoardData.matrix[rPos - 2][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveDownS() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// S-lefl
		if (iCurrentBrickDim == 0) {
			//
			// $ #
			// # #
			if (rPos < 2 || cBoardData.matrix[rPos - 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 2][cPos] > 0
					|| cBoardData.matrix[rPos - 2][cPos - 1] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			if (rPos < 2 || cBoardData.matrix[rPos - 2][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveDownJ() {
		// J
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((rPos < 2) // at the right-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0) || (cBoardData.matrix[rPos - 2][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos + 1] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 2][cPos] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((rPos <= 0) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos - 2][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 2][cPos - 1] > 0)) {
				return false;
				// return rPos;
			}

		}
		return true;
	}

	private static boolean checkMoveDownL() {
		// L
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			if ((rPos < 2) // at the right-bound
					|| (cBoardData.matrix[rPos - 2][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 2][cPos] > 0)) {
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			if ((rPos <= 0) // at the left-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			if ((rPos < 2) // at the left-bound
					|| (cBoardData.matrix[rPos - 2][cPos] > 0) // 1 died cell
					|| (cBoardData.matrix[rPos - 2][cPos + 1] > 0)) {
				return false;
				// return rPos;
			}

		}
		return true;
	}

	private static boolean checkMoveDownI() {
		// I type

		if (iCurrentBrickDim == 0) {
			// # $ # #
			if (rPos < 1 || cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0
					|| cBoardData.matrix[rPos - 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 2] > 0) {
				// there's no space to DOWN
				return false;
			}

		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			if (rPos < 3 || cBoardData.matrix[rPos - 3][cPos] > 0) {
				// there's no space to DOWN
				return false;
			}
		}
		return true;
	}

	private static boolean checkMoveDownO() {
		// Square
		if (rPos < 2 || cBoardData.matrix[rPos - 2][cPos] > 0 || cBoardData.matrix[rPos - 2][cPos - 1] > 0) {
			return false;
		}
		return true;
	}

	/*
	 * CHeck rptation
	 */
	private static boolean checkRotateT() {
		// T
		if (iCurrentBrickDim == 0) {
			// #
			// # $ # => $ #
			// # #
			if (rPos >= cBoardData.ROW || cBoardData.matrix[rPos + 1][cPos] > 0
					|| cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0) { // 1
																	// died
																	// cell
				return false;
			}
		}

		if (iCurrentBrickDim == 90) {
			// # #
			// $ # => # $ #
			// #
			if (cPos < 1 || cBoardData.matrix[rPos][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0 || cBoardData.matrix[rPos + 1][cPos - 1] > 0) { // 1
																													// died
																													// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// # #
			// # $ # => # $
			// #
			if (rPos < 1 || cBoardData.matrix[rPos - 1][cPos] > 0 || cBoardData.matrix[rPos + 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos - 1] > 0) { // 1
																													// died
																													// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $ => # $ #
			// # #
			if (rPos >= cBoardData.ROW || (cPos == cBoardData.COL - 1) // at the
																		// left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) || (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateZ() {
		// Z-lefl
		if (iCurrentBrickDim == 0) {
			//
			// H -> V
			//
			if (rPos >= cBoardData.ROW || cBoardData.matrix[rPos - 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos - 1] > 0 || cBoardData.matrix[rPos + 1][cPos] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			if (rPos >= cBoardData.ROW || cPos == cBoardData.COL - 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateS() {
		// S
		if (iCurrentBrickDim == 0) {
			// #
			// @ # @ #
			// # # #
			if (rPos >= cBoardData.ROW || cBoardData.matrix[rPos + 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0) {
				return false;
			}
		}
		// T
		if (iCurrentBrickDim == 90) {
			// #
			// $ # => V
			// #
			if (rPos >= cBoardData.ROW || cPos < 1 || cBoardData.matrix[rPos + 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0 || cBoardData.matrix[rPos - 1][cPos - 1] > 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateJ() {
		// J
		if (iCurrentBrickDim == 0) {
			// # #
			// # $ # => $
			// # #
			if (rPos >= cBoardData.ROW || cBoardData.matrix[rPos + 1][cPos] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0) { // 1
																// died
																// cell
				return false;
			}
		}

		if (iCurrentBrickDim == 90) {
			// # # #
			// $ => # $ #
			// #
			if (cPos < 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0 || cBoardData.matrix[rPos][cPos - 1] > 0
					|| cBoardData.matrix[rPos][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0) { // 1
																												// died
																												// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// # #
			// # $ # => $
			// # #
			if (rPos < 1 || cBoardData.matrix[rPos + 1][cPos] > 0 || cBoardData.matrix[rPos + 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0) { // 1
																												// died
																												// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $ => # $ #
			// # # #
			if (rPos >= cBoardData.ROW || (cPos == cBoardData.COL - 1) // at the
																		// left-bound
					|| (cBoardData.matrix[rPos + 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos][cPos + 1] > 0)
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateL() {
		// L
		if (iCurrentBrickDim == 0) {
			// #
			// # $ # => $
			// # # #
			if (rPos >= cBoardData.ROW || cBoardData.matrix[rPos + 1][cPos] > 0
					|| cBoardData.matrix[rPos + 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos] > 0) { // 1
																// died
																// cell
				return false;
			}
		}

		if (iCurrentBrickDim == 90) {
			// # #
			// $ => # $ #
			// # #
			if (cPos < 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0 || cBoardData.matrix[rPos][cPos - 1] > 0
					|| cBoardData.matrix[rPos][cPos + 1] > 0 || cBoardData.matrix[rPos + 1][cPos + 1] > 0) { // 1
																												// died
																												// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 180) {
			// # # #
			// # $ # => $
			// #
			if (rPos < 1 || cBoardData.matrix[rPos + 1][cPos] > 0 || cBoardData.matrix[rPos + 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos - 1] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0) { // 1
																												// died
																												// cell
				return false;
			}
		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $ => # $ #
			// # #
			if (rPos >= cBoardData.ROW || (cPos == cBoardData.COL - 1) // at the
																		// left-bound
					|| (cBoardData.matrix[rPos - 1][cPos - 1] > 0) // 1 died
																	// cell
					|| (cBoardData.matrix[rPos][cPos - 1] > 0) || (cBoardData.matrix[rPos][cPos + 1] > 0)
					|| (cBoardData.matrix[rPos - 1][cPos + 1] > 0)) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateI() {
		// I type
		if (iCurrentBrickDim == 0) {
			// # $ # #
			if (rPos < 2 || rPos >= cBoardData.ROW - 1 || cBoardData.matrix[rPos + 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos + 1][cPos] > 0 || cBoardData.matrix[rPos - 1][cPos] > 0
					|| cBoardData.matrix[rPos - 1][cPos + 1] > 0 || cBoardData.matrix[rPos - 1][cPos + 2] > 0
					|| cBoardData.matrix[rPos - 2][cPos] > 0 || cBoardData.matrix[rPos - 2][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 2][cPos + 2] > 0) {
				// there's no space to rotate
				return false;
			}
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			if (cPos < 1 || cPos >= cBoardData.COL - 2 || cBoardData.matrix[rPos + 1][cPos - 1] > 0
					|| cBoardData.matrix[rPos][cPos - 1] > 0 || cBoardData.matrix[rPos][cPos + 1] > 0
					|| cBoardData.matrix[rPos][cPos + 2] > 0 || cBoardData.matrix[rPos - 1][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 1][cPos + 2] > 0 || cBoardData.matrix[rPos - 2][cPos + 1] > 0
					|| cBoardData.matrix[rPos - 2][cPos + 2] > 0) {
				// there's no space to rotate
				return false;
			}
		}
		return true;
	}

	private static boolean checkRotateO() {
		if (iCurrentBrickType == 7) {
			return true;
		} // end O
		return false;
	}

	/*
	 * move the brick to LEFT
	 */
	private static void moveLeftT() {
		// T
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos][cPos - 2] = 1;
			cBoardData.matrix[rPos - 1][cPos - 1] = 1;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = 1;
			cBoardData.matrix[rPos][cPos - 1] = 1;
			cBoardData.matrix[rPos + 1][cPos - 1] = 1;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
		}
	}

	private static void moveLeftZ() {
		if (iCurrentBrickDim == 0) {
			// # $
			// # #
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 2] = iCurrentBrickType;
		}
	}

	private static void moveLeftS() {
		// throw new UnsupportedOperationException("Not yet implemented");
		if (iCurrentBrickDim == 0) {
			// $ #
			// # #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 2] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
		}
	}

	private static void moveLeftJ() {

		// J
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			return;
			// return rPos;

		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 2] = iCurrentBrickType;
		}
	}

	private static void moveLeftL() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// L
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 2] = iCurrentBrickType;
			return;
			// return rPos;
		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos - 2] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
		}

	}

	private static void moveLeftI() {
		// I type
		if (iCurrentBrickDim == 0) {
			// # $ # #
			cBoardData.matrix[rPos][cPos + 2] = 0;
			cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos - 2][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;

		}
	}

	private static void moveLeftO() {
		// Square
		cBoardData.matrix[rPos][cPos] = 0;
		cBoardData.matrix[rPos - 1][cPos] = 0;

		cBoardData.matrix[rPos][cPos - 2] = iCurrentBrickType;
		cBoardData.matrix[rPos - 1][cPos - 2] = iCurrentBrickType;
	}

	private static void moveRightT() {
		// T
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
		}
	}

	private static void moveRightZ() {
		if (iCurrentBrickDim == 0) {
			// # $
			// # #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 2] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
		}
	}

	private static void moveRightS() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// throw new UnsupportedOperationException("Not yet implemented");
		if (iCurrentBrickDim == 0) {
			// $ #
			// # #
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 2] = iCurrentBrickType;
		}
	}

	private static void moveRightJ() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// J
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 2] = iCurrentBrickType;
			return;
			// return rPos;

		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
		}
	}

	private static void moveRightL() {
		// new UnsupportedOperationException("Not yet implemented");
		// L
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			return;
			// return rPos;

		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos + 2] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 2] = iCurrentBrickType;
		}

	}

	private static void moveRightI() {
		// I type
		if (iCurrentBrickDim == 0) {
			// # $ # #
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos][cPos + 3] = iCurrentBrickType;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos - 2][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos + 1] = iCurrentBrickType;

		}
	}

	private static void moveRightO() {
		// Square
		cBoardData.matrix[rPos][cPos - 1] = 0;
		cBoardData.matrix[rPos - 1][cPos - 1] = 0;

		cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
		cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
	}

	/*
	 * Movedown
	 */
	private static void moveDownT() {
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = 1;
			cBoardData.matrix[rPos - 1][cPos + 1] = 1;
			cBoardData.matrix[rPos - 2][cPos] = 1;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos + 1] = 1;
			cBoardData.matrix[rPos - 2][cPos] = 1;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = 1;
			cBoardData.matrix[rPos - 1][cPos] = 1;
			cBoardData.matrix[rPos - 1][cPos + 1] = 1;
			return;
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos - 2][cPos] = 1;
			cBoardData.matrix[rPos - 1][cPos - 1] = 1;
		}
	}

	private static void moveDownZ() {
		if (iCurrentBrickDim == 0) {
			// # $
			// # #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos + 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// # $
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;
		}
	}

	private static void moveDownS() {
		// throw new UnsupportedOperationException("Not yet implemented");
		if (iCurrentBrickDim == 0) {
			// $ #
			// # #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $ #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos + 1] = iCurrentBrickType;
		}
	}

	private static void moveDownJ() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// J
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos + 1] = iCurrentBrickType;
			return;
			// return rPos;

		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 270) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;
		}
	}

	private static void moveDownL() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// L
		if (iCurrentBrickDim == 0) {
			// # $ #
			// #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;
			return;
			// return rPos;

		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $
			// #
			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// #
			// # $ #

			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			return;
			// return rPos;
			// }
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos + 1] = iCurrentBrickType;

		}

	}

	private static void moveDownI() {
		// I type
		if (iCurrentBrickDim == 0) {
			// # $ # #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos + 2] = 0;

			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 2] = iCurrentBrickType;

		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;

			cBoardData.matrix[rPos - 3][cPos] = iCurrentBrickType;
		}
	}

	private static void moveDownO() {
		// Square
		cBoardData.matrix[rPos][cPos] = 0;
		cBoardData.matrix[rPos][cPos - 1] = 0;

		cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
		cBoardData.matrix[rPos - 2][cPos - 1] = iCurrentBrickType;
	}

	private static void rotateT() {
		// T
		if (iCurrentBrickDim == 0) {
			// #
			// # $ # => $ #
			// # #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// # #
			// $ # => # $ #
			// #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			iCurrentBrickDim = 180;
			return;
		}
		if (iCurrentBrickDim == 180) {
			// # #
			// # $ # => # $
			// #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 270;
			return;
		}
		if (iCurrentBrickDim == 270) {
			// #
			// # $ => # $ #
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 0;

		}

	}

	private static void rotateZ() {
		// Z
		if (iCurrentBrickDim == 0) {
			// #
			// # $ => # $
			// # # #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;
			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}
		// Z
		if (iCurrentBrickDim == 90) {
			// #
			// # $ <= # $
			// # # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 0;
		}

	}

	private static void rotateS() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// S
		if (iCurrentBrickDim == 0) {
			// #
			// $ # => $ #
			// # # #
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}
		// S
		if (iCurrentBrickDim == 90) {
			// #
			// $ # <= $ #
			// # # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			iCurrentBrickDim = 0;
		}

	}

	private static void rotateJ() {
		// throw new UnsupportedOperationException("Not yet implemented");
		// J
		if (iCurrentBrickDim == 0) {
			// # #
			// # $ # => $
			// # #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}

		if (iCurrentBrickDim == 90) {
			// # # #
			// $ => # $ #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 180;
			return;

		}
		if (iCurrentBrickDim == 180) {
			// # #
			// # $ # => $
			// # #
			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 270;
			return;

		}
		if (iCurrentBrickDim == 270) {
			// #
			// $ => # $ #
			// # # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 0;
		}
	}

	private static void rotateL() {
		// throw new UnsupportedOperationException("Not yet implemented");4
		// L
		if (iCurrentBrickDim == 0) {
			// #
			// # $ # => $
			// # # #
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos - 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}

		if (iCurrentBrickDim == 90) {
			// # #
			// $ => # $ #
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos + 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos + 1][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			iCurrentBrickDim = 180;
			return;

		}
		if (iCurrentBrickDim == 180) {
			// # # #
			// # $ # => $
			// #
			cBoardData.matrix[rPos + 1][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;

			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos + 1][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 270;
			return;

		}
		if (iCurrentBrickDim == 270) {
			// # #
			// $ => # $ #
			// # #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos + 1][cPos - 1] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;

			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos - 1] = iCurrentBrickType;
			iCurrentBrickDim = 0;
		}

	}

	private static void rotateI() {
		// I type
		if (iCurrentBrickDim == 0) {
			// # $ # #
			cBoardData.matrix[rPos][cPos - 1] = 0;
			cBoardData.matrix[rPos][cPos + 1] = 0;
			cBoardData.matrix[rPos][cPos + 2] = 0;
			cBoardData.matrix[rPos + 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 1][cPos] = iCurrentBrickType;
			cBoardData.matrix[rPos - 2][cPos] = iCurrentBrickType;
			iCurrentBrickDim = 90;
			return;
		}
		if (iCurrentBrickDim == 90) {
			// #
			// $
			// #
			// #
			cBoardData.matrix[rPos + 1][cPos] = 0;
			cBoardData.matrix[rPos - 1][cPos] = 0;
			cBoardData.matrix[rPos - 2][cPos] = 0;
			cBoardData.matrix[rPos][cPos - 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 1] = iCurrentBrickType;
			cBoardData.matrix[rPos][cPos + 2] = iCurrentBrickType;
			iCurrentBrickDim = 0;
		}
	}

}
