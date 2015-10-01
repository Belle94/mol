import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class that implement method to access a database
 */
public class Database {

    private JdbcConnectionSource jdbcConnectionSource;
    private Dao<Client,Integer> daoClient;
    private Dao<Order,Integer> daoOrder;
    private Dao<GoodOrder,Integer> daoGoodOrder;
    private Dao<Good,Integer> daoGood;
    private Dao<Itinerary,Integer> daoItinerary;
    private Dao<Vehicle,String> daoVehicle;
    
    /**
     * Setup our database and DAOs
     */
    private void setupDao() throws Exception {
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
    private void openConnection(String databaseUrl) throws SQLException, ClassNotFoundException, Exception {
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
