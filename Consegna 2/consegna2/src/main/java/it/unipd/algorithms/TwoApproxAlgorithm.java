package it.unipd.algorithms;

import it.unipd.graph.Heap;

import java.util.*;

public class TwoApproxAlgorithm {


    public static List<Integer> Preorder(int s, HashMap<Integer, List<Integer>> tree) {
      List<Integer> visited = new ArrayList<>();
      PreorderRec(s, tree, visited);
      return visited;
    }

    private static void PreorderRec(int s, HashMap<Integer, List<Integer>> tree, List<Integer> visited) {
      visited.add(s);
      for(int child : tree.get(s)) {
        PreorderRec(child, tree, visited);
      }
    }

    private static Map<Integer, Integer> key;
    public static Map<Integer, Integer> prim(int s, Integer[][] g) {
        int nVertices = g.length;
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
            for (int i : Q) {
                if (g[u][i] < key.get(i)) {
                    parent.replace(i, u);
                    key.replace(i, g[u][i]);
                    minHeap.update(i);
                }
            }
        }
        return parent;
    }

    public static int solve(int s, Integer[][] g) {
        int nVertices = g.length;
        Map<Integer, Integer> parent = prim(s, g);
        HashMap<Integer, List<Integer>> tree = new HashMap<>();
        Integer totalCost = 0;

        for (int i = 0; i < nVertices; i++) {
            List<Integer> l = new LinkedList<Integer>();
            tree.put(i, l);
        }
        for (int i = 1; i < nVertices; i++) {
            Integer p = parent.get(i);
            tree.get(p).add(i);
        }

        List<Integer> path = Preorder(0, tree);
        path.add(0);

        for (int i = 0; i < path.size() - 1; i++) {
            totalCost += g[path.get(i)][path.get(i + 1)];
        }
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
