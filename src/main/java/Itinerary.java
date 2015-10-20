import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Class implement an itinerary of one vehicle
 */
@DatabaseTable(tableName = "itinerary")
public class Itinerary {
    public final static String VEHICLE_FIELD_NAME = "vehicle_id";
    public final static String DATE_FIELD_NAME = "date";
    public final static String RANK_FIELD_NAME = "ranking";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = VEHICLE_FIELD_NAME)
    private Vehicle vehicle;
    @DatabaseField(canBeNull = false, columnName = DATE_FIELD_NAME)
    private Date date;
    @DatabaseField(canBeNull = false, columnName = RANK_FIELD_NAME)
    private Integer rank;
    @ForeignCollectionField
    ForeignCollection<Order> orders;

    public Itinerary() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Itinerary(Integer id, Vehicle v, Date d, Integer r) {
        this.id = id;
        this.vehicle = v;
        this.date = d;
        this.rank = r;
    }

    public Itinerary(Vehicle v, Date d, Integer r) {
        this.vehicle = v;
        this.date = d;
        this.rank = r;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public ForeignCollection<Order> getOrders() {
        return orders;
    }

    public void setOrders(ForeignCollection<Order> orders) {
        this.orders = orders;
    }
}
