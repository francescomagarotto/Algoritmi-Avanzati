package it.unipd.advancedalgorithms.algorithms;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unipd.advancedalgorithms.Prova;
import it.unipd.advancedalgorithms.graph.Edge;
import it.unipd.advancedalgorithms.graph.Graph;
import it.unipd.advancedalgorithms.graph.GraphReader;

public class Kruskal implements Runnable {
    final List<String[]> kruskalTimes = new ArrayList<>();
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

    @Override
    public void run() {
        try (Stream<Path> paths = Files.walk(Paths.get("datasets"))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
              String f = file.getFileName().toString();
              final Graph g = GraphReader.getGraph("datasets/" + f);
              Long inizio = System.currentTimeMillis();
              Integer result = MST(g);
              Long fine = System.currentTimeMillis() - inizio;
              kruskalTimes.add(new String[] { f, result.toString(), fine.toString() });
    });
    }
    catch(Exception e) {}
    GraphReader.printFile("kruskal.csv", kruskalTimes);
}

}