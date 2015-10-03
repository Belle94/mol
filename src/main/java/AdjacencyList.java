import javafx.util.Pair;

import java.util.*;

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

    public HashMap<Vehicle, AdjacencyList> clark_wright
            (Integer vehicles, Integer zero) {
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
        for (int i = nodes().size(); i < vehicles && decreased;) {
            decreased = false;

            for (Pair<Integer, Integer> p : orderedSavingsKey) {
                // if clients involved have goods to be transported
                // for which the sum of the goods is <= than the
                // capacity of the vehicle, then merge the two routes
                // and decrease the number of vehicles used
                // and set decrease to true
            }
        }

        return null;
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
}
