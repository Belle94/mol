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
        int i=0;
        bins.add(i,new Bin(volumeMax)); //add first bin
        for(Good good : goods){
            for (Bin bin:bins){
                for (int j=0; j<good.getQnt();j++) {
                    //se c'è già una merce di quel tipo dentro provo aggiungere una unità
                    if (bin.getGoods().get(j).getId() == good.getId()) {
                        //ci riesco
                        if (bin.getVolumeWasted() >= bin.getGoods().get(j).getVolume())
                            bin.getGoods().get(j).setQnt(
                                    bin.getGoods().get(j).getQnt() + 1);
                        //se non riesco provo col bin successivo

                    //se non esiste un merce di quel tipo allora provo ad crearne una nuovo con qnt 1.
                    } else
                        bin.addGood(new Good(good.getId(), good.getVolume(), 1, good.getDescription()));
                        //se non riesco inserire nell'attuale bin provo col sucessivo
                }
                i++;
                bins.add(i,new Bin(volumeMax));
                if (!bins.get(i).addGood(new Good(good.getId(), good.getVolume(), 1, good.getDescription())))
                    return null; //vuol dire che il volume massimo del veicolo non è sufficiente a trasportare una qnt di una merce.
            }
        }
        return bins;
    }
}
