package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of our FakeSorter.
 */
public class TestFakeSorter extends TestSorter {
  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new FakeSorter<String>((x, y) -> x.compareTo(y));
    intSorter = new FakeSorter<Integer>((x, y) -> x.compareTo(y));
  } // setup()

} // class TestFakeSorter
