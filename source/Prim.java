import java.util.ArrayList;
import java.util.List;

public class Prim {

  //dumb implementation
  List<Edge> solve(Graph G, int s) {

    List<Edge> A = new ArrayList<>();
    List<Integer> X = new ArrayList<>();
    X.add(s);

    Edge lightEdge;
    do {
      //reset current light edge
      lightEdge = new Edge(-1,-1, Integer.MAX_VALUE);

      //find light edge
      for (Edge e : G.E) {
        if (X.contains(e.start) && !X.contains(e.end)) {
          if (e.weight < lightEdge.weight) {
            lightEdge = e;
          }
        }
      }

      //add light edge to A and update X
      A.add(lightEdge);
      X.add(lightEdge.start);

    } while (lightEdge.start != -1); // there is no light edge

    return A;
  }
}
