/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	//We born new integer a.
	int a;
	//We born new integer b. 
	int b;

	public void run() {
		// Program tells what it does.
		println("Enter values to compute Pythagorean theorem.");
		// We enter first number.
		enteringFirstNumber();
		// We enter second number.
		enteringSecondNumber();
		// We get new number c, with Pythagorean theorem.
		getYourNumber();
		
	}

	private int enteringFirstNumber() {
		//We born new "a" which will live only in this private int.
		int a = readInt("Enter first number: ");
		//We want our a to be positive so we reenter the value.
		while (true) {
			//this.a means the value we burn at the beginning of program.
			if (this.a <= 0) {
				//if a is negative we reenter it.
				reenterFirstNumber();
			} else {
				break;
			}
		}
		//We give the final a to the program.
		return a;
	}
	//Reentering a.
	private void reenterFirstNumber() {
		a = readInt("It's not valid number. Reenter number: ");
	}
	//we do here the same as we did for a, but now we do it for second number b.
	private int enteringSecondNumber() {
		int b = readInt("Enter second number: ");
		while (true) {
			if (this.b <= 0) {
				reenterSecondNumber();
			} else {
				break;
			}
		}
		return b;
	}

	private void reenterSecondNumber() {
		b = readInt("It's not valid number. Reenter number: ");
	}
	//This is last step, getting our number c.
	private double getYourNumber(){
		//It takes square root from (a * a + b * b)
		double c = Math.sqrt(a * a + b * b);
		//Program prints our new number c.
		println("c= " + c);
		//It returns c.
		return c;
	}
}
