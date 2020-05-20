package it.unipd.algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NearestApprox {

    public static Integer solve(Integer n, Integer[][] graph) {
        List<Integer> visited = new LinkedList<Integer>();
        visited.add(0);
        List<Integer> notVisited = new LinkedList<Integer>();
        for (int i = 1; i < n; i++)
            notVisited.add(i);
        int currentVertex = 0;
        int sumOfPath = 0;
        while (!notVisited.isEmpty()) {
            Iterator<Integer> notVisitedIterator = notVisited.iterator();
            Integer minDistance = Integer.MAX_VALUE;
            Integer nextNode = -1;
            while (notVisitedIterator.hasNext()) {
                Integer node = notVisitedIterator.next();
                if (graph[currentVertex][node] <= minDistance) {
                    minDistance = graph[currentVertex][node];
                    nextNode = node;
                }
            }
            visited.add(nextNode);
            notVisited.remove(nextNode);
            sumOfPath += graph[currentVertex][nextNode];
        }
        return sumOfPath;
    }
}