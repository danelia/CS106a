/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	public void run() {
		int n = initProgram();
		findMinMax(n);
	}

	/*
	 * Here we provide to initialize our program and remember first entered
	 * number.
	 */
	private int initProgram() {
		println("This program finds the largest and smallest numbers.");
		int n = readInt("? ");
		return n;
	}

	/*
	 * In this method we choose minimum and maximum number from entered integers
	 * and return the value.
	 */
	private void findMinMax(int n) {
		if (n == 0) {
			println("I don't have number, only special symbol.");
		} else {
			int max = n;
			int min = n;
			while (n != 0) {
				n = readInt("? ");
				if (n > max && n != 0) {
					max = n;
				}
				if (n < min && n != 0) {
					min = n;
				}
			}
			println("largest=" + max);
			println("smallest=" + min);
		}
	}
}
