package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of our MergeSorter.
 */
public class TestMergeSorter extends TestSorter {
  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new MergeSorter<String>((x, y) -> x.compareTo(y));
    intSorter = new MergeSorter<Integer>((x, y) -> x.compareTo(y));
  } // setup()

} // class TestMergeSorter
