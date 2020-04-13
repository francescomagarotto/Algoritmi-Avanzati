package it.unipd.advancedalgorithms.graph;

import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphReader {
  public static Graph getGraph(String path) {
    try {
      Iterator<String> iterator = Files.lines(Paths.get(path)).iterator();
      String[] dimension = iterator.next().split(" ");
      List<Edge> edgeList = new ArrayList<Edge>(Integer.parseInt(dimension[1]));
      HashMap<Integer, LinkedList<Edge>> map = new HashMap<>();

      while (iterator.hasNext()) {
        String[] info = iterator.next().split(" ");
        int src = Integer.parseInt(info[0]);
        int dest = Integer.parseInt(info[1]);
        int weight = Integer.parseInt(info[2]);
        Edge e = new Edge(src, dest, weight);

        if (map.containsKey(src)) {
          Optional<Edge> oE = map.get(src).stream().filter(p -> p.getEnd() == dest).findAny();
          if(oE.isPresent()) {
            if(oE.get().getWeight() > weight)
              oE.get().setWeight(weight);
          } else {
            map.get(src).add(e); 
          }
        } else {
          LinkedList<Edge> xe = new LinkedList<Edge>();
          xe.add(e);
          map.put(src, xe);
        }
        if (map.containsKey(dest)) {
          Optional<Edge> check = map.get(dest).stream().filter(p -> p.getEnd() == src).findAny();
          if(check.isPresent()) {
            if(check.get().getWeight() > weight)
              check.get().setWeight(weight);
          } else {
            map.get(dest).add(new Edge(dest, src, weight)); 
          }
        } else {
          LinkedList<Edge> xe = new LinkedList<Edge>();
          xe.add(new Edge(dest, src, weight));
          map.put(dest, xe);
        }
        edgeList.add(e);
      }
      return new Graph(edgeList, map);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new Graph();
  }
}