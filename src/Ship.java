/**
 * A Ship that will be placed of the board in battleship to represent a part of a ship.
 *
 * @author  Matt Saffert
 * @version 2.0
 * @since   2020-1-27
 */

public class Ship {
	int length; // length of ship
	int life; //initialized to the size of the ship. Decremented each time ship is hit

	/**
	 * Initializes Ship with size becoming both length and life.
	 * @param size The length of the ship
	 */
	public Ship(int size) {
		length = size;
		life = size;
	}

	/**
	 * Gets the value of life attribute.
	 * @return int This returns the value of the life attribute
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Decrements the value of life attribute by 1.
	 */
	public void decrementLife() {
		life -= 1;
	}

	/**
	 * Returns a string with the length of the ship to be used in the displaying of the game board.
	 * @return String This returns a string of the length of the ship
	 */
	public String toString() {
		return Integer.toString(length);
	}

	public static void main(String[] args) {

	}
}
