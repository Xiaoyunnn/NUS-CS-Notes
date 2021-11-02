import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;
	private int order;
	private HashMap<String, Integer[]> model;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.model = new HashMap<>();

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here

		for (int i = 0; i < text.length() - this.order; i++) {
			String str = text.substring(i, i + this.order);
			Integer[] currArr = this.model.get(str);

			if (currArr == null) {
				currArr = new Integer[256];
				for (int j = 0; j < 256; j++) {
					currArr[j] = (int) NOCHARACTER;
				}
				currArr[text.charAt(i + this.order)]++;
				this.model.put(str, currArr);
				// System.out.println(i + " added " + str);
			} else {
				// str is alr in the hash map
				// System.out.println(i + " here " + str);
				currArr[text.charAt(i + this.order)]++;
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		int count = 0;
		Integer[] currArr = this.model.get(kgram);
		if (kgram.length() != this.order || currArr == null) {
			return NOCHARACTER;
		} else {
			for (int i = 0; i < 256; i ++) {
				count += currArr[i];
			}
			return count;
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		Integer[] currArr = this.model.get(kgram);
		if (kgram.length() != this.order || currArr == null) {
			return NOCHARACTER;
		} else {
			return currArr[c];
		}
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		Integer[] currArr = this.model.get(kgram);
		if (kgram.length() == this.order && currArr != null) {
			int randInt = this.generator.nextInt(getFrequency(kgram));
			for (int i = 0; i < 256; i++) {
				randInt -= currArr[i];
				if (randInt < 0) {
					return (char) i;
				}
			}
		}
		return NOCHARACTER;
	}
}
