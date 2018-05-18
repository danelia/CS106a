/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		while (leftIsClear()) {
			knightMoves();
		}
		if (leftIsBlocked()) {
			goToTheDestination();
		}
		putBeeper();
	}

	/*
	 * pre-condition:stands at the bottom left corner post-condition:goes twice
	 * upward and once left
	 */
	private void knightMoves() {
		turnLeft();
		if (frontIsClear()) {
			move();
		}
		if (frontIsClear()) {
			move();
		}
		turnRight();
		move();
	}

	/*
	 * pre-condition:stands at the top, in the middle of line
	 * post-condition:stands at the bottom, in the middle of line
	 */
	private void goToTheDestination() {
		turnRight();
		while (frontIsClear()) {
			move();
		}

	}

}
