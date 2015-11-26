import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.chart.PieChart;
import javafx.util.Pair;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.DoubleAccumulator;

import static org.junit.Assert.assertEquals;

public class AdjacencyListTest {
    @Test
    public void testDijkstra() throws Exception {
        System.out.println("--------------------");
        System.out.println("TEST DIJKSTRA");
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
        System.out.println("--------------------");
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

    @Test
    public void testReverseEdgeWeight() {
        System.out.println("--------------------");
        System.out.println("TEST REVERSE EDGE WEIGHT");
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(1, 2, 5.0);
        adj.addEdge(1, 3, 1.0);
        adj.addEdge(2, 4, 2.0);
        adj.addEdge(3, 4, 3.0);
        printGraph(adj);
        adj.reverseEdgeWeight();

        AdjacencyList exp = new AdjacencyList();
        exp.addEdge(1, 2, 1.0);
        exp.addEdge(1, 3, 5.0);
        exp.addEdge(2, 4, 4.0);
        exp.addEdge(3, 4, 3.0);
        printGraph(adj);
        System.out.println("--------------------");
        assertEquals(exp.getGraph(), adj.getGraph());
    }

    @Test
    public void testClarkWright2() throws Exception {
        System.out.println("--------------------");
        System.out.println("TEST CLARK WRIGHT 2");
        Pair<List<Client>, AdjacencyList> p = Algorithms.generateRndGraph(4, 4, 100);
        Algorithms.generateRndCharge(p.getKey(), 100);
        List<Good> gs = Algorithms.generateGoods(4, 4, 4);
        List<Bin> bs = Algorithms.generateBins(4, 40);
        List<Vehicle> vs = Algorithms.generateVehicle(100, bs);
        List<Order> os = Algorithms.generateOrders(p.getKey(), 2);
        List<GoodOrder> gos = Algorithms.generateGoodOrder(2, os, gs);

        Database db = new Database("adjtest2.db");
        db.addClients(p.getKey());
        db.addGoods(gs);
        db.addBins(bs);
        db.addVehicles(vs);
        db.addOrders(os);
        db.addGoodOrders(gos);

        System.out.println("Graph");
        printGraph(p.getValue());
        HashMap<Bin, AdjacencyList> hm = p.getValue().clark_wright(db, 0, bs);
        for (Bin b : hm.keySet()) {
            System.out.println("Bin: " + b.toString());
            printGraph(hm.get(b));
            System.out.println();
        }
        System.out.println("--------------------");
    }

    @Test
    public void testClarkWright() throws SQLException, ClassNotFoundException {
        System.out.println("--------------------");
        System.out.println("TEST CLARK WRIGHT");
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
        c0.setId(1);
        Client c1 = new Client("c1", 100);
        db.addClient(c1);
        c1.setId(2);
        Client c2 = new Client("c2", 100);
        db.addClient(c2);
        c2.setId(3);
        Client c3 = new Client("c3", 100);
        db.addClient(c3);
        c3.setId(4);
        Client c4 = new Client("c4", 100);
        db.addClient(c4);
        c4.setId(5);

        Good g0 = new Good(25.0, 10, "d");
        db.addGood(g0);
        g0.setId(1);
        Good g1 = new Good(5.0, 10, "d");
        db.addGood(g1);
        g1.setId(2);
        Good g2 = new Good(7.0, 10, "d");
        db.addGood(g2);
        g2.setId(3);
        Good g3 = new Good(12.0, 10, "d");
        db.addGood(g3);
        g3.setId(4);
        Good g4 = new Good(10.0, 10, "d");
        db.addGood(g4);
        g4.setId(5);

        Bin ub = new Bin(100);
        db.addBin(ub);
        ub.setId(1);


        Order o0 = new Order(c0, new Date(), 1);
        db.addOrder(o0);
        o0.setId(1);
        Order o1 = new Order(c1, new Date(), 1);
        db.addOrder(o1);
        o1.setId(2);
        Order o2 = new Order(c2, new Date(), 1);
        db.addOrder(o2);
        o2.setId(3);
        Order o3 = new Order(c3, new Date(), 1);
        db.addOrder(o3);
        o3.setId(4);
        Order o4 = new Order(c4, new Date(), 1);
        db.addOrder(o4);
        o4.setId(5);

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
        Bin b01 = new Bin(100);
        b01.setId(0);
        Bin b02 = new Bin(100);
        b02.setId(1);
        bins.add(b01);
        bins.add(b02);

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
        System.out.println("--------------------");
        Set<Bin> set = calculated.keySet();
        int i = 0;
        for (Bin b : set){
            if (i == 0)
                assertEquals(94.0, b.getVolumeCurrent(), 0.1);
            else
                assertEquals(65.0, b.getVolumeCurrent(), 0.1);
            i++;
        }
    }

    @Test
    public void testGetMaxDistance() {
        System.out.println("--------------------");
        System.out.println("TEST GET MAX DISTANCE");
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(0, 1, 5.0);
        adj.addEdge(0, 2, 4.0);
        adj.addEdge(1, 3, 1.0);
        adj.addEdge(2, 3, 3.0);
        adj.addEdge(3, 1, 4.0);
        Pair<Pair<Integer, Integer>, Double> ret = adj.getMaxDistance();
        System.out.println(new DistanceMatrix(adj));
        System.out.println(ret.getKey());
        System.out.println(ret.getValue());
        System.out.println("--------------------");
        assertEquals((Double) 7d, adj.getMaxDistance().getValue());
    }

    @Test
    public void testNodesToDestination() {
        System.out.println("---------------------");
        System.out.println("TEST GET MAX DISTANCE");
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(0, 1, 1.0);
        adj.addEdge(0, 3, 4.0);
        adj.addEdge(1, 2, 1.0);
        adj.addEdge(2, 5, 1.0);
        adj.addEdge(5, 4, 2.0);
        adj.addEdge(1, 3, 2.0);
        adj.addEdge(3, 6, 5.0);

        DistanceMatrix dm = new DistanceMatrix(adj);

        HashMap<Integer, AdjacencyList> minTree =
                dm.minDijkstraTree();
        for (Pair<Integer, Integer> p : dm.get().keySet()) {
            System.out.println(p);
            System.out.println(adj.nodesToDestination(p.getKey(), p.getValue(), minTree.get(p.getKey())));
            System.out.println();
        }
        System.out.println("Distance Matrix");
        System.out.println(dm.toString());
        System.out.println("--------------------");
    }
}
