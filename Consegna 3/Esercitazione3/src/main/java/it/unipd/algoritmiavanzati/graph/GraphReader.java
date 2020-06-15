package it.unipd.algoritmiavanzati.graph;

import org.javatuples.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GraphReader {
    public static Graph getGraph(String path) {
        List<Edge> edges = new LinkedList<>();
        Integer dim = 0;
        try {
            Iterator<String> iterator = Files.lines(Paths.get(path)).iterator();
            while (iterator.hasNext()) {
                String[] list = iterator.next().split(" ");
                Integer listDim = list.length;
                Integer currentNode = Integer.parseInt(list[0]);
                dim++;
                for (int i = 1; i < listDim; i++) {
                    Integer nextNode = Integer.parseInt(list[i]);
                    if (!(currentNode > nextNode)) { // evita di inserire lo stesso arco due volte
                        Edge currentEdge = new Edge(currentNode, nextNode);
                        edges.add(currentEdge);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Graph(edges, dim);
    }
}