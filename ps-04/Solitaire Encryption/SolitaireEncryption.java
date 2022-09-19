import java.util.*;

public class SolitaireEncryption {

    final static int jokerA_val = 27;
    final static int jokerB_val = 28;

    public static char encryptChar(char letter, int key) {
        int value =   letter - 'a';
        int encryptedValue =  (value + key) % 26;
        char encryptedChar = (char) (encryptedValue+'a');

        return encryptedChar;
    }


    public static char decryptChar(char letter, int key) {
        int value =   letter - 'a';
        int decryptedValue =  (value + (26-key)) % 26;
        char decryptedChar = (char) (decryptedValue+'a');

        return decryptedChar;
    }

    public static String sanitize(String word) {

        int roundedLength = roundToPower5(word.length());

        word =  word.replaceAll("[^a-zA-Z]+", "")
                    .trim()            
                    .toLowerCase();

        for (int i = word.length(); i < roundedLength; i++){
            word += "x";
        }

        return word;
    }


    public static int getKey(CircularLinkedList<Integer> deck){ // calls the steps methods
        
        int key = 0;

        do {
            step1(deck);
            step2(deck);
            step3(deck);
            step4(deck);
            key = step5(deck);
        } while (key == jokerA_val || key == jokerB_val);
        
        return key;
    }

    private static int roundToPower5(int number){
        return 5 * (int)Math.ceil(number/5);
    }

    private static void step1(CircularLinkedList<Integer> deck){
        
        int jokerA_index = deck.get_index(jokerA_val);
        deck.shift(jokerA_index, 1);
    }

    private static void step2(CircularLinkedList<Integer> deck){

        int jokerB_index = deck.get_index(jokerB_val);
        deck.shift(jokerB_index, 2);
    }

    private static void step3(CircularLinkedList<Integer> deck){

        CircularLinkedList<Integer> top_deck = new CircularLinkedList<>();
        CircularLinkedList<Integer> middle_deck = new CircularLinkedList<>();
        CircularLinkedList<Integer> bottom_deck = new CircularLinkedList<>();

        boolean found_top = false;
        boolean found_bottom = false;

        Iterator iter = deck.iterator();
        int size = deck.size;
        for (int i = 0; i < size; i++) {
            int value = (int)iter.next();

            if (value == jokerA_val || value == jokerB_val){
                if (!found_top && !found_bottom){

                    found_top = true;
                }
                else if (found_top && !found_bottom) {
                    found_bottom = true;
                }

                middle_deck.add(value);
            }
            else {
                if (!found_top && !found_bottom) {

                    top_deck.add(value);

                } else if (found_top && !found_bottom) {
                    middle_deck.add(value);
                }
                else if (found_top && found_bottom) {
                    bottom_deck.add(value);
                }

            }

            iter.remove();

        }
        Iterator top_iter = top_deck.iterator();
        Iterator middle_iter = middle_deck.iterator();
        Iterator bottom_iter = bottom_deck.iterator();

        for (int i = 0 ; i < bottom_deck.size ; i++){
            deck.add((int)bottom_iter.next());
        }
        for (int i = 0; i < middle_deck.size; i++) {
            deck.add((int) middle_iter.next());
        }
        for (int i = 0; i < top_deck.size; i++) {
            deck.add((int) top_iter.next());
        }

    }

    private static void step4(CircularLinkedList<Integer> deck){

        int last_card = deck.get_value(deck.size - 1);

        for (int i = last_card; i > 0; i--){
            deck.shift(0, deck.size - 2);
        }
    }
    private static int step5(CircularLinkedList<Integer> deck){
        
        int top_card = deck.get_value(0);
        // REMEMBER TO CHECK IF ITS A JOKER CARD OR NOT; IGNORE IF THERE IS A JOKER CARD
        return deck.get_value(top_card);
    }

    public static void main(String[] args) {
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
        ArrayList<Integer> key_stream = new ArrayList<Integer>();

        deck.add(1);
        deck.add(4);
        deck.add(7);
        deck.add(10);
        deck.add(13);
        deck.add(16);
        deck.add(19);
        deck.add(22);
        deck.add(25);
        deck.add(28);
        deck.add(3);
        deck.add(6);
        deck.add(9);
        deck.add(12);
        deck.add(15);
        deck.add(18);
        deck.add(21);
        deck.add(24);
        deck.add(27);
        deck.add(2);
        deck.add(5);
        deck.add(8);
        deck.add(11);
        deck.add(14);
        deck.add(17);
        deck.add(20);
        deck.add(23);
        deck.add(26);

        String test = "hello";
        test = sanitize(test);
        String encrypt_message = "";
        
        for (int i = 0; i < test.length(); i++) {
            int key = getKey(deck);
            key_stream.add(key);
        }
        System.out.println(key_stream.toString());
        System.out.println(test);
        for (int i = 0; i < test.length(); i++) {
            // int ascii_char = (int)test.charAt(i) - 96;
            char e_char = encryptChar(test.charAt(i), key_stream.get(i));
            encrypt_message += e_char;
        }

        System.out.println(encrypt_message);

        // encoded: snisyvzcsl
        // decoded: helloworld

        // encoded: eqfzsrlelac
        // decoded: thisisatest

        // encoded: sxtkyhalzacksjqetcf
        // decoded: howdoipassthisclass

        // encoded: sruehwiiefgahol
        // decoded: hixxxxxxxxxxxxx 

        // encoded: lcqhmjlekifq
        // decoded: attackatdawn

        // encoded: aalmordzyfarcjsgqsswzvcojdilgdlfkadhdsh
        // decoded: professorxrossenxifxyouxseexthisxhellox
    }
}
