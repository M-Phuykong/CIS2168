import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Track;

public class Rabbit extends Animal {


    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }
    
    int decideMove() {

        int curDir = Model.STAY;

        int dRow[] = { -1, -1, 0, 1, 1, 1, 0, 1};
        int dCol[] = { 0, 1, 1, 1, 0, -1, -1, -1};

        boolean atEdge = false;
        boolean seenFox = false;
        int dir = -1;

        List<Integer> edgeDist = new ArrayList<>();

        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++){

            if (look(i) == Model.EDGE && distance(i) <= 2) {
                atEdge = true;
            }

            if (look(i) == Model.FOX) {

                seenFox = true;
                dir = i;
            }
        }

        // if (atEdge && seenFox) {

        //     int curDist = distance(dir);

        //     for (int j = 0; j < 8; j+= 2) {

        //         if (distance(j) > curDist && canMove(j)){
        //             curDist = distance(j);
        //             curDir = j;
        //         }

        //     }

        //     return curDir;
        // }


        if (seenFox) {
            int[] foxLoc = calcFoxCoord(this.row, this.column, distance(dir), dir);

            int curDist = distance(dir);

            for (int j = 0; j < 8; j++) {
                int adjx = this.row + dRow[j];
                int adjy = this.column + dCol[j];

                if (adjx == foxLoc[0] && adjy == foxLoc[1]){
                    continue;
                }

                for (int k = 0; k < 8; k++){
                    int foxX = foxLoc[0] + dRow[k];
                    int foxY = foxLoc[1] + dCol[k];

                    if (foxX == adjx && foxY == adjy) {
                        break;
                    }
                    else {
                        int distance = calcDist(foxLoc[0], foxLoc[1], adjx, adjy);

                        if (distance > curDist && canMove(j)) {
                            curDist = distance;
                            curDir = j;
                        }

                    }

                }

                
            }

        }

        // System.out.println(curDir);

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
