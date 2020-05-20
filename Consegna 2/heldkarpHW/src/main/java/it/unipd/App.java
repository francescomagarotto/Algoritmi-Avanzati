package it.unipd;

import it.unipd.algorithms.HeldKarp;
import it.unipd.graph.GraphReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String file = "burma14.tsp";
        Integer[][] w = GraphReader.getGraph("tsp_dataset/"+file);
        /*
        for(int i = 0; i < w.length; ++i) {
            for(int j = 0; j < w.length; ++j) {
                System.out.print(w[i][j] + " ");
            }
            System.out.print("\n");
        }*/

        long t1 = System.currentTimeMillis();
        int res = new HeldKarp().HK_TSP(w);
        long t2 = System.currentTimeMillis();
        double elapsedSeconds = (t2-t1)/1000.0;
        System.out.println(file + " " + res + " " + elapsedSeconds);
    }
}
