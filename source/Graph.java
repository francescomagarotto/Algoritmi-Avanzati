import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

  ArrayList<Node> V;
  ArrayList<Edge> E;
  ArrayList<LinkedList<Edge>> adjList;
  HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();

  // add an edge from source to destination
  void addEdge(int src, int dest) {
    if (!map.containsKey(src)) {
      LinkedList<Integer> l = new LinkedList<>();
      l.add(dest);
      map.put(src, l);
    }

    else {
      LinkedList<Integer> l = map.get(src);
      l.add(dest);
      map.put(src, l);
    }
  }

  public Graph() {
  }

}
