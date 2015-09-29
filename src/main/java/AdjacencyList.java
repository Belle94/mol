import javafx.util.Pair;

import javax.xml.transform.Source;
import java.util.*;

public class AdjacencyList {
    private HashMap<Integer, List<Pair<Integer, Double>>> g;

    public AdjacencyList() {
        g = new HashMap<>();
    }

    public AdjacencyList(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }

    public int getNumNodes(){
        int n = g.size();
        return n;
    }

    public void addEdge(Integer source, Integer destination, Double distance) {
        List<Pair<Integer, Double>> n = g.containsKey(source) ?
                g.get(source) : new ArrayList<>();
        n.add(new Pair<Integer, Double>(destination, distance));
        g.put(source, n);
    }

    public void addNode(Integer source){
        List<Pair<Integer, Double>> n = g.containsKey(source) ?
                g.get(source) : new ArrayList<>();
        g.put(source,null);
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

        List<Integer> n = new ArrayList<>();

        for (Pair<Integer, Double> p : g.get(v))
            n.add(p.getKey());

        return n;
    }

    public Double getDistance(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        Double distance = nodep.getValue();
        return distance;
    }

    public Integer getIdNeighbor(Integer v, int index ){
        List<Pair<Integer, Double>> node = g.get(v);
        Pair<Integer, Double> nodep = node.get(index);
        Integer id = nodep.getKey();
        return id;
    }

    public int getNumNeighbor(Integer v){
        List<Pair<Integer, Double>> neighbor = g.get(v);
        if(neighbor == null){
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

    public HashMap<Integer, List<Pair<Integer, Double>>> Dijkstra(Integer source){
        HashMap<Integer, List<Pair<Integer, Double>>> pg = new HashMap<>();
        int n = this.getNumNodes();
        System.out.printf("Nodi: %d\n",n);
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
            u = Q.get(0);                     //it takes first element on list
            Q.remove(0);                        //and remove it

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

        for(int i = 0; i<n; i++){
            boolean ctrl = false;
            for (int j = i; j < n; j++) {
                if (i + 1 == prev[j]) {
                    ctrl = true;
                }
            }
            if((prev[i] != -1)){
                List<Pair<Integer, Double>> l = new ArrayList<>();
                l.add(new Pair<>(i+1, dist[i]));
                pg.put(prev[i],l);
            }
            if (ctrl == false) {
                pg.put(i+1, null);
            }
        }
        return pg;
    }
}

