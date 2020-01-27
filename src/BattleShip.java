import java.util.Scanner;

/**
 * Battleship class runs main program with methods to create the board, and run each turn
 *
 * @author  Matt Saffert
 * @version 2.0
 * @since   2020-1-27
 */

public class BattleShip {
	int boardCol;  // the width of the boards
	int boardRow;  // the height of the boards
	Board opponent_board;  // keeps track of the opponents board
	Board player_board;  // keeps track of the users board
	boolean penalty = false;  // keeps track of whether the player has a penalty turn
	int turns = 0;  // keeps track of the number of turns that have been played
	String game_status = "playing";  // stores whether the game is still being played or if the game is over, who won

	/**
	 * Asks user whether to run in debug mode or not. Asks for board parameters and calls methods to create board.
	 * Then runs main game, calling printBoard() to display opponent's then userFireShot() or opponentTurn()
	 * each turn until gameDone() switches the game status to denote a winner
	 */
	public static void main(String[] args) {
		BattleShip game = new BattleShip();  // the BattleShip instance that will house the entire game
		boolean debug = game.preGameSetUp(game);  // controls whether game is to be played in debug mode or not
		int turn = (int)(Math.floor(Math.random()*2));

		// loop contitnues until a player's ships all are sunk
		while (game.game_status.equals("playing")) {
			game.turns += 1;  // a new turn is being played
			if (turn == 0) {  // player's turn
				System.out.println("It's your turn.");
				turn = 1;  // oppenent's turn next cycle
				if (game.penalty) { // If there was a penalty the users turn will be skipped
					game.opponent_board.printBoard(debug);
					game.penalty = false;  // penalties last 1 turn
					System.out.println("Your turn was skipped because of your penalty.");
				}
				else {  // no penalty
					game.opponent_board.printBoard(debug);
					game.userFireShot();
				}
			}
			else if (turn == 1) {  // oppenent's turn
				System.out.println("It's your opponent's turn.");
				if (debug)  // only display players_board if in debug mode
					game.player_board.printBoard(debug);
				game.opponentTurn();
				turn = 0;  // players turn next cycle
			}
			game.isGameDone();  // this will switch the game_status if a winner was determined the previous turn
		}
		game.finishGame();
	}

	/**
	 * Sets up and asks for all the necessary information to create the game board
	 * @param game The current BattleShip game
	 * @return boolean This returns whether the game is in debug mode
	 */
	public boolean preGameSetUp(BattleShip game) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Would you like to run in debug mode(y/n)? ");

		String db = scan.next();
		boolean debug = false;

		if (db.equals("y") || (db.equals("Y")) || (db.equals("yes")) || (db.equals("YES")))
			debug = true;

		// gets the dimensions of the game board
		System.out.println("How many rows would you like the board to be? ");
		String sRow = scan.next();
		System.out.println("How many columns would you like the board to be? ");
		String sCol = scan.next();

		int row = Integer.parseInt(sRow);
		int col = Integer.parseInt(sCol);

		while ((row > 10) || (row < 2) || (col > 10) || (col < 2)) {  // continue to ask for game board dimensions until valid dimensions are given
			System.out.println("Sorry, the board parameters must be in the range 2-10.");
			System.out.println("How many rows would you like the board to be? ");
			sRow = scan.next ();
			System.out.println("How many columns would you like the board to be? ");
			sCol = scan.next();
			row = Integer.parseInt(sRow);
			col = Integer.parseInt(sCol);
		}
		game.setBoardSize(row, col);
		game.createBoard(); // creates both player and opponent boards

