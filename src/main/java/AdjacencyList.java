import javafx.util.Pair;
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
        List<Pair<Integer, Double>> n = g.containsKey(source) ?
                g.get(source) : new ArrayList<>();
        n.add(new Pair<>(destination, distance));
        g.put(source, n);
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

    public HashMap<Integer, List<Pair<Integer, Double>>> clark_wright
            (Integer vehicles) {
        return null;
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
     * the wight of the edge is less of the current distance value in the node.
     * @param source the node where Dijkstra start
     * @return the graph with the shortest path form the node source to each other nodes.
     */
    public HashMap<Integer, List<Pair<Integer, Double>>> Dijkstra(Integer source){
        AdjacencyList pg = new AdjacencyList();
        int n = this.getNumNodes();
        Integer u;
        Double [] dist = new Double[n];
        Integer [] prev = new Integer[n];

        for(int i = 0; i < n; i++){
            dist[i] = -1.0;
            prev[i] = -1;
        }

        dist[source-1] = 0.0;
        List<Integer> Q = new ArrayList<>();
        Q.add(source);
        while (!Q.isEmpty()){
            u = Q.get(0);                     // it takes first element on list
            Q.remove(0);                        // and remove it

            if(dist[u-1] == -1){
                break;
            }

            for (int i = 0; i < this.getNumNeighbor(u); i++){
                Double alt = dist[u-1] + getDistance(u,i);
                Integer nId = getIdNeighbor(u,i);
                if((dist[nId-1] == -1) || (alt < dist[nId-1])){
                    dist[nId-1] = alt;
                    prev[nId-1] = u;
                    Q.add(nId);
                }
            }
        }

        //creation of graph
        for(int i = 0; i<n; i++){
            /*boolean ctrl = false;
            for (int j = i; j < n; j++) {
                if (i + 1 == prev[j]) {
                    ctrl = true;
                }
            }*/
            if((prev[i] != -1)){
                pg.addEdge(prev[i],i+1,dist[i]);
            }
            /*if (!ctrl) {
                pg.put(i+1, new ArrayList<>());
            }*/
        }
        return pg.getGraph();
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