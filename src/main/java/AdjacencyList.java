import java.util.ArrayList;
import java.util.List;

public class AdjacencyList {
    int n;
    List<Integer>[] adj;

    AdjacencyList(int n) {
        this.n = n;
        adj = (List<Integer>[]) new List[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<Integer>();
    }

    void addEdge(int i, int j) {
        
    }
}