		return debug;  // is program in debug mode
	}

	/**
	 * Intializes user inputed board size for the battleships class.
	 * @param row The number of rows the board has
	 * @param col The number of columns the board has
	 */
	public void setBoardSize(int row, int col) {
		boardRow = row;
		boardCol = col;
	}

	/**
	 * Creates a board by calling the createBoard method in the Board class.
	 */
	public void createBoard() {
		opponent_board = new Board(boardRow, boardCol);
		player_board = new Board(boardRow, boardCol);
	}

	/**
	 * Asks for user input for the coordinates they would like to fire at. Then checks if coordinate is valid.
	 * If it is valid it checks the status of the board piece using the board class and changes the board as necessary,
	 * or invokes a penalty.
	 */
	public void userFireShot() {
		Scanner scan = new Scanner(System.in);
		System.out.println("At what row would you like to fire? ");
		int fRow = scan.nextInt() - 1;
		System.out.println ("At what column would you like to fire? ");
		int fCol = scan.nextInt() - 1;

		if ((fRow < 0) || (fRow >= boardRow) || (fCol < 0) || (fCol >= boardCol)) {  // user chose a location off the board
			penalty = true;  // next turn will be skipped
			System.out.println("You fired off the grid! You get a penalty.");
		}
		else if ((((opponent_board.board[fRow][fCol]).toString()).equals("X")) ||
		         (((opponent_board.board[fRow][fCol]).toString()).equals("H"))) {  // user already fired at that spot
			penalty = true;  // next turn will be skipped
			System.out.println("You already fired at that spot! You get a penalty.");
		}
		else {  // user fired at valid spot
			if (opponent_board.isTypeShip(fRow, fCol)) {
				Ship ship = (Ship)(opponent_board.board[fRow][fCol]);

				ship.decrementLife();  // Ship objects have a variable called life initialized to its length that decrementes each time it is hit
				System.out.println("You hit a ship!");
				if (ship.getLife() == 0) { // When a ships life equals 0 it is sunk
					System.out.println("You sunk a ship!");
					opponent_board.board[fRow][fCol] = "H"; // When a spot on a board with a ship is hit the spot becomes an "H" String object
				}
				else {
					opponent_board.board[fRow][fCol] = "H"; // When a spot on a board with a ship is hit the spot becomes an "H" String object
				}
			}
			else {
				System.out.println("Sorry, you missed.");
				opponent_board.board[fRow][fCol] = "X";  // "H" marks a location that has been fired at
			}
		}
		try {  // timer to make game a little smoother by allowing user to read messages before next turn is started
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		System.out.println();
	}

	/**
	 * Checks to see if all ships have been sunk. If all ships have been sunk it returns true causing the main
	 * method to end the game.
	 * @return boolean This returns whether the game is done
	 */
	public void isGameDone() {
		boolean opponent_win = player_board.allShipsSunk();
		boolean player_win = opponent_board.allShipsSunk();

		if (opponent_win)
			game_status = "opponent";
		else if (player_win)
			game_status = "player";

	}

	/**
	 * Prints the game ending messages
	 */
	public void finishGame() {
		if (game_status.equals("opponent")) {
			System.out.println("All your ships hve been destroyed.");
			System.out.println("You lose! It took your opponent " + (int)Math.ceil(turns/2) + " turns.");
		}
		else if (game_status.equals("player")) {
			System.out.println("You destroyed all your opponent's ships.");
			System.out.println("You win! It took you " + (int)Math.ceil(turns/2) + " turns.");
		}
	}

	/**
	 * Randomly chooses a random location for opponent to fire at. Then checks if coordinate are valid.
	 * If it is valid it checks the status of the board piece using the board class and changes the board as necessary.
	 * If not valid, chooses another coordinate pair.
	 */
	public void opponentTurn() {
		boolean firedShot = false;
		while (!firedShot) {  // loops until a shot has been fired with valid coordinates
			int randRow = (int)(Math.floor(Math.random()*boardRow));
			int randCol = (int)(Math.floor(Math.random()*boardCol));
			String loc = ((player_board.board[randRow][randCol]).toString());
			if (!(loc.equals("X")) && !(loc.equals("H"))) {
				firedShot = true;
				if (player_board.isTypeShip(randRow, randCol)) {
					Ship ship = (Ship)(player_board.board[randRow][randCol]);
					ship.decrementLife();  // Ship objects have a variable called life initialized to its length that decrementes each time it is hit
					if (ship.getLife() == 0) { // When a ships life equals 0 it is sunk
						System.out.println("Your opponent fired and sunk a ship of length " + player_board.board[randRow][randCol].toString() + ".");
						player_board.board[randRow][randCol] = "H"; // When a spot on a board with a ship is hit the spot becomes an "H" String object
					}
					else {
						System.out.println("Your opponent fired and struck a ship of length " + player_board.board[randRow][randCol].toString() + ".");
						player_board.board[randRow][randCol] = "H"; // When a spot on a board with a ship is hit the spot becomes an "H" String object
					}
				}
				else {
					player_board.board[randRow][randCol] = "X"; // "H" marks a location that has been fired at
					System.out.println("Your opponent fired and missed.");
				}
			}
		}
		try {  // timer to make game a little smoother by allowing user to read messages before next turn is started
			Thread.sleep(3000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		System.out.println();
	}
}
