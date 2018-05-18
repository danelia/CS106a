/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		// we initialize 4 arrays, first one is boolean that will tell us if
		// player already chose that category.
		categoryChosenChecker = new boolean[nPlayers + 1][N_CATEGORIES];
		// next one is for upper score.
		upperScore = new int[nPlayers + 1];
		// for lower score.
		lowerScore = new int[nPlayers + 1];
		// and finally, for total score.
		totalScore = new int[nPlayers + 1];
		// here we have for loop, because we want our game to go for
		// N_SCORING_CATEGORIES times.
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			// and here is another for loop, because each player should roll the
			// dice.
			for (int j = 1; j <= nPlayers; j++) {
				// first roll.
				firstRoll(j);
				// and the last for loop, because second and third rolls are
				// similar and can be written in one method and we want this
				// method to happen 2 times.
				for (int k = 1; k <= 2; k++) {
					// second and third rolls.
					otherRolls();
				}
				// after rolls, player will choose category.
				category(j);
			}
		}
		// when the game ends program will tell who is the winner.
		whoIsTheWinner();
	}

	// this is method for first roll.
	private void firstRoll(int playerName) {
		// we randomly get six integers, from one to six, and we do it N_DICE
		// times and every time we write our random integer in the array.
		for (int i = 0; i < N_DICE; i++) {
			int dice = rgen.nextInt(1, 6);
			diceResults[i] = dice;
		}
		// program will tell witch players turn it is.
		display.printMessage(playerNames[playerName - 1]
				+ "'s turn! Click the " + '\"' + "Roll Dice" + '\"'
				+ " button to roll the dice.");
		// program will wait until player clicks "roll dice" button.
		display.waitForPlayerToClickRoll(playerName);
		// program will display dice, that depends on our randomly taken
		// integers.
		display.displayDice(diceResults);
	}

	// method for second and third rolls.
	private void otherRolls() {
		// program tells that player should select dices to re-roll.
		display.printMessage("Select the dice you wish to re-roll and click "
				+ '\"' + "Roll Again" + '\"');
		// program will wait until player clicks "roll again" button.
		display.waitForPlayerToSelectDice();
		// we randomly get six integers, from one to six, and we do it N_DICE
		// times and every time we write our random integer in the array.
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i) == true) {
				int dice = rgen.nextInt(1, 6);
				diceResults[i] = dice;
			}
		}
		// program will display dice, that depends on our randomly taken
		// integers.
		display.displayDice(diceResults);
	}

	private void category(int playerName) {
		int category;
		while (true) {
			// this returns integer of witch category player chose.
			category = display.waitForPlayerToSelectCategory();
			// program will display message to select category.
			display.printMessage("Select a category for this roll.");
			// if in the array that tells us if player already chose that
			// category for this category and player is written false than it
			// will break the while(true) loop in other way it will go on until
			// player chooses the category which he didn't choose until this
			// turn.
			if (!categoryChosenChecker[playerName][category]) {
				break;
			}
		}
		// after breaking the while(true) loop in the array that tells us if
		// player already chose that category for this category and player will
		// be written true, so that the player couldn't chose the same category.
		categoryChosenChecker[playerName][category] = true;
		// program will check if the dices and category he chose match up with
		// together.
		if (checkCaTegoryLegitness(diceResults, category) == true) {
			// if they match up it will calculate score.
			int score = scoreForThisCategory(category, playerName);
			// displays score for the category, total score, lower and upper
			// score and bonus score.
			scoreCounter(playerName, category, score);

		} else {
			// if they don't match up it means that score is 0.
			int score = 0;
			// displays score for the category, total score, lower and upper
			// score and bonus score.
			scoreCounter(playerName, category, score);
		}
	}

	// method for updating total, upper, lower, bonus and category scores.
	private void scoreCounter(int playerName, int category, int score) {
		// firstly it will update the category score.
		display.updateScorecard(category, playerName, score);
		// if the category is lower than upper score it means that player chose
		// one to six, and we calculate than the upper score.
		if (category < UPPER_SCORE) {
			upperScore[playerName] += score;
			display.updateScorecard(UPPER_SCORE, playerName,
					upperScore[playerName]);
			// if the category is more than upper score it means that player
			// chose and we calculate than the lower score.
		} else if (category > UPPER_SCORE) {
			lowerScore[playerName] += score;
			display.updateScorecard(LOWER_SCORE, playerName,
					lowerScore[playerName]);
		}
		// here we calculate total score by summing upper and lower scores.
		totalScore[playerName] = upperScore[playerName]
				+ lowerScore[playerName];
		// if upper score is more than 63 it means player got the bonus.
		if (upperScore[playerName] >= 63) {
			display.updateScorecard(UPPER_BONUS, playerName, 63);
			// we plus that bonus to total score.
			totalScore[playerName] += 63;
		} else {
			// if upper score is lower than 63 it means player didn't get bonus,
			// and it means that bonus=0.
			display.updateScorecard(UPPER_BONUS, playerName, 0);
		}
		// and finally we display total score.
		display.updateScorecard(TOTAL, playerName, totalScore[playerName]);
	}

	// this method calculates score and returns it.
	private int scoreForThisCategory(int category, int playerName) {
		// at start let the score be 0.
		int score = 0;
		// if player chose one to six we score++ every time we get that number
		// in the dice. that will give us how many that number player chose is
		// in the dice.
		if (category >= 1 && category <= 6) {
			for (int i = 0; i < N_DICE; i++) {
				if (diceResults[i] == category) {
					score++;
				}
			}
			// than we multiple it on what number(category) player chose to get
			// the final score for this category.
			score *= category;
			// if player chose THREE_OF_A_KIND than score is the sum of the
			// numbers on dice.
		} else if (category == THREE_OF_A_KIND) {
			for (int i = 0; i < N_DICE; i++) {
				score += diceResults[i];
			}
			// if player chose FOUR_OF_A_KIND than score is the sum of the
			// numbers on dice.
		} else if (category == FOUR_OF_A_KIND) {
			for (int i = 0; i < N_DICE; i++) {
				score += diceResults[i];
			}
			// if player chose FULL_HOUSE than score is 25.
		} else if (category == FULL_HOUSE) {
			score = 25;
			// if player chose SMALL_STRAIGHT than score is 30.
		} else if (category == SMALL_STRAIGHT) {
			score = 30;
			// if player chose LARGE_STRAIGHT than score is 40.
		} else if (category == LARGE_STRAIGHT) {
			score = 40;
			// if player chose YAHTZEE than score is 50.
		} else if (category == YAHTZEE) {
			score = 50;
			// if player chose CHANCE score is the sum of numbers on the dice.
		} else if (category == CHANCE) {
			for (int i = 0; i < N_DICE; i++) {
				score += diceResults[i];
			}
		}
		// finally method will return the score.
		return score;
	}

	// method that checks if the dices and category player chose match up with
	// each other and returns boolean.
	private boolean checkCaTegoryLegitness(int[] dice, int category) {
		// our boolean that is false at the beginning.
		boolean legitnessStatus = false;
		// we don't need to check anything for one to six or for check so
		// boolean
		// will be true. we don't need to check for one to six because if player
		// chose one to six we score++ every time we get that number
		// in the dice. and if there is no that number than the score
		// automatically will be 0.
		if (category >= 1 && category <= 6 || category == CHANCE) {
			legitnessStatus = true;
		} else {
			// but if player chose something other than one to six or chance we
			// make 6 array lists.
			ArrayList<Integer> ones = new ArrayList<Integer>();
			ArrayList<Integer> twos = new ArrayList<Integer>();
			ArrayList<Integer> threes = new ArrayList<Integer>();
			ArrayList<Integer> fours = new ArrayList<Integer>();
			ArrayList<Integer> fives = new ArrayList<Integer>();
			ArrayList<Integer> sixes = new ArrayList<Integer>();
			// after that we get ones, twos and etc. until six from the dice
			// numbers we randomly got and add that numbers in the array it
			// belongs. for example if the integer on first dice is 5 we add 1
			// in array list called fives. finally, arrays size will give us how
			// many that number we have in our dices.
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == 1) {
					ones.add(1);
				} else if (dice[i] == 2) {
					twos.add(1);
				} else if (dice[i] == 3) {
					threes.add(1);
				} else if (dice[i] == 4) {
					fours.add(1);
				} else if (dice[i] == 5) {
					fives.add(1);
				} else if (dice[i] == 6) {
					sixes.add(1);
				}
			}
			// after we know how many ones, twos etc. we have its easy to check.
			// for THREE_OF_A_KIND if there is any of the arrays that size is
			// equal or more than 3 legitnessStatus should get true.
			if (category == THREE_OF_A_KIND) {
				if (ones.size() >= 3 || twos.size() >= 3 || threes.size() >= 3
						|| fours.size() >= 3 || fives.size() >= 3
						|| sixes.size() >= 3) {
					legitnessStatus = true;
				}
				// for FOUR_OF_A_KIND if there is any of the arrays that size is
				// equal or more than 4 legitnessStatus should get true.
			} else if (category == FOUR_OF_A_KIND) {
				if (ones.size() >= 4 || twos.size() >= 4 || threes.size() >= 4
						|| fours.size() >= 4 || fives.size() >= 4
						|| sixes.size() >= 4) {
					legitnessStatus = true;
				}
				// for FULL_HOUSE we want any of our array list size to be equal
				// to three and after that we have that we want one more array
				// to be
				// equal to two. that means that we will have three similar dice
				// and two more similar dice.
			} else if (category == FULL_HOUSE) {
				if (ones.size() == 3 || twos.size() == 3 || threes.size() == 3
						|| fours.size() == 3 || fives.size() == 3
						|| sixes.size() == 3) {
					if (ones.size() == 2 || twos.size() == 2
							|| threes.size() == 2 || fours.size() == 2
							|| fives.size() == 2 || sixes.size() == 2) {
						legitnessStatus = true;
					}
				}
				// there is only 3 way we can get SMALL_STRAIGHT. these are if
				// dices show: 1,2,3,4 or 2,3,4,5, or 3,4,5,6. for first one it
				// means we need our first array size to be one AND our second
				// array size to be one AND our third array size to be one AND
				// out fourth array list size to be one. and so on for other two
				// ways.
			} else if (category == SMALL_STRAIGHT) {
				if (ones.size() == 1 && twos.size() == 1 && threes.size() == 1
						&& fours.size() == 1) {
					legitnessStatus = true;
				} else if (twos.size() == 1 && threes.size() == 1
						&& fours.size() == 1 && fives.size() == 1) {
					legitnessStatus = true;
				} else if (threes.size() == 1 && fours.size() == 1
						&& fives.size() == 1 && sixes.size() == 1) {
					legitnessStatus = true;
				}
				// for LARGE_STRAIGHT there is only 2 ways. these are:1,2,3,4,5
				// or 2,3,4,5,6. for first one it
				// means we need our first array size to be one AND our second
				// array size to be one AND our third array size to be one AND
				// out fourth array list size to be one AND our fifth array list
				// size to be one. similar for second way starting from second
				// array list and going through to last sixth array list.
			} else if (category == LARGE_STRAIGHT) {
				if (ones.size() == 1 && twos.size() == 1 && threes.size() == 1
						&& fours.size() == 1 && fives.size() == 1) {
					legitnessStatus = true;
				} else if (twos.size() == 1 && threes.size() == 1
						&& fours.size() == 1 && fives.size() == 1
						&& sixes.size() == 1) {
					legitnessStatus = true;
				}
				// and for YAHTZEE we want any of our array list size to be
				// five. it will mean that all dices show same thing. because if
				// for example array list ones size is five it means that we
				// added 1 in that array 5 times and it means that we see 5 same
				// number on all dices.
			} else if (category == YAHTZEE) {
				if (ones.size() == 5 || twos.size() == 5 || threes.size() == 5
						|| fours.size() == 5 || fives.size() == 5
						|| sixes.size() == 5) {
					legitnessStatus = true;
				}
			}
		}
		// finally it will return boolean of true or false.
		return legitnessStatus;
	}

	// method that tells us who the winner is.
	private void whoIsTheWinner() {
		// at first we say let the number for winner and the maximal score be 0.
		int winnerName = 0;
		int maxScore = 0;
		// then, for each player, we check if his score is more than maximal
		// score, and if it is we let that score to be our new maximal score and
		// we generate the player number by it and we let that player number to
		// be the winner, in other way maximal scores doen't change.
		for (int i = 1; i <= nPlayers; i++) {
			if (totalScore[i] > maxScore) {
				maxScore = totalScore[i];
				winnerName = i - 1;
			}
		}
		// finally, program displays on screen who is the winner,and what score
		// he/she/it got.
		display.printMessage("Congratulations, " + playerNames[winnerName]
				+ ", you're the winner with a total score of " + maxScore + "!");
	}

	/* Private instance variables */

	private int nPlayers;
	private String[] playerNames;
	private int[] diceResults = new int[N_DICE];
	private int[] upperScore;
	private int[] lowerScore;
	private int[] totalScore;
	private boolean[][] categoryChosenChecker;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
