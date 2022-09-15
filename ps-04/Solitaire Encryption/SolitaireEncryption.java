
public class SolitaireEncryption {

    static int jokerA_val = 27;
    static int jokerB_val = 28;

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

    public int getKey(CircularLinkedList<Integer> deck){ // calls the steps methods
        return -1;
    }

    private static void step1(CircularLinkedList<Integer> deck){
        
        int jokerA_index = deck.get_index(jokerA_val);
        // System.out.println(jokerA_index);
        // deck.swap(jokerA_index, jokerA_index + 1);
        // deck.swap(1,5 );
        deck.remove(0);
        deck.add(0, 69);
        
        

    }

    private static void step2(CircularLinkedList<Integer> deck){

    }
    private static void step3(CircularLinkedList<Integer> deck){

    }
    private static void step4(CircularLinkedList<Integer> deck){

    }
    private static int step5(CircularLinkedList<Integer> deck){
        return -1;
    }

    public static void main(String[] args) {
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
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
        System.out.println(deck.toString());

        step1(deck);
        System.out.println(deck.toString());
        // System.out.println(deck.size);



    }
}
