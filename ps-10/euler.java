import java.util.*;
import java.io.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class euler {




    public static void main(String[] args) {

        String filename = "keylog.txt";
        Graph<Integer, String> g = new SparseGraph<Integer, String>(); 

        try {
            Scanner scanner = new Scanner(new File(filename));
            
            while (scanner.hasNextLine()){
                char[] edges = scanner.nextLine().toCharArray();

                // g.addEdge(edges[0] + edges[1], null);
                System.out.println(new StringBuilder(edges[0]).append(edges[1]), Integer.parseInt(edges[0]), );

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    
}
