package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of our SelectionSorter.
 */
public class TestSelectionSorter extends TestSorter {

  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new SelectionSorter<String>((x, y) -> x.compareTo(y));
    intSorter = new SelectionSorter<Integer>((x, y) -> x.compareTo(y));
  } // setup()
} // class TestSelectionSorter
