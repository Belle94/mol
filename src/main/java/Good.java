import java.util.Comparator;

/**
 * Class that describe a product will be transported.
 * One Good have one unique id, a volume, quantity of the object
 * and finally the description of the obj
 */
public class Good {
    private double id;
    private double volume;
    private double qnt;
    private double description;

    public Good(double id, double volume, double qnt, double description) {
        this.id = id;
        this.volume = volume;
        this.qnt = qnt;
        this.description = description;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getQnt() {
        return qnt;
    }

    public void setQnt(double qnt) {
        this.qnt = qnt;
    }

    public double getDescription() {
        return description;
    }

    public void setDescription(double description) {
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
}
