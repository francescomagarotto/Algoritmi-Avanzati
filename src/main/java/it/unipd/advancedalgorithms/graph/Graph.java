package it.unipd.advancedalgorithms.graph;

import java.util.LinkedList;
import java.util.ArrayList;
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
  public void addEdge(int src, int dest, int weight) {
    if (adjacencyLists.containsKey(src))
      adjacencyLists.get(src).add(new Edge(src, dest, weight));
    else {
      LinkedList<Edge> l = new LinkedList<>();
      l.add(new Edge(src, dest, weight));
      adjacencyLists.put(src, l);
    }
    if (adjacencyLists.containsKey(dest))
      adjacencyLists.get(dest).add(new Edge(dest, src, weight));
    else {
      LinkedList<Edge> l = new LinkedList<>();
      l.add(new Edge(dest, src, weight));
      adjacencyLists.put(dest, l);
    }
  }

  public Graph() {
    edges = new ArrayList<>();
    adjacencyLists = new HashMap<>();
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
