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
        return g.size();
    }

    /**
     * @return return the Graph
     */
    public HashMap<Integer,List<Pair<Integer,Double>>> getGraph(){
        return g;
    }

    /**
     * Add an edge from source to destination with weight equals to distance
     * @param source the node from which the edge starts
     * @param destination the other end of the edge
     * @param distance weight
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
     * Add to the node source the neighbours indicated in adj
     * @param source node to which the adj neighbours will be added
     * @param adj neighbours
     */
    public void addEdge(Integer source, List<Pair<Integer, Double>> adj) {
        for (Pair<Integer, Double> p : adj) {
            if (!g.containsKey(p.getKey()))
                addNode(p.getKey());
        }

        if (g.containsKey(source)) {
            for (Pair<Integer, Double> p : g.get(source)) {
                if (!adj.contains(p))
                    adj.add(p);
            }
        }

        g.put(source, adj);
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
        if (g.containsKey(source) &&
                g.containsKey(destination) && index!=-1) {
            g.get(source).remove(index);
        }
    }
    /**
     * Returns the index of the neighbour
     * @param source
     * @param destination
     * @return index of neighbor or -1 otherwise
     */
    public int getIndexNeighbor(Integer source, Integer destination) {
        int index = -1;
        int n = this.getNumNeighbor(source);
        for(int i = 0; i < n; i ++){
            if(Objects.equals(destination, this.getIdNeighbor(source, i))){
                index = i;
            }
        }
        return index;
    }

    /**
     * Returns the graph's nodes
     * @return graph's nodes
     */
    public Set<Integer> nodes() {
        return g.keySet();
    }

    /**
     * Add to the node source the neighbours indicated in ns
     * @param source node to which the ns neighbours will be added
     * @param ns source's neighbour
     */
    public void addNeighbors(Integer source, List<Pair<Integer, Double>> ns) {
        if (ns != null) {
            if (g.containsKey(source))
                g.remove(source);
            g.put(source, ns);
        }
    }

    /**
     * Gets the neighbours of a given node
     * @param v node from which the neighbours are returned
     * @return node's neighbours
     */
    public List<Pair<Integer, Double>> getNeighborsDistances(Integer v) {
        return g.containsKey(v) ? g.get(v) : null;
    }

    /**
     * Gets the neighbours of a given node
     * @param v node from which the neighbours are returned
     * @return node's neighbours
     */
    public List<Integer> getNeighbor(Integer v) {
        if (!g.containsKey(v))
            return null;

        return g.get(v).stream().map(Pair::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Gets the neighbours of a given node
     * @param v node from which the neighbours are returned
     * @return node's neighbours
     */
    public List<Pair<Integer,Double>> getPairNeighbors(Integer v) {
        if (!g.containsKey(v))
            return null;
        return g.get(v);
    }

    /**
     * The index-th neighbour of the given node
     * @param v the input node
     * @param index of a neighbor list.
     * @return index-th neighbour of v
     */
    public Double getDistance(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        return nodep.getValue();
    }

    /**
     * Gets the neighbours of a given node
     * @param v node from which the neighbours are returned
     * @return node's neighbours
     */
    public Integer getIdNeighbor(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        return nodep.getKey();
    }
    public List<Pair<Integer, Double>> getNeighbors(Integer source) {
        return g.get(source);
    }

    /**
     * Neighbour's numbers of v
     * @param v input vertex
     * @return numbers of neighbor form the input node.
     */
    public int getNumNeighbor(Integer v){
        List<Pair<Integer, Double>> neighbor = g.get(v);
        if(neighbor.isEmpty()){
            return 0;
        }
        return neighbor.size();
    }

    /**
     * Inverts the edge weight
     */
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

    /**
     * Sum the edges of (path[i-1], path[i])
     * following the minimum graph evaluated
     * by dijkstra
     * @param path
     * @return
     */
    public Double sumEdges(List<Integer> path) {
        Double retVal = 0.0;
        for (int i = 1; i < path.size(); i++) {
            retVal += getDistance(path.get(i - 1), path.get(i));
        }

        return retVal;
    }

    /**
     * Returns the maximum distance between all pairs
     * of nodes in the dijkstra graph.
     * @return the maximum distance between all pairs
     * of nodes in the dijkstra graph.
     */
    public Pair<Pair<Integer, Integer>, Double> getMaxDistance() {
        return new DistanceMatrix(this).max();
    }

    /**
     * Removes the edge (node, neighbour)
     * @param node the node from which
     *             the search of the neighbour will start
     * @param neighbour node's neighbour to remove
     */
    public void removeEdge(Integer node, Integer neighbour) {
        for (Iterator<Pair<Integer, Double>> it = g.get(node).iterator();
             it.hasNext();) {
            Pair<Integer, Double> p = it.next();
            if ( p.getKey() == neighbour) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Removes the given node from the graph
     * @param node node to remove
     */
    public void removeNode(Integer node) {
        g.remove(node);
        for (Integer n : g.keySet())
            removeEdge(n, node);
    }

    /**
     * List of nodes to remove
     * @param nodes nodes to remove
     */
    public void removeNodes(List<Integer> nodes) {
        nodes.forEach(this::removeNode);
    }

    /**
     * Merge to graphs
     * @param a graph that will be merged with b
     * @param b graph that will be merged in a
     * @return the merged graphs between a and b
     */
    public static AdjacencyList mergeAdjacencyList
            (AdjacencyList a, AdjacencyList b) {
        if (a == null)
            return b;
        if (b == null)
            return a;
        AdjacencyList ret = new AdjacencyList(a.get());
        for (Integer n : b.getNodes())
            ret.addEdge(n, b.getNeighbors(n));

        return ret;
    }

    /**
     * Nodes that connects source and destination in
     * dijkstra graph
     * @param source source node
     * @param destination destination node
     * @param adj the minimum dijkstra graph
     * @return list of nodes that connects source and
     * destination
     */
    public List<Integer> nodesToDestination
            (Integer source, Integer destination, AdjacencyList adj) {
        List<Integer> retNodes = new LinkedList<>();

        while (destination != source) {
            boolean found = false;
            for (Integer n : adj.getNodes()) {
                if (found)
                    break;
                for (Integer ad : adj.getNeighbor(n)) {
                    if (ad == destination) {
                        retNodes.add(0, ad);
                        destination = n;
                        found = true;
                        break;
                    }
                }
            }
        }

        if (retNodes.isEmpty())
            return null;

        retNodes.add(0, source);
        return retNodes;
    }

    /**
     * Return the graph that connects two node
     * @param source source node
     * @param destination destination node
     * @return graph that connects source and destination
     */
    public AdjacencyList getMinGraphFromSource(Integer source, Integer destination) {
        Pair<HashMap<Integer, Double>, AdjacencyList> retDijkstra =
                dijkstra(source);

        AdjacencyList ret = retDijkstra.getValue();
        List<Integer> nts = nodesToDestination
                (source, destination, retDijkstra.getValue());
        if (nts == null)
            return null;
        List<Integer> unwantedNodes = new LinkedList<>();
        unwantedNodes.addAll(ret.getNodes());
        unwantedNodes.removeAll(nts);

        ret.removeNodes(unwantedNodes);

        return ret;
    }

    /**
     * Evaluates the total orders' weight of the given clients
     * @param db database
     * @param clientsInvolved the clients involved
     * @return (goods involved, total weight)
     * @throws SQLException
     */
    public Pair<List<Good>, Double> evaluateGoodsVolumeForBin
            (Database db,  List<Integer> clientsInvolved) throws SQLException {
        List<Good> goods = new LinkedList<>();
        Double cap = .0;
        for (Integer client : clientsInvolved) {
            for (Order o : db.getOrderByClient(client)) {
                for (Good g : db.getGoodByOrder(o)) {
                    cap += g.getVolume();
                    g.setQnt(1);
                    goods.add(g);
                }
            }
        }

        return new Pair<>(goods, cap);
    }

    /**
     * Evaluates where in a given path the vehicle
     * should charge
     * @param subsetNodes the nodes from which the
     *                    vehicle can charge
     * @param charge the total charge of the given
     *               vehicle
     * @param db database
     * @return list of nodes where the vehicle should charge
     * @throws SQLException
     */
    public List<Integer> evaluateChargeNodes
            (List<Integer> subsetNodes, double charge, Database db) throws SQLException {
        Integer prev = -1;
        List<Integer> retValue = new LinkedList<>();

        for (Integer node : subsetNodes) {
            Client c = db.getClientByID(node);
            if (c.getCharge() > 0) {
                if (charge < c.getCharge()) {
                    retValue.add(prev);
                    prev = -1;
                }
                else {
                    charge -= c.getCharge();
                    prev = node;
                }
            }
        }

        return retValue;
    }

    /**
     * Clark and Wright's sequential implementation
     * @param db database
     * @param zero warehouse
     * @param bins bins that describes the vehicles' capacity
     * @return for every utilized bin its graph
     */
    public HashMap<Bin, AdjacencyList> clark_wright
            (Database db, Integer zero, List<Bin> bins) {
        HashMap<Bin, AdjacencyList> ret = new HashMap<>();
        DistanceMatrix matDistance = new DistanceMatrix(this);
        HashMap<Pair<Integer, Integer>, Double> savings = new HashMap<>();
        // initializing savings
        for (Integer i : g.keySet()) {
            for (Integer j : g.keySet()) {
                if (matDistance.contains(i, j) &&
                        matDistance.contains(zero, i) &&
                        matDistance.contains(zero, j)) {
                    savings.put(new Pair<>(i,j),
                            matDistance.get(zero, i) +
                                    matDistance.get(zero, j) -
                                    matDistance.get(i, j));
                }
            }
        }

        List<Pair<Integer, Integer>> orderedSavingsKey =
                orderByValue((HashMap<Pair<Integer,Integer>, Double>)
                        savings.clone());

        // Merge route between nodes
        int ib = 0;
        try {
            for (boolean decreased = true;
                 decreased && ib < bins.size() && !orderedSavingsKey.isEmpty();
                 ib++) {

                decreased = false;

                List<Integer> l = new LinkedList<>();
                l.add(0);

                Pair<Integer, Integer> fp = orderedSavingsKey.get(0);
                l.add(fp.getKey());
                l.add(fp.getValue());
                bins
                        .get(ib)
                        .addGood(evaluateGoodsVolumeForBin(
                                db,
                                l.subList(l.size() -2, l.size())).getKey());

                orderedSavingsKey.remove(fp);
                orderedSavingsKey.remove(0);

                Pair<Integer, Integer> or =
                        new Pair<>(l.get(l.size() - 2), l.get(l.size() - 1));
                Pair<Integer, Integer> pp =
                        new Pair<>(l.get(l.size() - 1), l.get(l.size() - 2));

                for (Iterator<Pair<Integer, Integer>> it = orderedSavingsKey.iterator();
                     it.hasNext();) {
                    boolean skip = false;
                    boolean evaluate = false;
                    boolean swap = false;
                    // if clients involved have goods to be transported
                    // for which the sum of the goods is <= than the
                    // capacity of the vehicle, then merge the two routes
                    // and decrease the number of vehicles used
                    // and set decrease to true
                    Pair<Integer, Integer> p = it.next();
                    // Checks whether the current saving is consecutive
                    // to the last one inserted in the bin AND
                    if (p.getKey().equals(l.get(l.size() - 1))) {
                        if (!l.contains(p.getValue())) {
                            evaluate = true;
                        }
                        if (l.contains(p.getValue()))
                            skip = true;
                    }
                    else {
                        if (!skip && savings.keySet().contains(pp) &&
                                savings.get(or).equals(savings.get(pp))) {
                            if (p.getKey().equals(pp.getValue())) {
                                evaluate = true;
                                swap = true;
                            }
                        }
                    }
                    if (evaluate) {
                        List<Integer> clientsInvolved =
                                new LinkedList<Integer>();
                        //clientsInvolved.add(p.getKey());
                        clientsInvolved.add(p.getValue());
                        Pair<List<Good>, Double> ev =
                                evaluateGoodsVolumeForBin(db, clientsInvolved);
                        Double cap = ev.getValue();
                        List<Good> goods = ev.getKey();
                        // Merge routes
                        if (bins.get(ib).getVolumeWasted() >= cap) {
                            if (swap) {
                                Integer last = l.get(l.size() - 1);
                                Integer preLast = l.get(l.size() - 2);
                                l.set(l.size() - 2, last);
                                l.set(l.size() - 1, preLast);
                            }
                            l.add(p.getValue());
                            bins.get(ib).addGood(goods);
                            it.remove();
                            decreased = true;
                        }
                    }
                }

                // Block that associates Nodes' list to Bin
                AdjacencyList adj = new AdjacencyList();
                l.add(0);
                // Build the adjacency list correlated to the bin
                for (int i = 1; i < l.size(); i++)
                    adj = AdjacencyList.mergeAdjacencyList(
                            adj, getMinGraphFromSource(l.get(i-1), l.get(i)));

                // Clean already used savings
                l.remove(0);
                l.remove(l.size() - 1);

                for (Integer in : l) {
                    for (Iterator<Pair<Integer, Integer>> it = orderedSavingsKey.iterator();
                            it.hasNext();) {
                        Pair<Integer, Integer> p = it.next();
                        if (p.getKey().equals(in) ||
                                p.getValue().equals(in)) {
                            it.remove();
                        }
                    }
                }

                // Associates adjacency list to bin
                ret.put(bins.get(ib), adj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Order the savings' key by value in descending order
     * @param h savings
     * @return ordered keys
     */
    public static List<Pair<Integer, Integer>> orderByValue
            (HashMap<Pair<Integer, Integer>, Double> h) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        Pair<Integer, Integer> kMax = new Pair<>(0, 0);
        int n = h.keySet().size();
        for (int i = 0; i < n; i++) {
            Double max = Double.MIN_VALUE;
            for (Pair<Integer, Integer> p : h.keySet()) {
                if (max < h.get(p)) {
                    max = h.get(p);
                    kMax = p;
                }
            }
            result.add(kMax);
            h.remove(kMax);
        }

        return result;
    }

    /**
     * Return the graph.
     * @return graph
     */
    public HashMap<Integer, List<Pair<Integer, Double>>> get() {
        return g;
    }

    /**
     * Set the graph
     * @param g graph
     */
    public void set(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }

    /**
     * Return the graph's vertex
     * @return graph's vertex
     */
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
     * @return the graph with the shortest path form the source node
     *         to each other nodes.
     */
    public Pair<HashMap<Integer, Double>, AdjacencyList> dijkstra(Integer source) {
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
            
            // if (!this.g.get(i).equals(that.getGraph().get(i))))
            // return true if & only if
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
