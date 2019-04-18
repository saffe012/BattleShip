/**
 * Created by Matthew on 10/1/2016.
 */

//saffe012

//Run BattleShip.main()

import java.util.Scanner;

//Battleship class runs main program with methods to create the board, and run each turn
public class BattleShip {

    int boardCol;
    int boardRow;
    Board board;
    boolean penalty = false;
    int turns = 0;

    /** Asks user whether to run in debug mode or not. Asks for board parameters and calls methods to create board.
     * Then runs main game, calling printBoard() if run in debug mode each turn and then fireShot() and gameDone()
     * each turn until gameDone() returns true
     */
    public static void main(String[] args){
        BattleShip game = new BattleShip();
        boolean terminate = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to run in debug mode(y/n)? ");
        String db = scan.next();
        boolean debug = false;
        if (db.equals("y")||(db.equals("Y"))||(db.equals("yes"))){
            debug = true;
        }
        int row;
        int col;
        System.out.println("How many rows would you like the board to be? ");
        String sRow = scan.next();
        System.out.println("How many columns would you like the board to be? ");
        String sCol = scan.next();
        row = Integer.parseInt(sRow);
        col = Integer.parseInt(sCol);
        while ((row > 10)||(row < 2)||(col > 10)||(col < 2)){
            System.out.println("Sorry, the board parameters must be in the range 2-10.");
            System.out.println("How many rows would you like the board to be? ");
            sRow = scan.next();
            System.out.println("How many columns would you like the board to be? ");
            sCol = scan.next();
            row = Integer.parseInt(sRow);
            col = Integer.parseInt(sCol);
        }
        game.setBoardSize(row,col);
        game.createBoard();
        //loop contitnues until all ships are sunk and .gameDone() returns true
        while (!terminate){
            if (game.penalty){ //If there was a penalty the users turn will be skipped and 1 will be added to turns
                if (debug) {
                    game.board.printBoard();
                }
                game.penalty = false;
                game.turns += 1;
                System.out.println("Your turn was skipped because of your penalty.");
            }else{
                if (debug) {
                    game.board.printBoard();
                }
                game.fireShot();
                terminate = game.gameDone();
            }
        }
        System.out.println("You win! It took you " + game.turns + " turns.");
    }


    //intializes user inputed board size for the battleships class
    public void setBoardSize(int row, int col){
        boardRow = row;
        boardCol = col;
    }
    //creates a board by calling the createBoard method in the Board class
    public void createBoard(){
        board = new Board(boardRow, boardCol);
    }

    /**Asks for user input for the coordinates they would like to fire at. Then checks if coordinate is valid.
     * If it is valid it checks the status of the board piece using the board class and changes the board as necessary,
     * or invokes a penalty.
     */
    public void fireShot(){
        turns = turns + 1;
        Scanner scan = new Scanner(System.in);
        System.out.println("At what row would you like to fire? ");
        int fRow = scan.nextInt()-1;
        System.out.println("At what column would you like to fire? ");
        int fCol = scan.nextInt()-1;
        if ((fRow < 0)||(fRow >= boardRow)||(fCol < 0)||(fCol >= boardCol)){
            penalty = true;
            System.out.println("You fired off the grid! You get a penalty.");
        } else if ((((board.board[fRow][fCol]).toString()).equals("X"))||(((board.board[fRow][fCol]).toString()).equals("H"))){
            penalty = true;
            System.out.println("You already fired at that spot! You get a penalty.");
        } else{
            if (board.getType(fRow, fCol)){
                Ship a = (Ship)(board.board[fRow][fCol]);
                a.life = a.life - 1;//Ship objects have a variable called life initialized to its length that decrementes each time it is hit
                System.out.println("You hit a ship!");
                if (a.life == 0){ //When a ships life equals 0 it is sunk
                    System.out.println("You sunk a ship!");
                    board.board[fRow][fCol] = "H"; //When a spot on a board with a ship is hit the spot becomes a String object
                } else{
                    board.board[fRow][fCol] = "H";
                }
            } else{
                System.out.println("Sorry, you missed.");
                board.board[fRow][fCol] = "X";
            }
        }
    }

    /**checks to see if all ships have been sunk. If all ships have been sunk it returns true causing the main
     *method to end the game
     */
    public boolean gameDone(){
        boolean done = true;
        //When all spaces on the board are String objects the game is done
        for (int i = 0; i < boardRow; i++){
            for (int j = 0; j < boardRow; j++){
                if (!(board.board[i][j].equals("H"))&&!(board.board[i][j].equals("X"))&&!(board.board[i][j].equals("0"))){
                    done = false;
                }
            }
        }
        return done;
    }
}
