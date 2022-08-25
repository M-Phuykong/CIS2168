import java.util.ArrayList;
import java.util.List;


public class Rabbit extends Animal {

    private int turnNotSeeFox;

    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }
    
    int decideMove() {

        int curDir = Model.STAY;

        int dRow[] = { -1, -1, 0, 1, 1, 1, 0, 1};
        int dCol[] = { 0, 1, 1, 1, 0, -1, -1, -1};

        int diagDir[] = {7,5,3,1};

        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {

            if (look(i) == Model.FOX) {

                for (int d : diagDir) {

                    if (canMove(Model.turn(i, d))) {
                        return Model.turn(i, d);
                    }

                }

            }
        }

        

        
        // System.out.println(turnNotSeeFox);

        return curDir;
    }

    int calcDist(int foxRow, int foxCol, int RabitRow, int RabitCol) {

        double distance = Math.hypot(foxRow - RabitRow, foxCol - RabitCol);

        int res = (int) Math.ceil(distance);

        return res;
    }


    int[] calcFoxCoord(int RabitRow, int RabitCol, int distance, int dir) {

        int[] coord = new int[2];
        
        // Should change to switch
        //
        if (dir == 0) {
            coord[0] = RabitRow - distance;
            coord[1] = RabitCol;
        }
        else if (dir == 2) {
            coord[0] = RabitRow;
            coord[1] = RabitCol + distance;
        } 
        else if (dir == 4){
            coord[0] = RabitRow + distance;
            coord[1] = RabitCol;
        }
        else if (dir == 6) {
            coord[0] = RabitRow;
            coord[1] = RabitCol - distance;
        } 

        else if (dir == 1 || dir == 7){
            coord[0] = RabitRow - distance;
            coord[1] = RabitCol - distance;
        } 
        else {
            coord[0] = RabitRow + distance;
            coord[1] = RabitCol + distance;
        }

        return coord;
    }
}
