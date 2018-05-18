/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.*;

import acm.util.*;

import java.util.*;

public class HangmanLexicon {
	private ArrayList <String> words = new ArrayList <String>();	

	// This is the HangmanLexicon constructor 
	public HangmanLexicon() { 
		try{
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true){
				String line = rd.readLine();
				if (line==null){
					break;
				}
				words.add(line);
			}
			rd.close();
		}catch(IOException ex){
			throw new ErrorException(ex);
		}
	} 


/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	};
}
