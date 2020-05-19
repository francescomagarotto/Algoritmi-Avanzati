package it.unipd.algorithms;

import it.unipd.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HeldKarp {

  private ArrayList<HashMap<Set<Integer>, Integer>> calculatedDistance;
  private ArrayList<HashMap<Set<Integer>, Integer>> parent;

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

  public int HK_Visit(int v, Set<Integer> S) {
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
      }

      setDistance(v, S, mindist);
      setParent(v, S, minprec);
      return mindist;
    }
  }

  private int w(int u, int v) {
    return 0;
  }

  public int HK_TSP(Graph g) {
    calculatedDistance = new ArrayList<>(g.getnVertex());
    parent = new ArrayList<>(g.getnVertex());

    for (int i = 0; i < g.getnVertex(); i++) {
      //calculatedDistance.set(i,new HashMap<>());
      //parent.set(i, new HashMap<>());
    }

    HashSet<Integer> V = new HashSet<>(); //g.nodes
    return HK_Visit(0, V);

  }

}
