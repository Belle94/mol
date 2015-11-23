import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
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
    private Dao<Neighbor, Integer> daoNeighbor;

    public Database(String databaseUrl) throws SQLException, ClassNotFoundException {
        this.databaseUrl = databaseUrl;
        openConnection();
    }

    public void addClient(Client client) throws SQLException, ClassNotFoundException {
        daoClient.createIfNotExists(client);
    }
    public void addClients(List<Client> clients) throws SQLException, ClassNotFoundException {
        for (Client client:clients)
            daoClient.create(client);
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
    public void addOrders(List<Order> orders) throws SQLException, ClassNotFoundException {
        for (Order order:orders)
            daoOrder.create(order);
    }
    public void addNeighbor(Neighbor neighbor) throws SQLException {
           daoNeighbor.createIfNotExists(neighbor);
    }
    public void addNeighbors(List<Neighbor> neighbors) throws SQLException {
        for (Neighbor n:neighbors)
            daoNeighbor.createIfNotExists(n);
    }
    public Order getOrderByID(Integer id) throws SQLException {
        return daoOrder.queryForId(id);
    }
    public List<Order> getOrdersInVehicle(Vehicle vehicle) throws SQLException {
        return daoOrder.queryForEq(Order.VEHICLE_FIELD_NAME, vehicle.getNumberPlate());
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
    public void addGoods(List<Good> goods) throws SQLException, ClassNotFoundException {
        for (Good good:goods)
            daoGood.create(good);
    }
    public Good getGoodByID(Integer id) throws SQLException {
        return daoGood.queryForId(id);
    }
    public List<Good> getGoodByOrder(Order order) throws SQLException {
        List<Good> ret = new LinkedList<>();

        for (GoodOrder go : daoGoodOrder.
                queryForEq(GoodOrder.ORDER_FIELD_NAME, order)) {
            Good g = getGoodByID(go.getGood().getId());
            ret.add(g);
        }

        return ret;
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
    public void addGoodOrders(List<GoodOrder> gos) throws SQLException {
        for (GoodOrder go:gos)
            daoGoodOrder.create(go);
    }
    public void addVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        daoVehicle.createIfNotExists(vehicle);
    }
    public void addVehicles(List<Vehicle> vehicles) throws SQLException, ClassNotFoundException {
        for(Vehicle vehicle:vehicles)
            daoVehicle.create(vehicle);
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
    public void addBins(List<Bin> bins) throws SQLException {
        for (Bin bin:bins)
            daoBin.create(bin);
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
    public void addAdjacencyList(AdjacencyList adj) throws SQLException {
        for (Integer node: adj.getNodes())
            for(Pair<Integer,Double> neighbor : adj.getPairNeighbors(node))
                daoNeighbor.createIfNotExists(
                        new Neighbor(
                            new Client(node,node.toString(),null),
                            new Client(neighbor.getKey(),neighbor.getKey().toString(),null),
                            neighbor.getValue())
                );
    }
    public AdjacencyList getAdjacencyList() throws SQLException {
        AdjacencyList adj = new AdjacencyList();
        List<Neighbor> neighbors = daoNeighbor.queryForAll();
        for (Neighbor n : neighbors)
            adj.addEdge(n.getFrom().getId(),n.getTo().getId(),n.getWeight());
        return adj;
    }
    public void clearClients() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Client.class);
    }
    public void clearOrders() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Order.class);
    }
    public void clearGoodOrders() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,GoodOrder.class);
    }
    public void clearGoods() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Good.class);
    }
    public void clearVehicles() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Vehicle.class);
    }
    public void clearBins() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Bin.class);
    }
    public void clearNeighbor() throws SQLException {
        TableUtils.clearTable(jdbcConnectionSource,Neighbor.class);
    }
    public void clearTables() throws SQLException {
        clearClients();
        clearOrders();
        clearGoodOrders();
        clearGoods();
        clearVehicles();
        clearBins();
        clearNeighbor();
    }
    /**
     * Setup our database and DAOs
     */
    private void setupDao() throws SQLException {
        /**
         * Create our DAOs. One for each class and associated table.
         */
            daoClient = DaoManager.createDao(jdbcConnectionSource, Client.class);
            daoGoodOrder = DaoManager.createDao(jdbcConnectionSource, GoodOrder.class);
            daoGood = DaoManager.createDao(jdbcConnectionSource, Good.class);
            daoBin = DaoManager.createDao(jdbcConnectionSource, Bin.class);
            daoVehicle = DaoManager.createDao(jdbcConnectionSource, Vehicle.class);
            daoNeighbor = DaoManager.createDao(jdbcConnectionSource, Neighbor.class);
            daoOrder = DaoManager.createDao(jdbcConnectionSource, Order.class);

            TableUtils.createTableIfNotExists(jdbcConnectionSource, Client.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Order.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Good.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, GoodOrder.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Bin.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Vehicle.class);
            TableUtils.createTableIfNotExists(jdbcConnectionSource, Neighbor.class);
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
