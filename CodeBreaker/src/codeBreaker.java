/*
 * This is the codeBreaker.java program. This program creates a random sequence of characters using the letters GRBYOP (Green, Red, Blue, Yellow, Orange and Purple)
 * to a length defined by a constant that can be changed within the program. The program will first ask the user to enter their first guess, if it is valid, 
 * the game will progress on, if its not valid, then the program re-asks the user to enter a valid guess until it becomes valid. The user has 10 guesses to guess 
 * the random code.
 * 
 * Author : Smit Bhavsar
 * Teacher : Ms. Ragunathan
 * Course : ICS4U1-11
 * Program Name : codeBreaker.java
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class codeBreaker {

	// declare a global scanner to use anywhere within the methods to gain input
	// from the user
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		/*
		 * These are the 3 key variables. Seq is the variable with all the possible
		 * letters the program can choose from. SIZE is the constant that determines how
		 * long both the random array and the guess array can be up to. Finally, ogCode
		 * is the random sequence of length SIZE
		 */
		String seq = "GRBYOP";
		final int SIZE = 4;
		final int TRIES = 10;
		int trys = 0;
		char ogCode[] = createCode(seq, SIZE);
		String[][] clues = new String[TRIES][SIZE];
		char[] guessChar;
		int i = -1;
		System.out.println(ogCode);
		int number = 1;
		ArrayList<String> clueFinder = new ArrayList<String>();
		System.out.println("Please enter your guess of size " + SIZE + " using the letters " + seq);

		do {
			String guess = input.nextLine();
			guessChar = guess.toCharArray();

			while (!valid(guessChar, seq, SIZE)) {
				System.out.println("Please enter your guess again of size " + SIZE + " using the letters " + seq);
				guess = input.nextLine();
				guessChar = guess.toCharArray();
			}
			
			if (number == 1) {
				System.out.println("Guess\tClues\n****************");
				System.out.print(guess);
			}
			--number;
			trys++;

			ArrayList<String> black = findFullyCorrect(ogCode, guessChar);
			ArrayList<String> white = findColourCorrect(ogCode, guessChar);
			clueFinder.addAll(white);
			clueFinder.addAll(black);
			++i;

			String[] a = new String[clueFinder.size()];
			
			System.out.print("\t");
			for (int k = 0; k < clueFinder.size(); k++) {
				if (clueFinder.size() > 0) {
					a[k] = clueFinder.get(k);
				}
			}
			
			
			clues[i] = a;
			System.out.println(clueFinder);
			clueFinder.clear();
			//displayGame(guesses, clues);
		} while (trys <= 10);

		System.out.println("\nSorry, you couldn't guess the secret code in " + (trys - 1) + " tries");
		//System.out.println("The secret code was " + ogCode);
	}

	/**
	 * This method prints the current state of the game.
	 * 
	 * 
	 * @param guesses	the guesses the user enters 
	 * @param clues		all the current clues given to the user for their guesses
	 */
	public static void displayGame(String[][] guesses, String[][] clues) {
		System.out.print("        ");
		for (int i = 0; i < clues.length; i++) {
			for (int j = 0; j < clues[i].length; j++) {
				System.out.print((clues[i][j]) + " ");
			}
		}
		System.out.println(" ");
	}

	/**
	 * This method checks whether or not the user got a letter right within the
	 * array, just not in the correct position. The amount of w's represents the
	 * amount of letters that are correct in the array, but are not in the correct
	 * position. So 4 w's means the user got all the letters correct, only they are
	 * all jumbled up and the user needs to redo the sequence of those particular
	 * letters.
	 * 
	 * 
	 * @param ogCode    the random sequence made by the program.
	 * @param guessChar the character array the user inputed.
	 * @return returns an ArrayList with w's in it, signifying the amount of letters
	 *         that are correctly placed within the array, however just aren't in
	 *         the right position as of yet.
	 */
	public static ArrayList<String> findColourCorrect(char[] ogCode, char[] guessChar) {
		ArrayList<String> white = new ArrayList<String>();

		// checks if the guess array has a letter in the exact same position as the
		// random sequence. If so, it is replaced by a 'Z'.
		for (int i = 0; i < ogCode.length; i++) {
			if (ogCode[i] == guessChar[i]) {
				ogCode[i] = 'Z';
				guessChar[i] = 'Z';
			}
		}

		// checks if the guess array has common elements anywhere within the random
		// sequence, disregarding the 'Z'. If so, then a 'w' is added to the
		// white ArrayList, telling the code that there is a correct letter somewhere
		// within the array, just not where.
		for (int j = 0; j < ogCode.length; j++) {
			for (int k = 0; k < ogCode.length; k++) {
				if (ogCode[j] == guessChar[k] && ogCode[j] != 'Z' && guessChar[k] != 'Z') {
					guessChar[k] = 'Z';
					white.add("w");
				}
			}
		}

		return white;
	}

	/**
	 * This method removes characters that are not in the correct spot. For example,
	 * if the lists are ['A', 'B', 'C', 'D'] and ['C', 'B', 'A', 'D'] then return
	 * ['A', 'C'] because those two are not in their right positions.
	 * 
	 * 
	 * @param ogCode    the random sequence made by the program.
	 * @param guessChar the character array the user inputed.
	 * @return returns a character array with all the removed characters within it.
	 */
	public static ArrayList<Character> removeFullyCorrect(char[] ogCode, char[] guessChar) {
		ArrayList<Character> remover = new ArrayList<Character>();

		// checks if there are elements in the guess array that are not in the same
		// position as the random sequence. If not, then an ArrayList is returned
		// with all the letters that are missing in the guess array.
		for (int i = 0; i < ogCode.length; i++) {
			if (ogCode[i] != guessChar[i]) {
				remover.add(ogCode[i]);
			}
		}

		return remover;
	}

	/**
	 * This method makes checks to see whether or not the user got a letter in the
	 * correct position or not. The amount of letters in the correct position are
	 * determined by the amount of b's. 0 b's means the user got no letters in the
	 * correct position, and 4 b's means the guess is = to the random sequence.
	 * 
	 * 
	 * @param ogCode    the random sequence the program made.
	 * @param guessChar the sequence that the user entered.
	 * @return returns an ArrayList that contains a certain amount of b's. One 'b'
	 *         means that there is a letter that is the correct color as well as is
	 *         in correct position as well. So 4 b's essentially means the user has
	 *         guessed the entire sequence.
	 */
	public static ArrayList<String> findFullyCorrect(char[] ogCode, char[] guessChar) {
		ArrayList<String> black = new ArrayList<String>();
		ArrayList<Character> remover = new ArrayList<Character>();

		// this for loop checks if the two arrays have common letters in the exact same
		// position. If so, it removes them.
		for (int i = 0; i < ogCode.length; i++) {
			if (ogCode[i] == guessChar[i]) {
				remover.add(ogCode[i]);
			}
		}

		// however many are removed, return a new ArrayList with the same length of
		// removed elements, only consisting of b's instead.
		for (int i = 0; i < remover.size(); i++) {
			black.add("b");
		}
		return black;

	}

	/**
	 * This method validates a char array that is passed through it. This method is
	 * primarily used for validating the users' guesses however it may have a use
	 * here or there. It returns true if the conditions are met, false if they're
	 * not.
	 * 
	 * 
	 * @param charArray The array that is passed through from the main method,
	 *                  primarily the user's guess array.
	 * @param seq       the 6 letter String consisting of 'GRBYOP' to randomize a
	 *                  sequence of those letters.
	 * @param SIZE      a constant that determines how long to make the random
	 *                  sequence.
	 * @return returns a boolean value of either true or false. Returns true if the
	 *         conditions such as length is equivalent to the constant size as well
	 *         as the letters within the guess are the letters within the 'seq'
	 *         String. False if either or both are not applicable.
	 */
	public static boolean valid(char[] charArray, String seq, int SIZE) {
		int count = 0;
		if (charArray.length == SIZE) {
			for (int i = 0; i < charArray.length; i++) {
				if (seq.indexOf(charArray[i]) >= 0) {
					count++;
				}
			}
		} else {
			return false;
		}

		if (count == SIZE) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method creates a random sequence of letters prohibited to only using the
	 * 6 letters predefined in the main method of 'GRBYOP' as well as a length
	 * prohibited to the length of the constant within the main method. It then
	 * returns a char array with the random sequence within it.
	 * 
	 * @param seq  the 6 letter String consisting of 'GRBYOP' to randomize a
	 *             sequence of those letters.
	 * @param SIZE a constant that determines how long to make the random sequence.
	 * @return returns a character array of a random sequence determined by length
	 *         of the constant and prohibited to the 6 letters listed in the 'seq'
	 *         variable.
	 */
	public static char[] createCode(String seq, int SIZE) {
		Random rand = new Random();
		int randomIndex;
		char[] ogCode = new char[4];
		for (int i = 0; i < SIZE; i++) {
			randomIndex = rand.nextInt(SIZE);
			ogCode[i] = seq.charAt(randomIndex);
		}
		return ogCode;
	}

}