/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	// Height of each rectangle.
	private static final double RECT_HEIGHT = 100;
	// Width of each rectangle/
	private static final double RECT_WIDTH = 200;
	// Distance between lower rectangles.
	private static final double DISTANCE_BETWEEN_LOWER_RECTS = 50;
	// Length of middle line.
	private static final double MIDDLE_LINE_LENGTH = 100;

	// Canvas's width.
	double a = getWidth();
	// Canvas's height.
	double b = getHeight();

	public void run() {
		upperRect();// This is top rectangle.

		/*
		 * These are lines, where -1 means that its first one, 0 means its
		 * second one and 1 means its the third one.
		 */
		line(-1);
		line(0);
		line(1);

		/*
		 * These are rectangles, where -1 means that its first one, 0 means its
		 * second one and 1 means its the third one.
		 */
		lowerRect(-1);
		lowerRect(0);
		lowerRect(1);

		// these is word "program", witch appears in the top rectangle.
		upperLabel();

		/*
		 * These are labels, where -1 means that it appears in the middle of
		 * first rectangle, 0 means that it appears in the second rectangle and
		 * 1 means that it appears in the third rectangle.
		 */
		lowerLabel("GraphicsProgram", -1);
		lowerLabel("ConsoleProgram", 0);
		lowerLabel("DialogProgram", 1);

	}

	/*
	 * Transfers the number of the lower rectangle. We should only write -1, 0
	 * or 1 for z. Where -1 means first one , 0 second one and 1 means the third
	 * one.
	 */
	private GRect lowerRect(int z) {

		GRect rect = new GRect(a / 2 - MIDDLE_LINE_LENGTH + z
				* (DISTANCE_BETWEEN_LOWER_RECTS + RECT_WIDTH), b / 2
				+ MIDDLE_LINE_LENGTH / 2, RECT_WIDTH, RECT_HEIGHT);
		add(rect);
		return rect;

	}

	// Same as lowerRect s.
	private GLine line(int k) {

		GLine line = new GLine(a / 2, b / 2 - MIDDLE_LINE_LENGTH / 2, a / 2 + k
				* (DISTANCE_BETWEEN_LOWER_RECTS + RECT_WIDTH), b / 2
				+ MIDDLE_LINE_LENGTH / 2);
		add(line);
		return line;
	}

	// This draws single upper rectangle.
	private void upperRect() {

		GRect mainRect = new GRect(a / 2 - RECT_WIDTH / 2, b / 2
				- MIDDLE_LINE_LENGTH / 2 - RECT_HEIGHT, RECT_WIDTH, RECT_HEIGHT);
		add(mainRect);

	}

	/*
	 * Transfers the label and and the number of rectangle it should be written
	 * in. Same as in lowerRects we should only write -1, 0 or 1 for z. Where -1
	 * means first one , 0 second one and 1 means the third one.
	 */
	private GLabel lowerLabel(java.lang.String str, int k) {

		GLabel lowerLabel = new GLabel(str);
		double m = lowerLabel.getWidth();
		double n = lowerLabel.getAscent();
		lowerLabel.setLocation(a / 2 - m / 2 + k
				* (DISTANCE_BETWEEN_LOWER_RECTS + RECT_WIDTH), b / 2 + n / 2
				+ MIDDLE_LINE_LENGTH / 2 + RECT_HEIGHT / 2);
		add(lowerLabel);
		return lowerLabel;

	}

	// Writes single upper label.("Program")
	private void upperLabel() {

		GLabel Program = new GLabel("Program");
		double m = Program.getWidth();
		double n = Program.getAscent();
		Program.setLocation(a / 2 - m / 2, b / 2 - MIDDLE_LINE_LENGTH + n / 2);
		add(Program);

	}
}
