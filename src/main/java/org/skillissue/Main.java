package org.skillissue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {



  public static void main(String[] args) {

    int numberThreads = Runtime.getRuntime().availableProcessors();

    welcomeMessage(numberThreads);

    ExecutorService executor = Executors.newFixedThreadPool(numberThreads);

    Solver solver = new Solver();

    for (int i = 0; i < numberThreads; i++) {
      executor.execute(solver);
    }
    executor.shutdown();

  }

  private static void welcomeMessage(int numberThreads) {
    System.out.println("Welcome to SudokuSolver");

    System.out.println();

    System.out.println("Number of threads available: " + numberThreads);

    System.out.println();
    System.out.println();

    System.out.println("Solving Sudokus....");


  }
}

