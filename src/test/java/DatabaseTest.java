import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {
    Database db;

    public DatabaseTest() {
        try {
            db = new Database("test.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Init db
        init();
    }

    public void init() {

    }

    @Test
    public void insertClientOrderGood(){

    }

    @Test
    public void inputDatabaseTest() throws Exception{

    }
    @Test
    public void outputDatabaseTest() throws Exception{
        db.openConnection();
        System.out.println("CLIENTS:");
        for (Client c : db.getAllClients()) {
            System.out.println("------");
            System.out.println(c.toString());
            System.out.println("------");
        }

        System.out.println("\nORDERS:");
        for (Order o : db.getAllOrders()) {
            System.out.println("------");
            if (o.getClient() != null)
                System.out.println(o.toString());
            else
                System.out.println("null");
            System.out.println("------");
        }

        System.out.println("\nGOOD_ORDER");
        for (GoodOrder go : db.getAllGoodOrders()) {
            System.out.println("------");
            System.out.println(go.toString());
            System.out.println("------");
        }
        db.closeConnection();
    }

    @Test
    public void deleteDatabaseTest() throws Exception{

    }
    @Test
    public void createDatabaseTest() throws Exception {

    }
}
