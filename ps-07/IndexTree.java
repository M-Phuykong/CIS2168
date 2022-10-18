package index;

import java.util.Scanner;
import java.io.*;


// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;
	
	// Make your constructor
	// It doesn't need to do anything
	public IndexTree(){}
	
	// complete the methods below
	
	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber){
		this.root = add(this.root, word, lineNumber);
	}
	
	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber){

		if (root == null) {
			return new IndexNode(word, lineNumber);
		}

		int compare = word.compareTo(root.word);

		if (compare == 0){
			root.occurences++;
			root.list.add(lineNumber);
			return root;
		}
		else if (compare < 0){
			root.left = add(root.left, word, lineNumber);
			return root;
		}
		else {
			root.right = add(root.right, word, lineNumber);
			return root;
		}
	}
	
	// returns true if the word is in the index
	public boolean contains(String word){
		return contains(this.root, word);
	}

	private boolean contains(IndexNode root, String word){
		if (root == null){
			return false;
		}

		int compare = word.compareTo(root.word);
		
		if (compare == 0){
			return true;
		}
		else if (compare < 0){
			return contains(root.left, word);
		}
		else {
			return contains(root.right, word);
		}
	}

	
	// call your recursive method
	// use book as guide
	public void delete(String word){
		this.root = delete(this.root, word);
	}
	
	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word){
		
		if (root == null){
			return null;
		}

		int compare = word.compareTo(root.word);

		if (compare == 0){
			if (root.left == null && root.right == null){
				return null;
			} 
			else if (root.left != null && root.right == null){
				return root.left;
			}
			else if (root.left == null && root.right != null){
				return root.right;
			}
			else{
				IndexNode cur = root.left;
				while (cur.right != null){
					cur = cur.right;
				}

				root.word = cur.word;
				root.occurences = cur.occurences;
				root.list = cur.list;

				delete(root.left, root.word);
			}
		}
		else if (compare < 0){
			root.left = delete(root.left, word);
			return root;
		}
		else {
			root.right = delete(root.right, word);
			return root;
		}
		return null;
	}
	
	
	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line
	public void printIndex(){
		printIndex(this.root);
	}

	private IndexNode printIndex(IndexNode node){
		
		if (node == null){
			return null;
		}

		printIndex(node.left);
		System.out.println(node.toString());
		printIndex(node.right);

		return node;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		String UTF8_BOM = "ï»¿";
		IndexTree index = new IndexTree();
		String fileName = "pg100.txt";
		int lineNum = 0;

		PrintStream out = new PrintStream(new File("print.txt"));
		System.setOut(out);

		// add all the words to the tree
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line.startsWith(UTF8_BOM)){
					line = line.substring(3);
				}
				lineNum++;
				String[] words = line.trim().split("\\s+");
				for(String word : words){
					word = word.replaceAll("\\p{Punct}", "");
					if (word.equals("")) continue;
					index.add(word, lineNum);
				}
			}
			scanner.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// print out the index
		index.printIndex();

		// test removing a word from the index
		index.delete("1");
	}
}
