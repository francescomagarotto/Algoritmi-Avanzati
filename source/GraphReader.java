import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class GraphReader {
    //for now it creates a graph with only the list of edges
    public static Graph getGraph(String path) {
      Graph graph = new Graph();
      try {
        Iterator<String> iterator = Files.lines(Paths.get(path)).iterator();
        String[] dimension = iterator.next().split(" ");
        HashSet<Node> nodeSet = new HashSet<Node>(Integer.parseInt(dimension[0]));
        List<Edge> edgeList = new ArrayList<Edge>(Integer.parseInt(dimension[1]));

        while(iterator.hasNext()) {
          String[] info = iterator.next().split(" ");
          int src = Integer.parseInt(info[0]);
          int dest = Integer.parseInt(info[1]);
          int weight = Integer.parseInt(info[2]);
          nodeSet.add(new Node(src));
          nodeSet.add(new Node(dest));
          edgeList.add(new Edge(src, dest, weight));
        }
        graph.setE(edgeList);
        graph.setV(new LinkedList<>(nodeSet));

      } catch (Exception e) {
        e.printStackTrace();
      }

      return graph;
    }

    // TODO leggere le altre linee ed creare un grafo
}