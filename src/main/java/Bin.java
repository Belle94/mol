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
    private Integer id;
    @DatabaseField(canBeNull = false)
    private double volumeCurrent;
    @DatabaseField(canBeNull = false)
    private double volumeMax;

    public Bin() {
        // required by ORMLite
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bin)) return false;

        Bin bin = (Bin) o;

        return Double.compare(bin.volumeCurrent, volumeCurrent) == 0
                && Double.compare(bin.volumeMax, volumeMax) == 0
                /*&& goods.equals(bin.goods)*/;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(volumeCurrent);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(volumeMax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        //result = 31 * result + goods.hashCode();
        return result;
    }
}
