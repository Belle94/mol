import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Algorithms{

    /**
     * First fit decreasing algorithm for bin packing problem.
     * Every single bin will be assign to vehicle.
     * The bin contains a list of good, every good is placed
     * in the bin just to get the best possible packaging.
     * @param goods the goods that will be fit into the bins
     * @param volumeMax the maximum volume that can be transported
     * @return List of Bin which can be null if empty or the volumeMax
     *         isn't enough to contain a single good.
     */
    public static List<Bin> firstFitDecreasing(List<Good> goods, double volumeMax) {
        if (goods.isEmpty()){
            return null;
        }
        goods.sort(Collections.reverseOrder(Good.comparator())); // decreasing order
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(volumeMax)); // add first bin
        for(Good good : goods){
            for (int j=0; j<good.getQnt();j++) {
                good.setQnt(1);
                bins = insertGood(good, bins);
                if (bins == null){
                    System.err.println("[Error] Something doing wrong");
                }
            }
        }
        return bins;
    }

    /**
     * Insert a good in the bin list.
     * @param good the good that will be added to the list
     * @param bins the bin's list containing the proper bin to which
     *             the new good will be added
     * @return List of Bin which can be null if empty or the volumeMax
     *         isn't enough to contain a single good.
     */
    private static List<Bin> insertGood(Good good, List<Bin> bins){
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
                System.err.println("[Error] the maximum Volume of bins must be greater " +
                        "than the volume of a good");
                return null;
            }
            bins.add(bin);
        }
        return bins;
    }
}
