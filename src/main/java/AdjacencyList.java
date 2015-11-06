import javafx.util.Pair;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyList {
    private HashMap<Integer, List<Pair<Integer, Double>>> g;

    public AdjacencyList() {
        g = new HashMap<>();
    }
    public AdjacencyList(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }
    public void setGraph(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }

    /**
     * @return the numbers of nodes in the Graph (the size of the graph).
     */
    public int getNumNodes(){
        int n = g.size();
        return n;
    }

    /**
     * @return return the Graph
     */
    public HashMap<Integer,List<Pair<Integer,Double>>> getGraph(){
        return g;
    }

    /** @param source the value of node
     * @param destination node
     * @param distance the weight
     */
    public void addEdge(Integer source, Integer destination, Double distance) {
        List<Pair<Integer, Double>> nodes = g.containsKey(source) ?
                g.get(source) : new ArrayList<>();
        for (Pair<Integer,Double> node: nodes)
            if (node.getKey().equals(destination))
                return;
        nodes.add(new Pair<>(destination, distance));
        g.put(source, nodes);
        if (!g.containsKey(destination))
            addNode(destination);
    }

    /**
     * Add new node with an empty List.
     * @param source the value of the node
     */
    public boolean addNode(Integer source){
        if (!g.containsKey(source)) {
            g.put(source, new ArrayList<>());
            return true;
        }
        return false;
    }

    /**
     * Delete an edge
     * @param source
     * @param destination
     */
    public void deleteEdge(Integer source, Integer destination){
        int index = this.getIndexNeighbor(source, destination);
        boolean empty= false;
        if (g.containsKey(source) && g.containsKey(destination) && index!=-1) {
            g.get(source).remove(index);
        }
    }

    /**
     *
     * @param source
     * @param destination
     * @return index of neighbor
     */
    public int getIndexNeighbor(Integer source, Integer destination) {
        int index = -1;
        int n = this.getNumNeighbor(source);
        for(int i = 0; i < n; i ++){
            if(destination==this.getIdNeighbor(source, i)){
                index = i;
            }
        }
        return index;
    }

    public Set<Integer> nodes() {
        return g.keySet();
    }

    public void addNeighbors(Integer source, List<Pair<Integer, Double>> ns) {
        if (ns != null) {
            if (g.containsKey(source))
                g.remove(source);
            g.put(source, ns);
        }
    }

    public List<Pair<Integer, Double>> getNeighborsDistances(Integer v) {
        return g.containsKey(v) ? g.get(v) : null;
    }

    public List<Integer> getNeighbor(Integer v) {
        if (!g.containsKey(v))
            return null;

        return g.get(v).stream().map(Pair::getKey).collect(Collectors.toList());
    }


    public List<Pair<Integer,Double>> getPairNeighbors(Integer v) {
        if (!g.containsKey(v))
            return null;
        return g.get(v);
    }
    /**
     * @param v the input node
     * @param index of a neighbor list.
     * @return Distance in the "index" position from the input node.
     */
    public Double getDistance(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        Double distance = nodep.getValue();
        return distance;
    }
    /**
     * @param v the input node
     * @param index of a neighbor list that will know the ID
     * @return ID in the position "index" from the node.
     */
    public Integer getIdNeighbor(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        Integer id = nodep.getKey();
        return id;
    }
    /**
     * @param v input node
     * @return numbers of neighbor form the input node.
     */
    public int getNumNeighbor(Integer v){
        List<Pair<Integer, Double>> neighbor = g.get(v);
        if(neighbor.isEmpty()){
            return 0;
        }
        return neighbor.size();
    }

    public void reverseEdgeWeight() {
        Double wMax = 0.0;
        for (Integer c : g.keySet()) {
            for (Pair<Integer, Double> p : g.get(c)) {
                if (p.getValue() > wMax)
                    wMax = p.getValue();
            }
        }

        wMax += 1.0;

        for (Integer c : g.keySet()) {
            List<Pair<Integer, Double>> l = new ArrayList<>();
            for (Pair<Integer, Double> p : g.get(c)) {
                 l.add(new Pair<>(p.getKey(), wMax - p.getValue()));
            }
            g.put(c, l);
        }
    }

    public double getMaxDistance(){
        double maxWeight = 0;
        for (Integer node:nodes()) {
            Pair<HashMap<Integer, Double>, AdjacencyList> ret = dijkstra(node);
            HashMap<Integer,Double> listWeight = ret.getKey();
            for (Integer n: listWeight.keySet()){
                maxWeight = (listWeight.get(n)> maxWeight) ? listWeight.get(n) : maxWeight ;
            }
        }
        return maxWeight;
    }

    public HashMap<Bin, AdjacencyList> clark_wright
            (Database db, Integer vehicles, Integer zero, List<Bin> bins) {
        HashMap<Bin, AdjacencyList> ret = new HashMap<>();
        DistanceMatrix matDistance = new DistanceMatrix(this);
        HashMap<Pair<Integer, Integer>, Double> savings = new HashMap<>();
        // initializing savings
        for (Integer i : g.keySet()) {
            for (Integer j : g.keySet()) {
                if (!Objects.equals(i, j))
                savings.put(new Pair<>(i, j),
                        matDistance.get(zero, i) +
                                matDistance.get(zero, j) -
                                matDistance.get(i,j));
            }
        }

        List<Pair<Integer, Integer>> orderedSavingsKey = orderByValue(savings);

        // Merge route between nodes
        boolean decreased = true;
        Integer ib = 0;
        try {
            for (; decreased; ib++) {
                decreased = false;
                List<Integer> l = new LinkedList<>();
                Pair<Integer, Integer> fp = orderedSavingsKey.get(0);
                l.add(0);
                l.add(fp.getKey());
                l.add(fp.getValue());
                orderedSavingsKey.remove(fp);
                for (Pair<Integer, Integer> p : orderedSavingsKey) {
                    // if clients involved have goods to be transported
                    // for which the sum of the goods is <= than the
                    // capacity of the vehicle, then merge the two routes
                    // and decrease the number of vehicles used
                    // and set decrease to true

                    if (p.getKey().equals(l.get(l.size() - 1))) {
                        List<Integer> clientsInvolved = new LinkedList<Integer>();
                        List<Good> goods = new LinkedList<>();
                        clientsInvolved.add(p.getKey());
                        clientsInvolved.add(p.getValue());
                        Double cap = .0;
                        for (Integer client : clientsInvolved) {
                            for (Order o : db.getOrderByClient(client)) {
                                for (Good g : db.getGoodByOrder(o)) {
                                    cap += g.getVolume();
                                    goods.add(g);
                                }
                            }
                        }

                        // Merge routes
                        if (bins.get(ib).getVolumeWasted() >= cap) {
                            l.add(p.getValue());
                            bins.get(ib).addGood(goods);
                        }

                        orderedSavingsKey.remove(p);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<Pair<Integer, Integer>> orderByValue(HashMap<Pair<Integer, Integer>, Double> h) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        Pair<Integer, Integer> kMax = new Pair<>(0, 0);
        for (int i = 0; i < h.keySet().size(); i++) {
            Double max = Double.MIN_VALUE;
            for (Pair<Integer, Integer> p : h.keySet()) {
                if (max > h.get(p)) {
                    max = h.get(p);
                    kMax = p;
                }
            }
            result.add(kMax);
            h.remove(kMax);
        }

        return result;
    }

    public HashMap<Integer, List<Pair<Integer, Double>>> get() {
        return g;
    }

    public void set(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }

    public Set<Integer> getNodes(){
        return g.keySet();
    }

    /**
     * Dijkstra run with a queue Q.
     * The queue as initialize only with Source node.
     * At the first the graph is empty.
     * The algorithm take the last input node from the queue and
     * update the value distance with previous distance if there was.
     * The node is updated if the sum of the previous node plus the
     * the weight of the edge is less of the current distance value in the node.
     * @param source the node where Dijkstra start
     * @return the graph with the shortest path form the node source to each other nodes.
     */
    public Pair<HashMap<Integer, Double>, AdjacencyList> dijkstra(Integer source){
        AdjacencyList pg = new AdjacencyList();
        int n = this.getNumNodes();
        Integer u;
        Double [] dist = new Double[n];
        Integer [] prev = new Integer[n];

        for(int i = 0; i < n; i++){
            dist[i] = -1.0;
            prev[i] = -1;
        }

        dist[source] = 0.0;
        List<Integer> Q = new ArrayList<>();
        Q.add(source);
        while (!Q.isEmpty()){
            u = Q.get(0);                     // takes first element on list
            Q.remove(0);                        // removes first element

            if(dist[u] != -1) {
                for (int i = 0; i < this.getNumNeighbor(u); i++) {
                    Double alt = dist[u] + getDistance(u, i);
                    Integer nId = getIdNeighbor(u, i);
                    if ((dist[nId] == -1) || (alt < dist[nId])) {
                        dist[nId] = alt;
                        prev[nId] = u;
                        Q.add(nId);
                    }
                }
            }
        }

        HashMap<Integer, Double> ret = new HashMap<>();

        // graph's creation
        for(int i = 0; i<n; i++){
            if((prev[i] != -1)){
                pg.addEdge(prev[i],i,dist[i] - dist[prev[i]]);
                ret.put(i, dist[i]);
            }
        }

        return (new Pair<>(ret, pg));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdjacencyList)) return false;

        AdjacencyList that = (AdjacencyList) o;
        if(that.getNodes().size() != this.getNodes().size())
            return false;

        for (Integer i : that.getGraph().keySet()) {
            if (!this.g.containsKey(i))
                return false;
            this.g.get(i).sort(AdjacencyList.comparator());
            that.getGraph().get(i).sort(AdjacencyList.comparator());
            
            // if (!this.g.get(i).equals(that.getGraph().get(i))))return true if & only if
            // Pair in the list are in the same order
            if (!this.g.get(i).equals(that.getGraph().get(i)))
                return false;
            }
        return true;
    }

    @Override
    public int hashCode() {
        return g.hashCode();
    }

    /**
     * create and implement a new comparator for the node inside the list
     * @return comparator of 2 Pair
     */
    public static Comparator<Pair<Integer,Double>> comparator(){
        return (o1, o2) -> {
            if (o1.getKey() < o2.getValue())
                return -1;
            if (o1.getKey() > o2.getKey())
                return 1;
            return 0;
        };
    }
}
