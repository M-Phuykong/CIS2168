import java.util.HashSet;

public class eightQueens {
    
    int[][] board = {   {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}   };

    HashSet<Integer> column;
    HashSet<Integer> posDiag;
    HashSet<Integer> negDiag;

    public eightQueens() {

    }
    

    private void solve(int position){
        System.out.println("solving...");
    }


    private void backtrack(int r, HashSet posDiag, HashSet negDiag){

        if (r == 8){
            System.out.println("do something");
        }


    }


    public static void main(String[] args) {
        
    }


}
