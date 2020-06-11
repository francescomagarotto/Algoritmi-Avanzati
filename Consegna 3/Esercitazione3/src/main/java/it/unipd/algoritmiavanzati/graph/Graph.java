package it.unipd.algoritmiavanzati.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private HashMap<Integer, LinkedList<Edge>> adjacencyLists;

    public Graph(List<Edge> edgeList, HashMap<Integer, LinkedList<Edge>> adjList) {
        edges = edgeList;
        adjacencyLists = adjList;
    }

    public Graph() {
        edges = new ArrayList<>();
        adjacencyLists = new HashMap<>();
    }

    // add weighted edge from source to destination
    public void addEdge(int src, int dest, int weight) {
        if (adjacencyLists.containsKey(src))
            adjacencyLists.get(src).add(new Edge(src, dest));
        else {
            LinkedList<Edge> l = new LinkedList<>();
            l.add(new Edge(src, dest));
            adjacencyLists.put(src, l);
        }
        if (adjacencyLists.containsKey(dest))
            adjacencyLists.get(dest).add(new Edge(dest, src));
        else {
            LinkedList<Edge> l = new LinkedList<>();
            l.add(new Edge(dest, src));
            adjacencyLists.put(dest, l);
        }
    }

    public Integer getnVertex() {
        return adjacencyLists.keySet().size();
    }

    public List<Edge> getEdges() {
        return new LinkedList<>(edges);
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public HashMap<Integer, LinkedList<Edge>> getAdjacencyLists() {
        return adjacencyLists;
    }

    public void setAdjacencyLists(HashMap<Integer, LinkedList<Edge>> adjacenyLists) {
        this.adjacencyLists = adjacenyLists;
    }

}
