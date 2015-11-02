import java.util.*;

public class Algorithms{
    /**
     * First fit decreasing algorithm for bin packing problem.
     * Every single bin will be assign to vehicle.
     * The bin contains a list of good, every good is placed
     * in the bin just to get the best possible packaging.
     * @param goods the goods will be fit into the bins
     * @param volumeMax the Maximal volume that can be transported
     * @return List of Bin, null if empty or the volumeMax isn't enough.
     */
    public static List<Bin> firstFitDecreasing(List<Good> goods, double volumeMax) {
        if (goods.isEmpty()){
            return null;
        }
        goods.sort(Collections.reverseOrder(Good.comparator())); //decreasing order
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(volumeMax)); //add first bin
        for(Good good : goods){
            for (int j=0; j<good.getQnt();j++) {
                bins = insertGood(new Good(good.getId(), good.getVolume(), 1, good.getDescription()), bins);
                if (bins == null){
                    System.err.println("[Error] Something doing wrong");
                }
            }
        }
        return bins;
    }
    /**
     * the method below insert a good in the bins list.
     * @param good the good that will be added to the list
     * @param bins the list that is required for adding of a new good, at least one empty bin
     * (but with maximal Volume assigned) inside of a list is required.
     * @return List of Bin, null if empty or the volumeMax isn't enough.
     */
    public static List<Bin> insertGood(Good good, List<Bin> bins){
        if (bins.isEmpty()){
            return null;
        }
        double volumeMax = bins.get(0).getVolumeMax();
        boolean added = false;
        for (Bin bin:bins){
            if (bin.addGood(good)){
                added = true;
                break;
            }
        }
        if (!added){
            Bin bin = new Bin(volumeMax);
            if (!bin.addGood(good)){
                System.err.println("[Error] the maximal Volume of bins must be greater " +
                        "than the maximum volume of a good");
                return null;
            }
            bins.add(bin);
        }
        return bins;
    }

    public static AdjacencyList generateRndGraph(int nodeMax, int edgeMax, double distanceMax){
        AdjacencyList g = new AdjacencyList();
        if(nodeMax>0 && edgeMax > 0 && distanceMax > 0)
        for (int source=0; source < nodeMax; source++) {
            g.addNode(source);
            int rndEdge = 1 + (int)(Math.random()*edgeMax);
            for (int j=0; j < rndEdge; j++){
                int destination = (int)(Math.random()*nodeMax);
                double distance = Math.round(Math.random()*distanceMax);
                if (source != destination)
                    g.addEdge(source, destination, distance);
            }
        }
        return g;
    }

    public static List<Order> generateOrders(List<Client> clients, List<Bin> bins){
        List<Order> orders = new ArrayList<>();
        if (clients.size() > bins.size())
            for (int i=0; i<bins.size(); i++)
                orders.add(i,new Order(clients.get(i),new Date(),bins.get(i)));
        else
            for (int i=0; i<clients.size(); i++)
                orders.add(i,new Order(clients.get(i),new Date(),bins.get(i)));
        return orders;
    }
    public static List<Client> generateClients(int n, int minCharge){
        List<Client> clients = new ArrayList<>(n);
        for (int i=0; i<n; i++){
            Random rnd = new Random();
            boolean getCharge = rnd.nextBoolean();
            int rndValue = (getCharge) ? ( minCharge + (int)Math.round(Math.random()*minCharge) ) : 0;
            clients.add(i, new Client("Client "+String.valueOf(i), rndValue));
        }
        return clients;
    }
    public static List<Good> generateGoods(int n, int maxQnt, double maxVolume){
        List<Good> goods = new ArrayList<>(n);
        for (int i=0; i<n; i++) {
            int qnt = 1+ (int)Math.round(Math.random()*maxQnt);
            int volume = 1+ (int)Math.round(Math.random()*maxVolume);
            goods.add(i, new Good(volume,qnt,"Good "+String.valueOf(i)));
        }
        return goods;
    }
    public static List<Bin> generateBins(int n, double volumeMax){
        List<Bin> bins = new ArrayList<>(n);
        for (int i=0; i<n; i++)
            bins.add(i,new Bin(volumeMax));
        return bins;
    }
    public static List<Vehicle> generateVheicle(double minChargeMax, List<Bin> bins){
        List<Vehicle> vehicles = new ArrayList<>(bins.size());
        String number_plate;
        double chargeMax;
        for (int i=0; i<bins.size(); i++) {
            Random rnd = new Random();
            number_plate = String.valueOf(48+rnd.nextInt(47));
            number_plate += String.valueOf(48+rnd.nextInt(47));
            number_plate += String.valueOf(rnd.nextInt(999));
            number_plate += String.valueOf(48+rnd.nextInt(47));
            number_plate += String.valueOf(48+rnd.nextInt(47));
            chargeMax = minChargeMax + Math.round(Math.random()*minChargeMax);
            vehicles.add(i,new Vehicle(number_plate,chargeMax,bins.get(i)));
        }
        return vehicles;
    }


}