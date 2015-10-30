public class CountInversions
{
    public static long countInvs(Comparable[] a)
    {
        /* count inversions in an array in O(nlogn) */
        int N = a.length;
        Comparable[] a_copy = new Comparable[N];
        for (int k = 0; k < N; k++)
            a_copy[k] = a[k];
        Comparable[] a_aux = new Comparable[N];
        return countInvsSort(a_copy, a_aux, 0, N-1);
    }

    private static long countInvsSort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (lo >= hi) return 0;
        int mid = lo + (hi - lo) / 2;
        long num1 = countInvsSort(a, aux, lo, mid);
        long num2 = countInvsSort(a, aux, mid+1, hi);
        long num3 = mergeCountSplitInvs(a, aux, lo, mid, hi);
        return num1 + num2 + num3;
    }

    private static long mergeCountSplitInvs(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        long count = 0;
        for (int k = lo; k <= hi; k++)
        {
            if      (i > mid) a[k] = aux[j++];
            else if (j > hi)  { a[k] = aux[i++]; }
            else if (less(aux[j], aux[i])) { count += (mid - i + 1); a[k] = aux[j++]; }
            else    a[k] = aux[i++];
        }
        return count;
    }

    private static boolean less(Comparable a, Comparable b)
    {  return a.compareTo(b) < 0;  }

    public static void main(String[] args)
    {
        Integer[] a = {6, 5, 4, 3, 2, 1};
        long counts = countInvs(a);
        StdOut.println(counts);
    }
}