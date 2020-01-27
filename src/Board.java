import java.util.Arrays;
import java.util.List;

/**
 * The battleship board is made up of a 2d object array. It is made of String objects and Ship objects. This
 * initializes the board, prints it when necessary and keeps track of the types of objects of each space
 *
 * @author  Matt Saffert
 * @version 2.0
 * @since   2020-1-27
 */

public class Board {

	Object[][] board; // Array representing game board that holds both Ships and Strings
	int row; // number of rows
	int col; // number of columns
	Ship[] boats; // Array holding ships on the board

	/**
	 * Creates board parameters and randomly places Ship objects onto the board and makes all other spaces
	 * in the array the String "0".
	 * @param boardRow number of rows of board
	 * @param boardCol number of columns of board
	 */
	public Board(int boardRow, int boardCol) {
		row = boardRow;
		col = boardCol;
		board = new Object[row][col];
		// initializes board with "0"s at all locations
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = "0";
			}
		}

		buildBoats();
		placeBoats();
	}

	/**
	 * Returns the type of Object at a specific coordinates
	 * @param sRow Y coordinate of location
	 * @param sCol X coordinate of location
	 * @return boolean True if object on baord is type Ship
	 */
	public boolean isTypeShip(int sRow, int sCol) {
		if (board[sRow][sCol] instanceof Ship) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Builds the array that holds the boards boats
	 */
	private void buildBoats() {
		//Decides how many boats to place on board
		if ((row == 2)||(col == 2)) {
			boats = new Ship[1];
		} else if (((2 < row) && (row <= 4)) || ((2 < col) && (col <= 4))) {
			boats = new Ship[2];
		} else if (((4 < row) && (row <= 6)) || ((4 < col) && (col <= 6))) {
			boats = new Ship[3];
		} else if (((6 < row) && (row <= 8)) || ((6 < col) && (col <= 8))) {
			boats = new Ship[4];
		} else if (((8 < row) && (row <= 10)) || ((8 < col) && (col <= 10))) {
			boats = new Ship[5];
		}

		int boat_length = 2;
		// builds boats of correct length and places them in boats
		for (int i = 0; i < boats.length; i++) {
			Ship boat = new Ship(boat_length);
			boats[i] = boat;
			if (i != 1) { // if more than 2 boats, there are 2 with length of three
				boat_length += 1;
			}
		}
	}

	/**
	 * Places the board's boats on the game board randomly.
	 */
	private void placeBoats() {
		//places boats on board randomly
		for(int i = 0; i < boats.length; i++) {
			boolean placed = false;
			boolean clear = true; // true if there are already no boats where boat is trying to be placed
			while (!placed) { // randomly chooses places to place boats until they are placed
				int randRow = (int)(Math.floor(Math.random()*row));
				int randCol = (int)(Math.floor(Math.random()*col));
				int verticalOrHorizontal = (int)(Math.floor(Math.random()*2)); // 1 means boat is vertical, 0 horizontal
				if (verticalOrHorizontal == 0) {  // horizontal
					if ((randCol + boats[i].length) <= col) {  // boat cannot fit here
						clear = true;
						for (int j = randCol; j < randCol + boats[i].length; j++) {  // cycles through spots of boats length to see if they already contain boat
							if (board[randRow][j] instanceof Ship) {  // spot already contain a boat
								clear = false;
							}
						}
						if (clear == true) {  // boat can be placed
							for (int j = randCol; j < randCol + boats[i].length; j++) {  // place boat
								board[randRow][j] = boats[i];
							}
							placed = true;
						}
					}
				} else if (verticalOrHorizontal == 1) {  // vertical
					if ((randRow + boats[i].length) <= row) {  // boat cannot fit here
						clear = true;
						for (int j = randRow; j < randRow + boats[i].length; j++) {  // cycles through spots of boats length to see if they already contain boat
							if (board[j][randCol] instanceof Ship) {  // spot already contain a boat
								clear = false;
							}
						}
						if (clear == true) {  // boat can be placed
							for (int j = randRow; j < boats[i].length + randRow; j++) {  // place boat
								board[j][randCol] = boats[i];
							}
							placed = true;
						}
					}
				}
			}
		}
	}

	/**
	 * Prints the board in a semi-attractive manner for debugging mode
	 * Empty spots that have not been fired at are marked by "0". Empty spots that have been fired at are marked by "X".
	 * Spots with an unhit ship are marked by the ships length. A spot on a ship that has been hit is marked by "H".
	 * @param debug whether program is in debug mode or not
	 */
	public void printBoard(boolean debug) {
    String header = "   ";
    for (int i = 0; i < board.length; i++) {  // add column headers
      header += " " + (i+1);
		}
    System.out.print(header);
    System.out.println();
    System.out.println();
		for (int i = 0; i < board.length; i++) {
      if (i != 9)  // add row headers
        System.out.print((i+1) + "   ");
      else // corrects spacing for row 10
        System.out.print((i+1) + "  ");
			for (int j = 0; j < board[i].length; j++) {
				if ((!debug) && (board[i][j] instanceof Ship)) { // spot conatins ship sp cover with "0"
					System.out.print("0 ");
				}
        else {  // debug mode or spot does not contain ship
          System.out.print((board[i][j]).toString() + " "); // prints boards values
        }
			}
			System.out.println();
		}
    System.out.println();
	}

	/**
	 * Returns True if all ships on game board are sunk
	 * @return boolean True if all ships are sunk
	 */
  public boolean allShipsSunk() {
    boolean done = true;

		// When all spaces on the board are String objects the game is done
    for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] instanceof Ship)  // if any spot is a boat, game will continue
					done = false;
			}
		}
		return done;
  }

	public static void main(String[] args){

	}
}
