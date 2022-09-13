public class SolitaireEncryption {

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
    }
}
