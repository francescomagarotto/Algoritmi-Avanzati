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
          boolean trovato = false;
          for (Edge e1 : map.get(src)) {
            if (e1.getEnd() == dest) {
              if (e1.getWeight() > weight)
                e1.setWeight(weight);
              trovato = true;
              break;
            }
          }
          if (!trovato) 
            map.get(src).add(e);
          
        } else {
          LinkedList<Edge> r = new LinkedList<Edge>();
          r.add(e);
          map.put(src, r);
        }
        if (map.containsKey(dest)) {
          boolean trovato = false;
          for (Edge e1 : map.get(dest)) {
            if (e1.getEnd() == dest) {
              if (e1.getWeight() > weight)
                e1.setWeight(weight);
              trovato = true;
              break;
            }
          }
          if (!trovato) 
            map.get(src).add(new Edge(dest, src, weight));
        }

        for (Edge e1 : edgeList) {
          if (e1.getEnd() == dest && e1.getStart() == src) {
            if (weight > e1.getWeight())
              e1.setWeight(weight);
            else
              edgeList.add(e);
          }
        }
      }
      return new Graph(edgeList, map);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new Graph();
  }
}