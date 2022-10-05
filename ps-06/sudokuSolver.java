import java.util.*;

public class sudokuSolver {
    
    final static int N = 9;

    int[][] board = {   {0, 0, 3, 0, 2, 0, 6, 0, 0}, 
                        {9, 0, 0, 3, 0, 5, 0, 0, 1}, 
                        {0, 0, 1, 8, 0, 6, 4, 0, 0}, 
                        {0, 0, 8, 1, 0, 2, 9, 0, 0}, 
                        {7, 0, 0, 0, 0, 0, 0, 0, 8}, 
                        {0, 0, 6, 7, 0, 8, 2, 0, 0}, 
                        {0, 0, 2, 6, 0, 9, 5, 0, 0},
                        {8, 0, 0, 2, 0, 3, 0, 0, 9},
                        {0, 0, 5, 0, 1, 0, 3, 0, 0}   };

    public sudokuSolver() {}

// https://stackoverflow.com/questions/15797446/how-do-i-add-values-to-a-set-inside-a-map
    private boolean solve(int[][] board, int pos){
        /*if(col == 9) {
            row++;
            col = 0;
            //return solve(board, row +1, 0);
        }
        if(row== 9) {
            return true;
        }*/
        if(pos == 81){
            return true;
        }

        int row = pos / 9;
        int col = pos % 9;

        if(board[row][col] != 0 ) {
            return solve(board, pos +1);
        }
        

        return false;        
    }



private void solve() {
        System.out.println("solving...");
        HashMap<Integer, HashSet<Integer>> gridMap = new HashMap<Integer, HashSet<Integer>>();
        HashMap<Integer, HashSet<Integer>> rowMap = new HashMap<Integer, HashSet<Integer>>();
        HashMap<Integer, HashSet<Integer>> colMap = new HashMap<Integer, HashSet<Integer>>();
        int remainingZero = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {

                int gridIndex = (c / 3) + (r / 3) * 3;
                gridMap.computeIfAbsent(gridIndex, k -> new HashSet<Integer>()).add(board[r][c]);
                rowMap.computeIfAbsent(r, k -> new HashSet<Integer>()).add(board[r][c]);
                colMap.computeIfAbsent(c, k -> new HashSet<Integer>()).add(board[r][c]);
                if (board[r][c] == 0){
                    remainingZero++;
                }
                // if (board[r][c] != 0){
                // } else{
                //     remainingZero++;
                // }
                
            }
        }

        // for (int r = 0; r < board.length; r++){
        //     for (int c = 0; c < board.length; c++){
        //         backtrack(r, c, remainingZero, gridMap, rowMap, colMap);
        //     }
        // }
        backtrack(0, 0, remainingZero, gridMap, rowMap, colMap);

        
        // System.out.println(gridMap.keySet());
        // System.out.println(rowMap.keySet());
        // System.out.println(colMap.keySet());
        // System.out.println(colMap.get(1));
        // System.out.println(remainingZero);
    }

    private boolean backtrack(int r, int c,
                            int remainingZero,
                            HashMap<Integer, HashSet<Integer>> gridMap, 
                            HashMap<Integer, HashSet<Integer>> rowMap, 
                            HashMap<Integer, HashSet<Integer>> colMap) {
        
        if (r == N || r < 0 || c == N || c < 0 || board[r][c] != 0){
            return false;
        }
        if (remainingZero == 0){
            for (int i = 0; i < board.length; i++) {
                for (int index = 0; index < board.length; index++) {
                    System.out.print(board[i][index]);
                }
                System.out.println();
            }
            return true;
        }
        int gridIndex = (c / 3) + (r / 3) * 3;

        for (int i = 1; i <= 9; i++){

            if (!gridMap.get(gridIndex).contains(i) && 
                !rowMap.get(r).contains(i) &&
                !colMap.get(c).contains(i)){
                    
                    board[r][c] = i;
                    add(gridMap, gridIndex, i);
                    add(rowMap, r, i);
                    add(colMap, c, i);
                    remainingZero--;
                    // System.out.println(r + " " + c + " " + i);
        
                    if (backtrack(r + 1, c, remainingZero, gridMap, rowMap, colMap)){
                        return true;
                    }
                    if (backtrack(r - 1, c, remainingZero, gridMap, rowMap, colMap)){
                        return true;
                    };
                    if (backtrack(r, c + 1, remainingZero, gridMap, rowMap, colMap)) {
                        return true;
                    }
                    if (backtrack(r, c - 1, remainingZero, gridMap, rowMap, colMap)){
                        return true;
                    }
                    
                    board[r][c] = 0;
                    remainingZero++;
                    remove(gridMap, gridIndex, i);
                    remove(rowMap, r, i);
                    remove(colMap, c, i);
            }

        }
        
        return false;
    } 

    public void add(HashMap<Integer, HashSet<Integer>> map, Integer key, Integer value) {
        Set<Integer> set = map.get(key);
        if (set == null) {
            map.put(key, (HashSet<Integer>) (set = new HashSet<Integer>()));
        }
        set.add(value);
    }

    public void remove(HashMap<Integer, HashSet<Integer>> map, Integer key, Integer value) {
        Set<Integer> set = map.get(key);
        set.remove(value);
    }

    public static void main(String[] args) {
        sudokuSolver SS = new sudokuSolver();
        SS.solve();
    }



}
