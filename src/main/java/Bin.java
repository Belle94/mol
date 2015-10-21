import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class implement a Bin.
 */
@DatabaseTable(tableName = "bin")
public class Bin {
    @DatabaseField(generatedId = true)
    Integer id;
    @DatabaseField(canBeNull = false)
    private double volumeCurrent;
    @DatabaseField(canBeNull = false)
    private double volumeMax;

    private ForeignCollection<Good> goods;

    public Bin(double volumeMax){
        this(0, volumeMax);
    }

    public Bin(double volumeCurrent, double volumeMax) {
        this.volumeCurrent = volumeCurrent;
        this.volumeMax = volumeMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bin)) return false;

        Bin bin = (Bin) o;

        return Double.compare(bin.volumeCurrent, volumeCurrent) == 0
                && Double.compare(bin.volumeMax, volumeMax) == 0
                && goods.equals(bin.goods);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(volumeCurrent);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(volumeMax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + goods.hashCode();
        return result;
    }
}
