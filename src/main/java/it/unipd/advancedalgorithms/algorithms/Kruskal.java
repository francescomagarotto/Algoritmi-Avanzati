package it.unipd.advancedalgorithms.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import it.unipd.advancedalgorithms.graph.Edge;
import it.unipd.advancedalgorithms.graph.Graph;

public class Kruskal {
    static class subset {
        int parent;

        subset(int parent) {
            this.parent = parent;
        }
    }

    static int find(subset subsets[], int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    static void Union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        subsets[xroot].parent = yroot;
    }

    static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.getWeight() - e2.getWeight();
        }
    }

    public static Integer MST(Graph g) {
        Edge result[] = new Edge[g.getnVertex()]; // result stores the resultant MST
        for (int i = 0; i < g.getnVertex(); ++i)
            result[i] = new Edge();
        Collections.sort(g.getEdges(), new EdgeComparator()); // order the edges

        // Allocate subsets for each node, where his parent is itself
        subset subsets[] = new subset[g.getnVertex()];
        for (int i = 0; i < g.getnVertex(); ++i)
            subsets[i] = new subset(i);

        int e = 0; // keeps track of the number of edges added to the MST

        Iterator<Edge> edgesIterator = g.getEdges().iterator();
        while (e < g.getnVertex() - 1) {
            Edge next_edge = edgesIterator.next(); // pick smallest edge
            int x = find(subsets, next_edge.getStart() - 1);
            int y = find(subsets, next_edge.getEnd() - 1);
            if (x != y) { // check is there is no cycle
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        int sum = 0;
        for (int i = 0; i < e; ++i) {
            sum += result[i].getWeight();
        }
        return sum;
    }

}