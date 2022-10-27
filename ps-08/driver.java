import java.util.*;
import java.io.*;
import java.lang.Math;

public class driver {

    private static int maxWordSetLength = 0;
    private static Set<Integer> userGuess = new HashSet<>();
    private static Map<Integer, List<String>> wordMapByLength = new HashMap<>();
    private static Map<String, List<String>> wordMap = new HashMap<>();

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

        return wordFamilies;
    }

    private static String getBestFamily(Map<String, List<String>> wordFamilies){
        String bestFamily = "";
        return bestFamily;
    }

    private static void game(List<String> wordList) {
        String boardState = "";
        for (int i = 0; i < wordList.get(0).length(); i++) {
            boardState += "_";
        }
        getWordFamilies(wordList);


    }

    public static void main(String[] args) {
        
        init();
        
        int chosenWordLength;
        Scanner inp = new Scanner(System.in);

        System.out.println("-----Welcome to Hangman----");

        System.out.println("Please Choose the Length of the Word:");
        do {
            chosenWordLength = inp.nextInt();
        }
        while (chosenWordLength > maxWordSetLength);

        System.out.println("How many tries would you like?");
        int numOfTries = inp.nextInt();

        game(wordMapByLength.get(chosenWordLength));


    }


}