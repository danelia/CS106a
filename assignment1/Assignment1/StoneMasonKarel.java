import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		fillColumn();
		fillOtherColumns();
	}

	/*
	 * pre-condition:stands at the bottom left corner of the world
	 * post-condition:stands where it was with this line filled with beepers
	 */
	private void fillColumn() {
		turnLeft();
		if (noBeepersPresent()) {
			putBeeper();
		}
		while (frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
		if (frontIsBlocked()) {
			turnAround();
			while (frontIsClear()) {
				move();
			}
		}
		turnLeft();

	}

	/*
	 * pre-condition:stands at the bottom left corner post-condition:stands at
	 * the bottom right corner, every 4th column filled with beepers
	 */
	private void fillOtherColumns() {
		while (frontIsClear()) {
			for (int i = 0; i < 4; i++) {
				move();
			}
			fillColumn();
		}

	}

}
