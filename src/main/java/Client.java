public class Client {
    private String name;
    private Integer charge;

    Client(String name, Integer charge) {
        this.name = name;
        this.charge = null;
        setCharge(charge);
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
}
