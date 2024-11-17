## Mini-Project #8: Sorting Algorithms

### Authors

- *Sara Jaljaa*
- *Samuel A. Rebelsky (starter code)*

### Overview

Four traditional sorting algorithms:

- Insertion sort: Swap an unsorted element into the last sorted portion of the collection,
then compare the new element to the sorted portion to find the correct sorted position.

- Selection sort: Find the next smallest element and swap with the first unsorted element.

- Merge sort: Divide the collection in half, then continue to divide the collections until they
are singletons; re-combine the initial halves of the collection.

- Quicksort: A divide-and-conquer algorithm that uses a random pivot point to divide each subarray,
swapping the elements in-place.

Along with an additional created algorithm:

- A modified Merge sort algorithm that limits unecessary calls to merge already-sorted arrays and switches
to an insertion sort algorithm when the array is less than 60 elements.

---

### Acknowledgements

- [Array javadocs, for referencing](https://docs.oracle.com/javase/8/docs/api/?java/util/Arrays.html)

---

### Source

- This project: [500dpi/mp-sorting-maven](https://github.com/500dpi/mp-sorting-maven)
- Original: [Grinnell-CSC207/mp-sorting-maven](https://github.com/Grinnell-CSC207/mp-sorting-maven)
