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
    final List<String[]> kruskalUnionFindTimes = new ArrayList<String[]>();
    final List<String[]> kruskalTimes = new ArrayList<>();
    final List<String[]> primTimes = new ArrayList<>();
    int size = new File("datasets").listFiles().length;
    AtomicInteger counter = new AtomicInteger(1);
    try (Stream<Path> paths = Files.walk(Paths.get("mst_dataset"))) {
      paths.filter(Files::isRegularFile).forEach(file -> {
        String f = file.getFileName().toString();
        final Graph g = GraphReader.getGraph("mst_dataset/" + f);
        Integer numberVertex = g.getnVertex();

        // Benchmark Prim
        Long time = System.currentTimeMillis();
        Integer prim = Prim.solve(g, 1);
        Long temp = (System.currentTimeMillis() - time);
        primTimes.add(new String[] { f, numberVertex.toString(), temp.toString(), prim.toString() });

        // Benchmark Kruskal
        time = System.currentTimeMillis();
        Integer kruskal = Kruskal.MST(g);
        temp = (System.currentTimeMillis() - time);
        kruskalTimes.add(new String[] { f, numberVertex.toString(), temp.toString(), kruskal.toString() });

        // Benchmark Kruskal with UnionFind
        time = System.currentTimeMillis();
        Integer kruskalUF = KruskalUnionFind.KruskalMST(g);
        temp = (System.currentTimeMillis() - time);
        kruskalUnionFindTimes.add(new String[] { f, numberVertex.toString(), temp.toString(), kruskalUF.toString() });

        System.out.println("\n--------------" + counter.getAndIncrement() + "/" + size + "----------------");
        System.out.println((kruskal == prim && prim == kruskalUF) ? "\u2705" : "ERROR");
        System.out.println(f);
        System.out.println("Prim: " + prim);
        System.out.println("Kruskal: " + kruskal);
        System.out.println("Kruskal Union-Find: " + kruskalUF);
        System.out.println("----------------------------------");

      });
    } catch (final Exception e) {
      e.printStackTrace();
    }
    new Thread(() -> {
      printFile("kruskal.csv", kruskalTimes);
    }).start();
    new Thread(() -> {
      printFile("prim.csv", primTimes);
    }).start();
    new Thread(() -> {
      printFile("unionkruskal.csv", kruskalUnionFindTimes);
    }).start();
  }

  private static void printFile(final String filename, final List<String[]> entries) {
    try {
      Files.deleteIfExists(Paths.get(filename));
    } catch (Exception e) {
    }
    try (FileOutputStream fos = new FileOutputStream(filename);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        CSVWriter writer = new CSVWriter(osw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
      writer.writeAll(entries);
    } catch (IOException e) {
    }
  }
}