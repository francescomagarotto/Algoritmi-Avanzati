import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;

public class Prim {

  private static Map<Integer, Integer> key;

  //Used in minHeap to sort the nodes by key
  private static class NodeComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer n1, Integer n2) {
      return (key.get(n1) - key.get(n2));
    }
  }

  public static List<Edge> solve(Graph G, int s) {

    key = new HashMap<>();
    Map<Integer, Integer> parent = new HashMap<>();
    Set<Integer> Q = new HashSet<>();   //contains all the nodes not in the MST
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(new NodeComparator());


    //initialize data structures
    for (Node u : G.V) {
      key.put(u.id, Integer.MAX_VALUE);
      parent.put(u.id, -1);
      Q.add(u.id);
      minHeap.add(u.id);
    }

    key.replace(s, 0);

    while (!Q.isEmpty()) {
      Integer u = minHeap.poll();   //get the node with the smallest key
      Q.remove(u);

      for (Edge edge : G.map.get(u)) {    //cycle all the edges adjacent to the node u
        Integer v = edge.end;
        if (Q.contains(v) && edge.weight < key.get(v)) {
          //if the new edge is lighter update parent and key for the node v
          parent.replace(v, u);
          key.replace(v, edge.weight);
        }
      }
    }

    List<Edge> A = new LinkedList<>();  //minimum spanning tree
    for (Integer node : key.keySet()) {
      A.add(new Edge(node, parent.get(node), key.get(node)));
    }

    return A;
  }


  //dumb implementation
  public static List<Edge> dumbSolve(Graph G, int s) {

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
