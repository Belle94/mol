import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Class implement an order.
 */
@DatabaseTable(tableName = "order")
public class Order {
    public final static String CLIENT_FIELD_NAME = "client_id";
    public final static String DATE_FIELD_NAME = "date";

    @DatabaseField (generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = CLIENT_FIELD_NAME)
    private Client client;
    @DatabaseField (canBeNull = false, columnName = DATE_FIELD_NAME)
    private Date date;
    @ForeignCollectionField
    private ForeignCollection<GoodOrder> goodOrders;

    public Order() {
        // all persisted classes must define a no-arg constructor with at least package visibility
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

    public ForeignCollection<GoodOrder> getGoodOrders() {
        return goodOrders;
    }

    public void setGoodOrders(ForeignCollection<GoodOrder> goodOrders) {
        this.goodOrders = goodOrders;
    }
}
