import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Comparator;

/**
 * Class that describe a product will be transported.
 * One Good have one unique id, a volume, quantity of the object
 * and finally the description of the obj
 */
@DatabaseTable(tableName = "good")
public class Good {
    public final static String VOLUME_FIELD_NAME = "volume";
    public final static String QNT_FIELD_NAME = "qnt";
    public final static String DESC_FIELD_NAME = "description";

    @DatabaseField (generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, columnName = VOLUME_FIELD_NAME)
    private double volume;
    @DatabaseField(canBeNull = false, columnName = QNT_FIELD_NAME)
    private Integer qnt;
    @DatabaseField(canBeNull = true, columnName = DESC_FIELD_NAME)
    private String description;

    public Good() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Good(double volume, Integer qnt, String description) {
        this.volume = volume;
        this.qnt = qnt;
        this.description = description;
    }

    public Good(Integer id, double volume, Integer qnt, String description) {
        this.id = id;
        this.volume = volume;
        this.qnt = qnt;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * create and implement a new comparator for goods object.
     * @return comparator of 2 Good
     */
    public static Comparator<Good> comparator(){
        return (o1, o2) -> {
            if (o1.getVolume() < o2.getVolume())
                return -1;
            if (o1.getVolume() > o2.getVolume())
                return 1;
            return 0;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Good)) return false;

        Good good = (Good) o;
        if (Double.compare(good.volume, volume) != 0) return false;
        if (!id.equals(good.id)) return false;
        if (!qnt.equals(good.qnt)) return false;
        return !(description != null ? !description.equals(good.description) : good.description != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        temp = Double.doubleToLongBits(volume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + qnt.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                "\nVolume: " + volume +
                "\nQuantity: " + qnt +
                "\nDescription: " + description;
    }
}