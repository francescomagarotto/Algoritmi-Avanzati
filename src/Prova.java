import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Prova {
    public static void main(final String[] args){
      System.out.println("Initial: " + System.currentTimeMillis());
      System.out.println((Path.of("datasets/input_random_1_10.txt")).toAbsolutePath());
      Graph g = GraphReader.getGraph("datasets/input_random_1_10.txt");
      final List<Edge> minSpanningTree = Prim.solve(g, 1);
      System.out.println("Graph created: " + System.currentTimeMillis());
      for (final Edge edge : minSpanningTree) {
          System.out.println(edge.start+" "+edge.end+" "+edge.weight);

        }
        System.out.println("End execution: " + System.currentTimeMillis());
    }
}