package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Arrays;

/**
 * Something that sorts using merge sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 * @author Sara Jaljaa
 */

public class MergeSorter<T> implements Sorter<T> {

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
  public MergeSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // MergeSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using merge sort.
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
    // Check if there are enough elements to sort
    if (values.length < 2) {
      return;
    } // if

    // Find the midpoint, then create two subarrays for the halves
    int mid = values.length / 2;
    T[] left = Arrays.copyOfRange(values, 0, mid);
    T[] right = Arrays.copyOfRange(values, mid, values.length);

    // Recursively sort each subarray
    sort(left);
    sort(right);

    // Merge the subarrays together
    merge(values, left, right, mid);
  } // sort(T[])

  /**
   * Merge the subarrays together.
   *
   * @param values
   *    The merged array.
   * @param left
   *    The left subarray.
   * @param right
   *    The right subarray.
   * @param midpoint
   *    The midpoint of the values array that split it into left and right.
   */
  private void merge(T[] values, T[] left, T[] right, int midpoint) {
    int tmp = 0;
    int i = 0;
    int j = 0;

    // Bounds for the left and right subarrays
    int lb = midpoint;
    int rb = values.length - midpoint;

    // Add elements in order by comparing elements of
    // each subarray; if an element is larger, it is compared to
    // the next element of the other subarray
    for (; i < lb && j < rb; tmp++) {
      if (order.compare(left[i], right[j]) < 0) {
        values[tmp] = left[i];
        i++;
      } else {
        values[tmp] = right[j];
        j++;
      } // elif
    } // for

    // Add the remaining left elements (if any)
    for (; i < lb; i++, tmp++) {
      values[tmp] = left[i];
    } // for

    // Add the remaining right elements (if any)
    for (; j < rb; j++, tmp++) {
      values[tmp] = right[j];
    } // for
  } // merge(T[], T[], T[], int)
} // class MergeSorter
