package it.unipd.advancedalgorithms.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.unipd.advancedalgorithms.graph.Edge;
import it.unipd.advancedalgorithms.graph.Graph;

public class Kruskal {
    public static int MST(Graph g) {
        Graph copy = new Graph();
        List<Edge> orderedList = g.getEdges().stream().sorted(Comparator.comparingInt(Edge::getWeight))
                .collect(Collectors.toList());
        Iterator<Edge> x = orderedList.iterator();
        List<Edge> res = new ArrayList<>();
        while (res.size() < g.getnVertex() - 1) {
            Edge e = x.next();
            // if e is not a loop edge and there is not a path from start to end ( no loop),
            // add the edge to MST
            if ((e.getStart() != e.getEnd()) && !Kruskal.findPath(e.getStart(), e.getEnd(), copy)) {
                copy.addEdge(e.getStart(), e.getEnd(), e.getWeight());
                res.add(e);
            }
        }
        return res.stream().map(Edge::getWeight).reduce(0, Integer::sum);
    }

    public static Boolean findPath(int s, int e, Graph graph) {
        // if a node doesn't exist, there can't be a path to it
        if (!graph.getAdjacencyLists().containsKey(s) || !graph.getAdjacencyLists().containsKey(e))
            return false;

        LinkedList<Integer> stack = new LinkedList<>();
        HashMap<Integer, Boolean> visited = new HashMap<>(graph.getnVertex());
        for (Integer node : graph.getAdjacencyLists().keySet())
            visited.put(node, false);
        visited.put(s, true);
        stack.add(s);

        while (stack.size() != 0) {
            int current = stack.getFirst();
            Iterator<Edge> itr = graph.getAdjacencyLists().get(current).iterator();
            while (itr.hasNext()) {
                int v = itr.next().getEnd();
                if (v == e) // if we arrive at the destination node there exists a path from s to e
                    return true;
                if (!visited.get(v)) { // continue with BFS
                    stack.addLast(v);
                    visited.put(v, true);
                }
            }
            stack.poll();
        }
        return false;
    }

}