package it.unipd.algorithms;

import it.unipd.graph.Edge;
import it.unipd.graph.Graph;
import it.unipd.graph.Heap;

import java.util.*;

public class primAdjMatrix {
    private static Map<Integer, Integer> key;

    public static List<Integer> DFS(int s, HashMap<Integer, List<Integer>> tree) {
        LinkedList<Integer> stack = new LinkedList<>();
        HashMap<Integer, Boolean> visited = new HashMap<>(tree.keySet().size());
        for (Integer node : tree.keySet())
            visited.put(node, false);
        visited.put(s, true);
        stack.addFirst(s);
        LinkedList<Integer> path = new LinkedList<>();
        while (stack.size() != 0) {
            int current = stack.poll();
            path.addLast(current);
            Iterator<Integer> itr = tree.get(current).iterator();
            if (!visited.get(current)) {
                visited.put(current, true);
            }
            while (itr.hasNext()) {
                int v = itr.next();
                if (!visited.get(v)) { // continue with BFS
                    stack.addFirst(v);
                }
            }
        }
        return path;
    }

    public static int solve(int s, Integer[][] g, int nVertices) {
        key = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> Q = new HashSet<>(); // contains all the nodes not in the MST
        Heap minHeap = new Heap(nVertices, new NodeComparator());

        // initialize data structures
        for (Integer i = 0; i < nVertices; i++) {
            key.put(i, Integer.MAX_VALUE);
            parent.put(i, -1);
            Q.add(i);
            minHeap.add(i);
        }
        key.replace(s, 0);

        while (!Q.isEmpty()) {
            Integer u = minHeap.poll(); // get the node with the smallest key
            Q.remove(u);
            for (int i = 0; i < nVertices; i++) {
                if (u != i) {
                    if (Q.contains(i) && g[u][i] < key.get(i)) {
                        parent.replace(i, u);
                        key.replace(i, g[u][i]);
                        minHeap.update(i);
                    }
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
        HashMap<Integer, List<Integer>> tree = new HashMap<>();

        for (int i = 0; i < nVertices; i++) {
            List<Integer> l = new LinkedList<Integer>();
            tree.put(i, l);
        }
        for (int i = 1; i < nVertices; i++) {
            Integer p = parent.get(i);
            tree.get(p).add(i);
        }

        List<Integer> path = DFS(0, tree);
        System.out.println(path);
        for (int i = 0; i < path.size() - 1; i++) {
            totalCost += g[i][path.get(i + 1)];
        }
        totalCost += g[path.get(path.size() - 1)][0];
        return totalCost;
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
