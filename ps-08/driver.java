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

        return wordFamilies;
    }

    private static String getBestFamily(Map<String, List<String>> wordFamilies){
        String bestFamily = "";
        int curMax = 0;

        for (var entry: wordFamilies.entrySet()){
            
            if (entry.getValue().size() > curMax){
                bestFamily = entry.getKey();
                curMax = entry.getValue().size();
            }
        }

        return bestFamily;
    }

    private static void game() {

        String choice;
        Scanner inp = new Scanner(System.in);
        
        do {
            int chosenWordLength;
            int round = 0;
            char guess;
            String boardState = "";
            List<String> wordList;
            userGuess.clear();
            
            System.out.println("-----Welcome to Hangman----");
            
            // Get Word Length
            System.out.println("Please Choose the Length of the Word:");
            do {
                chosenWordLength = inp.nextInt();
            } while (chosenWordLength > maxWordSetLength || chosenWordLength < 2);
            
            // Get number of tries
            System.out.println("How many tries would you like?");
            int numOfTries = inp.nextInt();
            
            // Initial board state
            for (int i = 0; i < chosenWordLength; i++) {
                boardState += "_";
            }
            
            // Initial word list
            wordList = wordMapByLength.get(chosenWordLength);
            long boardStateCount = chosenWordLength;
            
            while (numOfTries > 0 && boardStateCount != 0){
                
                System.out.println("----- " + round + " -----");
                System.out.println("Word: " + boardState + "     " + "[" + wordList.size() + "]");
                System.out.println("Number of Try Leftover: " + numOfTries);
                System.out.println("Guessed Letters: " + userGuess.toString());
                
                do {
                    System.out.println("Please Guess Your Character");
                    guess = inp.next().charAt(0);
                    inp.nextLine();
                } while (userGuess.contains(guess));
                
                // add user guess to the universal set
                userGuess.add(guess);
                
                // generate the word families
                Map<String, List<String>> wordFamilies = getWordFamilies(wordList);
                
                // get the best family state
                String newState = getBestFamily(wordFamilies);
                
                // update the word list
                wordList = wordFamilies.get(newState);
    
                // count the number of "_" of the new board state
                boardStateCount = newState.chars().filter(ch -> ch == '_').count();
                
                if (!newState.equals(boardState)){
                    boardState = newState;
                } 
                else{
                    numOfTries--;
                }
                
                round++;
                System.out.println();
            }
    
            if (boardStateCount == 0){
                System.out.println("----- " + round + " -----");
                System.out.println("Word: " + boardState);
                System.out.println("Number of Try Leftover: " + numOfTries);
                System.out.println("Guessed Letters: " + userGuess.toString());
    
                System.out.println("You Win!");
            } else
            {
                System.out.println("Sorry, you ran out of guesses.");
                System.out.println("The Word: " + wordList.get(0));
            }
            
            System.out.println();
            System.out.println("Would you like to play again? [Y/N]");
            choice = inp.nextLine().toUpperCase();

        } while (choice.equals("Y"));
        
        inp.close();
    }

    public static void main(String[] args) {
        init();
        game();
    }


}