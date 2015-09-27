import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdjacencyList {
    private HashMap<Integer, List<Pair<Integer, Double>>> g;

    public AdjacencyList() {
        g = new HashMap<>();
    }

    public void addEdge(Integer source, Integer destination, Double distance) {
        List<Pair<Integer, Double>> n = g.get(source);
        n.add(new Pair<Integer, Double>(destination, distance));
        g.put(source, n);
    }

    public List<Pair<Integer, Double>> getNeighborsDistances(Integer v) {
        return g.get(v);
    }

    public List<Integer> getNeighbor(Integer v) {
        List<Integer> n = new ArrayList<>();

        for (Pair<Integer, Double> p : g.get(v))
            n.add(p.getKey());

        return n;
    }
}
