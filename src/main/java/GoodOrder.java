import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Class implement a relationship between Good and Order
 */
@DatabaseTable(tableName = "good_order")
public class GoodOrder {
    public final static String ORDER_FIELD_NAME = "order_id";
    public final static String GOOD_FIELD_NAME = "good_id";
    public final static String QNT_FIELD_NAME = "qnt";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, columnName = ORDER_FIELD_NAME)
    private Order order;
    @DatabaseField(foreign = true, columnName = GOOD_FIELD_NAME)
    private Good good;
    @DatabaseField (canBeNull = false, columnName = QNT_FIELD_NAME)
    private Integer qnt;

    public GoodOrder() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }
}
