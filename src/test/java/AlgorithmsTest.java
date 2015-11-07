import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
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