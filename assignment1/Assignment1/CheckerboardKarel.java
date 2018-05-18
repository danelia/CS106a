/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		while (leftIsClear()) {
			doLine(); // this will fill every line except of top
		}
		doLine(); // it is needed for the last, top line
	}

	/*
	 * pre-condition:stands at the bottom left corner post-condition:stands at
	 * the second line, first column, first line is filled with beepers
	 */
	private void doLine() {
		while (frontIsClear()) {
			move();
			putBeeper();
			if (frontIsClear()) {
				move();
			}
		}
		goBack();
		goToNextLine();
	}

	private void goBack() { /*
							 * it will turn around and move until there is a
							 * wall
							 */
		turnAround();
		while (frontIsClear()) {
			move();
		}
	}

	private void goToNextLine() { /*
								 * it will go to upper lane and put beeper or
								 * don't put beeper, depending if on the last
								 * line was beeper placed or not, it will help us
								 * for the odd and even columns and lines
								 */
		turnRight();
		if (beepersPresent()) {
			if (frontIsClear()) {
				move();
			}
			turnRight();
		} else {
			if (frontIsClear()) {
				move();
				turnRight();
				putBeeper();
				if (frontIsClear()) {
					move();
				}
			}
		}
	}
}
