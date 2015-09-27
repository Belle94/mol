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
     * @param volumeMax the Maximal volume that can be transported
     * @return List of Bin or null if the list empty or the volumeMax isn't enough.
     */
    public static List<Bin> firstFitDecreasing(List<Good> goods, double volumeMax) {
        if (goods.isEmpty()){
            return null;
        }
        goods.sort(Collections.reverseOrder(Good.comparator())); //decreasing order
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(volumeMax)); //add first bin
        for(Good good : goods){
            for (int j=0; j<good.getQnt();j++) {
                bins = insertGood(new Good(good.getId(), good.getVolume(), 1, good.getDescription()), bins);
                if (bins == null){
                    System.err.println("[Error] Something doing wrong");
                }
            }
        }
        return bins;
    }
    public static List<Bin> insertGood(Good good, List<Bin> bins){
        if (bins.isEmpty()){
            return null;
        }
        double volumeMax = bins.get(0).getVolumeMax();
        boolean added = false;
        for (Bin bin:bins){
            if (bin.addGood(good)){
                added = true;
                break;
            }
        }
        if (!added){
            Bin bin = new Bin(volumeMax);
            if (!bin.addGood(good)){
                System.err.println("[Error] the maximal Volume of bins must be greater " +
                        "than the maximum volume of a good");
                return null;
            }
            bins.add(bin);
        }
        return bins;
    }
}
