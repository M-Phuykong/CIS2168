import java.util.*;

/**
 * Creates and maintains a "field" for the rabbit and fox, and sets
 * up a number of methods to be used by animals. This class, rather
 * than the Animal class, is in charge of letting animals move and
 * look around--this makes it harder for an animal to "cheat" by
 * knowing things it shouldn't know, or making moves it shouldn't
 * be allowed to make.
 * 
 * @author David Matuszek 
 * @version October 26, 2001
 */
public class Model {
    
    // define instance variables
    private Object[][] field;
    private int numberOfRows;
    private int numberOfColumns;
    private Rabbit rabbit;
    private Fox fox;
    private int foxRow;
    private int foxColumn;
    private int rabbitRow;
    private int rabbitColumn;
    private Bush bush; // all bushes are the same bush
    private long oldRandomSeed;
    private static Random randomNumberGenerator = new Random();
    private boolean isUnderConstruction;
    
    /** Flag used to end the game when the fox wins */
    boolean rabbitIsAlive;
    /** Flag to tell when the game ends */
    boolean gameIsOver;
    /** The number of turns taken so far in the game */
    int stepsTaken;
    /** The number of turns the rabbit has to survive in order to win */
    final int MAX_NUMBER_OF_STEPS = 100;
    /** The number of rows in the field */
    static int NUMBER_OF_ROWS;
    /** The number of columns in the field */
    static int NUMBER_OF_COLUMNS;
    /** Remember each time animal does look(direction) for later viewing */
    static Vector looks = new Vector();
    /** True if rabbit's turn, false if fox's turn */
    static boolean isRabbitsTurn;

    // define some class constants to represent directions
    /** Represents the direction NORTH */
        static final int N = 0;
    /** Represents the direction NORTHEAST */
        static final int NE = 1;
    /** Represents the direction EAST */
        static final int E = 2;
    /** Represents the direction SOUTHEAST */
        static final int SE = 3;
    /** Represents the direction SOUTH */
        static final int  S = 4;
    /** Represents the direction SOUTHWEST */
        static final int SW = 5;
    /** Represents the direction WEST */
        static final int W = 6;
    /** Represents the direction NORTHWEST */
        static final int NW = 7;
    /** Represents the direction "right here" */
        static final int STAY = 8;
    /** The smallest int representing a direction */
        static final int MIN_DIRECTION = 0;
    /** The largest int representing a direction */
        static final int MAX_DIRECTION = 7;

    // define some constants to represent objects
    /** Indicates the edge of the playing field */
        final static int EDGE = 0;
    /** Indicates a bush */
        final static int FOX = 1;
    /** Indicates a rabbit */
        final static int RABBIT = 2;
    /** Indicates a fox */
        final static int BUSH = 3;
    
    /**
     * Constructs a model that uses the given field.
     *
     * @param field  the field to be used by the model
     */
    Model(Object[][] field) {
        this.field = field;
        NUMBER_OF_ROWS = numberOfRows = field.length;
        NUMBER_OF_COLUMNS = numberOfColumns = field[0].length;
        reset();
    }
    
    /**
     * Sets up a new hunt.
     */
    void reset() {
        gameIsOver = false;
        rabbitIsAlive = true;
        isRabbitsTurn = false; // will be changed before first move
        stepsTaken = 0;
        // populate using new random numbers
        oldRandomSeed = randomNumberGenerator.nextLong();
        randomNumberGenerator.setSeed(oldRandomSeed);
        populate();
    }
    
    /**
     * Sets up the same hunt all over again, using old random seed.
     */
    void replay() {
        gameIsOver = false;
        rabbitIsAlive = true;
        isRabbitsTurn = false; // will be changed before first move
        stepsTaken = 0;
        // populate using same random numbers as last time
        randomNumberGenerator.setSeed(oldRandomSeed);
        populate();
    }

