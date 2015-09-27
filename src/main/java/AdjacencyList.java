import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdjacencyList {
    private HashMap<Integer, List<Pair<Integer, Double>>> g;

    public AdjacencyList() {
        g = new HashMap<>();
    }

    public AdjacencyList(HashMap<Integer, List<Pair<Integer, Double>>> g) {
        this.g = g;
    }

    public void addEdge(Integer source, Integer destination, Double distance) {
        List<Pair<Integer, Double>> n = g.containsKey(source) ?
                g.get(source) : new ArrayList<>();

        n.add(new Pair<Integer, Double>(destination, distance));
        g.put(source, n);
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

    public HashMap<Integer, List<Pair<Integer, Double>>> clark_wright
            (Integer vehicles) {
        return null;
    }

    public HashMap<Integer, Double> dijkstra(Integer source) {
        return null;
    }
}
