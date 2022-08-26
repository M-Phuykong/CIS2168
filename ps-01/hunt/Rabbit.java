import java.util.ArrayList;
import java.util.List;

public class Rabbit extends Animal {

    private int notSeeFox;

    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }
    
    int decideMove() {

        int curDir = Model.STAY;
        int[] diagonalMove = {5,3,6,2,7,1};
        int foxDirection = -1;
        int closestEdgeDistance = Model.NUMBER_OF_ROWS + 1;
        int closestEdgeDir = -1;
        boolean seeFox = false;

        for (int dir = Model.MIN_DIRECTION; dir <= Model.MAX_DIRECTION; dir++){
            if (look(dir) == Model.FOX){
                notSeeFox = 0;
                foxDirection = dir;
                seeFox = true;
            }
            else if (look(dir) == Model.EDGE) {
                if (distance(dir) <= closestEdgeDistance){
                    closestEdgeDistance = distance(dir);
                    closestEdgeDir = dir;
                }
            }
        
        }

        if (seeFox) {
            notSeeFox = 0;
            for (int i : diagonalMove) {
                if (canMove(Model.turn(foxDirection, i))) {
                    return Model.turn(foxDirection, i);
                }
            }

        }
        else {
            notSeeFox++;
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
