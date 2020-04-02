import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphReader {
    private Path pathFile;
    public GraphReader(String fileName) {
        this.pathFile = Paths.get(fileName);

    }

    private String explodeFirstLine(int index) {
        // Non uso stringhe statiche intenzionalmente
        String info = new String();
        try {
            info = Files.lines(pathFile).findFirst().get();
        } catch (IOException e) {
        }
        String result = info.split(" ")[index].trim();
        return result;
    }

    public int getNumberOfVertex() {
        return Integer.parseInt(explodeFirstLine(0));
    }

    public int getNumberOfEdges() {
        return Integer.parseInt(explodeFirstLine(1));
    }

    private static Edge lineToEdge(String line) {
      String[] info = line.split(" ");
      int src = Integer.parseInt(info[0]);
      int dest = Integer.parseInt(info[1]);
      int weight = Integer.parseInt(info[2]);
      return new Edge(src, dest, weight);
    }

    //for now it creates a graph with only the list of edges
    public Graph getGraph() {
      Graph graph = new Graph();
      try {

        List<Edge> edgeList = Files.lines(pathFile).skip(1)
                                                   .map(GraphReader::lineToEdge)
                                                   .collect(Collectors.toList());
        graph.setE(edgeList);

      } catch (IOException e) {
        e.printStackTrace();
      }

      return graph;
    }

    // TODO leggere le altre linee ed creare un grafo
}