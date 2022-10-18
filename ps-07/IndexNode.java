package index;

import java.util.*;

public class IndexNode  {

	// The word for this entry
	String word;
	// The number of occurrences for this word
	int occurences;
	// A list of line numbers for this word.
	List<Integer> list; 
	
	IndexNode left;
	IndexNode right;
	
	// Constructors
	// Constructor should take in a word and a line number
	// it should initialize the list and set occurrences to 1
	public IndexNode(String word, int line_num){
		this.word = word;
		this.occurences = 1;
		this.list = new ArrayList<Integer>(Arrays.asList(line_num));
	}
	// Complete This
	// return the word, the number of occurrences, and the lines it appears on.
	// string must be one line
	
	public String toString(){

		return "Word: " + this.word + "\n"
			+ "Occurences: " + this.occurences + "\n"
			+ "Line Numbers: " + this.list + "\n";
	}
}
