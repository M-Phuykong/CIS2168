import java.util.*;
import java.io.*;
import java.lang.Math;

public class driver {

    private static int maxWordSetLength = 0;
    private static Set<String> wordSet = new HashSet<String>();
    private static Map<String, HashSet<String>> wordMap = new HashMap<String, HashSet<String>>();

    private static void init(){
        String fileName = "words.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()){
                String word = scanner.nextLine();
                wordSet.add(word);
                maxWordSetLength = Math.max(maxWordSetLength, word.length());
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        init();
        
        int chosenWordLenght;
        Scanner inp = new Scanner(System.in);

        System.out.println("-----Welcome to Hangman----");

        System.out.println("Please Choose the Length of the Word:");
        do {
            chosenWordLenght = inp.nextInt();
        }
        while (chosenWordLenght > maxWordSetLength);

        System.out.println("How many tries would you like?");
        int numOfTries = inp.nextInt();

        
        



    }


}