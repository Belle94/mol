import java.util.ArrayList;
import java.util.List;
/**
 * Class implement a Bin.
 */
public class Bin {
    private double volumeCurrent;
    private double volumeMax;
    private List<Good> goods;

    public Bin(double volumeMax){
        this.volumeCurrent = 0;
        this.volumeMax = volumeMax;
        goods = new ArrayList<>();
    }

    public double getVolumeWasted() {
        return volumeMax - volumeCurrent;
    }

    public double getVolumeCurrent() {
        return volumeCurrent;
    }

    public void setVolumeCurrent(double volumeCurrent) {
        this.volumeCurrent = volumeCurrent;
    }

    public double getVolumeMax() {
        return volumeMax;
    }

    public void setVolumeMax(double volumeMax) {
        this.volumeMax = volumeMax;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    /**
     * implement addGood method, refresh the volumeCurrent
     * @param good will be added
     * @return boolean value, true if the function add the good correctly, else false.
     */
    public boolean addGood(Good good){
        if ( getVolumeWasted() >= (good.getVolume()*good.getQnt()) ){
            volumeCurrent += good.getVolume()*good.getQnt();
            goods.add(good);
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
        if (goods.contains(good)){
            volumeCurrent -= good.getQnt()*good.getVolume();
            goods.remove(good);
            return true;
        }
        return false;
    }

}
