/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {
	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		// We get canvas's width.
		double a = getWidth();
		// We get canvas's height.
		double b = getHeight();
		double y = BRICKS_IN_BASE;
		// With this for loot we draw columns.
		for (double rectCoordinateY = b - BRICK_HEIGHT; rectCoordinateY <= (b - BRICK_HEIGHT)
				* y; rectCoordinateY -= BRICK_HEIGHT) {
			// With this for loot we draw first row of rectangles.
			for (double x = 0; x < y * BRICK_WIDTH; x += BRICK_WIDTH) {
				// This is our first rectangle.
				double rectCoordinateX = x + a / 2 - y / 2 * BRICK_WIDTH;
				GRect rect = new GRect(rectCoordinateX, rectCoordinateY,
						BRICK_WIDTH, BRICK_HEIGHT);
				add(rect);
			}
			// We need this for getting -1 bricks for upper rows.
			y--;
		}
	}
}