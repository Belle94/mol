import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Class implement an order.
 */
@DatabaseTable(tableName = "orders")
public class Order {
    public final static String CLIENT_FIELD_NAME = "client_id";
    public final static String DATE_FIELD_NAME = "date";
    public final static String VEHICLE_FIELD_NAME = "vehicle_id";
    public final static String POS_FIELD_NAME = "pos";

    @DatabaseField (generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false,
            columnName = CLIENT_FIELD_NAME)
    private Client client;
    @DatabaseField (canBeNull = false, columnName = DATE_FIELD_NAME)
    private Date date;
    @DatabaseField (canBeNull = true, columnName = POS_FIELD_NAME)
    private Integer pos;
    @DatabaseField (canBeNull = true, foreign = true, columnName = VEHICLE_FIELD_NAME)
    private Vehicle vehicle;

    public Order() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Order (Integer id, Client client, Date date, Integer pos) {
        this.id = id;
        this.client = client;
        this.pos = pos;
        this.date = date;
        this.vehicle = null;
    }

    public Order (Client client, Date date, Integer pos) {
        this(null, client, date, pos);
    }

    public Order (Client client, Date date) {
        this(null, client, date, null);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nClient ID: " + client.getId() +
                "\nDate: " + date.toString() +
                "\nPos: " + pos;
    }
}
