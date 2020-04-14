package it.unipd.advancedalgorithms;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) throws Exception, InterruptedException, ExecutionException {
    final List<String[]> kruskalUnionFindTimes = new ArrayList<String[]>();
    final List<String[]> kruskalTimes = new ArrayList<>();
    final List<String[]> primTimes = new ArrayList<>();
    /*
     * Graph g = GraphReader.getGraph("datasets/prova.txt");
     * System.out.println("Kruskal: " + Kruskal.MST(g));
     */
    try (Stream<Path> paths = Files.walk(Paths.get("datasets"))) {
      paths.filter(Files::isRegularFile).forEach(file -> {
        String f = file.getFileName().toString();
        final Graph g = GraphReader.getGraph("datasets/" + f);
        Integer numberVertex = g.getnVertex();
        CompletableFuture<Integer> completableFuturePrim = new CompletableFuture<>();
        CompletableFuture<Integer> completableFutureKruskal = new CompletableFuture<>();
        CompletableFuture<Integer> completableFutureKruskalUnionFind = new CompletableFuture<>();

        new Thread(new Runnable() {
          @Override
          public void run() {
            // computation, reading input streams, etc
            Integer result = Prim.solve(g, 1);
            completableFuturePrim.complete(result);
          }
        }).start();

        new Thread(new Runnable() {
          @Override
          public void run() {
            // computation, reading input streams, etc
            Integer result = Kruskal.MST(g);
            completableFutureKruskal.complete(result);
          }
        }).start();

        new Thread(new Runnable() {
          @Override
          public void run() {
            // computation, reading input streams, etc
            Integer result = KruskalUnionFind.KruskalMST(g);
            completableFutureKruskalUnionFind.complete(result);
          }
        }).start();

        // Benchmark Prim
        Long time = System.nanoTime();
        Integer prim = 0;
        try {
          prim = completableFuturePrim.get();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        Long temp = (System.nanoTime() - time) / 1000000;
        primTimes.add(new String[] { f, numberVertex.toString(), temp.toString() });

        // Benchmark Kruskal
        time = System.nanoTime();
        Integer kruskal = 0;
        try {
          kruskal = completableFutureKruskal.get();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        temp = (System.nanoTime() - time) / 1000000;
        kruskalTimes.add(new String[] { f, numberVertex.toString(), temp.toString() });

        // Benchmark Kruskal with UnionFind
        time = System.nanoTime();
        Integer kruskalUF = 0;
        try {
          kruskalUF = completableFutureKruskalUnionFind.get();
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
        temp = (System.nanoTime() - time) / 1000000;
        kruskalUnionFindTimes.add(new String[] { f, numberVertex.toString(), temp.toString() });
        Path path = Paths.get("DatasetsOutput/" + file.getFileName().toString().replace("input", "output"));
        int outputres = 0;
        try {
          outputres = Integer.parseInt(Files.lines(path).iterator().next());// print each line
        } catch (IOException ex) {
          ex.printStackTrace();// handle exception here
        }
        System.out.println("\n----------------------------------");
        System.out.println(f);
        System.out.println("Prim: " + kruskalUF);
        System.out.println("Kruskal: " + kruskal);
        System.out.println("Kruskal Union-Find: " + kruskalUF);
        System.out.println("Real output: " + outputres);
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