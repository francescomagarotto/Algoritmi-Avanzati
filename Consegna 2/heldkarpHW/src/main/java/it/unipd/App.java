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
        Integer[][] w = GraphReader.getGraph("tsp_dataset/gr229.tsp");
        for(int i = 0; i < 230; ++i) {
            for(int j = 0; j < 230; ++j) {
                System.out.print(w[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
