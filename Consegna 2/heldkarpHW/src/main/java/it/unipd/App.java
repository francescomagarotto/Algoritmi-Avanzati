package it.unipd;

import com.opencsv.CSVWriter;
import it.unipd.algorithms.TwoApproxAlgorithm;
import it.unipd.algorithms.HeldKarp;
import it.unipd.algorithms.NearestApprox;
import it.unipd.graph.GraphReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        final List<String[]> table = new ArrayList<String[]>();
        final HashMap<String, Integer> soluzioneOttima = new HashMap<>();
        soluzioneOttima.put("burma14.tsp", 3323);
        soluzioneOttima.put("ulysses16.tsp",6859);
        soluzioneOttima.put("ulysses22.tsp",7013);
        soluzioneOttima.put("eil51.tsp",426);
        soluzioneOttima.put("berlin52.tsp",7542);
        soluzioneOttima.put("kroD100.tsp",21294);
        soluzioneOttima.put("kroA100.tsp",21282);
        soluzioneOttima.put("ch150.tsp",6528);
        soluzioneOttima.put("gr202.tsp",40160);
        soluzioneOttima.put("gr229.tsp",134602);
        soluzioneOttima.put("pcb442.tsp",50778);
        soluzioneOttima.put("d493.tsp",35002);
        soluzioneOttima.put("dsj1000.tsp",18659688);
        AtomicInteger counter = new AtomicInteger(1);
        try (Stream<Path> paths = Files.walk(Paths.get("tsp_dataset"))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                String f = file.getFileName().toString();
                System.out.println("-------- " + counter.getAndIncrement() + " ---------");
                Integer solottima = soluzioneOttima.get(f);
                Integer dimension = GraphReader.getNodes("tsp_dataset/" + f);
                Integer[][] w = GraphReader.getGraph("tsp_dataset/" + f);
                //testHK(w, file);
                long twoApproxStart = System.currentTimeMillis();
                Integer twoapproxcost = TwoApproxAlgorithm.solve(0, w);
                Double twoApproxExTime = ((double) (System.currentTimeMillis() - twoApproxStart))/1000.0;
                long nearestStart = System.currentTimeMillis();
                Integer nearestcost = NearestApprox.solve(dimension, w);
                Double nearestExTime = ((double) (System.currentTimeMillis() - nearestStart))/1000.0;
                Double[] heldkarp = testHK(w, f);

                System.out.println("FILE: " + f +  "\tSOL. OTTIMA: " + solottima);
                System.out.println("HK: \n\tSOLUZIONE: " + heldkarp[0].intValue() + "\n\tTEMPO ESECUZIONE: " + heldkarp[1].toString() + "\n\tERRORE: (" + heldkarp[0].intValue() + " - " + solottima  + ")/" + solottima + " = " +  getError(heldkarp[0].intValue(), solottima));
                System.out.println("2APPROX: \n\tSOLUZIONE: " + twoapproxcost + "\n\tTEMPO ESECUZIONE: " + twoApproxExTime + "\n\tERRORE: (" + twoapproxcost + " - " + solottima  + ")/" + solottima + " = " + getError(twoapproxcost, solottima)) ;
                System.out.println("NEAREST: \n\tSOLUZIONE: " +nearestcost + "\n\tTEMPO ESECUZIONE: " + nearestExTime + "\n\tERRORE: (" + nearestcost + " - " + solottima  + ")/" + solottima + " = " + getError(nearestcost, solottima));
                System.out.print("\n\n");
                table.add(new String[]{f,
                        heldkarp[0].toString(), heldkarp[1].toString(), getError(heldkarp[0].intValue(), solottima),
                        nearestcost.toString(), nearestExTime.toString(), getError(nearestcost, solottima),
                        twoapproxcost.toString(), twoApproxExTime.toString(), getError(twoapproxcost, solottima)
                        });
            });
        }
        catch(Exception ignored) {

        }
        printFile("tabella.csv", table);
    }

    static String getError(Integer costo, Integer solottima) {
        double d = ((costo-solottima)/solottima.doubleValue());
        return Double.toString(Math.round(d * 100.0));
    }

    public static Double[] testHK(Integer[][] g, String file) {

        Runnable interruptHK = new Runnable() {
            @Override
            public void run() {
                System.out.println("Timeout for " + file);
                HeldKarp.interrupted.set(true);
            }
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(interruptHK, 300, TimeUnit.SECONDS);

        long t1 = System.currentTimeMillis();
        double res = new HeldKarp().HK_TSP(g);
        long t2 = System.currentTimeMillis();
        double elapsedSeconds = ((double) (t2 - t1)) / 1000.0;
        executor.shutdownNow();
        return new Double[] {res, elapsedSeconds};
    }
    private static void printFile(final String filename, final List<String[]> entries) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (Exception ignored) {
        }
        try (FileOutputStream fos = new FileOutputStream(filename);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(osw, ';', CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeAll(entries);
        } catch (IOException ignored) {
        }
    }
}
