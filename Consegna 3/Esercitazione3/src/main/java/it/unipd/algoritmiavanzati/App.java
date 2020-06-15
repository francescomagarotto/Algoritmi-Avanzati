package it.unipd.algoritmiavanzati;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
import it.unipd.algoritmiavanzati.graph.*;
import it.unipd.algoritmiavanzati.karger.*;

public class App {
    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        try {
            Stream<Path> paths = Files.walk(Paths.get("mincut_dataset"));
            paths.filter(Files::isRegularFile).forEach(file -> {
                String f = file.getFileName().toString();
                String[] split = f.split("input_random_");
                String outputString = "mincut_output/output_random_" + split[1];
                Integer output_;
                try {
                    outputString = Files.lines(Paths.get(outputString)).iterator().next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output_ = Integer.parseInt(outputString);
                Graph g = GraphReader.getGraph("mincut_dataset/" + f);
                int n = g.getnVertex();
                int log = (int) Math.log(n);
                Integer k = (Integer) n * n / 2 * log;

                KargerResult result = karger.Karger(g, 100);
                Integer resKarger = result.min;
                double discoveryTime = result.discoveryTime;

                System.out.print("file: " + f + " => ");
                if (resKarger.equals(output_))
                    System.out.println("taglio minimo trovato  "+"Discovery Time: "+discoveryTime);
                else {
                    Double error = ((double) resKarger - (double) output_) / (double) output_;
                    System.out.println("errore dello " + error + " %");
                }

            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        double end = System.currentTimeMillis() - start;
        System.out.println("tempo trascorso: " + end);
    }
}