    /**
     * Puts a rabbit, a fox, and some bushes in the field.
     */
    void populate () {
    
       // protect against calls during creation of game
       isUnderConstruction = true;
       
       // create some abbreviations, just to save some typing
       int numRows = numberOfRows;
       int numCols = numberOfColumns;
       
       // remove any previous contents of field
       for (int i = 0; i < numRows; i++)
           for (int j = 0; j < numCols; j++)
               field[i][j] = null;
       
       // put the rabbit in a random location
       rabbitRow = random(0, numRows - 1);
       rabbitColumn = random(0, numCols - 1);
       isRabbitsTurn = true; // so error messages can place the blame
       rabbit = new Rabbit(this, rabbitRow, rabbitColumn);
       field[rabbitRow][rabbitColumn] = rabbit;
       
       // put the fox in a random location, not too close to the rabbit
       int distance;
       do {
           foxRow = random(0, numRows - 1);
           foxColumn = random(0, numCols - 1);
           distance = Math.max(Math.abs(foxRow - rabbitRow),
                               Math.abs(foxColumn - rabbitColumn));
       } while (distance < (numRows + numCols) / 4);
       isRabbitsTurn = false; // so error messages can place the blame
       fox = new Fox(this, foxRow, foxColumn);
       field[foxRow][foxColumn] = fox;
       
       // put in some random bushes
       // (since bushes don't do anything, we cheat and use the same bush)
       bush = new Bush();
       int numberOfBushes = (numRows * numCols) / 20;
       for (int i = 0; i < numberOfBushes; i++) {
           int bushRow = random(0, numRows - 1);
           int bushColumn = random(0, numCols - 1);
           if (field[bushRow][bushColumn] == null) {
               field[bushRow][bushColumn] = bush;
           }
           else i--;
       }
       
       // finish
       isUnderConstruction = false;
    }
    
    /**
     * Gives one animal a chance to move.
     *
     */
    void allowSingleMove() {
        Animal animal;
        int direction;
        int newRow;
        int newColumn;
        
        // make sure it's legal to allow moves
        if (gameIsOver) return;
        
        // prepare to save info about looks (for later use by view)
        looks.clear();
        
        // decide whose turn it is now (change isRabbitsTurn)
        isRabbitsTurn = !isRabbitsTurn;
        if (isRabbitsTurn) {
            animal = rabbit;
        }
        else { // fox's turn
            animal = fox;
        }
        
        // ask the animal to decide a direction
        direction = animal.decideMove();
        
        // if move is legal, do it
        if (direction != STAY) {
            newRow = animal.row + rowChange(direction);
            newColumn = animal.column + columnChange(direction);
            if (legalLocation(newRow, newColumn) &&
                    !(field[newRow][newColumn] instanceof Bush)) {
                moveAnimal(animal, newRow, newColumn);
            }
        }
        
        // check whether move was fatal for rabbit
        if (rabbit.row == fox.row && rabbit.column == fox.column) {
            rabbitIsAlive = false;
            gameIsOver = true;
        }
        
        // increment steps taken; check for end of game after fox's turn
        if (isRabbitsTurn) {
            stepsTaken++;
        }
        else if (stepsTaken >= MAX_NUMBER_OF_STEPS) {
            gameIsOver = true;
        }
    }
            
    /**
     * Gives the rabbit a chance to move, then gives the fox
     * a chance to move.
     */
    void allowMoves() {
        allowSingleMove();
        allowSingleMove();
    }
    
    /**
     * Utility method to absolutely move an animal from
     * one location to another. Any error checking must
     * be done before this method is invoked.
     *
     * @param animal the animal to be relocated
     * @param newRow the new row number for the animal
     * @param newColumn the new column number for the animal
     */
    private void moveAnimal(Animal animal, int row, int column) {

        // perform move
        field[animal.row][animal.column] = null;
        field[row][column] = animal;
        animal.row = row;
        animal.column = column;
        
        if (animal instanceof Rabbit) {
            rabbitRow = row;
            rabbitColumn = column;
        }
        else { // animal must be a fox
            foxRow = row;
            foxColumn = column;
        }
    }
    
    /**
     * Utility method to choose a random integer from min
     * to max, inclusive.
     *
     * @param min  the smallest number to be returned
     * @param max  the largest number to be returned
     * @return a random number N, where min &lt;= N &lt;= max
     */
    static int random(int min, int max) {
        return randomNumberGenerator.nextInt(max - min + 1) + min;
    }

    /**
     * Determines how moving in the given direction affects the
     * row number.
     *
     * @param direction the direction in which to move
     * @return the amount by which the row number will change
     */
    static int rowChange(int direction) {
        int change = 0;
        switch (direction) {
            case N:
            case NE:
            case NW:
                change = -1;
                break;
            case S:
            case SE:
            case SW:
                change = +1;
                break;
        }
        return change;
    }
    
