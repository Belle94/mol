import java.util.List;
/**
 * Class implement a Bin.
 */
public class Bin {
    private double qntCurrent;
    private double qntMax;
    private List<Good> goods;

    public Bin(double qntMax, List<Good> goods) {
        this.qntMax = qntMax;
        this.goods = goods;
    }

    public double getQntWasted() {
        return qntMax - qntCurrent;
    }

    public double getQntCurrent() {
        return qntCurrent;
    }

    public void setQntCurrent(double qntCurrent) {
        this.qntCurrent = qntCurrent;
    }

    public double getQntMax() {
        return qntMax;
    }

    public void setQntMax(double qntMax) {
        this.qntMax = qntMax;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
