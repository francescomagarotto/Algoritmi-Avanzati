import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Graph {

  List<Node> V;
  List<Edge> E;
  HashMap<Integer, LinkedList<Edge>> map = new HashMap<>();

  void setE (List<Edge> edgeList) {
    E = edgeList;
  }

  void setV (List<Node> nodeList) {
    V = nodeList;
  }

  // add weighted edge from source to destination
  void addEdge(int src, int dest, int weight) {
    E.add(new Edge(src, dest, weight));
  }

  public Graph() {
  }

}
