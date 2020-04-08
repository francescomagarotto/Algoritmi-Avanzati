package it.unipd.advancedalgorithms.algorithms;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import it.unipd.advancedalgorithms.graph.Edge;
import it.unipd.advancedalgorithms.graph.Graph;
public class KruskalUnionFind {
  class subset {
    int parent, rank;
  }

  int find(subset subsets[], int i) {
    // find root and make root as parent of i (path compression)
    if (subsets[i].parent != i)
      subsets[i].parent = find(subsets, subsets[i].parent);

    return subsets[i].parent;
  }

  void Union(subset subsets[], int x, int y) {
    int xroot = find(subsets, x);
    int yroot = find(subsets, y);

    // Attach smaller rank tree under root of high rank tree
    // (Union by Rank)
    if (subsets[xroot].rank < subsets[yroot].rank)
      subsets[xroot].parent = yroot;
    else if (subsets[xroot].rank > subsets[yroot].rank)
      subsets[yroot].parent = xroot;

    // If ranks are same, then make one as root and increment
    // its rank by one
    else {
      subsets[yroot].parent = xroot;
      subsets[xroot].rank++;
    }
  }

  class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
      return e1.getWeight() - e2.getWeight();
    }
  }

  public void KruskalMST(Graph g) {
    Edge result[] = new Edge[g.getnVertex()]; // Tnis will store the resultant MST
    int e = 0; // An index variable, used for result[]
    int i = 0; // An index variable, used for sorted edges
    for (i = 0; i < g.getnVertex(); ++i) result[i] = new Edge();
    // Step 1: Sort all the edges in non-decreasing order of their
    // weight. If we are not allowed to change the given graph, we
    // can create a copy of array of edges
    Collections.sort(g.getEdges(), new EdgeComparator());

    // Allocate memory for creating V ssubsets
    subset subsets[] = new subset[g.getnVertex()];
    for (i = 0; i < g.getnVertex(); ++i) subsets[i] = new subset();

    // Create V subsets with single elements
    for (int v = 0; v < g.getnVertex(); ++v) {
      subsets[v].parent = v;
      subsets[v].rank = 0;
    }

    i = 0; // Index used to pick next edge

    // Number of edges to be taken is equal to V-1
    Iterator<Edge> edgesIterator = g.getEdges().iterator();
    while (e < g.getnVertex() - 1) {
      // Step 2: Pick the smallest edge. And increment
      // the index for next iteration
      Edge next_edge = new Edge();
      next_edge = edgesIterator.next();

      int x = find(subsets, next_edge.getStart() - 1);
      int y = find(subsets, next_edge.getEnd() - 1);

      // If including this edge does't cause cycle,
      // include it in result and increment the index
      // of result for next edge
      if (x != y) {
        result[e++] = next_edge;
        Union(subsets, x, y);
      }
      // Else discard the next_edge
    }

    // print the contents of result[] to display
    // the built MST
    System.out.println("Following are the edges in "
        + "the constructed MST");
    int sum = 0;
    for (i = 0; i < e; ++i) {
      sum += result[i].getWeight();
    }
    System.out.println(sum);
  }

  public static void main(String[] args) {
    Graph g;
    HashMap<Integer, LinkedList<Edge>> adj1;
    LinkedList<Edge> l0 = new LinkedList<>();
    l0.add(new Edge(0, 1, 10));
    l0.add(new Edge(0, 2, 6));
    l0.add(new Edge(0, 3, 5));
    l0.add(new Edge(1, 3, 15));
    l0.add(new Edge(2, 3, 4));
    g = new Graph(l0, null, null);
    g.setNVertex(4);
    KruskalUnionFind order = new KruskalUnionFind();
    order.KruskalMST(g);
  }
}