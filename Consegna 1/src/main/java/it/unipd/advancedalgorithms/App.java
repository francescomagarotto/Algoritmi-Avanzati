package it.unipd.advancedalgorithms;

import com.opencsv.CSVWriter;
import it.unipd.advancedalgorithms.algorithms.Kruskal;
import it.unipd.advancedalgorithms.algorithms.KruskalUnionFind;
import it.unipd.advancedalgorithms.algorithms.Prim;
import it.unipd.advancedalgorithms.graph.Graph;
import it.unipd.advancedalgorithms.graph.GraphReader;

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

public class App {
    public static void main(final String[] args) throws Exception {
        String[] header = new String[]{"nodi", "tempo", "mst"};
        final List<String[]> kruskalUnionFindTimes = new ArrayList<String[]>();
        final List<String[]> kruskalTimes = new ArrayList<>();
        final List<String[]> primTimes = new ArrayList<>();
        kruskalUnionFindTimes.add(header);
        kruskalTimes.add(header);
        primTimes.add(header);
        int size = new File("mst_dataset").listFiles().length;
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
                primTimes.add(new String[]{numberVertex.toString(), temp.toString(), prim.toString()});

                // Benchmark Kruskal
                time = System.currentTimeMillis();
                Integer kruskal = Kruskal.MST(g);
                temp = (System.currentTimeMillis() - time);
                kruskalTimes.add(new String[]{numberVertex.toString(), temp.toString(), kruskal.toString()});

                // Benchmark Kruskal with UnionFind
                time = System.currentTimeMillis();
                Integer kruskalUF = KruskalUnionFind.KruskalMST(g);
                temp = (System.currentTimeMillis() - time);
                kruskalUnionFindTimes.add(new String[]{numberVertex.toString(), temp.toString(), kruskalUF.toString()});

                System.out.println("\n--------------" + counter.getAndIncrement() + "/" + size + "----------------");
                System.out.println((kruskal.equals(prim) && prim.equals(kruskalUF)) ? "OK" : "ERROR");
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