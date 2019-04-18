/**
 * Created by Matthew on 10/1/2016.
 */

//A Ship that will be placed of the board in battleship to represent a part of a ship.
public class Ship {
    int length;
    public int life; //initialized to the size of the ship. Decremented each time ship is hit

    //initializes Ship with size becoming both length and life
    public Ship(int size){
        length = size;
        life = size;
    }

    //Returns a string with the length of the ship to be used in the displaying of the game board
    public String toString(){
        return Integer.toString(length);
    }

    public static void main(String[] args){

    }
}