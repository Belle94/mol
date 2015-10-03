import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithms{
    /**
     * First fit decreasing algorithm for bin packing problem.
     * Every single bin will be assign to vehicle.
     * The bin contains a list of good, every good is placed
     * in the bin just to get the best possible packaging.
     * @param goods the goods will be fit into the bins
     * @param qntMax the Maximal quantity that can be transported
     * @return List of Bin or null if the list of goods is null or empty.
     */
    public static List<Bin> firstFitDecreasing(List<Good> goods, double qntMax) {
        if (goods.isEmpty()){
            return null;
        }
        goods.sort(Collections.reverseOrder(Good.comparator())); //decreasing order
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(qntMax)); //add first bin
        for(Good good : goods){
            int i = 0;
            while(bins.get(i) != null){ // or while (bins.contain(bins.get(i)))
                if (!bins.get(i).addGood(good))
                    i++;
            }
        }
        return bins;
    }
}
