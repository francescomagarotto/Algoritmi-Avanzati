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

public class Prova {
    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(Paths.get("tsp_dataset"))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                String f = file.getFileName().toString();
                Integer dimension = GraphReader.getNodes("tsp_dataset/" + f);
                Integer[][] w = GraphReader.getGraph("tsp_dataset/" + f);
                Integer nearestcost = NearestApprox.solve(dimension, w);
                System.out.println("costo di " + f + ": " + nearestcost);
                Integer twoapproxcost = TwoApproxAlgorithm.solve(0, w);
                System.out.println("costo di 2-approx " + f + ": " + twoapproxcost);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}