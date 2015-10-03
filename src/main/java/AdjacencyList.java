import javafx.util.Pair;

import java.util.*;
import java.util.stream.Stream;

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

    public void reverseEdgeWeight() {
        Double wMax = 0.0;
        for (Integer c : g.keySet()) {
            for (Pair<Integer, Double> p : g.get(c)) {
                if (p.getValue() > wMax)
                    wMax = p.getValue();
            }
        }

        for (Integer c : g.keySet()) {
            for (Pair<Integer, Double> p : g.get(c)) {
                p = new Pair<>(p.getKey(), wMax - p.getValue());
            }
        }
    }

    public HashMap<Integer, List<Pair<Integer, Double>>> clark_wright
            (Integer vehicles, Integer zero) {
        HashMap<Pair<Integer, Integer>, Double> matDistance = new HashMap<>();
        HashMap<Pair<Integer, Integer>, Double> savings = new HashMap<>();
        // initializing savings
        for (Integer i : g.keySet()) {
            for (Integer j : dijkstra(i).keySet()) {
                savings.put(new Pair<>(i,j),
                        matDistance.get((new Pair<>(zero, i))) +
                                matDistance.get(new Pair<>(zero, j)) -
                                matDistance.get(new Pair<>(i,j)));
            }
        }

        List<Pair<Integer, Integer>> orderedSavingsKey = orderByValue(savings);

        return null;
    }

    public static List<Pair<Integer, Integer>> orderByValue(HashMap<Pair<Integer, Integer>, Double> h) {
        List<Pair<Integer, Integer>> result = new LinkedList<>();
        Pair<Integer, Integer> kMax = new Pair<>(0,0);
        for (int i = 0; i < h.keySet().size(); i++) {
            Double max = Double.MIN_VALUE;
            for(Pair<Integer, Integer> p : h.keySet()) {
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

    public HashMap<Integer, Double> dijkstra(Integer source) {
        return null;
    }
}
