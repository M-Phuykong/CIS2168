import java.util.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class graphTest {


    public graphTest(){}

    public void DFS(Graph<Integer,Integer> graph, HashSet<Integer> visit ,Integer node) throws IllegalArgumentException{

        visit.add(node);
        
        // Discovery Order
        System.out.println(node);
        
        for (int nei: graph.getNeighbors(node)){
            if (!visit.contains(nei)){
                DFS(graph, visit, nei);
            }
        }

    }

    public void BFS(Graph<Integer,Integer> graph, Integer start_node) throws IllegalArgumentException{
        
        try {
            graph.getNeighborCount(start_node);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(String.format("Node %d not in graph", start_node));
        } 

        Set<Integer> visit = new HashSet<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(start_node);
        
        while (!q.isEmpty()){
            
            int cur = q.remove();
            visit.add(cur);
            System.out.println(cur);

            for (int nei: graph.getNeighbors(cur)){
                if (!visit.contains(nei)){
                    q.add(nei);
                    visit.add(nei);
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {

        graphTest gTest = new graphTest();

        Graph<Integer, Integer> g = new UndirectedSparseGraph<Integer, Integer>();
        g.addEdge(0, 0, 1);
        g.addEdge(1, 0, 3);
        g.addEdge(2, 1, 2);
        g.addEdge(3, 1, 4);
        g.addEdge(4, 1, 6);
        g.addEdge(5, 1, 7);
        g.addEdge(6, 2, 8);
        g.addEdge(7, 2, 9);
        g.addEdge(8, 3, 2);
        g.addEdge(9, 4, 5);
        g.addEdge(10, 4, 6);
        g.addEdge(11, 4, 7);
        g.addEdge(12, 6, 7);

        gTest.BFS(g, 0);

        Graph<Integer, Integer> g1 = new UndirectedSparseGraph<Integer, Integer>();
        g1.addEdge(0, 0, 1);
        g1.addEdge(1, 0, 2);
        g1.addEdge(2, 0, 3);
        g1.addEdge(3, 0, 4);        
        g1.addEdge(4, 1, 3);
        g1.addEdge(5, 1, 4);
        g1.addEdge(6, 2, 5);
        g1.addEdge(7, 2, 6);
        g1.addEdge(8, 3, 4);
        g1.addEdge(9, 5, 6);

        gTest.DFS(g1, new HashSet<Integer>(), 0);

    }
}