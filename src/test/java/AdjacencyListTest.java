import javafx.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by luca on 9/27/15.
 */
public class AdjacencyListTest {
    @Test
    public void Dijkstra() throws Exception {

        AdjacencyList g = new AdjacencyList();
        g.addEdge(1,6,14.0);
        g.addEdge(1,3,9.0);
        g.addEdge(1,2,7.0);
        g.addEdge(6,5,9.0);
        g.addEdge(6,3,2.0);
        g.addEdge(3,4,11.0);
        g.addEdge(2,3,10.0);
        g.addEdge(2,4,15.0);
        g.addEdge(5, 4, 6.0);
        g.addNode(4);

        AdjacencyList h = new AdjacencyList();
        h.addEdge(1,2,7.0);
        h.addEdge(1,3,9.0);
        h.addEdge(1,6,14.0);
        h.addNode(2);
        h.addEdge(3,4,20.0);
        h.addNode(4);
        h.addNode(5);
        h.addEdge(6,5,23.0);

        assertEquals(h.getGraph(),g.Dijkstra(1));
    }
}
