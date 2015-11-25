import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    @DatabaseField(canBeNull = false)
    private Integer ChargeNode;

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
        this.ChargeNode = 0;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Integer getChargeNode() {
        return ChargeNode;
    }

    public void setChargeNode(Integer chargeNode) {
        ChargeNode = chargeNode;
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

    public boolean canContain(Order oi, Order oj, Database db) throws SQLException {
        List<Good> goodsOi = db.getGoodByOrder(oi);
        List<Good> goodsOj = db.getGoodByOrder(oj);
        return goodsOi != null && goodsOj != null
                && this.bin.addAllGood(goodsOi)
                && this.bin.addAllGood(goodsOj);
    }

    public boolean canContain(Order o, Database db) throws SQLException {
        List<Good> goodsOi = db.getGoodByOrder(o);
        return this.bin.addAllGood(goodsOi);
    }

    public boolean canTravel(AdjacencyList adj){
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + numberPlate +
                "\nCurrent charge: " + chargeCurrent +
                "\nMax charge: " + chargeMax;
    }
}
