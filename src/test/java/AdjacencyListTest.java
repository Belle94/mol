import javafx.util.Pair;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

    /*@Test
    public void testClarkWright() throws SQLException, ClassNotFoundException {
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(0, 1, 28.0);
        adj.addEdge(1, 0, 28.0);
        adj.addEdge(0, 2, 31.0);
        adj.addEdge(2, 0, 31.0);
        adj.addEdge(0, 3, 20.0);
        adj.addEdge(3, 0, 20.0);
        adj.addEdge(0, 4, 25.0);
        adj.addEdge(4, 0, 25.0);
        adj.addEdge(0, 5, 34.0);
        adj.addEdge(5, 0, 34.0);

        adj.addEdge(1, 2, 21.0);
        adj.addEdge(2, 1, 21.0);
        adj.addEdge(1, 3, 29.0);
        adj.addEdge(3, 1, 29.0);
        adj.addEdge(1, 4, 26.0);
        adj.addEdge(4, 1, 26.0);
        adj.addEdge(1, 5, 20.0);
        adj.addEdge(5, 1, 20.0);

        adj.addEdge(2, 3, 38.0);
        adj.addEdge(3, 2, 38.0);
        adj.addEdge(2, 4, 20.0);
        adj.addEdge(4, 2, 20.0);
        adj.addEdge(2, 5, 32.0);
        adj.addEdge(5, 2, 32.0);

        adj.addEdge(3, 4, 30.0);
        adj.addEdge(4, 3, 30.0);
        adj.addEdge(3, 5, 27.0);
        adj.addEdge(5, 3, 27.0);

        adj.addEdge(4, 5, 25.0);
        adj.addEdge(5, 4, 25.0);

        Database db = new Database("adjtest.db");

        Client c0 = new Client("c0", 100);
        db.addClient(c0);
        c0.setId(0);
        Client c1 = new Client("c1", 100);
        db.addClient(c1);
        c1.setId(1);
        Client c2 = new Client("c2", 100);
        db.addClient(c2);
        c2.setId(2);
        Client c3 = new Client("c3", 100);
        db.addClient(c3);
        c3.setId(3);
        Client c4 = new Client("c4", 100);
        db.addClient(c4);
        c4.setId(4);

        Good g0 = new Good(25.0, 10, "d");
        db.addGood(g0);
        g0.setId(0);
        Good g1 = new Good(5.0, 10, "d");
        db.addGood(g1);
        g1.setId(1);
        Good g2 = new Good(7.0, 10, "d");
        db.addGood(g2);
        g2.setId(2);
        Good g3 = new Good(12.0, 10, "d");
        db.addGood(g3);
        g3.setId(3);
        Good g4 = new Good(10.0, 10, "d");
        db.addGood(g4);
        g4.setId(4);

        Bin ub = new Bin(100);
        db.addBin(ub);
        ub.setId(0);


        Order o0 = new Order(c0, new Date(), 1, ub);
        db.addOrder(o0);
        o0.setId(0);
        Order o1 = new Order(c1, new Date(), 1, ub);
        db.addOrder(o1);
        o1.setId(1);
        Order o2 = new Order(c2, new Date(), 1, ub);
        db.addOrder(o2);
        o2.setId(2);
        Order o3 = new Order(c3, new Date(), 1, ub);
        db.addOrder(o3);
        o3.setId(3);
        Order o4 = new Order(c4, new Date(), 1, ub);
        db.addOrder(o4);
        o4.setId(4);

        db.addGoodOrder(new GoodOrder(o0,g0,1));
        db.addGoodOrder(new GoodOrder(o0,g3,1));
        db.addGoodOrder(new GoodOrder(o1,g0,1));
        db.addGoodOrder(new GoodOrder(o1,g4,1));
        db.addGoodOrder(new GoodOrder(o2,g0,1));
        db.addGoodOrder(new GoodOrder(o2,g1,1));
        db.addGoodOrder(new GoodOrder(o3,g0,1));
        db.addGoodOrder(new GoodOrder(o4,g0,1));
        db.addGoodOrder(new GoodOrder(o4,g2,1));

        Bin b1 = new Bin(100); // 0 - 1 - 5 - 4 - 0
        b1.setId(0);
        b1.addGood(g0);
        b1.addGood(g3);
        b1.addGood(g0);
        b1.addGood(g2);
        b1.addGood(g0);
        AdjacencyList a1 = new AdjacencyList();
        a1.addEdge(0, 1, 28.0);
        a1.addEdge(1, 5, 20.0);
        a1.addEdge(5, 4, 25.0);
        a1.addEdge(4, 0, 25.0);

        Bin b2 = new Bin(100); // 0 - 2 - 3 - 0
        b2.setId(1);
        b2.addGood(g0);
        b2.addGood(g4);
        b2.addGood(g0);
        b2.addGood(g1);
        AdjacencyList a2 = new AdjacencyList();
        a2.addEdge(0, 2, 31.0);
        a2.addEdge(2, 3, 38.0);
        a2.addEdge(3, 0, 20.0);

        HashMap<Bin, AdjacencyList> correct = new HashMap<>();
        correct.put(b1, a1);
        correct.put(b2, a2);

        List<Bin> bins = new LinkedList<>();
        bins.add(b1);
        bins.add(b2);

        System.out.println("Correct:");
        for (Bin b : correct.keySet()) {
            printGraph(correct.get(b));
        }

        System.out.println();
        System.out.println("Calculated:");
        HashMap<Bin, AdjacencyList> calculated = adj.clark_wright(db, 0, bins);
        for (Bin b : calculated.keySet()) {
            printGraph(calculated.get(b));
        }
        assertEquals(correct, calculated);
    }*/
}
