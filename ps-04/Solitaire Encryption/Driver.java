import java.util.*;

public class Driver {

    public static void main(String[] args) {
        
        String retry = "y";
        Scanner scan = new Scanner(System.in);
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();

        System.out.println("===================================================");
        System.out.println("== Welcome to the Solitaire Encryption Decrypter ==");
        System.out.println("===================================================");
        System.out.println();

        // Deck Input
        System.out.println("Please input your deck order (1-28)");
        for (int i = 0; i < 28; i++){
            deck.add(scan.nextInt());
        }
        scan.nextLine();
        System.out.println("Done!");


        do {
            // Decrypted Message Input
            System.out.println("Please input your encrypted message");
            String encrypted_message = scan.nextLine().toLowerCase();

            // Decrypting...
            String decrypted_message = "";
            for (int i = 0; i < encrypted_message.length(); i++) {
                int key = SolitaireEncryption.getKey(deck);
                decrypted_message += SolitaireEncryption.decryptChar(encrypted_message.charAt(i), key);
            }

            // Printing Out The Result
            System.out.println();
            System.out.println("Encrypted Message: " + encrypted_message);
            System.out.println("Decrypted Message: " + decrypted_message);
            System.out.println();

            // Try again
            System.out.println("Would you like to decrypt another message? [y/n]");
            retry = scan.nextLine();

        } while (retry.equals("y"));

        scan.close();
    }

}
