/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers
	 * giving the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {
		someNumbers = new int[NDECADES];
		String split[] = line.split(" ");

		name = split[0];

		for (int i = 0; i < NDECADES; i++) {
			someNumbers[i] = Integer.parseInt(split[i + 1]);
		}
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The
	 * decade value is an integer indicating how many decades have passed since
	 * the first year in the database, which is given by the constant
	 * START_DECADE. If a name does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return someNumbers[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String one = name + " " + "[";
		String two = "";
		for (int i = 0; i < NDECADES; i++) {
			two += someNumbers[i];
			if (i != NDECADES - 1) {
				two += " ";
			}
		}
		String three = "]";
		String value = one + two + three;
		return value;
	}

	//instance variables.
	private String name;
	private int[] someNumbers; 	
	
}
