/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		drawHanger();
	}

	// draws hanger on canvas.
	private void drawHanger() {
		double scaffoldStartX = getWidth() / 2 - BEAM_LENGTH;
		double scaffoldStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2;
		double scaffoldEndX = getWidth() / 2 - BEAM_LENGTH;
		double scaffoldEndY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2
				+ SCAFFOLD_HEIGHT;
		GLine scaffold = new GLine(scaffoldStartX, scaffoldStartY,
				scaffoldEndX, scaffoldEndY);
		add(scaffold);
		double beamEndX = getWidth() / 2;
		GLine beam = new GLine(scaffoldStartX, scaffoldStartY, beamEndX,
				scaffoldStartY);
		add(beam);
		double ropeEndY = scaffoldStartY + ROPE_LENGTH;
		GLine rope = new GLine(beamEndX, scaffoldStartY, beamEndX, ropeEndY);
		add(rope);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		double wordX = getWidth() / 2 - BEAM_LENGTH;
		double wordY = getHeight() - HEAD_RADIUS;
		GLabel howWordLooks = new GLabel(word, wordX, wordY);

		if (getElementAt(wordX, wordY) != null) {
			remove(getElementAt(wordX, wordY));
		}
		add(howWordLooks);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(String listOfIncorrectTries) {
		double letterX = getWidth() / 2 - BEAM_LENGTH;
		double letterY = getHeight() - HEAD_RADIUS + 20;
		GLabel letters = new GLabel(listOfIncorrectTries, letterX, letterY);

		if (getElementAt(letterX, letterY) != null) {
			remove(getElementAt(letterX, letterY));
		}
		add(letters);

		// every time player is mistaken, listOfIncorrectTries's length will
		// increase, and it will add parts of our man on hanger.
		if (listOfIncorrectTries.length() == 1) {
			headComesIn();
		}
		if (listOfIncorrectTries.length() == 2) {
			bodyComesIn();
		}
		if (listOfIncorrectTries.length() == 3) {
			leftArmComesIn();
		}
		if (listOfIncorrectTries.length() == 4) {
			rightArmComesIn();
		}
		if (listOfIncorrectTries.length() == 5) {
			leftLegComesIn();
		}
		if (listOfIncorrectTries.length() == 6) {
			rightLegComesIn();
		}
		if (listOfIncorrectTries.length() == 7) {
			leftFootComesIn();
		}
		if (listOfIncorrectTries.length() == 8) {
			rightFootComesIn();
		}

	}

	private void headComesIn() {
		double headX = getWidth() / 2 - HEAD_RADIUS;
		double headY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH;
		GOval head = new GOval(headX, headY, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	private void bodyComesIn() {
		double bodyStartX = getWidth() / 2;
		double bodyStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS;
		double bodyEndX = getWidth() / 2;
		double bodyEndY = bodyStartY + BODY_LENGTH;
		GLine body = new GLine(bodyStartX, bodyStartY, bodyEndX, bodyEndY);
		add(body);
	}

	private void leftArmComesIn() {
		double armStartX = getWidth() / 2;
		double armStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double armEndX = getWidth() / 2 - UPPER_ARM_LENGTH;
		GLine upperArm = new GLine(armStartX, armStartY, armEndX, armStartY);
		double lowerArmEndY = armStartY + LOWER_ARM_LENGTH;
		GLine lowerArm = new GLine(armEndX, armStartY, armEndX, lowerArmEndY);

		add(upperArm);
		add(lowerArm);
	}

	private void rightArmComesIn() {
		double armStartX = getWidth() / 2;
		double armStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double armEndX = getWidth() / 2 + UPPER_ARM_LENGTH;
		GLine upperArm = new GLine(armStartX, armStartY, armEndX, armStartY);
		double lowerArmEndY = armStartY + LOWER_ARM_LENGTH;
		GLine lowerArm = new GLine(armEndX, armStartY, armEndX, lowerArmEndY);
		add(upperArm);
		add(lowerArm);
	}

	private void leftLegComesIn() {
		double hipStartX = getWidth() / 2;
		double hipStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + BODY_LENGTH;
		double hipEndX = hipStartX - HIP_WIDTH;
		GLine hip = new GLine(hipStartX, hipStartY, hipEndX, hipStartY);
		double legEndY = hipStartY + LEG_LENGTH;
		GLine leg = new GLine(hipEndX, hipStartY, hipEndX, legEndY);
		add(hip);
		add(leg);
	}

	private void rightLegComesIn() {
		double hipStartX = getWidth() / 2;
		double hipStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + BODY_LENGTH;
		double hipEndX = hipStartX + HIP_WIDTH;
		GLine hip = new GLine(hipStartX, hipStartY, hipEndX, hipStartY);
		double legEndY = hipStartY + LEG_LENGTH;
		GLine leg = new GLine(hipEndX, hipStartY, hipEndX, legEndY);
		add(hip);
		add(leg);
	}

	private void leftFootComesIn() {
		double footStartX = getWidth() / 2 - HIP_WIDTH;
		double footStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		double footEndX = footStartX - FOOT_LENGTH;
		GLine foot = new GLine(footStartX, footStartY, footEndX, footStartY);
		add(foot);
	}

	private void rightFootComesIn() {
		double footStartX = getWidth() / 2 + HIP_WIDTH;
		double footStartY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH
				+ 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		double footEndX = footStartX + FOOT_LENGTH;
		GLine foot = new GLine(footStartX, footStartY, footEndX, footStartY);
		add(foot);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
