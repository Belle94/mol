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
            for (Integer i = 0; i < 100; i++) {

                Client c = new Client("c" + i.toString(), (new Random()).nextInt(90));
                db.addClient(c);

                for (Integer j = 0; i < new Random().nextInt(4) + 1; j++) {
                    Order o = new Order();
                    o.setClient(c);
                    o.setDate(new Date());
                    GoodOrder go = new GoodOrder();
                    go.setOrder(o);
                    for (Integer k = 0; k < new Random().nextInt(4) + 1; k++) {
                        Good g = new Good(new Random().nextInt(6) + 4, 3, null);
                        go.setGood(g);
                        db.addGood(g);
                    }

                    db.addOrder(o, go);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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

    }
    @Test
    public void deleteDatabaseTest() throws Exception{

    }
    @Test
    public void createDatabaseTest() throws Exception {

    }
}
