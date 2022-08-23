/**
 * Describes a "rabbit hunt", in which a fox moves around a field
 * containing bushes, looking for a rabbit. The rabbit, of course,
 * tries not to be caught by the fox.
 * 
 * @author David Matuszek
 * @version October 12, 2001
 */
public class RabbitHunt {

    // class variables
    private static Object[][] field;
    private static Model model;
    private static View view;
    private static Controller controller;
    private static int numberOfRows;
    private static int numberOfColumns;

    /**
     * Main class for starting a rabbit hunt; no parameters
     * are needed or used.
     */
    public static void main(String args[]) {
        numberOfRows = 20;
        numberOfColumns = 20;
        field = new Object[numberOfRows][numberOfColumns];
        model = new Model(field);
        view = new View(field);
        controller = new Controller(model, view);
    }
}
