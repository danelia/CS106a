/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {

	// Radius of big circle in pixels.
	private static final double RADIOUS_OF_BIG_CIRCLE = 72;

	// Converter of centimeters in pixels.
	private static final double PIXELS_PER_CENTIMETERS = RADIOUS_OF_BIG_CIRCLE / 2.54;

	// Radius of medium circle converted in pixels.
	private static final double RADIOUS_OF_MEDIUM_CIRCLE = 1.65 * PIXELS_PER_CENTIMETERS;

	// Radius of small circle converted in pixels.
	private static final double RADIOUS_OF_LITTLE_CIRCLE = 0.76 * PIXELS_PER_CENTIMETERS;

	public void run() {

		// Draws biggest circle.
		filledCircle(RADIOUS_OF_BIG_CIRCLE, Color.RED);

		// Draws medium circle.
		filledCircle(RADIOUS_OF_MEDIUM_CIRCLE, Color.WHITE);

		// Draws little circle.
		filledCircle(RADIOUS_OF_LITTLE_CIRCLE, Color.RED);
	}

	/*
	 * Transfers the radius and color of the circle. And draws the colored
	 * circle with radius r.
	 */
	private GOval filledCircle(double r, Color color) {
		double a = getWidth() / 2;
		double b = getHeight() / 2;
		GOval circle = new GOval(a - r, b - r, 2 * r, 2 * r);
		circle.setFilled(true);
		circle.setColor(color);
		add(circle);
		return circle;
	}

}
