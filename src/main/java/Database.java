import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
    private Dao<Itinerary,Integer> daoItinerary;
    private Dao<Vehicle,String> daoVehicle;

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

    public void addOrder(Order order, GoodOrder goodOrder) throws SQLException, ClassNotFoundException {
        daoOrder.createIfNotExists(order);
        daoGoodOrder.createIfNotExists(goodOrder);
    }

    public Order getOrderByID(Integer id) throws SQLException {
        return daoOrder.queryForId(id);
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

    public List<Good> getAllGoods() throws SQLException {
        return daoGood.queryForAll();
    }

    public void deleteGood(Good good) throws SQLException, ClassNotFoundException {
        daoGood.delete(good);
    }

    public void addItinerary(Itinerary itinerary) throws SQLException, ClassNotFoundException {
        daoItinerary.createIfNotExists(itinerary);
    }

    public Itinerary getItineraryByID(Integer id) throws SQLException {
        return daoItinerary.queryForId(id);
    }

    public List<Itinerary> getAllItineraries() throws SQLException {
        return daoItinerary.queryForAll();
    }

    public void deleteItinerary(Itinerary itinerary) throws SQLException, ClassNotFoundException {
        daoItinerary.delete(itinerary);
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
            daoItinerary = DaoManager.createDao(jdbcConnectionSource, Itinerary.class);
            daoVehicle = DaoManager.createDao(jdbcConnectionSource, Vehicle.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Client.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Order.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Good.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, GoodOrder.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Itinerary.class);
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
