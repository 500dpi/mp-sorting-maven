package edu.grinnell.csc207.main;

import edu.grinnell.csc207.sorting.Sorter;
import edu.grinnell.csc207.util.SimpleTimer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import java.io.PrintWriter;

import java.lang.reflect.Constructor;

/**
 * Tools for exploring sorting implementations.
 */
public class SortTools {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * What is long enough for the basic test?
   */
  static final long MIN_USEFUL_TIME = 300;

  /**
   * What is too long for the basic test? (unused)
   */
  static final long MAX_USEFUL_TIME = 2000;

  /**
   * The number of rounds in the competition.
   */
  static final long ROUNDS = 10;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * Our random number generator.
   */
  static Random rand;

  /**
   * Prefixes for our sorters.
   */
  static final String[] prefixes =
      new String[] {"", "edu.grinnell.csc207.sorting.",
          "edu.grinnell.csc207.util."};

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run the program.
   *
   * @param args
   *   Command-line arguments. See the help document for details.
   */
  public static void main(String[] args) {
    rand = new Random();
    // Ensure that we have enough arguments.
    if (args.length < 2) {
      help();
      return;
    } // if

    // Grab the sort classes
    boolean ok = true;
    Sorter<Comparable>[] sorters = new Sorter[args.length - 1];
    for (int i = 0; i < sorters.length; i++) {
      sorters[i] = getSorter(args[i + 1]);
      if (sorters[i] == null) {
        ok = false;
      } // if
    } // for
    if (!ok) {
      System.err.println("Failed to load all the sorters.  Exiting.");
      System.exit(2);
      return;
    } // if (!ok)

    // For now, we're printing to standard output.  Eventually,
    // we may want to print to a log file for each sorter.
    PrintWriter pen = new PrintWriter(System.out, true);

    // Decide what command to try
    switch (args[0]) {
      case "time":
        for (Sorter sorter : sorters) {
          time(sorter, pen);
        } // for
      case "test":
        for (Sorter sorter : sorters) {
          if (!test(sorter, pen)) {
            System.err.println(sorter.getClass().getName() + " FAILED");
          } // if
        } // for
        break;
      case "compete":
        compete(sorters, pen);
        break;
      default:
        System.err.println("Invalid command: '" + args[0] + "'");
        help();
        System.exit(1);
    } // switch
  } // main(String[])

  // +---------+-----------------------------------------------------
  // | Actions |
  // +---------+

  /**
   * Test a sorter on a series of random arrays.
   *
   * @param s
   *    The sorter.
   * @param pen
   *    The PrintWriter to write with.
   * @return
   *    A boolean.
   */
  static boolean test(Sorter s, PrintWriter pen) {
    if (pen != null) {
      pen.println("Testing of " + s.getClass().getName());
    } // if

    Comparator<Comparable> order = (x, y) -> x.compareTo(y);

    String[] strings;

    // Empty array (does not need to pass this, but ...).
    strings = new String[0];
    try {
      s.sort(strings);
      if (pen != null) {
        pen.println("  Successfully sorted empty array.");
      } // if
    } catch (Exception e) {
      if (pen != null) {
        pen.println("  Crashed on sorting empty array.");
      } // if
    } // try/catch

    // Singleton array
    // if (pen != null) { pen.println("  Sorting singleton array."); }
    strings = new String[] {"a"};
    s.sort(strings);
    if (!Arrays.equals(strings, new String[] {"a"})) {
      if (pen != null) {
        pen.println("  Failed to sort singleton array.");
      } // if
      return false;
    } // if

    // Array of identical values.
    int size = 70;
    // if (pen != null) { pen.println("  Sorting identical array."); }
    strings = new String[size];
    for (int i = 0; i < 70; i++) {
      strings[i] = "eh";
    } // for
    String[] strings2 = strings.clone();
    s.sort(strings2);
    if (!Arrays.equals(strings, strings2)) {
      if (pen != null) {
        pen.println("  Failed to sort array of identical values.");
      } // if
      return false;
    } // if arrays are not equal

    // Many sets of randomized tests
    for (int round = 0; round < 20; round++) {
      size = 50 + rand.nextInt(50);
      Integer[] sorted = sampleSortedArray(size);
      Integer[] result = sorted.clone();

      // if (pen != null) { pen.println("  Round " + round + "."); }

      // Test 1: Already in order
      s.sort(result);
      if (!Arrays.equals(sorted, result)) {
        if (pen != null) {
          pen.println("  Failed on already-sorted array.");
          pen.println("  source: " + Arrays.toString(sorted));
          pen.println("  result: " + Arrays.toString(result));
        } // if pen
        return false;
      } // if sorting failed

      // Test 2: In reverse order
      Integer[] source = Arrays.copyOf(sorted, size);
      reverse(source);
      result = Arrays.copyOf(source, size);
      s.sort(result);
      if (!Arrays.equals(sorted, result)) {
        if (pen != null) {
          pen.println("  Failed on reverse-sorted array.");
          pen.println("  source: " + Arrays.toString(source));
          pen.println("  result: " + Arrays.toString(result));
          pen.println("  sorted: " + Arrays.toString(sorted));
        } // if pen
        return false;
      } // if sorting failed

      // Test 3: In random order
      permute(source);
      result = Arrays.copyOf(source, size);
      s.sort(result);
      if (!Arrays.equals(sorted, result)) {
        if (pen != null) {
          pen.println("  Failed on permuted array.");
          pen.println("  source: " + Arrays.toString(source));
          pen.println("  result: " + Arrays.toString(result));
          pen.println("  sorted: " + Arrays.toString(sorted));
        } // if pen
        return false;
      } // if sorting failed
    } // for

    if (pen != null) {
      pen.println("  SUCCESS!");
    } // if pen
    return true;
  } // test(Sorter, PrintWriter)

