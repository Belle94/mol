import javafx.util.Pair;

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
                mat.put(new Pair<>(i, k), p.getKey().get(k));
            }
        }
    }

    public AdjacencyList subAdjacencyList(Integer source, Integer destination) {
        AdjacencyList ret = new AdjacencyList();

        return ret;
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
}
