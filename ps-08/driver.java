import java.util.*;
import java.io.*;
import java.lang.Math;

public class driver {

    private static int maxWordSetLength = 0;
    private static Set<Character> userGuess = new HashSet<>();
    private static Map<Integer, List<String>> wordMapByLength = new HashMap<>();

    private static void init(){
        String fileName = "words.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()){
                String word = scanner.nextLine().toLowerCase();
                int wordLength = word.length();
                
                boolean key = wordMapByLength.containsKey(wordLength);
                if (!key){
                    wordMapByLength.put(wordLength, new ArrayList<String>(Arrays.asList(word)));
                }
                else{
                    wordMapByLength.get(wordLength).add(word);
                }

                maxWordSetLength = Math.max(maxWordSetLength,wordLength);
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static Map<String, List<String>> getWordFamilies(List<String> wordList){
        Map<String, List<String>> wordFamilies = new HashMap<>();

        for (String word : wordList){
            String curPattern = "";

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (userGuess.contains(c)){
                    curPattern += c;
                }
                else{
                    curPattern += "_";
                }
            }

            boolean key = wordFamilies.containsKey(curPattern);
            if (!key){
                wordFamilies.put(curPattern, new ArrayList<String>(Arrays.asList(word)));
            }
            else {
                wordFamilies.get(curPattern).add(word);
            }

        }

        System.out.println(wordFamilies);


        return wordFamilies;
    }

    private static String getBestFamily(Map<String, List<String>> wordFamilies){
        String bestFamily = "";
        return bestFamily;
    }

    private static void game() {

        int chosenWordLength;
        int round = 0;
        char guess;
        String boardState = "";
        List<String> wordList;
        Scanner inp = new Scanner(System.in);

        System.out.println("-----Welcome to Hangman----");

        // Get Word Length
        System.out.println("Please Choose the Length of the Word:");
        do {
            chosenWordLength = inp.nextInt();
        } while (chosenWordLength > maxWordSetLength);
        
        // Get number of tries
        System.out.println("How many tries would you like?");
        int numOfTries = inp.nextInt();
        
        // Initial board state
        for (int i = 0; i < chosenWordLength; i++) {
            boardState += "_";
        }

        // Initial word list
        wordList = wordMapByLength.get(chosenWordLength);
        
        while (numOfTries > 0){
            
            System.out.println("----- " + round + " -----");
            System.out.println(boardState);
            
            do {
                System.out.println("Please Guess Your Character");
                guess = inp.next().charAt(0);
            } while (userGuess.contains(guess));
            
            userGuess.add(guess);
            
            Map<String, List<String>> wordFamilies = getWordFamilies(wordList);
            String newState = getBestFamily(wordFamilies);
            
            if (!newState.equals(boardState)){
                boardState = newState;
            } 
            else{
                numOfTries--;
            }
            
            round++;
        }


    }

    public static void main(String[] args) {
        
        init();
        game();
    }


}