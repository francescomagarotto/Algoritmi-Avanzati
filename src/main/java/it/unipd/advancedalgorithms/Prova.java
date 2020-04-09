package it.unipd.advancedalgorithms;
import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) {
    System.out.println("Initial: " + System.currentTimeMillis());
    Graph g = GraphReader.getGraph("datasets/input_random_1_10.txt");
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