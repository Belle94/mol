import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
/**
 * AlgorithmsTest class, all testing are implemented below.
 */
public class AlgorithmsTest{
    @Test
    public void testFirstFitDecreasing() throws Exception {
        List<Good> testGoodList = new ArrayList<>();
        testGoodList.add(new Good(1, 2.0, 2, "Snack"));
        testGoodList.add(new Good(2, 3.0, 4, "Candies"));
        testGoodList.add(new Good(3, 4.0, 3, "Fruit juice"));
        testGoodList.add(new Good(4, 6.0, 1, "kinder pinguì"));
        testGoodList.add(new Good(5, 7.0, 2, "Kitkat"));

        List<Bin>binsExpected = new ArrayList<>();
        binsExpected.add(new Bin(12));
        binsExpected.add(new Bin(12));
        binsExpected.add(new Bin(12));
        binsExpected.add(new Bin(12));
        binsExpected.add(new Bin(12));

        binsExpected.get(0).addGood(new Good(5, 7.0, 1, "Kitkat"));
        binsExpected.get(0).addGood(new Good(3, 4.0, 1, "Fruit juice"));

        binsExpected.get(1).addGood(new Good(5, 7.0, 1, "Kitkat"));
        binsExpected.get(1).addGood(new Good(3, 4.0, 1, "Fruit juice"));

        binsExpected.get(2).addGood(new Good(4, 6.0, 1, "kinder pinguì"));
        binsExpected.get(2).addGood(new Good(3, 4.0, 1, "Fruit juice"));
        binsExpected.get(2).addGood(new Good(1, 2.0, 1, "Snack"));

        binsExpected.get(3).addGood(new Good(2, 3.0, 4, "Candies"));

        binsExpected.get(4).addGood(new Good(1, 2.0 ,1, "Snack"));

        System.out.println("\n# Data Input:");
        printGoods(testGoodList);
        System.out.println("\n# Expected Output:");
        printBins(binsExpected);
        System.out.println("\n# Algorithms Result:");
        printBins(Algorithms.firstFitDecreasing(testGoodList,12));

    }

    public static void printGoods(List<Good> list){
        for (int i=0; i<list.size(); i++)
            System.out.println(i+".\t id:"+list.get(i).getId()+"\tVolume:"+list.get(i).getVolume()
                    +"\t qnt:"+list.get(i).getQnt()+"\t Desc:"+list.get(i).getDescription());
    }

    public static void printBins(List<Bin> list){
        for (int i=0; i<list.size(); i++){
            System.out.println(i+". #BIN# \t CurrentVolume:"+list.get(i).getVolumeCurrent()+"\t WastedVolume:"+ list.get(i).getVolumeWasted());
            printGoods(list.get(i).getGoods());
        }
    }
}