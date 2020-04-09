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
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {

  public static void main(final String[] args) throws Exception {
    List<String[]> kruskalUnionFindTimes = new ArrayList<String[]>();
    List<String[]> kruskalTimes = new ArrayList<>();
    List<String[]> primTimes = new ArrayList<>();
    try (Stream<Path> paths = Files.walk(Paths.get("datasets"))) {
      paths.filter(Files::isRegularFile).forEach(file -> {
        Graph g = GraphReader.getGraph("datasets/" + file.getFileName().toString());
        Integer numberEdges = g.getEdges().size();
        Long primStartTime = System.nanoTime();
        Prim.solve(g, 1);
        Long primEndtTime = primStartTime - System.nanoTime();
        primTimes.add(new String[] {numberEdges.toString(), primEndtTime.toString()});

        Long kruskalStartTime = System.nanoTime();
        Kruskal.MST(g);
        Long kruskalEndtTime = kruskalStartTime - System.nanoTime();
        kruskalTimes.add(new String[] {numberEdges.toString(), kruskalEndtTime.toString()});

        long kruskalUStartTime = System.nanoTime();
        KruskalUnionFind.KruskalMST(g);
        Long kruskalUEndtTime = kruskalUStartTime - System.nanoTime();
        kruskalUnionFindTimes.add(new String[] {numberEdges.toString(), kruskalUEndtTime.toString()});

      });
    } catch (Exception e) {
    }

    printFile("kruskal.csv", kruskalTimes);
    printFile("prim.csv", primTimes);
    printFile("unionkruskal.csv", kruskalUnionFindTimes);
  }

  private static void printFile(String filename, List<String[]> entries) throws IOException {
    Files.deleteIfExists(Paths.get(filename));
    try (FileOutputStream fos = new FileOutputStream(filename);
                OutputStreamWriter osw = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(entries);
        }
  }
}