    /**
     * Determines how moving in the given direction affects the
     * column number.
     *
     * @param direction  the direction in which to move
     * @return the amount by which the column number will change
     */
    static int columnChange(int direction) {
        int change = 0;
        switch (direction) {
            case W:
            case NW:
            case SW:
                change = -1;
                break;
            case E:
            case NE:
            case SE:
                change = +1;
        }
        return change;
    }

    /** Determines whether the given row and column numbers represent
     * a legal location in the field.
     *
     * @param row    the row number
     * @param column the column number
     */
    boolean legalLocation(int row, int column) {
        return    row >= 0 &&    row < numberOfRows &&
               column >= 0 && column < numberOfColumns;
    }
    
    /**
     * Determines what can be seen from a given location, looking
     * in a given direction.
     *
     * @param row  the row of the object doing the looking
     * @param column  the column of the object doing the looking
     * @param direction  the direction of the look
     * @return the object seen
     */
    int look(int row, int column, int direction) {

        // check for illegal request
        if (!verifyLocation("look", row, column)) {
            return Model.EDGE;
        }
        
        // save the fact that this look was performed--this is
        // only here for purposes of later viewing
        looks.add(new Integer(direction));
        
        // decode direction into its x-y components
        int rowDelta = rowChange(direction);
        int columnDelta = columnChange(direction);
        
        // check in that direction until you see something
        // (if nothing else, you will eventually see the edge of the
        //  array, thus the loop <i>will</i> terminate)
        while (true) {
            row = row + rowDelta;
            column = column + columnDelta;
            if (!legalLocation(row, column))
                return EDGE;
            if (field[row][column] instanceof Rabbit)
                return RABBIT;
            if (field[row][column] instanceof Fox)
                return FOX;
            if (field[row][column] instanceof Bush)
                return BUSH;
        }
    }

    /**
     * Determines the distance to the nearest thing, or to the
     * edge of the field, looking in a given direction.
     *
     * @param row    the row of the object doing the looking
     * @param column the column of the object doing the looking
     * @param direction the direction of the look
     * @return the distance
     */
    int distance(int row, int column, int direction) {

        // check for illegal request
        // check for illegal request
        if (!verifyLocation("distance", row, column)) {
            return 1000;
        }
        
        
        // decode direction into its x-y components
        int rowDelta = rowChange(direction);
        int columnDelta = columnChange(direction);
        
        // check in that direction until you see something
        // (if nothing else, you will eventually see the edge of the
        //  array, thus the loop <i>will</i> terminate)
        int steps = 0;
        while (true) {
            row = row + rowDelta;
            column = column + columnDelta;
            steps++;
            if (!legalLocation(row, column) || field[row][column] != null) {
                return steps;
            }
        }
    }
    
    /**
     * Given a direction and a number of times to make 1/8 turn clockwise,
     * return the resultant direction.
     *
     * @param direction the initial direction
     * @param number of 45 degree turns clockwise
     * @return the resultant direction
     */
    static int turn(int direction, int number) {
        int mod = (direction + number) % (MAX_DIRECTION - MIN_DIRECTION + 1);
        if (mod >= MIN_DIRECTION) return mod;
        else return 8 + mod;
    }
    
    /**
     * Ensures that the rabbit or fox is at the location it claims
     * to be.
     *
     * @param methodName   the name of the method being used
     * @param row          the row that the caller claims the animal is in
     * @param column       the column that the caller claims the animal is in
     * @return  true if the location is valid
     */
    private boolean verifyLocation(String methodName, int row, int column) {
        if (isUnderConstruction) {
            System.out.println("Error! Call to " + methodName +
                               " while the hunt is still under construction!");
            return false;
        }
            
        if (isRabbitsTurn) {
            if (field[row][column] == rabbit) {
                return true;
            }
            else {
                System.out.println("Illegal call by rabbit: " + methodName +
                                   "(direction, " + row + ", " + column + ")");
                System.out.println("The rabbit is actually at row " +
                                   rabbitRow + ", column " + rabbitColumn);
                return false;
            }
        }
        else { // fox's turn
            if (field[row][column] == fox) {
                return true;
            }
            else {
                System.out.println("Illegal call by fox: " + methodName +
                                   "(direction, " + row + ", " + column + ")");
                System.out.println("The fox is actually at row " +
                                   foxRow + ", column " + foxColumn);
                return false;
            }
        }
    }
}
