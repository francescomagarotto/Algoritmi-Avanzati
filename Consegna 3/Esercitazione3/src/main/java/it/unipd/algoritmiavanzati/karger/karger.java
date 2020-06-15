package it.unipd.algoritmiavanzati.karger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unipd.algoritmiavanzati.graph.*;

public class karger {

    public static KargerResult Karger(Graph g, int k) {
        Integer min = Integer.MAX_VALUE;
        Integer t;
        long startTime = System.currentTimeMillis();
        long discoveryTime = startTime;

        for (int i = 0; i < k; i++) {
            t = FullContraction(g);
            if (t < min) {
                min = t;
                discoveryTime = System.currentTimeMillis();
            }
        }

        return new KargerResult(min, discoveryTime-startTime);
    }

    public static Integer FullContraction(Graph g) {
        List<Edge> copyOfEdges = g.getEdges();

        Integer numberOfNodes = g.getnVertex();
        Integer nextNodeLabel = numberOfNodes + 1;
        Random rand = new Random();
        for (int i = 0; i < numberOfNodes - 2; i++) {
            int randomIndex = rand.nextInt(copyOfEdges.size());
            Integer u = copyOfEdges.get(randomIndex).getStart();
            Integer v = copyOfEdges.get(randomIndex).getEnd();

            Contraction(copyOfEdges, u, v, nextNodeLabel);
            nextNodeLabel++;
        }
        return copyOfEdges.size();

    }

    public static void Contraction(List<Edge> copyOfEdge, Integer u, Integer v, Integer nextNodeLabel) {

        Iterator<Edge> iterator = copyOfEdge.iterator();
        while (iterator.hasNext()) { //rimuovo tutti gli archi tra u e v
            Edge e = iterator.next();
            if ((e.getStart().equals(v) && e.getEnd().equals(u)))
                iterator.remove();
            if ((e.getStart().equals(u) && e.getEnd().equals(v)))
                iterator.remove();
        }

        iterator = copyOfEdge.iterator();
        while (iterator.hasNext()) { //i nodi collegati ai cancellati li connetto al nuovo nodo nextNodeLabel
            Edge e = iterator.next();
            if (e.getStart().equals(v) || e.getStart().equals(u))
                e.setStart(nextNodeLabel);
            if (e.getEnd().equals(u) || e.getEnd().equals(v))
                e.setEnd(nextNodeLabel);
        }
    }

}