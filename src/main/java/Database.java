import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
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

    public Database(String databaseUrl){
        this.databaseUrl = databaseUrl;
    }

    public void addClient(Client client) throws SQLException, ClassNotFoundException {
        openConnection();
        daoClient.createIfNotExists(client);
        closeConnection();
    }

    public void deleteClient(Client client) throws SQLException, ClassNotFoundException {
        openConnection();
        daoClient.delete(client);
        closeConnection();
    }

    public void addOrder(Order order, GoodOrder goodOrder) throws SQLException, ClassNotFoundException {
        openConnection();
        daoOrder.createIfNotExists(order);
        daoGoodOrder.createIfNotExists(goodOrder);
        closeConnection();
    }

    public void deleteOrder(Order order) throws SQLException, ClassNotFoundException {
        openConnection();
        daoOrder.delete(order);
        closeConnection();
    }

    public void addGood(Good good) throws SQLException, ClassNotFoundException {
        openConnection();
        daoGood.createIfNotExists(good);
        closeConnection();
    }

    public void deleteGood(Good good) throws SQLException, ClassNotFoundException {
        openConnection();
        daoGood.delete(good);
        closeConnection();
    }

    public void addItinerary(Itinerary itinerary) throws SQLException, ClassNotFoundException {
        openConnection();
        daoItinerary.createIfNotExists(itinerary);
        closeConnection();
    }

    public void deleteItinerary(Itinerary itinerary) throws SQLException, ClassNotFoundException {
        openConnection();
        daoItinerary.delete(itinerary);
        closeConnection();
    }

    public void addVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        openConnection();
        daoVehicle.createIfNotExists(vehicle);
        closeConnection();
    }

    public void deleteVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        openConnection();
        daoVehicle.delete(vehicle);
        closeConnection();
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
    private void openConnection() throws SQLException, ClassNotFoundException {
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
