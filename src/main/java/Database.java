import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * class that implement method to access a database
 */
public class Database {
    private final String location = "database.db";
    private JdbcConnectionSource jdbcConnectionSource;
    private DaoManager dao;
    private DatabaseTableConfig ordersTable;
    public Database(){

    }
    /**
     * Open a connection with the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void openConnection(String databaseUrl) throws SQLException, ClassNotFoundException {
        final String prefixString = "jdbc:sqlite:";
        Class.forName("org.sqlite.JDBC");
        jdbcConnectionSource = new JdbcConnectionSource(prefixString+databaseUrl);
    }

    /**
     * Closes the connection with the database
     */
    public void closeConnection() {
        jdbcConnectionSource.closeQuietly();
    }
}
