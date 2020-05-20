package it.unipd.graph;

import org.javatuples.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Math.PI;

public class GraphReader {
    public static Integer[][] getGraph(String path) {
        try {
            boolean geo = false;
            List<String> file = Files.readAllLines(Paths.get(path));
            int dimension = Integer.parseInt(file.get(3).split(" ")[1]);
            Integer[][] weight = new Integer[dimension+1][dimension+1];
            Arrays.stream(weight).forEach(a -> Arrays.fill(a, 0));
            if(file.get(4).equals("EDGE_WEIGHT_TYPE: GEO")) geo = true;
            Iterator<String> cords = file.listIterator(7);
            while(cords.hasNext()) {
                String[] line = cords.next().trim().split(" ");
                if(line[0].equals("EOF")) continue;
                int currentVertex = Integer.parseInt(line[0]);
                double longitude1 = Double.parseDouble(line[2]);
                double latitude1 = Double.parseDouble(line[1]);
                Iterator<String> subcords = file.listIterator(7);
                while(subcords.hasNext()) {
                    String[] subline = subcords.next().trim().split(" ");
                    if(subline[0].equals("EOF")) continue;
                    int nextVertex = Integer.parseInt(subline[0]);
                    if(nextVertex != currentVertex) {
                        double longitude2 = Double.parseDouble(line[2]);
                        double latitude2 = Double.parseDouble(line[1]);
                        int costo = 0;
                        if(geo) {
                            costo = geoToWeight(
                                    degToRad(longitude1),
                                    degToRad(longitude2),
                                    degToRad(latitude1),
                                    degToRad(latitude2)
                            );
                        }
                        else {
                              costo = (int) Math.sqrt(Math.pow(latitude1 - latitude2, 2) + Math.pow(longitude1 - longitude2, 2));
                        }
                        weight[currentVertex][nextVertex] = costo;
                        weight[nextVertex][currentVertex] = costo;
                    }
                    else {
                        weight[currentVertex][nextVertex] = 0;
                    }
                }
            return  weight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Integer[0][0];
    }

    static double degToRad(double value) {
        int deg = (int) value;
        double min = value - deg;
        return PI * (deg + 5.0 * min/ 3.0) / 180.0;
    }

    static int geoToWeight(double longitude1, double longitude2, double latitude1, double latitude2) {
        final double RRR = 6378.388;
        double q1 = Math.cos( longitude1 - longitude2 );
        double q2 = Math.cos( latitude1 - latitude2 );
        double q3 = Math.cos( latitude1 + latitude2 );
        return (int) ( RRR * Math.acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
    }
}

