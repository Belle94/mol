import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

public class DistanceMatrix {
    private HashMap<Pair<Integer, Integer>, Double> mat;
    private HashMap<Integer, AdjacencyList> minGraph;

    public DistanceMatrix(AdjacencyList adjacencyList) {
        mat = new HashMap<>();
        minGraph = new HashMap<>();

        for (Integer i : adjacencyList.nodes()) {
            Pair<HashMap<Integer, Double>, AdjacencyList> p =
                    adjacencyList.dijkstra(i);
            minGraph.put(i, p.getValue());

            for (Integer k : p.getKey().keySet()) {
                if (i != k)
                    mat.put(new Pair<>(i, k), p.getKey().get(k));
            }
        }
    }

    public HashMap<Pair<Integer, Integer>, Double> get() {
        return mat;
    }

    public Double get(Integer source, Integer destination) {
        return mat.get(new Pair<>(source, destination));
    }

    public Double get(Pair<Integer, Integer> pair) {
        return mat.get(pair);
    }

    public void set(HashMap<Pair<Integer, Integer>, Double> mat) {
        this.mat = mat;
    }

    public HashMap<Integer, AdjacencyList> minDijkstraTree() {
        return minGraph;
    }

    public Pair<Pair<Integer, Integer>, Double> max() {
        Pair<Integer, Integer> k = new Pair<>(0,0);
        Double max = Double.MIN_VALUE;
        for (Pair<Integer, Integer> p : mat.keySet()) {
            if (mat.get(p) > max) {
                max = mat.get(p);
                k = p;
            }
        }

        return new Pair<Pair<Integer, Integer>, Double>(k, max);
    }

    @Override
    public String toString() {
        String ret = "";
        for (Pair<Integer, Integer> p : mat.keySet()) {
            ret += "(" + p.getKey() + " - " + p.getValue() + ")"
                    + " " + mat.get(p) + "\n";
        }

        return ret;
    }
}
