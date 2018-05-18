/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		// We get new integer n.
		int n = readInt("Enter a number(which is more than 0): ");
		/*
		 * We wan't our integer to be more than 0, so if it is <=0 program says
		 * that it is invalid number.
		 */
		if (n <= 0) {
			println("Told you more than 0!");
			// Other case, if n >0 program does its operation.
		} else {
			/*
			 * We get new integer that is equal to 0, and it get's plus one at
			 * the end of every operation, to know how many processes it took.
			 */
			int k = 0;
			/*
			 * We want our processes to go on until it gets one, so we make
			 * while loot.
			 */
			while (n != 1) {
				// if our integer is even we want to take half.
				if (n % 2 == 0) {
					println(n + " is even, so I take half: " + n / 2);
					n /= 2;
					k++;
					// If it is odd we want to get 3n+1.
				} else {
					int p = 3 * n + 1;
					println(n + " is odd, so I make 3n+1: " + p);
					n = 3 * n + 1;
					k++;
				}
			}
			/*
			 * At the end program tells how many processes were needed to reach
			 * 1.
			 */
			println("The process took " + k + " to reach 1.");
		}
	}
}
