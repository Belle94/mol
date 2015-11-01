import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {
    Database db;
    static int co = 0;
    public DatabaseTest() {
        try {
            db = new Database("test.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (co++ == 1)
            // Init db
            init();
    }

    public void init() {
        try {

            Bin bin = new Bin(160);
            db.addBin(bin);

            Integer pos = 0;

            for (int k = 0; k < 100; k++) {
                Good g = new Good(10,10,"desc" + k);
                db.addGood(g);
            }

            List<Good> gs = db.getAllGoods();

            for (int i = 0; i < 4; i++, pos++) {
                Client c = new Client("c" + i, 40);
                db.addClient(c);
                c.setId(db.getAllClients().get(i).getId());

                for (int j = 0; j < 4; j++, pos++) {
                    Order o = new Order(c, new Date(), pos, bin);
                    db.addOrder(o);

                    o.setId(db.getAllOrders().get(
                            db.getAllOrders().size() - 1).getId());
                    for (int k = 0; k < 3; k++) {
                        GoodOrder go = new GoodOrder(o,
                                gs.get(new Random().nextInt(100)),
                                1);
                        db.addGoodOrder(go);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void outputDatabaseTest() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
