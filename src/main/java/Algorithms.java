import javafx.util.Pair;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class Algorithms {
    /**
     * First fit decreasing algorithm for bin packing problem.
     * Every single bin will be assign to vehicle.
     * The bin contains a list of good, every good is placed
     * in the bin just to get the best possible packaging.
     *
     * @param goods     the goods will be fit into the bins
     * @param volumeMax the Maximal volume that can be transported
     * @return List of Bin, null if empty or the volumeMax isn't enough.
     */
    public static List<Bin> firstFitDecreasing(List<Good> goods, double volumeMax) {
        if (goods.isEmpty()) {
            return null;
        }
        goods.sort(Collections.reverseOrder(Good.comparator())); //decreasing order
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(volumeMax)); //add first bin
        for (Good good : goods) {
            for (int j = 0; j < good.getQnt(); j++) {
                bins = insertGood(new Good(good.getId(), good.getVolume(), 1, good.getDescription()), bins);
                if (bins == null) {
                    System.err.println("[Error] Something doing wrong");
                }
            }
        }
        return bins;
    }

    /**
     * the method below insert a good in the bins list.
     *
     * @param good the good that will be added to the list
     * @param bins the list that is required for adding of a new good, at least one empty bin
     *             (but with maximal Volume assigned) inside of a list is required.
     * @return List of Bin, null if empty or the volumeMax isn't enough.
     */
    public static List<Bin> insertGood(Good good, List<Bin> bins) {
        if (bins.isEmpty()) {
            return null;
        }
        double volumeMax = bins.get(0).getVolumeMax();
        boolean added = false;
        for (Bin bin : bins) {
            if (bin.addGood(good)) {
                added = true;
                break;
            }
        }
        if (!added) {
            Bin bin = new Bin(volumeMax);
            if (!bin.addGood(good)) {
                System.err.println("[Error] the maximal Volume of bins must be greater " +
                        "than the maximum volume of a good");
                return null;
            }
            bins.add(bin);
        }
        return bins;
    }

    public static Pair<List<Client>, AdjacencyList> generateRndGraph(int nodeMax, int edgeMax, double distanceMax) throws Exception {
        AdjacencyList g = new AdjacencyList();
        List<Client> clients = new ArrayList<>(nodeMax);

        if (!(nodeMax > 0 && edgeMax > 0 && distanceMax > 0)) throw new IllegalArgumentException();

        for (int source = 0; source < nodeMax; source++) {
            g.addNode(source);
            clients.add(source, new Client(source, "Client " + String.valueOf(source), null));
            int rndOutEdge = 1 + (int) (Math.random() * edgeMax / 2);
            int rndInEdge = 1 + (int) (Math.random() * edgeMax / 2);
            for (int j = 0; j < rndOutEdge; j++) {
                int destination = (int) (Math.random() * nodeMax);
                double distance = Math.round((1 + Math.random() * distanceMax) * 100) / 100;
                if (source != destination)
                    g.addEdge(source, destination, distance);
                else if (j == 0)
                    j--;
            }
            for (int j = 0; j < rndInEdge; j++) {
                int destination = (int) (Math.random() * nodeMax);
                double distance = Math.round((1 + Math.random() * distanceMax) * 100) / 100;
                if (source != destination)
                    g.addEdge(destination, source, distance);
                else if (j == 0)
                    j--;
            }
        }
        return new Pair<>(clients, g);
    }

    public static void generateRndCharge(List<Client> clients, int minCharge) throws Exception {
        for (Client c : clients) {
            Random rnd = new Random();
            boolean getCharge = rnd.nextBoolean();
            int rndValue = (getCharge) ? (minCharge + (int) Math.round(Math.random() * minCharge)) : 0;
            c.setCharge(rndValue);
        }
    }

    public static List<Order> generateOrders(List<Client> clients, int maxNumOrder) throws Exception {
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i < clients.size(); i++) {
            int numOrder = (int) Math.round(Math.random() * maxNumOrder);
            for (int j = 0; j < numOrder; j++)
                orders.add(new Order(clients.get(i), new Date()));
        }
        return orders;
    }

    public static List<Good> generateGoods(int n, int maxQnt, double maxVolume) throws Exception {
        List<Good> goods = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int qnt = 1 + (int) Math.round(Math.random() * maxQnt);
            int volume = 1 + (int) Math.round(Math.random() * maxVolume);
            goods.add(i, new Good(volume, qnt, "Good " + String.valueOf(i)));
        }
        return goods;
    }

    public static List<Bin> generateBins(int n, double volumeMax) throws Exception {
        List<Bin> bins = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            bins.add(i, new Bin(volumeMax));
        return bins;
    }

    public static List<Vehicle> generateVehicle(double minChargeMax, List<Bin> bins) throws Exception {
        List<Vehicle> vehicles = new ArrayList<>(bins.size());
        String number_plate;
        double chargeMax;
        for (int i = 0; i < bins.size(); i++) {
            Random rnd = new Random();
            number_plate = String.valueOf(48 + rnd.nextInt(47));
            number_plate += String.valueOf(48 + rnd.nextInt(47));
            number_plate += String.valueOf(rnd.nextInt(999));
            number_plate += String.valueOf(48 + rnd.nextInt(47));
            number_plate += String.valueOf(48 + rnd.nextInt(47));
            chargeMax = minChargeMax + Math.round(Math.random() * minChargeMax);
            vehicles.add(i, new Vehicle(number_plate, chargeMax, bins.get(i)));
        }
        return vehicles;
    }

    public static List<GoodOrder> generateGoodOrder(int maxQnt, List<Order> orders, List<Good> goods) throws Exception {
        int n = orders.size();
        int m = goods.size();
        int count = 0;
        List<GoodOrder> goodOrders = new ArrayList<>(n);
        for (Order order : orders) {
            Random rnd = new Random();
            int k = rnd.nextInt(m);
            for (int j = 0; j < k; j++) {
                int indexGood = rnd.nextInt(m);
                int qnt = rnd.nextInt(maxQnt);
                goodOrders.add(count, new GoodOrder(order, goods.get(indexGood), qnt));
            }
        }
        return goodOrders;
    }

    /**
     * get the list of saving from the source.
     * source = 0.
     *
     * @param dm
     */
    public static List<Pair<Pair<Integer, Integer>, Double>> getSavings(DistanceMatrix dm) {
        List<Pair<Pair<Integer, Integer>, Double>> savingList = new ArrayList<>();
        for (Map.Entry<Pair<Integer, Integer>, Double> elem : dm.get().entrySet()) {
            if (!(elem.getKey().getKey() == 0 || elem.getKey().getValue() == 0)) {
                savingList.add(new Pair<>(new Pair<>(elem.getKey().getKey(), elem.getKey().getValue()),
                                dm.get(elem.getKey().getKey(), 0) +
                                        dm.get(0, elem.getKey().getValue()) -
                                        dm.get(elem.getKey().getKey(), elem.getKey().getValue()))
                );
            }
        }
        return savingList;
    }

    public static Pair<Pair<Integer, Integer>, Double> getSaving
            (List<Pair<Pair<Integer, Integer>, Double>> list,
             Pair<Integer, Integer> saving)
    {
        for (Pair<Pair<Integer, Integer>, Double> s : list){
            if (s.getKey().getKey().equals(saving.getKey()) &&
                s.getKey().getValue().equals(saving.getValue())){
                return s;
            }
        }
        return new Pair<>(new Pair<>(-1,-1), (double)-1);
    }

    public static Pair<Pair<Integer, Integer>, Double> getReverseSaving
            (List<Pair<Pair<Integer, Integer>, Double>> list,
             Pair<Integer, Integer> saving)
    {
        return getSaving(list, new Pair<>(saving.getValue(),saving.getKey()));
    }

    public static Comparator<Pair<Pair<Integer, Integer>, Double>> savingComparator() {
        return (o1, o2) -> {
            if (o1.getValue() < o2.getValue())
                return -1;
            if (o1.getValue() > o2.getValue())
                return 1;
            return 0;
        };
    }

    public static List<AdjacencyList> clarkAndWright(AdjacencyList graph, Database db) throws SQLException {
        List<AdjacencyList> ret = new ArrayList<>();
        DistanceMatrix dm = new DistanceMatrix(graph);
        List<Pair<Pair<Integer, Integer>, Double>> saving = getSavings(dm);
        saving.sort(Collections.reverseOrder(Algorithms.savingComparator()));

        int i=0;
        if (db.getAllOrders().isEmpty()) {
            System.out.println("get all orders is empty");
        }else{
            while (!saving.isEmpty()) {
                System.out.println("entro nel while");
                AdjacencyList adj = new AdjacencyList();
                Vehicle v = new Vehicle();
                for (Pair<Pair<Integer, Integer>, Double> s : saving) {
                    List<Order> oiList = db.getOrderByClient(s.getKey().getKey());
                    List<Order> ojList = db.getOrderByClient(s.getKey().getValue());
                    Pair<Order, Order> order = null;
                    if (!oiList.isEmpty() && !ojList.isEmpty()) {
                        Order oi = oiList.get(0);
                        Order oj = ojList.get(0);
                        order = new Pair<>(oi, oj);
                    }
                    boolean canBeJoined = adj.canBeJoin(s, v, order, graph, db);
                    if (canBeJoined) {
                        adj.addSaving(graph, s);
                        saving.remove(s);
                        Pair<Pair<Integer, Integer>, Double> reverseS =
                                getReverseSaving(saving,
                                        new Pair<>(s.getKey().getValue(), s.getKey().getKey()));
                        if (!reverseS.equals(new Pair<>(new Pair<>(-1, -1), (double) -1)))
                            saving.remove(reverseS);
                    }
                    System.out.println("ca be joined " + ++i + " " + canBeJoined);
                }
                if (!adj.getGraph().isEmpty())
                    ret.add(adj);
                else
                    break;
            }
        }

        return ret;
    }
}