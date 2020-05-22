package it.unipd;

import it.unipd.algorithms.HeldKarp;
import it.unipd.algorithms.NearestApprox;
import it.unipd.graph.GraphReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        String file = "ch150.tsp";
        Integer dimension = GraphReader.getNodes("tsp_dataset/" + file);
        Integer[][] w = GraphReader.getGraph("tsp_dataset/" + file);

        testHK(w, file);

        //System.out.println("dimensione: " + dimension);
        //System.out.println("cammino:" + NearestApprox.solve(dimension, w));
        //Integer totalcost = primAdjMatrix.solve(0, w, dimension);
        //System.out.println(totalcost);
        /*
        for(int i = 0; i < w.length; ++i) {
            for(int j = 0; j < w.length; ++j) {
                System.out.print(w[i][j] + " ");
            }
            System.out.print("\n");
        }*/
    }

    public static void testHK(Integer[][] g, String file) {

        Runnable interruptHK = new Runnable() {
            @Override
            public void run() {
                System.out.println("Timeout");
                HeldKarp.interrupted.set(true);
            }
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(interruptHK, 30, TimeUnit.SECONDS);

        long t1 = System.currentTimeMillis();
        int res = new HeldKarp().HK_TSP(g);
        long t2 = System.currentTimeMillis();
        double elapsedSeconds = (t2 - t1) / 1000.0;
        System.out.println(file + " " + res + " " + elapsedSeconds);
        executor.shutdown();
    }

}
