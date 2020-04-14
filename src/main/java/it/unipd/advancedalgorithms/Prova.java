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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import it.unipd.advancedalgorithms.graph.*;
import it.unipd.advancedalgorithms.algorithms.*;

public class Prova {
  public static void main(final String[] args) throws Exception{
    Thread kruskalThread = new Thread(new Kruskal());
    Thread primThread = new Thread(new Prim());
    Thread kruskalUnionFindThread = new Thread(new KruskalUnionFind());
    kruskalThread.start();
    primThread.start();
    kruskalUnionFindThread.start();
  }

}