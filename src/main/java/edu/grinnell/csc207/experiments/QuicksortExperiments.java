package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.sorting.Quicksorter;
import edu.grinnell.csc207.sorting.Sorter;

import java.io.PrintWriter;

import java.util.Comparator;

/**
 * Some quick experiments with Quicksort.
 *
 * @author Samuel A. Rebelsky.
 */
public class QuicksortExperiments {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Sort various arrays.
   *
   * @param args
   *   Command-line arguments; one of the arrays we sort.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Comparator<Comparable> order = (x, y) -> x.compareTo(y);
    Sorter<Comparable> sorter = new Quicksorter(order);
    SortExperiments.manyExperiments(pen, sorter);
    SortExperiments.oneExperiment(pen, args, sorter);
    pen.close();
  } // main(String[])
} // class QuicksortExperiments
