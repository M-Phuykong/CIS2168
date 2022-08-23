/**
 * Represents a fox.
 * 
 * @author David Matuszek
 * @version October 12, 2001
 */
public class Fox extends Animal {

    // instance variables

    private boolean haveSeenRabbit = false;
    private boolean canSeeRabbitNow = false;
    private int distanceToRabbit;
    private int directionToRabbit;
    private int currentDirection;
    
    /**
     * Constructs a Fox in the given model, at the given position
     * in the field.
     *
     * @param model  the model that controls this fox.
     * @param row    the row of the field containing this fox.
     * @param column the column of the field containing this fox.
     */
    public Fox(Model model, int row, int column) {
        super(model, row, column);
        currentDirection = Model.random(Model.MIN_DIRECTION,
                                        Model.MAX_DIRECTION);
    }
    
    /**
     * Controls the movement of the fox.
     *
     * @return the direction in which the fox wishes to move.
     */
     int decideMove() {
    
        // look all around for rabbit
        canSeeRabbitNow = false;
        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
            if (look(i) == Model.RABBIT) {
                canSeeRabbitNow = haveSeenRabbit = true;
                directionToRabbit = i;
                distanceToRabbit = distance(i);
            }
        }
        
        // if rabbit has been seen recently (not necessarily this time),
        // move toward its last known position
        if (haveSeenRabbit) {
            if (distanceToRabbit > 0) {
                distanceToRabbit--;
                return directionToRabbit;
            }
            else { // rabbit was here--where did it go?
                haveSeenRabbit = false;
                currentDirection = Model.random(Model.MIN_DIRECTION,
                                                Model.MAX_DIRECTION);
            }
        }
        
        // either haven't seen rabbit, or lost track of rabbit
        // continue with current direction, maybe dodging bushes
        if (canMove(currentDirection))
            return currentDirection;
        else if (canMove(Model.turn(currentDirection, 1)))
            return Model.turn(currentDirection, 1);
        else if (canMove(Model.turn(currentDirection, -1)))
            return Model.turn(currentDirection, -1);
        else {
            currentDirection = Model.random(Model.MIN_DIRECTION,
                                            Model.MAX_DIRECTION);
            for (int i = 0; i < 8; i++) {
                if (canMove(currentDirection))
                    return currentDirection;
                else
                    currentDirection = Model.turn(currentDirection, 1);
            }
        }
        // stuck! cannot move
        return Model.STAY;
    }
            
}
