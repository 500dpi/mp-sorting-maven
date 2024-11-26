package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Random;

import edu.grinnell.csc207.util.ArrayUtils;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 * @author Sara Jaljaa
 */

public class Quicksorter<T> implements Sorter<T> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

  /**
   * For finding the pivot.
   */
  Random rnd;

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
  public Quicksorter(Comparator<? super T> comparator) {
    this.order = comparator;
    this.rnd = new Random();
  } // Quicksorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using Quicksort.
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
    quicksort(values, 0, values.length);
  } // sort(T[])

  /**
   * Find the median of 3 values to pick a better pivot.
   *
   * @param values
   *    The array to sort.
   * @param lb
   *    The lower bound of the subarray (inclusive).
   * @param ub
   *    The upper bound of the subarray (exclusive).
   * @return
   *    The median pivot value.
   */
  @SuppressWarnings({"unchecked"})
  public T median(T[] values, int lb, int ub) {
    T[] pivot = (T[]) new Object[3];

    for (int i = 0; i < 3; i++) {
      pivot[i] = values[this.rnd.nextInt(ub - lb) + lb];
    } // for

    for (int i = 0; i < 2; i++) {
      if (this.order.compare(pivot[i + 1], pivot[i]) > 0) {
        ArrayUtils.swap(pivot, i, i + 1);
      } // if
    } // for
    return pivot[1];
  } // median(T[], int, int)

  /**
   * The recursive portion of the quicksort algorithm that
   * partitions each subarray.
   *
   * @param values
   *    The array to sort.
   * @param lb
   *    The lower bound of the subarray (inclusive).
   * @param ub
   *    The upper bound of the subarray (exclusive).
   */
  private void quicksort(T[] values, int lb, int ub) {
    if (lb >= ub) {
      return;
    } // if

    // For better pivot-picking
    T pivot = median(values, lb, ub);
    int[] bound = partition(values, pivot, lb, ub);

    // Recurse over subarrays containing elements less than the pivot
    // and elements greater than the pivot
    quicksort(values, lb, bound[0]);
    quicksort(values, bound[1], ub);
  } // quicksort(T[], int, int)

  /**
   * Partitions the array into two subarrays less than
   * and greater than the pivot using the (modified) Dutch
   * national flag algorithm.
   *
   * @param values
   *    The array to partition.
   * @param pivot
   *    The value to divide the array by.
   * @param lb
   *    The lower bound of the (sub)array, inclusive.
   * @param ub
   *    The upper bound of the (sub)array, exclusive.
   *
   * @return
   *    A 2-element array that holds the new upper bound for elements less than
   *    the pivot (at index = 0) and the new lower bound for elements greater
   *    than the pivot (at index = 1).
   */
  private int[] partition(T[] values, T pivot, int lb, int ub) {
    int red = lb;
    int white = lb;
    int blue = ub;

    while (white < blue) {
      int compare = this.order.compare(values[white], pivot);
      if (compare > 0) {
        ArrayUtils.swap(values, blue - 1, white);
        blue--;
      } else if (compare < 0) {
        ArrayUtils.swap(values, red, white);
        red++;
        white++;
      } else {
        white++;
      } // elif
    } // while
    return new int[] {red, white};
  } // partition(T[], int, int)
} // class Quicksorter
