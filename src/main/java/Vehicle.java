import com.j256.ormlite.field.DatabaseField;

import javax.xml.crypto.Data;

/**
 * Class that implement Vehicle. Each vehicle transport the goods
 */
public class Vehicle{
    @DatabaseField(id =true)
    private String numberPlate;
    @DatabaseField(canBeNull = false)
    private double chargeCurrent;
    @DatabaseField(canBeNull = false)
    private double chargeMax;
    private Bin bin;

    public Vehicle(String numberPlate, double chargeMax, Bin bin) {
        this.numberPlate = numberPlate;
        this.chargeMax = chargeMax;
        this.bin = bin;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public double getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(double chargeMax) {
        this.chargeMax = chargeMax;
    }

    public double getChargeCurrent() {
        return chargeCurrent;
    }

    public void setChargeCurrent(double chargeCurrent) {
        this.chargeCurrent = chargeCurrent;
    }
}
