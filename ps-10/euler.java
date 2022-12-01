import java.util.*;
import java.io.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class euler {

    public static void kahn(Graph<Integer, String> graph){

        Stack<Integer> s = new Stack<Integer>();
        Collection<Integer> vertices;
        
        for (int v: vertices){
            int indegree = graph.inDegree(v);
            System.out.println(v);
            System.out.println(new StringBuilder().append("indegree ").append(graph.inDegree(v)).toString());
            if (indegree == 0){
                s.push(v);
                graph.removeVertex(v);
            }

        }

        System.out.println(s.toString());
    }

    public static void main(String[] args) {

        String filename = "keylog.txt";
        Graph<Integer, String> g = new DirectedSparseGraph<Integer, String>(); 

        try {
            Scanner scanner = new Scanner(new File(filename));
            
            while (scanner.hasNextLine()){
                char[] points = scanner.nextLine().toCharArray();
                String edge1 = new StringBuilder().append(points[0]).append(points[1]).toString();
                String edge2 = new StringBuilder().append(points[1]).append(points[2]).toString();
                
                g.addEdge(edge1, 
                        Character.getNumericValue(points[0]), 
                        Character.getNumericValue(points[1]));
                
                g.addEdge(edge2,
                        Character.getNumericValue(points[1]),
                        Character.getNumericValue(points[2]));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        
        kahn(g);
        

    }

    
}
