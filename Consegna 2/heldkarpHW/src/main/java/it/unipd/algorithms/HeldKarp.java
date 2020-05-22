package it.unipd.algorithms;

import it.unipd.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeldKarp {

  private ArrayList<HashMap<Set<Integer>, Integer>> calculatedDistance;
  private ArrayList<HashMap<Set<Integer>, Integer>> parent;
  private Integer[][] matrix;
  public static AtomicBoolean interrupted;

  private Integer getDistance(int i, Set<Integer> S) {
    return calculatedDistance.get(i).get(S);
  }

  private void setDistance(int i, Set<Integer> S, Integer d) {
    calculatedDistance.get(i).put(S, d);
  }

  private Integer getParent(int i, Set<Integer> S) {
    return parent.get(i).get(S);
  }

  private void setParent(int i, Set<Integer> S, Integer p) {
    parent.get(i).put(S, p);
  }

  private int HK_Visit(int v, Set<Integer> S) {
    if (S.size() == 1 && S.contains(v))
      return w(v, 0);
    else if (getDistance(v, S) != null)
      return getDistance(v, S);
    else {
      Integer mindist = Integer.MAX_VALUE;
      Integer minprec = null;
      //newSet is a copy of S without the node v (S\{v})
      Set<Integer> newSet = new HashSet<>(S);
      newSet.remove(v);

      int dist;
      for (int u : newSet) {
        dist = HK_Visit(u, newSet);
        if (dist + w(u, v) < mindist) {
          mindist = dist + w(u, v);
          minprec = u;
        }

        //if the algorithm is stopped early interrupt the cycle and return the current minimum
        if(interrupted.get()) break;
      }

      setDistance(v, S, mindist);
      setParent(v, S, minprec);
      return mindist;
    }
  }

  private int w(int u, int v) {
    return matrix[u][v];
  }

  public int HK_TSP(Integer[][] graphMatrix) {
    matrix = graphMatrix;
    int nVertex = graphMatrix.length;


    calculatedDistance = new ArrayList<>(nVertex);
    parent = new ArrayList<>(nVertex);
    HashSet<Integer> V = new HashSet<>();
    HeldKarp.interrupted = new AtomicBoolean(false);

    for(int i = 0; i < nVertex; i++) {
      calculatedDistance.add(new HashMap<>());
      parent.add(new HashMap<>());
      V.add(i);
    }


    return HK_Visit(0, V);
  }

}
