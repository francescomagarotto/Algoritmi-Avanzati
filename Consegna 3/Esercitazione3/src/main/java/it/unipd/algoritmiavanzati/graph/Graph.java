package it.unipd.algoritmiavanzati.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private Integer nNodes;

    public Graph() {
        edges = new ArrayList<>();
        nNodes = 0;
    }

    public Graph(List<Edge> list, Integer n) {
        edges = list;
        nNodes = n;
    }

    public Integer getnVertex() {
        return nNodes;
    }

    public LinkedList<Edge> getEdges() {
        LinkedList<Edge> newEdges = new LinkedList<>();
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge e = iterator.next();
            Edge newEdge = new Edge(e.getStart(), e.getEnd());
            newEdges.add(newEdge);
        }
        return newEdges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
