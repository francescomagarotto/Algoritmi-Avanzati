package it.unipd.algoritmiavanzati;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * Hello world!
 *
 */
import it.unipd.algoritmiavanzati.graph.*;
import it.unipd.algoritmiavanzati.karger.*;

public class App {
    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        final List<String[]> table = new ArrayList<String[]>();
        try {
            Stream<Path> paths = Files.walk(Paths.get("mincut_dataset"));
            paths.filter(Files::isRegularFile).forEach(file -> {
                String f = file.getFileName().toString();
                String[] split = f.split("input_random_");
                String index = split[1].split("_")[0]; //numero di indice del grafo
                String outputString = "mincut_output/output_random_" + split[1]; // per estrarre il risultato dal corrispondente file di output
                Integer output_;
                try {
                    outputString = Files.lines(Paths.get(outputString)).iterator().next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output_ = Integer.parseInt(outputString); // risultato atteso, letto dal file
                Graph g = GraphReader.getGraph("mincut_dataset/" + f);
                int n = g.getnVertex(); // numero vertici
                Integer k = (int) (((n * n) / 2) * Math.log(n)); //numero iterazioni
                double timeout = 60000;
                KargerResult result = karger.Karger(g, k, timeout);
                Integer resKarger = result.min;
                double discoveryTime = result.discoveryTime;

                System.out.print("file: " + f + " => ");
                if (resKarger.equals(output_))
                    System.out.println("taglio minimo trovato  " + "Discovery Time: " + discoveryTime);
                Double error = ((double) resKarger - (double) output_) / (double) output_;
                System.out.println("errore dello " + error + " %");

                table.add(new String[] { index, String.valueOf(n), String.valueOf(k), String.valueOf(output_),
                        String.valueOf(result.totalTime), String.valueOf(result.discoveryTime),
                        String.valueOf(error) });
            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        printFile("tabella.csv", table);
        double end = System.currentTimeMillis() - start;
        System.out.println("tempo trascorso: " + end);
    }

    private static void printFile(final String filename, final List<String[]> entries) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (Exception ignored) {
        }
        try (FileOutputStream fos = new FileOutputStream(filename);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw, ',', CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(entries);
        } catch (IOException ignored) {
        }
    }
}
