import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Client")
public class Client {
    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private Integer charge;

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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