  /**
   * Time a sorter on a series of random arrays until it takes
   * more than MIN_USEFUL_TIME milliseconds.
   *
   * @param s
   *    The sorter.
   * @param pen
   *    The PrintWriter to write with.
   * @return
   *    A time.
   */
  static int time(Sorter s, PrintWriter pen) {
    long time = 0;
    int size = 1000;

    if (pen != null) {
      pen.println("Timing of " + s.getClass().getName());
      pen.println("\tSize\tTime (in milliseconds)");
    } // if pen

    do {
      Integer[] sorted = sampleSortedArray(size);
      Integer[] source = Arrays.copyOf(sorted, size);
      permute(source);
      SimpleTimer st = new SimpleTimer();
      s.sort(source);
      time = st.elapsed();
      if (pen != null) {
        pen.printf("\t%d\t%d\n", size, time);
      } // if pen
      size = size * 2 + rand.nextInt(10);
    } while ((time < MIN_USEFUL_TIME) && (size < Integer.MAX_VALUE / 4));

    return size;
  } // time(Sorter, PrintWriter)

  /**
   * Compare a bunch of sorters.
   *
   * @param sorters
   *    The sorters for each algorithm.
   * @param pen
   *    The pen to write with.
   *
   * @return
   *    The best sorter.
   */
  static Sorter compete(Sorter[] sorters, PrintWriter pen) {
    // Set up the comparator
    Comparator<Integer> order = (x, y) -> x.compareTo(y);

    // Which ones are ok (testing)
    if (pen != null) {
      pen.println("Testing sorters");
    } // if pen
    boolean[] ok = new boolean[sorters.length];
    for (int i = 0; i < sorters.length; i++) {
      ok[i] = test(sorters[i], null);
      if (!ok[i]) {
        System.err.println(sorters[i].getClass().getName() + " FAILED!");
      } // if the ith sorter is not ok
    } // for

    // Find useful sizes
    if (pen != null) {
      pen.println("Computing useful array sizes");
    } // if pen
    int[] sizes = new int[sorters.length];
    int size = Integer.MAX_VALUE;
    long sum = 0;
    int count = 0;
    for (int i = 0; i < sorters.length; i++) {
      if (ok[i]) {
        sizes[i] = time(sorters[i], null);
        sum += sizes[i];
        count += 1;
        if (sizes[i] < size) {
          size = sizes[i];
        } // if
      } // if
    } // for
    // int size = (int) (sum / count);

    // Set up the times
    long[] times = new long[sorters.length];
    for (int i = 0; i < sorters.length; i++) {
      times[i] = 0;
    } // for

    // Run the rounds of the competition
    for (int round = 1; round <= ROUNDS; round++) {
      int tmpsize = (3 * size) / 4 + rand.nextInt(size / 2);

      // Generate the array
      Integer[] original = sampleSortedArray(tmpsize);
      String type;
      switch (rand.nextInt(7)) {
        case 0:
          type = "sorted";
          break;
        case 1:
          type = "reverse sorted";
          reverse(original);
          break;
        case 2:
          type = "slightly randomized";
          permute(original, tmpsize / 10);
          break;
        case 3:
          type = "slightly randomized reverse sorted";
          reverse(original);
          permute(original, tmpsize / 10);
          break;
        default:
          type = "randomized";
          permute(original);
          break;
      } // switch

      if (pen != null) {
        pen.printf("\nRound %d (%s, size %d)\n", round, type, tmpsize);
      } // if pen

      for (int i = 0; i < sorters.length; i++) {
        if (ok[i]) {
          // Set up the arrays to sort
          Integer[] source = Arrays.copyOf(original, tmpsize);
          // Sort and time
          SimpleTimer st = new SimpleTimer();
          sorters[i].sort(source);
          long time = st.elapsed();
          times[i] += time;
          if (pen != null) {
            pen.println("  " + sorters[i].getClass().getName() + ": " + time);
          } // if pen
        } // if ok
      } // for i
    } // for round

    // Print out the final results
    if (pen != null) {
      pen.println("\n" + "Final results");
    } // if pen
    long bestTime = Long.MAX_VALUE;
    Sorter bestSorter = null;
    for (int i = 0; i < sorters.length; i++) {
      if (ok[i]) {
        if (pen != null) {
          pen.println(sorters[i].getClass().getName() + ": " + times[i]);
        } // if pen
        if (times[i] < bestTime) {
          bestTime = times[i];
          bestSorter = sorters[i];
        } // if
      } // if (ok[i])
    } // for

    // And we're done
    if (pen != null) {
      pen.println("\nThe winner is " + bestSorter.getClass().getName());
    } // if pen

    return bestSorter;
  } // compete(Sorter[])

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Print the help message.
   */
  static void help() {
    System.err.println("Usage: java SortTools COMMAND SortClass1 ... SortClassn");
    System.err.println("  Commands: time, test, compete\n");
  } // help()

