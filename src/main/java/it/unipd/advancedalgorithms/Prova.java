package it.unipd.advancedalgorithms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) throws Exception {
    final List<String[]> kruskalTimes = new ArrayList<>();
    int size = new File("datasets").listFiles().length;
    AtomicInteger counter = new AtomicInteger(1);
    try (Stream<Path> paths = Files.walk(Paths.get("datasets"))) {
      paths.filter(Files::isRegularFile).forEach(file -> {
        String f = file.getFileName().toString();
        Long init = System.currentTimeMillis();
        final Graph g = GraphReader.getGraph("datasets/" + f);
        System.out.print("Tempo per realizzare il grafo: " + (System.currentTimeMillis() - init));
        Integer numberVertex = g.getnVertex();
        // Benchmark Kruskal
        Long time = System.currentTimeMillis();
        int kruskal = Kruskal.MST(g);
        Long temp = System.currentTimeMillis() - time;
        kruskalTimes.add(new String[] { f, numberVertex.toString(), temp.toString() });
        Path path = Paths.get("DatasetsOutput/" + file.getFileName().toString().replace("input", "output"));
        int outputres = 0;
        try {
          outputres = Integer.parseInt(Files.lines(path).iterator().next());// print each line
        } catch (IOException ex) {
          ex.printStackTrace();// handle exception here
        }
        System.out.println("\n--------------"+ counter.getAndIncrement() + "/" + size + "----------------");
        System.out.println((kruskal == outputres) ? "\u2705" : "ERROR");
        System.out.println(f);
        System.out.println("Kruskal: " + kruskal);
        System.out.println("Real output: " + outputres);
        System.out.println("----------------------------------");

      });
    } catch (final Exception e) {
      e.printStackTrace();
    }
    printFile("kruskal.csv", kruskalTimes);
  }

  private static void printFile(final String filename, final List<String[]> entries){
    try {
      Files.deleteIfExists(Paths.get(filename));
    } catch (Exception e) {}
    try (FileOutputStream fos = new FileOutputStream(filename);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        CSVWriter writer = new CSVWriter(osw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
      writer.writeAll(entries);
    }
    catch(IOException e) {}
  }
}