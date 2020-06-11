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
        try (Stream<Path> paths = Files.walk(Paths.get("mincut_dataset"))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                String f = file.getFileName().toString();
                Graph g = GraphReader.getGraph("mincut_dataset/" + f);
                Integer resKarger = karger.Karger(g, 10);
                System.out.println("min-cut for " + f + " = " + resKarger);
            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
