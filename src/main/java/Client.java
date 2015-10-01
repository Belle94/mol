import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "client")
public class Client {
    public static final String NAME_FIELD_NAME="name";
    public static final String CHARGE_FIELD_NAME="charge";

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField(canBeNull = false, columnName = NAME_FIELD_NAME)
    private String name;
    @DatabaseField(canBeNull = false, columnName = CHARGE_FIELD_NAME)
    private Integer charge;
    @ForeignCollectionField
    private ForeignCollection<Order> orders;

    public Client(){
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Client(String name, Integer charge) {
        this.name = name;
        this.charge = null;
        setCharge(charge);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCharge() {
        return this.charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge >= 0 ? charge :
                this.charge == null ? 0 : charge;
    }

    public ForeignCollection<Order> getOrders() {
        return orders;
    }

    public void setOrders(ForeignCollection<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