  /**
   * Get the sorter for a particular class name.  Yay introspection!
   *
   * @param name
   *    The name of the sorter.
   * @return
   *    The sorter.
   */
  static Sorter<Comparable> getSorter(String name) {
    Class<?> sclass = null;
    Comparator<Comparable> order = (x, y) -> x.compareTo(y);

    for (String prefix : prefixes) {
      try {
        sclass = Class.forName(prefix + name);
        break;
      } catch (Exception e) {
      } // try/catch
    } // for

    if (null == sclass) {
      System.err.println("Cannot find class: " + name);
      return null;
    } // if

    Constructor<Sorter> construct = null;
    try {
      construct =
          (Constructor<Sorter>) sclass.getConstructor(
              Class.forName("java.util.Comparator"));
    } catch (Exception e) {
      System.err.println("Cannot find constructor for " + name);
      System.err.println("  " + e.toString());
      return null;
    } // try/catch
    try {
      return construct.newInstance(order);
    } catch (Exception e) {
      System.err.println("Cannot call constructor for " + name);
      System.err.println("  " + e.toString());
      return null;
    } // try/catch
  } // getSorter(String)

  /**
   * Create an array of a particular size in which the values are
   * in order.
   *
   * @param size
   *    The size of the array to generate.
   * @return
   *    An integer array of length size.
   */
  static Integer[] sampleSortedArray(int size) {
    Integer[] result = new Integer[size];
    result[0] = 0;
    for (int i = 1; i < size; i++) {
      result[i] = result[i - 1] + rand.nextInt(3);
    } // for
    return result;
  } // sampleSortedArray

  /**
   * Permute an array.
   *
   * I recall a colleague telling me that this is not a great way to
   * permute an array.  But I also recall an article by Knuth suggesting
   * that this does randomly arrange things.  In any case, it's good
   * enough for our purposes.
   *
   * @param arr
   *    The array of objects to permute.
   */
  static void permute(Object[] arr) {
    for (int i = 0; i < arr.length; i++) {
      swap(arr, i, rand.nextInt(arr.length));
    } // for
  } // permute(Object[])

  /**
   * Permute an array by swapping n random pairs.
   *
   * Or perhaps this is the one that's less correct, even if
   * n is arr.length.
   *
   * @param arr
   *    The array of objects to permute.
   * @param n
   *    The number of swapped pairs.
   */
  static void permute(Object[] arr, int n) {
    for (int i = 0; i < n; i++) {
      swap(arr, rand.nextInt(arr.length), rand.nextInt(arr.length));
    } // for
  } // permute(Object[], int)

  /**
   * Reverse an array.
   *
   * @param arr
   *    The array to reverse.
   */
  static void reverse(Object[] arr) {
    int lo = 0;
    int hi = arr.length - 1;
    while (lo < hi) {
      swap(arr, lo++, hi--);
    } // while
  } // reverse(Object[]

  /**
   * Swap two values in an array.
   *
   * @param arr
   *    The array to swap object in.
   * @param i
   *    The array index to swap with.
   * @param j
   *    The other array index to swap with.
   */
  public static void swap(Object[] arr, int i, int j) {
    Object tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  } // swap(Object[], int, int)

} // class SortTools
