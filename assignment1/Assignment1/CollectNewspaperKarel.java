/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		goOutOfHouse();
		pickBeeper();
		getBackInHouse();
	}

	/*
	 * pre-condition:stands in the corner of house post-condition:stands outside
	 * the house
	 */
	private void goOutOfHouse() {
		turnRight();
		move();
		turnLeft();
		for (int i = 0; i < 3; i++) {
			move();
		}
	}

	/*
	 * pre-condition:stands outside the house post-condition:stands in the
	 * corner of house, from where it started moving
	 */
	private void getBackInHouse() {
		turnAround();
		for (int i = 0; i < 3; i++) {
			move();
		}
		turnRight();
		move();
		turnRight();
	}

}
