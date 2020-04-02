import java.util.List;

public class Prova {
    public static void main(String[] args) {
        GraphReader reader = new GraphReader("../datasets/input_random_1_10.txt");
        System.out.println(reader.getNumberOfEdges());

        List<Edge> minSpanningTree = Prim.solve(reader.getGraph(), 1);
        for (Edge edge : minSpanningTree) {
          System.out.println(edge.start+" "+edge.end+" "+edge.weight);

        }
    }
}