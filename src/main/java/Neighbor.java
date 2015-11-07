import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable (tableName = "edge")
public class Neighbor {
    public static final String CLIENT_FIELD_NAME_FROM="client_from";
    public static final String CLIENT_FIELD_NAME_TO="client_to";
    public static final String WEIGHT_FIELD_NAME="weight";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, canBeNull = false, columnName = CLIENT_FIELD_NAME_FROM)
    private Client from;
    @DatabaseField(foreign = true, canBeNull = false, columnName = CLIENT_FIELD_NAME_TO)
    private Client to;
    @DatabaseField(canBeNull = true, columnName = WEIGHT_FIELD_NAME)
    private Double weight;

    public Neighbor(){

    }

    public Neighbor(Client from, Client to, Double weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getFrom() {
        return from;
    }

    public void setFrom(Client from) {
        this.from = from;
    }

    public Client getTo() {
        return to;
    }

    public void setTo(Client to) {
        this.to = to;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
