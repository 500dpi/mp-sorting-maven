package edu.grinnell.csc207.sorting;

import java.util.Arrays;
import java.util.Comparator;
import edu.grinnell.csc207.main.SortTools;

/**
 * Something that sorts using a modified Merge sort algorithm that
 * limits uncessary calls to merge already-sorted arrays and switches
 * to insertion sort when the array is less than 75 elements.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Sara Jaljaa
 */

public class JaljaaSaraSorter<T> implements Sorter<T> {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The minimum size an array has to reach before switching from merge
   * sort to insertion sort.
   */
  private static final int LIMIT = 60;

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
  public JaljaaSaraSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // JaljaaSaraSorter(Comparator)

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

    // When the array is small enough, switch to insertion sort
    if (values.length <= LIMIT) {
      insertionSort(values);
    } else {
      merge(values, left, right, mid);
    } // elif
  } // sort(T[])

  /**
   * Merge subarrays together, checking if they arrays are pre-sorted before
   * merging.
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

    int compare = order.compare(left[lb - 1], right[0]);

    // Check if the array is already sorted
    if (compare <= 0) {
      for (; i < left.length; i++, tmp++) {
        values[tmp] = left[i];
      } // for

      for (; j < right.length; j++, tmp++) {
        values[tmp] = right[j];
      } // for
      return;
    } // if

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

  /**
   * Sort by inserting the next ordered value in the last sorted
   * position until all the elements are sorted.
   *
   * @param values
   *    The array to sort.
   */
  private void insertionSort(T[] values) {
    for (int unsorted = 1; unsorted < values.length; unsorted++) {
      insert(values, unsorted);
    } // for
  } // insertionSort(T[])

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
} // class JaljaaSaraSorter
