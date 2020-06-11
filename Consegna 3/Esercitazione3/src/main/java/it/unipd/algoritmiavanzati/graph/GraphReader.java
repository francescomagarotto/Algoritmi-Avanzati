package it.unipd.algoritmiavanzati.graph;

import org.javatuples.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GraphReader {
    public static Graph getGraph(String path) {
        HashMap<Integer, LinkedList<Edge>> map = new HashMap<>();
        List<Edge> edges = new LinkedList<>();
        try {
            Iterator<String> iterator = Files.lines(Paths.get(path)).iterator();
            while (iterator.hasNext()) {
                String[] list = iterator.next().split(" ");
                Integer listDim = list.length;
                Integer currentNode = Integer.parseInt(list[0]);
                if (!map.containsKey(currentNode)) {
                    LinkedList<Edge> adjListOfCurrentNode = new LinkedList<>();
                    map.put(currentNode, adjListOfCurrentNode);
                }
                for (int i = 1; i < listDim; i++) {
                    Integer nextNode = Integer.parseInt(list[i]);
                    Edge currentEdge = new Edge(currentNode, nextNode);
                    map.get(currentNode).add(currentEdge);
                    edges.add(currentEdge);

                    if (!map.containsKey(nextNode)) {
                        LinkedList<Edge> adjListOfNeighborNode = new LinkedList<>();
                        adjListOfNeighborNode.add(new Edge(nextNode, currentNode));
                        map.put(nextNode, adjListOfNeighborNode);
                    } else
                        map.get(nextNode).add(new Edge(nextNode, currentNode));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Graph(edges, map);
    }
}