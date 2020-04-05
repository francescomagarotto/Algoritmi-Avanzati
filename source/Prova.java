import java.util.List;

public class Prova {
    public static void main(String[] args) {
        List<Edge> minSpanningTree = Prim.solve(GraphReader.getGraph("../datasets/input_random_1_10.txt"), 1);
        for (Edge edge : minSpanningTree) {
          System.out.println(edge.start+" "+edge.end+" "+edge.weight);

        }
    }
}