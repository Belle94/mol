import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdjacencyListTest {
//    @Test
    public void Dijkstra() throws Exception {
        AdjacencyList g = new AdjacencyList();
        g.addEdge(1, 6, 14.0);
        g.addEdge(1, 3, 9.0);
        g.addEdge(1, 2, 7.0);
        g.addEdge(6, 5, 9.0);
        g.addEdge(6, 3, 2.0);
        g.addEdge(3, 4, 11.0);
        g.addEdge(2, 3, 10.0);
        g.addEdge(2, 4, 15.0);
        g.addEdge(5, 4, 6.0);

        AdjacencyList h = new AdjacencyList();
        h.addEdge(1, 2, 7.0);
        h.addEdge(1, 3, 9.0);
        h.addEdge(1, 6, 14.0);
        h.addEdge(3, 4, 20.0);
        h.addEdge(6, 5, 23.0);
        g = new AdjacencyList(g.Dijkstra(1));
        assertEquals(h, g);

    }
    public static void printGraph(AdjacencyList g){
        for(Integer k : g.getNodes()){
            System.out.println("Node "+k+"\t hashCode: "+ g.getGraph().get(k).hashCode());
            for (Pair<Integer,Double> c : g.getGraph().get(k)){
                System.out.print("(" + c.getKey() + "," + c.getValue() + ") ");
            }
            System.out.println();
        }
    }
}
