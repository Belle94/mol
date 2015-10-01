import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import javax.xml.crypto.Data;

/**
 * Class that implement Vehicle. Each vehicle transport the goods
 */
@DatabaseTable (tableName = "vehicle")
public class Vehicle{
    public final static String CHARGE_CURRENT_FIELD_NAME = "current charge";
    public final static String CHARGE_MAX_FIELD_NAME = "maximal charge";

    @DatabaseField(id =true)
    private String numberPlate;
    @DatabaseField(canBeNull = false, columnName = CHARGE_CURRENT_FIELD_NAME)
    private double chargeCurrent;
    @DatabaseField(canBeNull = false, columnName = CHARGE_MAX_FIELD_NAME)
    private double chargeMax;
    @ForeignCollectionField
    private ForeignCollection<Itinerary> itineraries;

    private Bin bin;

    public Vehicle() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

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

    public ForeignCollection<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(ForeignCollection<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
}
