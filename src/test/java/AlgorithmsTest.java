import javafx.util.Pair;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * AlgorithmsTest class, all testing are implemented below.
 */
public class AlgorithmsTest{
//    @Test
    public void testFirstFitDecreasing() throws Exception {
        List<Good> testGoodList = new ArrayList<>();
        testGoodList.add(new Good(1, 2.0, 2, "Snack"));
        testGoodList.add(new Good(2, 3.0, 4, "Candies"));
        testGoodList.add(new Good(3, 4.0, 3, "Fruit juice"));
        testGoodList.add(new Good(4, 6.0, 1, "kinder pinguì"));
        testGoodList.add(new Good(5, 7.0, 2, "Kitkat"));

        List<Bin>binsExpected = new ArrayList<>();
        int max = 12;
        binsExpected.add(new Bin(max));
        binsExpected.add(new Bin(max));
        binsExpected.add(new Bin(max));
        binsExpected.add(new Bin(max));
        binsExpected.add(new Bin(max));

        binsExpected.get(0).addGood(new Good(5, 7.0, 1, "Kitkat"));
        binsExpected.get(0).addGood(new Good(3, 4.0, 1, "Fruit juice"));
        binsExpected.get(1).addGood(new Good(5, 7.0, 1, "Kitkat"));
        binsExpected.get(1).addGood(new Good(3, 4.0, 1, "Fruit juice"));
        binsExpected.get(2).addGood(new Good(4, 6.0, 1, "kinder pinguì"));
        binsExpected.get(2).addGood(new Good(3, 4.0, 1, "Fruit juice"));
        binsExpected.get(2).addGood(new Good(1, 2.0, 1, "Snack"));
        binsExpected.get(3).addGood(new Good(2, 3.0, 4, "Candies"));
        binsExpected.get(4).addGood(new Good(1, 2.0, 1, "Snack"));

        assertEquals(Algorithms.firstFitDecreasing(testGoodList,max),binsExpected);
    }
    //@Test
    public void testGetSaving(){
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(0, 1, 2.0);
        adj.addEdge(1, 2, 2.0);
        adj.addEdge(2, 0, 3.0);
        adj.addEdge(2, 3, 1.0);
        adj.addEdge(3, 1, 1.0);
        DistanceMatrix dm = new DistanceMatrix(adj);

/*
        System.out.println("0 - 3");
        adj.getMinPath(0,3).printGraphToString();
        System.out.println("3 - 0");
        adj.getMinPath(3,0).printGraphToString();
*/
/*
        for (Map.Entry<Pair<Integer,Integer>,Double> elem: dm.get().entrySet())
            System.out.println("Dm: ("+elem.getKey().getKey()+" - "+elem.getKey().getValue()+") - "+elem.getValue());
*/
        List<Pair<Pair<Integer,Integer>,Double>> saving = Algorithms.getSavings(dm);
        saving.sort(Collections.reverseOrder(Algorithms.savingComparator()));
        AdjacencyList adj1 = new AdjacencyList();
        System.out.println("refadj");
        adj.printGraphToString();
        System.out.println("adj1 - before join");
        adj1.printGraphToString();
        System.out.println("Saving will be joined: ("
                +saving.get(0).getKey().getKey()+","+saving.get(0).getKey().getValue()+") w:"
                +saving.get(0).getValue());
        adj1.addSaving(adj, saving.get(0));
        System.out.println("adj1 - after join");
        adj1.printGraphToString();
/*
        for(Pair<Pair<Integer,Integer>,Double> elem : saving)
            System.out.println("S: ("+elem.getKey().getKey()+" - "+elem.getKey().getValue()+") - "+elem.getValue());
*/
//        System.out.println("Mx from source: "+dm.maxSumFromSource());
    }
    @Test
    public void testClarkAndWright() throws SQLException, ClassNotFoundException {
        AdjacencyList adjacencyList = new AdjacencyList();
        Pair<List<Client>, AdjacencyList> pair = null;
        Database db= new Database("testcw.db");

        try {
            pair = Algorithms.generateRndGraph(4,3,4);
            adjacencyList = pair.getValue();
            DistanceMatrix distanceMatrix = new DistanceMatrix(adjacencyList);
            List<Pair<Pair<Integer,Integer>,Double>> saving = Algorithms.getSavings(distanceMatrix);
            saving.sort(Collections.reverseOrder(Algorithms.savingComparator()));
            List<Client> clients = pair.getKey();
            Pair<Pair<Integer,Integer>,Double> rtn = adjacencyList.getMaxDistance();
            Double maxDistance = rtn.getValue();
            Algorithms.generateRndCharge(clients, maxDistance.intValue());
            List <Good> goods = Algorithms.generateGoods(1,1,3);
            List <Bin> bins = Algorithms.generateBins(5, 10);
            List <Vehicle> vehicles = Algorithms.generateVehicle(maxDistance, bins);
            List <Order> orders = Algorithms.generateOrders(clients,1);
            List<GoodOrder> goodOrders = Algorithms.generateGoodOrder(1,orders,goods);
            db.clearTables();
            db.addClients(clients);
            db.addOrders(orders);
            db.addGoods(goods);
            db.addGoodOrders(goodOrders);
            db.addBins(bins);
            db.addVehicles(vehicles);
            db.addAdjacencyList(adjacencyList);
            db.closeConnection();
            List<AdjacencyList> adjs = Algorithms.clarkAndWright(adjacencyList, db);
            int i = 0;
            System.out.println("\n \nadj - generated");
            adjacencyList.printGraphToString();
            for (AdjacencyList adj:adjs){
                System.out.println("adj - "+ ++i);
                adj.printGraphToString();
            }
            if (adjs.isEmpty())
                System.out.println("empty list clarkAndWright");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}