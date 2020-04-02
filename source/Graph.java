import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Graph {

  ArrayList<Node> V;
  List<Edge> E;
  ArrayList<LinkedList<Edge>> adjList;
  HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();

  void setE (List<Edge> edgeList) {
    E = edgeList;
  }

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

  // add weighted edge from source to destination
  void addEdge(int src, int dest, int weight) {
    E.add(new Edge(src, dest, weight));
  }

  public Graph() {
  }

}
