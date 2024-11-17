package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.sorting.Sorter;

import java.io.PrintWriter;

import java.util.Arrays;

/**
 * Some tools for quick experiments with sorting routines.
 *
 * @author Samuel A. Rebelsky.
 */
public class SortExperiments {
  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Run an experiment on a single array.
   *
   * @param pen
   *   For printing out info.
   * @param values
   *   The array of values to sort.
   * @param sorter
   *   The sorter to use.
   */
  public static <T> void oneExperiment(PrintWriter pen, T[] values,
      Sorter<T> sorter) {
    pen.printf("sort(%s) -> ", Arrays.toString(values));
    pen.flush();
    sorter.sort(values);
    pen.printf("%s\n\n", Arrays.toString(values));
  } // sortExperiment(PrintWriter, String[], Sorter<String>)

  /**
   * Run multiple experiments.
   *
   * @param pen
   *   For printing out info.
   * @param sorter
   *   The sorter to use.
   */
  public static void manyExperiments(PrintWriter pen,
      Sorter<Comparable> sorter) {
    // A singleton array.
    oneExperiment(pen, new String[] {"a"}, sorter);
    // An array of integers that gave same trouble.
    oneExperiment(pen,
        new Integer[] {28, 37, 20, 55, 10, 17, 9, 17},
        sorter);
  } // sortExperiments(PrintWriter, Sorter<String>)

} // class SortExperiments
