import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphReader {
    //for now it creates a graph with only the list of edges
    public static Graph getGraph(String path) {
      try {
        Iterator<String> iterator = Files.lines(Paths.get(path)).iterator();
        String[] dimension = iterator.next().split(" ");
        List<Edge> edgeList = new ArrayList<Edge>(Integer.parseInt(dimension[1]));
        HashMap<Integer, LinkedList<Edge>> map = new HashMap<>();
        
        while(iterator.hasNext()) {
          String[] info = iterator.next().split(" ");
          int src = Integer.parseInt(info[0]);
          int dest = Integer.parseInt(info[1]);
          int weight = Integer.parseInt(info[2]);
          Edge e = new Edge(src, dest, weight);
          if(map.containsKey(src)) {
        	  map.get(src).add(e);
          }
          else {
        	  LinkedList<Edge> xe = new LinkedList<Edge>();
        	  xe.add(e);
        	  map.put(src, xe);
          }
          edgeList.add(e);
          
        }
        return new Graph(edgeList, new LinkedList<>(map.keySet()), map);

      } catch (Exception e) {
        e.printStackTrace();
      }

      return new Graph();
    }

    // TODO leggere le altre linee ed creare un grafo
}