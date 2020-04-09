package it.unipd.advancedalgorithms;
<<<<<<< HEAD

import java.nio.file.Path;
import java.util.List;
=======
>>>>>>> a5e8aa90816eeefabbe5d781a46a8fa1cb0f415b
import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) {
    System.out.println("Initial: " + System.currentTimeMillis());
<<<<<<< HEAD
    System.out.println((Path.of("datasets/input_random_3_10.txt")).toAbsolutePath());
    Graph g = GraphReader.getGraph("datasets/input_random_3_10.txt");
    final List<Edge> minSpanningTree = Prim.solve(g, 1);
    KruskalUnionFind solve = new KruskalUnionFind();
    solve.KruskalMST(g);
=======
    Graph g = GraphReader.getGraph("datasets/input_random_1_10.txt");
>>>>>>> a5e8aa90816eeefabbe5d781a46a8fa1cb0f415b
    System.out.println("Graph created: " + System.currentTimeMillis());
    Prim.solve(g, 1).forEach(e -> printEdge(e));
    System.out.println("---------- KRUSKAL -----------");
    Kruskal.MST(g).forEach(e -> printEdge(e));
    System.out.println("End execution: " + System.currentTimeMillis());
  }

  static void printEdge(Edge e) {
    System.out.println(e.getStart() + " " + e.getEnd() + " " + e.getWeight()); 
  }
}