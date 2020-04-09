package it.unipd.advancedalgorithms;

import java.nio.file.Path;
import java.util.List;
import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) {
    System.out.println("Initial: " + System.currentTimeMillis());
    System.out.println((Path.of("datasets/input_random_3_10.txt")).toAbsolutePath());
    Graph g = GraphReader.getGraph("datasets/input_random_3_10.txt");
    final List<Edge> minSpanningTree = Prim.solve(g, 1);
    KruskalUnionFind solve = new KruskalUnionFind();
    solve.KruskalMST(g);
    System.out.println("Graph created: " + System.currentTimeMillis());
    for (final Edge edge : minSpanningTree) {
      System.out.println(edge.getStart() + " " + edge.getEnd() + " " + edge.getWeight());
    }
    System.out.println("End execution: " + System.currentTimeMillis());
  }
}