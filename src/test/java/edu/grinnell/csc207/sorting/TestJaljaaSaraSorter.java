package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of JaljaaSaraSorter (individually created algorithm).
 */
public class TestJaljaaSaraSorter extends TestSorter {

  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new JaljaaSaraSorter<String>((x, y) -> x.compareTo(y));
    intSorter = new JaljaaSaraSorter<Integer>((x, y) -> x.compareTo(y));
  } // setup()

} // class JaljaaSaraSorter
