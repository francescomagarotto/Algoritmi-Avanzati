package it.unipd.advancedalgorithms;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {

  public static void main(final String[] args) throws Exception {
    HashMap<Integer, Long> kruskalU = new HashMap<>();
    HashMap<Integer, Long> kruskal = new HashMap<>();
    HashMap<Integer, Long> prim = new HashMap<>();
    try (Stream<Path> paths = Files.walk(Paths.get("datasets"))) {
      paths.filter(Files::isRegularFile).forEach(file -> {
        Graph g = GraphReader.getGraph("datasets/" + file.getFileName().toString());
        int numberEdges = g.getEdges().size();
        long primStartTime = System.nanoTime();
        Prim.solve(g, 1);
        long primEndtTime = primStartTime - System.nanoTime();
        prim.put(numberEdges, primEndtTime);

        long kruskalStartTime = System.nanoTime();
        Kruskal.MST(g);
        long kruskalEndtTime = kruskalStartTime - System.nanoTime();
        kruskal.put(numberEdges, kruskalEndtTime);

        long kruskalUStartTime = System.nanoTime();
        KruskalUnionFind.KruskalMST(g);
        Long kruskalUEndtTime = kruskalUStartTime - System.nanoTime();
        kruskalU.put(numberEdges, kruskalUEndtTime);

      });
    } catch (Exception e) {
    }

  }

  static void printEdge(Edge e) {
    System.out.println(e.getStart() + " " + e.getEnd() + " " + e.getWeight());
  }
}