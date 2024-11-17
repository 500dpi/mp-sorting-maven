package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import edu.grinnell.csc207.main.SortTools;

/**
 * Something that sorts using selection sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 * @author Sara Jaljaa
 */

public class SelectionSorter<T> implements Sorter<T> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a sorter using a particular comparator.
   *
   * @param comparator
   *   The order in which elements in the array should be ordered
   *   after sorting.
   */
  public SelectionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // SelectionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using selection sort.
   *
   * @param values
   *   an array to sort.
   *
   * @post
   *   The array has been sorted according to some order (often
   *   one given to the constructor).
   * @post
   *   For all i, 0 &lt; i &lt; values.length,
   *     order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    if (values.length < 2) {
      return;
    } // if
    for (int unsorted = 0; unsorted < values.length - 1; unsorted++) {
      select(values, unsorted);
    } // for
  } // sort(T[])

  /**
   * Finds the next value to be ordered given an array and the previous
   * ordered element.
   *
   * @param values
   *    An array with elements.
   * @param unsorted
   *    The last sorted index/first unsorted index.
   */
  private void select(T[] values, int unsorted) {
    // To keep track of the next value to be ordered's index
    int nextSorted = unsorted;

    // Iterate through the array to find the next value to be ordered
    for (int sorted = unsorted; sorted < values.length; sorted++) {
      if (order.compare(values[nextSorted], values[sorted]) > 0) {
        nextSorted = sorted;
      } // if
    } // for

    // Swap the next ordered element to the first unsorted (or, last sorted)
    // index of the array
    SortTools.swap(values, nextSorted, unsorted);
  } // select(T[], int)
} // class SelectionSorter
