import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdjacencyListTest {
//    @Test
    public void testDijkstra() throws Exception {
        AdjacencyList g = new AdjacencyList();
        g.addEdge(0, 5, 14.0);
        g.addEdge(0, 2, 9.0);
        g.addEdge(0, 1, 7.0);
        g.addEdge(5, 4, 9.0);
        g.addEdge(5, 2, 2.0);
        g.addEdge(2, 3, 11.0);
        g.addEdge(1, 2, 10.0);
        g.addEdge(1, 3, 15.0);
        g.addEdge(4, 3, 6.0);

        AdjacencyList h = new AdjacencyList();
        h.addEdge(0, 1, 7.0);
        h.addEdge(0, 2, 9.0);
        h.addEdge(0, 5, 14.0);
        h.addEdge(2, 3, 11.0);
        h.addEdge(5, 4, 9.0);

        g = new AdjacencyList(g.dijkstra(0).getValue().getGraph());
        printGraph(g);
        System.out.println();
        printGraph(h);
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

//    @Test
    public void testReverseEdgeWeight() {
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(1, 2, 5.0);
        adj.addEdge(1, 3, 1.0);
        adj.addEdge(2, 4, 2.0);
        adj.addEdge(3, 4, 3.0);
        adj.reverseEdgeWeight();

        AdjacencyList exp = new AdjacencyList();
        exp.addEdge(1, 2, 1.0);
        exp.addEdge(1, 3, 5.0);
        exp.addEdge(2, 4, 4.0);
        exp.addEdge(3, 4, 3.0);

        assertEquals(exp.getGraph(), adj.getGraph());
    }

    @Test public void testClarkWright() {

    }
}
