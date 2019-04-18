/**
 * Created by Matthew on 10/1/2016.
 */

/**The battleship board is made up of a 2d object array. It is made of String objects and Ship objects. This
 * initializes the board, prints it when necessary and keeps track of the types of objects of each space
 */
public class Board {

    Object[][] board;
    int row;
    int col;
    Ship[] boats;

    /**Creates board parameters and randomly places Ship objects onto the board and makes all other spaces
     * in the array the String "0".
     */
    public Board(int boardRow, int boardCol){
        row = boardRow;
        col = boardCol;
        board = new Object[boardRow][boardCol];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = "0";
            }
        }

        //Decides how many boats to place on board
        if ((row == 2)||(col == 2)){
            boats = new Ship[1];
            Ship boat1 = new Ship(2);
            boats[0] = boat1;
        } else if (((2 < row)&&(row <= 4))||((2 < col)&&(col <= 4))){
            boats = new Ship[2];
            Ship boat1 = new Ship(2);
            Ship boat2 = new Ship(3);
            boats[0] = boat1;
            boats[1] = boat2;
        } else if (((4 < row)&&(row <= 6))||((4 < col)&&(col <= 6))) {
            boats = new Ship[3];
            Ship boat1 = new Ship(2);
            Ship boat2 = new Ship(3);
            Ship boat3 = new Ship(3);
            boats[0] = boat1;
            boats[1] = boat2;
            boats[2] = boat3;
        } else if (((6 < row)&&(row <= 8))||((6 < col)&&(col <= 8))) {
            boats = new Ship[4];
            Ship boat1 = new Ship(2);
            Ship boat2 = new Ship(3);
            Ship boat3 = new Ship(3);
            Ship boat4 = new Ship(4);
            boats[0] = boat1;
            boats[1] = boat2;
            boats[2] = boat3;
            boats[3] = boat4;
        } else if (((8 < row)&&(row <= 10))||((8 < col)&&(col <= 10))) {
            boats = new Ship[5];
            Ship boat1 = new Ship(2);
            Ship boat2 = new Ship(3);
            Ship boat3 = new Ship(3);
            Ship boat4 = new Ship(4);
            Ship boat5 = new Ship(5);
            boats[0] = boat1;
            boats[1] = boat2;
            boats[2] = boat3;
            boats[3] = boat4;
            boats[4] = boat5;
        }

        //places boats randomly
        for(int i = 0; i < boats.length; i++){
            boolean placed = false;
            while (!placed) {
                int randRow = (int)(Math.floor(Math.random()*row));
                int randCol = (int)(Math.floor(Math.random()*col));
                int vertHorz = (int)(Math.floor(Math.random()*2));
                if (vertHorz == 0){
                    if ((randCol + boats[i].length) <= col) {
                        boolean clear = true;
                        for (int j = randCol; j < randCol + boats[i].length; j++) {
                            if (board[randRow][j] instanceof Ship){
                                clear = false;
                            }
                        }
                        if (clear == true){
                            for (int j = randCol; j < randCol + boats[i].length; j++){
                                board[randRow][j] = boats[i];
                            }
                            placed = true;
                        }
                    }
                } else if (vertHorz == 1){
                    if ((randRow + boats[i].length) <= row) {
                        boolean clear = true;
                        for (int j = randRow; j < randRow + boats[i].length; j++) {
                            if (board[j][randCol] instanceof Ship){
                                clear = false;
                            }
                        }
                        if (clear == true){
                            for (int j = randRow; j < boats[i].length + randRow; j++){
                                board[j][randCol] = boats[i];
                            }
                            placed = true;
                        }
                    }
                }
            }
        }
    }

    //Returns the type of Object at a specific coordinates
    public boolean getType(int sRow, int sCol){
        if (board[sRow][sCol] instanceof Ship){
            return true;
        } else{
            return false;
        }
    }

    /**Prints the board in a semi-attractive manner for debugging mode
     * Empty spots that have not been fired at are marked by "0". Empty spots that have been fired at are marked by "X".
     * Spots with an unhit ship are marked by the ships length. A spot on a ship that has been hit is marked by "H".
     */
    public void printBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j]).toString() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){

    }


}
