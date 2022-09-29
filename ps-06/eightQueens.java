import java.util.HashSet;

public class eightQueens {
    // 8 * 8 = 92 ways to arrange it
    final static int N = 8;

    int[][] board = {   {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}   };




    public eightQueens() {

    }
    

    private void solve(){
        System.out.println("solving...");
        HashSet<Integer> column = new HashSet<Integer>();
        HashSet<Integer> posDiag = new HashSet<Integer>();
        HashSet<Integer> negDiag = new HashSet<Integer>();
        backtrack(0, column, posDiag, negDiag);
    }


    private boolean backtrack(int r, HashSet<Integer> column, HashSet<Integer> posDiag, HashSet<Integer> negDiag ){
        // System.out.println(column);

        if (r == N){
            // System.out.println(board.toString());
            System.out.println("-----START-----");
            for (int i = 0; i < board.length; i++) {
                for (int index = 0; index < board.length; index++) {
                    System.out.print(board[i][index]);
                }
                System.out.println();
            }
            System.out.println("-----END----");
            return true;
        }

        for (int c = 0; c < N; c++){

            if (!column.contains(c) && !posDiag.contains(r + c) && !negDiag.contains(r-c)){

                this.board[r][c] = 1;
                column.add(c);
                posDiag.add(r + c);
                negDiag.add(r - c);


                backtrack(r + 1, column, posDiag, negDiag);

                this.board[r][c] = 0;
                column.remove(c);
                posDiag.remove(r + c);
                negDiag.remove(r - c);
            }
        }

        return false;

    }


    public static void main(String[] args) {
        eightQueens EQ = new eightQueens();

        EQ.solve();

    }


}
