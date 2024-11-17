package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import edu.grinnell.csc207.main.SortTools;

/**
 * Something that sorts using insertion sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 * @author Sara Jaljaa
 */

public class InsertionSorter<T> implements Sorter<T> {

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
  public InsertionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // InsertionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using insertion sort.
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
    for (int unsorted = 1; unsorted < values.length; unsorted++) {
      insert(values, unsorted);
    } // for
  } // sort(T[])

  /**
   * Find the next walue to insert into the array.
   *
   * @param values
   *    The array of elements.
   * @param unsorted
   *    The beginning of the unsorted index of the array.
   */
  private void insert(T[] values, int unsorted) {
    for (int sorted = unsorted; sorted > 0
        && (order.compare(values[sorted - 1], values[sorted]) > 0);) {
      SortTools.swap(values, (sorted - 1), sorted);
      sorted--;
    } // for
  } // insert(T[])
} // class InsertionSorter
