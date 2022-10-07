import java.io.*;
import java.util.*;


public class sudokuSolver {
    
    public static int EulerNum;

    final static int N = 9;
    int[][] board = { { 0, 0, 3, 0, 2, 0, 6, 0, 0 },
            { 9, 0, 0, 3, 0, 5, 0, 0, 1 },
            { 0, 0, 1, 8, 0, 6, 4, 0, 0 },
            { 0, 0, 8, 1, 0, 2, 9, 0, 0 },
            { 7, 0, 0, 0, 0, 0, 0, 0, 8 },
            { 0, 0, 6, 7, 0, 8, 2, 0, 0 },
            { 0, 0, 2, 6, 0, 9, 5, 0, 0 },
            { 8, 0, 0, 2, 0, 3, 0, 0, 9 },
            { 0, 0, 5, 0, 1, 0, 3, 0, 0 } };
            
    public sudokuSolver() {}

    private boolean solve(int[][] board, int pos){
        
        // end of the board 
        if(pos == 81){
            for (int i = 0; i < board.length; i++) {
                for (int index = 0; index < board.length; index++) {
                    System.out.print(board[i][index]);
                }
                System.out.println();
            }
            // System.out.print("Euler Number");
            int tmp = 0;
            for(int i = 0, j = 2; i < 3; i++, j--){
                tmp += board[0][i] * (Math.pow(10, j));
            }
            EulerNum += tmp;
            System.out.println();

            return true;
        }

        // convert 1d array to 2d index
        int row = pos / 9;
        int col = pos % 9;

        // move to the next position
        if(board[row][col] != 0 ) {
            return solve(board, pos + 1);
        }

        // go through all possible choices
        for (int i = 1; i <= 9; i++){
            // check if the option is valid
            if (validRow(board, row, col, i) && 
                validCol(board, row, col, i) && 
                validBox(board, row, col, i)) {

                board[row][col] = i;
                solve(board, pos + 1);
                // backtrack and reset board to the original state
                board[row][col] = 0;
            }

        }
        
        return false;
    }

    private boolean validRow(int[][] board, int r, int c, int i){
        for (int j = 0; j < board.length; j++) {
            if (board[r][j] == i) {
                return false;
            }
        }
        return true;
    }

    private boolean validCol(int[][] board, int r, int c, int i) {
        for (int j = 0; j < board.length; j++) {
            if (board[j][c] == i) {
                return false;
            }
        }
        return true;
    }

    private boolean validBox(int[][] board, int r, int c, int i) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (board[(r - r % 3) + j][(c - c % 3) + k] == i){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        sudokuSolver SS = new sudokuSolver();

        File sudokuTextFile = new File("sudoku.txt");
        PrintStream out = new PrintStream(new File("sudokuAnswer.txt"));
        Scanner sc = new Scanner(sudokuTextFile);

        int[][] board = new int[9][9];
        int arrayInd = 0;

        // redirecting it to a file
        //
        System.setOut(out);
        
        while (sc.hasNextLine()){
            String line = sc.nextLine();

            if (line.contains("Grid")){
                
                System.out.println(line);

                arrayInd = 0;
                for (int index = 0; index < board.length; index++) {
                    for (int inner = 0; inner < board[index].length; inner++) {
                        board[index][inner] = 0;
                    }
                }
            }
            else{
                
                int[] row = new int[9];
                for (int i = 0; i < board.length; i++) {
                    row[i] = Character.getNumericValue(line.charAt(i));
                }
                board[arrayInd] = row;
                arrayInd++;
            }

            if (arrayInd == 9){
                SS.solve(board, 0);
            }
        }

        System.out.println("Total Euler Number " + EulerNum);
        sc.close();
    }
}
