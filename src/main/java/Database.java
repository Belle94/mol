import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class that implement method to access a database
 */
public class Database {

    private JdbcConnectionSource jdbcConnectionSource;
    private String databaseUrl;
    private Dao<Client,Integer> daoClient;
    private Dao<Order,Integer> daoOrder;
    private Dao<GoodOrder,Integer> daoGoodOrder;
    private Dao<Good,Integer> daoGood;
    private Dao<Vehicle,String> daoVehicle;
    private Dao<Bin,Integer> daoBin;

    public Database(String databaseUrl) throws SQLException, ClassNotFoundException {
        this.databaseUrl = databaseUrl;
        openConnection();
    }

    public void addClient(Client client) throws SQLException, ClassNotFoundException {
        daoClient.createIfNotExists(client);
    }

    public Client getClientByID(Integer id) throws SQLException {
        return daoClient.queryForId(id);
    }

    public List<Client> getAllClients() throws SQLException {
        return daoClient.queryForAll();
    }

    public void deleteClient(Client client) throws SQLException, ClassNotFoundException {
        daoClient.delete(client);
    }

    public void addOrder(Order order) throws SQLException, ClassNotFoundException {
        daoOrder.createIfNotExists(order);
    }

    public Order getOrderByID(Integer id) throws SQLException {
        return daoOrder.queryForId(id);
    }

    public List<Order> getOrdersInBin(Bin bin) throws SQLException {
        return daoOrder.queryForEq(Order.BIN_FIELD_NAME, bin.getId());
    }

    public List<Order> getOrderByClient(Client c) throws SQLException {
        return daoOrder.queryForEq(Order.CLIENT_FIELD_NAME, c.getId());
    }

    public List<Order> getOrderByClient(Integer id) throws SQLException {
        return daoOrder.queryForEq(Order.CLIENT_FIELD_NAME, id);
    }

    public List<Order> getAllOrders() throws SQLException {
        return daoOrder.queryForAll();
    }

    public void deleteOrder(Order order) throws SQLException, ClassNotFoundException {
        daoOrder.delete(order);
    }

    public void addGood(Good good) throws SQLException, ClassNotFoundException {
        daoGood.createIfNotExists(good);
    }

    public Good getGoodByID(Integer id) throws SQLException {
        return daoGood.queryForId(id);
    }

    public List<Good> getGoodsInBin(Bin bin) throws SQLException {
        LinkedList<Good> l = new LinkedList<>();
        for (Order o : getOrdersInBin(bin))
            l.addAll(getGoodByOrder(o).stream().collect(Collectors.toList()));

        return l;
    }

    public List<Good> getGoodByOrder(Order order) throws SQLException {
        return daoGoodOrder.queryForEq(
                GoodOrder.ORDER_FIELD_NAME,
                order.getId()).stream().map(GoodOrder::getGood)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<Good> getAllGoods() throws SQLException {
        return daoGood.queryForAll();
    }

    public void deleteGood(Good good) throws SQLException, ClassNotFoundException {
        daoGood.delete(good);
    }

    public List<GoodOrder> getAllGoodOrders() throws SQLException {
        return daoGoodOrder.queryForAll();
    }

    public void addGoodOrder(GoodOrder go) throws SQLException {
        daoGoodOrder.createIfNotExists(go);
    }

    public void addVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        daoVehicle.createIfNotExists(vehicle);
    }

    public Vehicle getVehiclesByNumberPlate(String numberPlate) throws SQLException {
        return daoVehicle.queryForId(numberPlate);
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        return daoVehicle.queryForAll();
    }

    public void deleteVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        daoVehicle.delete(vehicle);
    }

    public void addBin(Bin bin) throws SQLException {
        daoBin.createIfNotExists(bin);
    }

    public List<Bin> getAllBins() throws SQLException {
        return daoBin.queryForAll();
    }

    public Bin getBinById(Integer id) throws SQLException {
        return daoBin.queryForId(id);
    }

    public void deleteBin(Bin b) throws SQLException {
        daoBin.delete(b);
    }

    /**
     * Setup our database and DAOs
     */
    private void setupDao() throws SQLException {
        /**
         * Create our DAOs. One for each class and associated table.
         */
            daoClient = DaoManager.createDao(jdbcConnectionSource, Client.class);
            daoOrder = DaoManager.createDao(jdbcConnectionSource, Order.class);
            daoGoodOrder = DaoManager.createDao(jdbcConnectionSource, GoodOrder.class);
            daoGood = DaoManager.createDao(jdbcConnectionSource, Good.class);
            daoBin = DaoManager.createDao(jdbcConnectionSource, Bin.class);
            daoVehicle = DaoManager.createDao(jdbcConnectionSource, Vehicle.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Client.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Order.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Good.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, GoodOrder.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Bin.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Vehicle.class);
    }

    /**
     * Open a connection with the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void openConnection() throws SQLException, ClassNotFoundException {
        final String prefixString = "jdbc:sqlite:";
        Class.forName("org.sqlite.JDBC");
        jdbcConnectionSource = new JdbcConnectionSource(prefixString+databaseUrl);
        setupDao();
    }
    
    /**
     * Closes the connection with the database
     */
    public void closeConnection() {
        jdbcConnectionSource.closeQuietly();
    }
}
