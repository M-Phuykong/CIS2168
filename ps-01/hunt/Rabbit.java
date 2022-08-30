import java.util.ArrayList;
import java.util.List;

public class Rabbit extends Animal {

    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }
    
    int decideMove() {
        
        // local variable
        //
        boolean seeFox = false;
        int[] diagonalMove = {5,3,6,2,7,1};
        int curDir = Model.STAY;
        int closestEdgeDistance = Model.NUMBER_OF_ROWS + 1;
        int foxDirection = -1;
        int closestEdgeDir = -1;


        // check all direction
        //
        for (int dir = Model.MIN_DIRECTION; dir <= Model.MAX_DIRECTION; dir++){
            if (look(dir) == Model.FOX){
                notSeeFox = 0;
                foxDirection = dir;
                seeFox = true;
            }
            else if (look(dir) == Model.EDGE) {
                
                // find the closest edge direction
                //
                if (distance(dir) <= closestEdgeDistance){
                    closestEdgeDistance = distance(dir);
                    closestEdgeDir = dir;
                }
            }
        
        }
        
        // Move the rabbit if the fox is in it sight
        //
        if (seeFox) {
            notSeeFox = 0;
            for (int i : diagonalMove) {
                if (canMove(Model.turn(foxDirection, i))) {
                    return Model.turn(foxDirection, i);
                }
            }

        }
        else {

            // Increase the turn if the rabbit does not see the fox
            //
            notSeeFox++;

            // After 15 moves, start moving the opposite the direction of
            // the closest edge direction
            //
            if (notSeeFox == 15) {
                notSeeFox = 0;
                for (int i = 0; i < 8; i++){
                    if (canMove(Model.turn(closestEdgeDir, 4))) {
                        return Model.turn(closestEdgeDir, 4);
                    }
                    else {
                        closestEdgeDir++;
                    }
                }
                
            }
        }

        return curDir;
    }

}
