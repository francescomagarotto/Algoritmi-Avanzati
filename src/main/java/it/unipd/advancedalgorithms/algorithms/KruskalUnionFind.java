package it.unipd.advancedalgorithms.algorithms;
<<<<<<< HEAD

=======
>>>>>>> a5e8aa90816eeefabbe5d781a46a8fa1cb0f415b
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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

    for (int i = 0; i < g.getnVertex(); ++i)
      result[i] = new Edge();
    // Step 1: Sort all the edges in non-decreasing order of their
    // weight. If we are not allowed to change the given graph, we
    // can create a copy of array of edges
    Collections.sort(g.getEdges(), new EdgeComparator());

    // Allocate memory for creating V ssubsets
    subset subsets[] = new subset[g.getnVertex()];
    for (int i = 0; i < g.getnVertex(); ++i)
      subsets[i] = new subset();

    // Create V subsets with single elements
    for (int vertex = 0; vertex < g.getnVertex(); vertex++) {
      subsets[vertex].parent = vertex;
      subsets[vertex].rank = 0;
    }

    int e = 0; // An index variable, used for result[]
    int i = 0; // An index variable, used for sorted edges

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
    System.out.println("Minimum spanning tree value: ");
    int sum = 0;
    for (i = 0; i < e; ++i) {
      sum += result[i].getWeight();
    }
    System.out.println(sum);
  }
}