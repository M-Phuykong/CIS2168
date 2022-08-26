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

        boolean seenFox = false;
        int dir = -1;

        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++){
            if (look(i) == Model.FOX) {
                seenFox = true;
                dir = i;
            }
            else if (look(i) == Model.EDGE){
                if (distance(i) < closestEdgeDistance){
                    closestEdgeDistance = distance(i);
                }
            }
        }

        if (seenFox) {
            int[] foxLoc = calcFoxCoord(this.row, this.column, distance(dir), dir);

            int curDist = distance(dir);
            int midDist = calcDist(this.row, this.column, Model.NUMBER_OF_ROWS / 2, Model.NUMBER_OF_COLUMNS / 2);
            int minMidDist = midDist;
            // Fox is only 3 grid near
            //
            if (curDist <= 3){
              if (canMove(Model.turn(dir, 3))){
                return Model.turn(dir, 3);
              }
              else if (canMove(Model.turn(dir, 5))) {
                return Model.turn(dir, 5);
              }
              else {
                for (int i = 0; i < 8; i++) {
                  if (i == dir){
                    continue;
                  }
                  if (canMove(i)){
                    return i;
                  }
                }
              }
            }

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

                        if (midDist >= (Model.NUMBER_OF_ROWS / 4)){

                          int distanceToMid = calcDist(adjx, adjy, Model.NUMBER_OF_ROWS / 2, Model.NUMBER_OF_COLUMNS / 2);


                          if (distance >= curDist && canMove(j) && distanceToMid < midDist) {
                            curDist = distance;
                            curDir = j;
                            midDist = distanceToMid;
                          }

                        }

                        else {

                          if (distance > curDist && canMove(j)) {
                              curDist = distance;
                              curDir = j;
                          }
                        }


                    }

                }

                
            }

        }
        else {

          // int midDist = calcDist(this.row, this.column, Model.NUMBER_OF_ROWS / 2, Model.NUMBER_OF_COLUMNS / 2);
  
          // if (midDist >= Model.NUMBER_OF_ROWS / 4){
          //   for (int k = 0; k < 8; k++){
          //     int adjx = this.row + dRow[k];
          //     int adjy = this.column + dCol[k];
  
          //     int distanceToMid = calcDist(adjx, adjy, Model.NUMBER_OF_ROWS / 2, Model.NUMBER_OF_COLUMNS / 2);
              
          //     if (distanceToMid < midDist && canMove(k)){
          //       curDir = k;
          //       midDist = distanceToMid;
          //     }
  
  
          //   }
  
          // }
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
