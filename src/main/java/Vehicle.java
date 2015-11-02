import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Class that implement Vehicle. Each vehicle transport the goods
 */
@DatabaseTable (tableName = "vehicle")
public class Vehicle{
    public final static String CHARGE_CURRENT_FIELD_NAME = "current charge";
    public final static String CHARGE_MAX_FIELD_NAME = "maximal charge";

    @DatabaseField(id = true)
    private String numberPlate;
    @DatabaseField(canBeNull = false, columnName = CHARGE_CURRENT_FIELD_NAME)
    private double chargeCurrent;
    @DatabaseField(canBeNull = false, columnName = CHARGE_MAX_FIELD_NAME)
    private double chargeMax;
    @DatabaseField(foreign = true, canBeNull = false)
    private Bin bin;

    public Vehicle() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    /**
     *
     * @param numberPlate numberPlate
     * @param chargeMax max charge can get vehicle
     * @param bin the bin of a vehicle
     */
    public Vehicle(String numberPlate, double chargeMax, Bin bin) {
        this.numberPlate = numberPlate;
        this.chargeMax = chargeMax;
        this.bin = bin;
        this.chargeCurrent = 0;
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

    @Override
    public String toString() {
        return "ID: " + numberPlate +
                "\nCurrent charge: " + chargeCurrent +
                "\nMax charge: " + chargeMax;
    }
}
