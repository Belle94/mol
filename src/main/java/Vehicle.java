/**
 * Class that implement Vehicle. Each vehicle transport the goods
 */
public class Vehicle{
    private double chargeCurrent;
    private double chargeMax;
    private Bin bin;

    public Vehicle(double chargeMax, Bin bin) {
        this.chargeMax = chargeMax;
        this.bin = bin;
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
