package edu.grinnell.csc207.util;

import java.util.Random;

/**
 * Utilities for working with arrays.
 *
 * @author Samuel A. Rebelsky
 */
public class ArrayUtils {
  // +---------------+-----------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A handy, dandy random-number generator.
   */
  static Random rand = new Random();

  // +----------------+----------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Swap two elements in an array.
   *
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array.
   * @param p
   *   The position of one element.
   * @param q
   *   The position of the other element.
   * @pre 0 *lt;= p,q &lt; values.length
   */
  public static <T> void swap(T[] values, int p, int q) {
    if (p == q) {
      return;
    } // if
    T tmp = values[p];
    values[p] = values[q];
    values[q] = tmp;
  } // swap(T[], int, int)

  /**
   * Randomly permute elements in an array.
   *
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array.
   */
  public static <T> void permute(T[] values) {
    for (int i = 0; i < values.length; i++) {
      swap(values, i, rand.nextInt(values.length));
    } // for
  } // permute(T[])

  /**
   * Convert a subset of an array to a string. Intended mostly for debug
   * printing in which we need to keep track of parts of an array.
   *
   * @param <T>
   *   The type of elements in the array.
   * @param values
   *   The array.
   * @param lb
   *   The lower bound of the section of interest.
   * @param ub
   *   The upper bound of the section of interest.
   * @return
   *    The string of the object T.
   */
  public static <T> String toString(T[] values, int lb, int ub) {
    if (lb >= ub) {
      return "[]";
    } // if
    StringBuilder result = new StringBuilder("[");
    result.append(lb);
    result.append(":");
    result.append(values[lb].toString());
    for (int i = lb + 1; i < ub; i++) {
      result.append(", ");
      result.append(i);
      result.append(":");
      result.append(values[i].toString());
    } // for
    result.append("]");
    return result.toString();
  } // toString(T[], int, int)
} // ArrayUtils
