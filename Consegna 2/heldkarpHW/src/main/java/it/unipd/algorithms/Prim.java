package it.unipd.algorithms;

import it.unipd.graph.Edge;
import it.unipd.graph.Graph;
import it.unipd.graph.Heap;

import java.util.*;

public class Prim {
    private static Map<Integer, Integer> key;

    public static int solve(Graph G, int s) {
        key = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> Q = new HashSet<>(); // contains all the nodes not in the MST
        Heap minHeap = new Heap(G.getnVertex(), new NodeComparator());

        // initialize data structures
        for (Integer id : G.getAdjacencyLists().keySet()) {
            key.put(id, Integer.MAX_VALUE);
            parent.put(id, -1);
            Q.add(id);
            minHeap.add(id);
        }

        key.replace(s, 0);

        while (!Q.isEmpty()) {
            Integer u = minHeap.poll(); // get the node with the smallest key
            Q.remove(u);

            for (Edge edge : G.getAdjacencyLists().get(u)) { // cycle all the edges adjacent to the node u
                Integer v = edge.getEnd();
                if (Q.contains(v) && edge.getWeight() < key.get(v)) {
                    // if the new edge is lighter update parent and key for the node v
                    parent.replace(v, u);
                    key.replace(v, edge.getWeight());
                    // update the heap
                    minHeap.update(v);
                }
            }
        }

        int totalCost = 0;
        for (Integer node : key.keySet()) {
            if (node != s)
                totalCost += key.get(node);
        }

        List<Edge> A = new LinkedList<>(); // minimum spanning tree
        for (Integer node : key.keySet()) {
            if (node != s)
                A.add(new Edge(node, parent.get(node), key.get(node)));
        }
        //return A;

        return totalCost;
    }

    // dumb implementation
    public static List<Edge> dumbSolve(Graph G, int s) {
        List<Edge> A = new ArrayList<>(); // List of edges that form the minimum spanning tree
        Set<Integer> X = new HashSet<>(); // Set of nodes that have been visited
        X.add(s);

        Edge lightEdge;
        do {
            // reset current light edge
            lightEdge = new Edge(-1, -1, Integer.MAX_VALUE);

            // find light edge
            for (Edge e : G.getEdges()) {
                if (X.contains(e.getStart()) && !X.contains(e.getEnd())) {
                    if (e.getWeight() < lightEdge.getWeight()) {
                        lightEdge = e;
                    }
                }
            }

            if (lightEdge.getStart() != -1) {
                // add light edge to A and update X
                A.add(lightEdge);
                X.add(lightEdge.getEnd());
            }

        } while (lightEdge.getStart() != -1); // there is no light edge

        return A;
    }

    // Used in minHeap to sort the nodes by key
    private static class NodeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer n1, Integer n2) {
            Integer key1 = key.get(n1);
            Integer key2 = key.get(n2);
            return key1.compareTo(key2);
        }
    }
}
