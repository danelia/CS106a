/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	// random generator.
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// our lexicon of words.
	private HangmanLexicon ourWords = new HangmanLexicon();
	// this is how player sees word.
	private String howWordLooks;
	// the symbol that player will input.
	private String yourTry;
	// randomly taken word.
	private String wordToGuess;
	// string of incorrect tries.
	private String listOfIncorrectTries = "";
	// number of turns.
	private static final int NTURNS = 8;
	// canvas.
	private HangmanCanvas canvas;

	// adds canvas.
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		setUp();
		playGame();
	}

	private void setUp() {
		// it will draw hanger on canvas.
		canvas.reset();
		println("Welcome To Hangman!");
		// we will take our word randomly from our lexicon.
		wordToGuess = ourWords.getWord(rgen.nextInt(0,
				ourWords.getWordCount() - 1));
		// it will transform our randomly token word into dashes.
		howWordLooks = transform(wordToGuess);
		// draws dashes on canvas.
		canvas.displayWord(howWordLooks);
		println("Word now Looks Like This: " + howWordLooks);
		println("You have " + NTURNS + " guesses left");
	}

	private void playGame() {
		int n = NTURNS;
		while (n != 0) {
			yourTry = readLine("Your Guess: ");
			// checks if the symbol that user inputed is actually a letter and
			// the only one(for example if user inputs "as" or ";" program
			// will say that it's illegal and player can try again).
			checkForLegitness();
			// upper cases input.
			yourTry = yourTry.toUpperCase();
			if (wordToGuess.indexOf(yourTry) != -1) {
				// if the letter player inputed exists in our word it will
				// replace dash with letter.
				replaceDashWishLetter();
				// if there is no dashes left in the string that player sees, it
				// means he wins the game.
				if (howWordLooks.indexOf('-') == -1) {
					println("That guess is correct.");
					println("You guessed the word: " + wordToGuess);
					println("You win");
					break;
				}
				println("That guess is correct");
				println("Word now Looks Like This: " + howWordLooks);
				println("You have " + n + " guesses left");
			} else {
				// if the letter player inputed doesn't exist number of lives
				// will be -1;
				n--;
				// also, in the string of incorrect tries, this letter will be
				// added, and it will appear on canvas.
				listOfIncorrectTries = listOfIncorrectTries + yourTry;
				canvas.noteIncorrectGuess(listOfIncorrectTries);
				if (n != 0) {
					println("There are no " + yourTry + "'s in the word");
					println("Word now Looks Like This: " + howWordLooks);
					println("You have " + n + " guesses left");
					// if number of tries will be 0, it means player lost the
					// game.
				} else if (n == 0) {
					println("There are no " + yourTry + "'s in the word");
					println("You are completely hung.");
					println("The word was: " + wordToGuess);
					println("You lose!");
				}
			}
		}
	}

	// checks if the symbol that user inputed is actually a letter and
	// the only one(for example if user inputs "as" or ";" program
	// will say that it's illegal and player can try again).
	private void checkForLegitness() {
		// checks if player inputed just one symbol.
		while (yourTry.length() != 1) {
			if (yourTry.length() > 1) {
				yourTry = readLine("Just One Letter! YourGuess: ");
			}
		}
		// checks if player inputed letter.
		while ((yourTry.charAt(0) < 'a' || yourTry.charAt(0) > 'z')
				&& (yourTry.charAt(0) < 'A' || yourTry.charAt(0) > 'Z')) {
			if ((yourTry.charAt(0) < 'a' || yourTry.charAt(0) > 'z')
					&& (yourTry.charAt(0) < 'A' || yourTry.charAt(0) > 'Z')) {
				yourTry = readLine("Invalid guess. Try again: ");
			}
		}
	}

	// it will replace dash with letter.
	private void replaceDashWishLetter() {
		int index = wordToGuess.indexOf(yourTry);
		while (index != -1) {
			String first = howWordLooks.substring(0, index);
			String second = howWordLooks.substring(index + 1);
			howWordLooks = first + yourTry + second;
			// it will check if there is other dash to replace.
			index = wordToGuess.indexOf(yourTry, index + 1);
			// it will display the how the unguessed word(with dashes and
			// guessed letters) looks on canvas.
			canvas.displayWord(howWordLooks);
		}
	}

	// transforms our randomly took word from lexicon into the dashes.
	private String transform(String str) {
		int k = str.length();
		String result = "";
		for (int i = 0; i < k; i++) {
			result += "-";
		}
		return result;
	}

}
