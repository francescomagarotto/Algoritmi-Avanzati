
public class Prova {
    public static void main(String[] args) {
        GraphReader reader = new GraphReader("input_random_1_10.txt");
        System.out.println(reader.getNumberOfEdges());
    }
}