/**
 * Created by pmg on 2015/10/30.
 * 1. According to Standford Algorithms video 5-2
 * 2. Does not have a random shuffle, but have a different pivot choosing strategy.
 * 3. Write 3 kinds of pivot choosing strategy, and count the comparison number.
 */

public class QuickSort
{
    private static long comparisons = 0;

    public static long getComparisons()
    { return comparisons; }

    public static void sort(Comparable[] a)
    {
        // StdRandom.shuffle(a);    // Eliminate dependence on input
        comparisons = 0;
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (lo >= hi) return;
        // the pivot element is compared to each of the other m−1 elements in the
        // recursive call on a subarray of size m.
        comparisons += hi - lo;

        // using the lo as pivot
        // exch(a, lo, pivotLo(a, lo, hi));

        // using the hi as pivot
        // exch(a, lo, pivotHi(a, lo, hi));

        // using the median of three as pivot
        exch(a, lo, pivotMedianOfThree(a, lo, hi));
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi)
    {
        Comparable v = a[lo];
        int i = lo + 1, j = lo + 1;
        // i always points to the leftmost element of the elements that are larger than the pivot.
        for (; j <= hi; j++)
            if (less(a[j], v)) exch(a, i++, j);
        // swap with the rightmost element of the elements that are smaller than the pivot
        exch(a, lo, --i);
        return i;
    }

    private static int pivotLo(Comparable[] a, int lo, int hi)
    { return lo; }

    private static int pivotHi(Comparable[] a, int lo, int hi)
    { return hi; }

    private static int pivotMedianOfThree(Comparable[] a, int lo, int hi)
    {
        int mid = lo + (hi - lo) / 2;
        int median;
        if (less(a[lo], a[mid])) {
            if (less(a[lo], a[hi]))
                if (less(a[mid], a[hi])) median = mid;
                else median = hi;
            else median = lo;
        }
        else {
            if (less(a[mid], a[hi]))
                if (less(a[lo], a[hi])) median = lo;
                else median = hi;
            else median = mid;
        }
        return median;
    }

    private static boolean less(Comparable a, Comparable b)
    { return a.compareTo(b) < 0; }

    private static void exch(Comparable[] a, int i, int j)
    { Comparable tmp = a[i]; a[i] = a[j]; a[j] = tmp; }

    private static void show(Object[] a)
    {
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    private static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void main(String[] args)
    {
        /**
         * Random input test
         */
        StdOut.println("Random input test: ");
        int N = 1000;
        Double[] randomInput = new Double[N];
        for (int i = 0; i < N; i++)
            randomInput[i] = StdRandom.uniform();
        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();
        sort(randomInput);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();

        if (isSorted(randomInput)) StdOut.println("Random input test passed!\n");
        else             StdOut.println("Random input test NOT passed!\n");

        /**
         * Corner cases test
         */
        /* When the array is already sorted */
        StdOut.println("Corner cases test, 1: when the array is already sorted: ");
        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();
        sort(randomInput);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(randomInput[i] + " ");
        StdOut.println();

        if (isSorted(randomInput)) StdOut.println("When the array is already sorted, test passed!\n");
        else             StdOut.println("When the array is already sorted, test NOT passed!\n");

        /* When the array is in reverse order */
        StdOut.println("Corner cases test, 2: when the array is in reversed order: ");
        Integer[] reversedArr = new Integer[N];
        for (int i = 0; i < N; i++)
            reversedArr[i] = N - i;
        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(reversedArr[i] + " ");
        StdOut.println();
        sort(reversedArr);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(reversedArr[i] + " ");
        StdOut.println();
        if (isSorted(reversedArr)) StdOut.println("When the array is in reversed order, test passed!\n");
        else             StdOut.println("When the array is in reversed order, test NOT passed!\n");

        StdOut.println("Corner cases test, 3: when all the entries in array are the same: ");
        Integer[] sameKeyArr = new Integer[N];
        for (int i = 0; i < N; i++)
            sameKeyArr[i] = 1;
        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(sameKeyArr[i] + " ");
        StdOut.println();
        sort(sameKeyArr);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(sameKeyArr[i] + " ");
        StdOut.println();
        if (isSorted(sameKeyArr)) StdOut.println("when all the entries in array are the same, test passed!\n");
        else             StdOut.println("when all the entries in array are the same, test NOT passed!\n");

        StdOut.println("Corner cases test, 4: array consisting of only two distinct keys: ");
        Integer[] twoDistinctKeysArr = new Integer[N];
        for (int i = 0; i < N; i++)
            twoDistinctKeysArr[i] = i % 2;
        StdOut.println("First 10 samples of input: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(twoDistinctKeysArr[i] + " ");
        StdOut.println();
        sort(twoDistinctKeysArr);
        StdOut.println("First 10 samples of sort output: ");
        for (int i = 0; i < 10; i++)
            StdOut.print(twoDistinctKeysArr[i] + " ");
        StdOut.println();
        if (isSorted(twoDistinctKeysArr)) StdOut.println("array consisting of only two distinct keys, test passed!\n");
        else             StdOut.println("array consisting of only two distinct keys, test NOT passed!\n");

        StdOut.println("Corner cases test, 5: array of size 0: ");
        String[] emptyArr = new String[0];
        sort(emptyArr);
        if (isSorted(emptyArr)) StdOut.println("array of size 0, test passed!\n");
        else             StdOut.println("array of size 0, test NOT passed!\n");

        StdOut.println("Corner cases test, 6: array of size 1: ");
        String[] arrOfSingleElem = new String[1];
        arrOfSingleElem[0] = "one";
        sort(arrOfSingleElem);
        if (isSorted(arrOfSingleElem)) StdOut.println("array of size 1, test passed!\n");
        else             StdOut.println("array of size 1, test NOT passed!\n");

        /* Stanford Algorithms course, Programming Question - 2 */
        In in = new In("QuickSort.txt");
        Integer[] input = new Integer[10000];
        for (int i = 0; !in.isEmpty(); i++)
        {
            int num = in.readInt();
            input[i] = num;
        }

        sort(input);
        if (isSorted(input)) StdOut.println("sort succeed!\n");
        else             StdOut.println("sort FAILED\n");

        StdOut.println("Number of Comparisons: " + getComparisons());
    }
}
