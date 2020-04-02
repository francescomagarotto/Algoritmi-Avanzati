import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Prim {

  //dumb implementation
  public static List<Edge> solve(Graph G, int s) {

    List<Edge> A = new ArrayList<>();  //List of edges that form the minimum spanning tree
    Set<Integer> X = new HashSet<>();  //Set of nodes that have been visited
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

      if (lightEdge.start != -1) {
        //add light edge to A and update X
        A.add(lightEdge);
        X.add(lightEdge.end);
      }

    } while (lightEdge.start != -1); // there is no light edge

    return A;
  }
}
