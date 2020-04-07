import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Graph {

  List<Integer> V;
  List<Edge> E;
  HashMap<Integer, LinkedList<Edge>> map;

  void setE (List<Edge> edgeList) {
    E = edgeList;
  }

  void setV (List<Integer> nodeList) {
    V = nodeList;
  }
  
  public Graph(List<Edge> edgeList, List<Integer> nodeList, HashMap<Integer, LinkedList<Edge>> adjList) {
	  V = nodeList;
	  E = edgeList;
	  map = adjList;
	  
  }

  // add weighted edge from source to destination
  void addEdge(int src, int dest, int weight) {
    E.add(new Edge(src, dest, weight));
  }

  public Graph() {
  }

}
