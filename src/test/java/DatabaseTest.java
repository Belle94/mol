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
        try {
            db.openConnection();
            for (Integer i = 0; i < 2; i++) {

                Client c = new Client("c" + i.toString(), (new Random()).nextInt(90));
                db.addClient(c);

                for (Integer j = 0; j < 4; j++) {
                    Order o = new Order();
                    o.setClient(c);
                    o.setDate(new Date());
                    GoodOrder go = new GoodOrder();
                    go.setOrder(o);
                    Integer k = 0;
                    for (; k < (4) + 1; k++) {
                        Good g = new Good((new Random()).nextInt(6) + 4, 4, "desc" + k.toString());
                        go.setGood(g);
                        db.addGood(g);
                    }
                    go.setQnt(k-1);
                    db.addOrder(o, go);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
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

        /*System.out.println("\nORDERS:");
        for (Order o : db.getAllOrders()) {
            System.out.println("------");
            System.out.println(o.toString());
            System.out.println("------");
        }*/

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
