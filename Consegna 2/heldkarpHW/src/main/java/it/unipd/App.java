package it.unipd;

import it.unipd.graph.GraphReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Integer[][] w = GraphReader.getGraph("tsp_dataset/ch150.tsp");
        for(int i = 0; i < w.length; ++i) {
            for(int j = 0; j < w.length; ++j) {
                System.out.print(w[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
