import javafx.util.Pair;
import org.omg.CORBA.INTERNAL;

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
                System.err.println("[Error] the maximum Volume of bins must be greater " +
                        "than the volume of a good");
                return null;
            }
            bins.add(bin);
        }
        return bins;
    }

    /**
     * implement addGood method, refresh the volumeCurrent
     * @param good will be added
     * @return boolean value, true if the function add the good correctly, else false.
     */
    public boolean addGood(Good good){
        if ( getVolumeWasted() >= (good.getVolume()*good.getQnt()) ){
            Good g = containsId(good.getId());
            if (g != null){
                g.setQnt(g.getQnt()+good.getQnt());
                volumeCurrent += g.getVolume()*good.getQnt();
            }else {
                volumeCurrent += good.getVolume()*good.getQnt();
                goods.add(good);
            }
            return true;
        }
        return false;
    }

    /**
     * implement removeGood method, refresh the volumeCurrent
     * @param good will be remove
     * @return boolean value, true if the function remove the good correctly, else false.
     */
    public boolean removeGood(Good good){
        Good g = containsId(good.getId());
        if (g!=null){
            volumeCurrent -= good.getQnt()*good.getVolume();
            if (g.getQnt()-good.getQnt() <=0)
                goods.remove(good);
            else
                g.setQnt(g.getQnt()-good.getQnt());
            return true;
        }
        return false;
    }
    /**
     * contains Id method looking for the id param in the bins, than return
     * the Good object if it is founded, null if it isn't.
     * @param id that will be looking for in the bin
     * @return null when the ID isn't in the bin, else the good with the same ID is returned.
     */
    public Good containsId(Integer id){
        for (Good good: goods){
            if (Objects.equals(id, good.getId()))
                return good;
        }
        return null;
    }

}
