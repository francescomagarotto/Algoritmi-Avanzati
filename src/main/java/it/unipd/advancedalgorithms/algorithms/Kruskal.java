package it.unipd.advancedalgorithms.algorithms;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unipd.advancedalgorithms.graph.Edge;
import it.unipd.advancedalgorithms.graph.Graph;

public class Kruskal {
    public static int MST(Graph g) {
        Set<Edge> a = new HashSet<>();
        Set<Integer> cyclic = new HashSet<>();
        List<Edge> orderedList = g.getEdges().stream().sorted(Comparator.comparingInt(Edge::getWeight))
                .collect(Collectors.toList());
        Iterator<Edge> x = orderedList.iterator();
        while (a.size() < orderedList.size() && x.hasNext()) {
            Edge e = x.next();
            if (!(cyclic.contains(e.getStart()) && cyclic.contains(e.getEnd()))) {
                a.add(e);
                cyclic.add(e.getStart());
                cyclic.add(e.getEnd());
            }
        }
        int result = 0;
        for (Edge e : a) {
          result += e.getWeight();
        }
        return result;
    }

}