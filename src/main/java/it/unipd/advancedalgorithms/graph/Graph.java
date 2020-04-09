package it.unipd.advancedalgorithms.graph;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

public class Graph {
  private List<Edge> edges;
  private HashMap<Integer, LinkedList<Edge>> adjacencyLists;

  public Graph(List<Edge> edgeList, HashMap<Integer, LinkedList<Edge>> adjList) {
    edges = edgeList;
    adjacencyLists = adjList;
  }

  // add weighted edge from source to destination
  void addEdge(int src, int dest, int weight) {
    edges.add(new Edge(src, dest, weight));
  }

  public Graph() {
  }

  public Integer getnVertex() {
    return adjacencyLists.keySet().size();
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public HashMap<Integer, LinkedList<Edge>> getAdjacencyLists() {
    return adjacencyLists;
  }

  public void setEdges(List<Edge> edges) {
    this.edges = edges;
  }

  public void setAdjacencyLists(HashMap<Integer, LinkedList<Edge>> adjacenyLists) {
    this.adjacencyLists = adjacenyLists;
  }

}
