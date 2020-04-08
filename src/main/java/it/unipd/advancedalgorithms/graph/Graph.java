package it.unipd.advancedalgorithms.graph;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Graph {
  private List<Integer> V;
  private Integer nVertex;
  private List<Edge> edges;
  private HashMap<Integer, LinkedList<Edge>> adjacencyLists;

  public Graph(
      List<Edge> edgeList, List<Integer> nodeList, HashMap<Integer, LinkedList<Edge>> adjList) {
    V = nodeList;
    edges = edgeList;
    adjacencyLists = adjList;
  }

  // add weighted edge from source to destination
  void addEdge(int src, int dest, int weight) {
    edges.add(new Edge(src, dest, weight));
  }

  public Graph() {}

  public Integer getnVertex() {
    return nVertex;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public List<Integer> getV() {
    return V;
  };
  public HashMap<Integer, LinkedList<Edge>> getAdjacencyLists() {
    return adjacencyLists;
  }

  public void setNVertex(Integer nVertex) {
    this.nVertex = nVertex;
  }

  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  public void setAdjacencyLists(HashMap<Integer, LinkedList<Edge>> adjacenyLists) {
    this.adjacencyLists = adjacenyLists;
  }

  public void setV(List<Integer> V) {
    this.V = V;
  }
}
