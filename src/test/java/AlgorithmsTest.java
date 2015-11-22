import javafx.util.Pair;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
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
    @Test
    public void testGetSaving(){
        AdjacencyList adj = new AdjacencyList();
        adj.addEdge(0, 1, 2.0);
        adj.addEdge(1, 2, 2.0);
        adj.addEdge(2, 0, 3.0);
        adj.addEdge(2, 3, 1.0);
        adj.addEdge(3, 1, 1.0);

        DistanceMatrix dm = new DistanceMatrix(adj);

/*
        for (Map.Entry<Pair<Integer,Integer>,Double> elem: dm.get().entrySet())
            System.out.println("Dm: ("+elem.getKey().getKey()+" - "+elem.getKey().getValue()+") - "+elem.getValue());
*/

        List<Pair<Pair<Integer,Integer>,Double>> saving = Algorithms.getSaving(dm);
        saving.sort(Collections.reverseOrder(Algorithms.savingComparator()));

        AdjacencyList adj1 = adj.getMinPath(2);
        AdjacencyList adj2 = adj.getMinPath(1);

        System.out.println("adj 1");
        for (Map.Entry<Integer,List<Pair<Integer,Double>>> elem:adj1.getGraph().entrySet())
            for (Pair<Integer,Double> e: elem.getValue())
                System.out.println("("+elem.getKey()+","+e.getKey()+") - "+e.getValue());

        System.out.println("adj 2");
        for (Map.Entry<Integer,List<Pair<Integer,Double>>> elem:adj2.getGraph().entrySet())
            for (Pair<Integer,Double> e: elem.getValue())
                System.out.println("("+elem.getKey()+","+e.getKey()+") - "+e.getValue());


/*
        for(Pair<Pair<Integer,Integer>,Double> elem : saving)
            System.out.println("S: ("+elem.getKey().getKey()+" - "+elem.getKey().getValue()+") - "+elem.getValue());
*/

//        System.out.println("Mx from source: "+dm.maxSumFromSource());
    }
}