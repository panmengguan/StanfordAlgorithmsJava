import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Created by pmg on 2015/7/14.
 */
public class MinPQGeneric<Key extends Comparable<Key>> implements Iterable<Key>
{
    private Key[] pq;
    private int N;

    public MinPQGeneric()
    {
        pq = (Key[]) new Comparable[1];
        N = 0;
    }

    public MinPQGeneric(int maxNum)
    {
        pq = (Key[]) new Comparable[maxNum+1];
        N = 0;
    }

    public MinPQGeneric(Key[] a)
    {
        N = a.length;
        pq = (Key[]) new Comparable[N+1];
        for (int i = 0; i < a.length; i++)
            pq[i+1] = a[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
    }

    public boolean isEmpty()
    { return N == 0; }

    public int size()
    { return N; }

    public void insert(Key key)
    {
        if (N+1 >= pq.length) resizing(2 * pq.length);
        pq[++N] = key;
        swim(N);
    }

    public Key delMin()
    {
        Key min = pq[1];
        exch(1, N--);
        pq[N+1] = null; // avoid loitering and help with gc
        sink(1);
        if (N > 0 && N == (pq.length - 1) / 4) resizing(pq.length / 2);
        return min;
    }

    public Key min()
    { return pq[1]; }

    private void sink(int k)
    {
        while (2 * k <= N)
        {
            int j = 2 * k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k)
    {
        while (k > 1 && greater(k/2, k))
        {
            exch(k, k/2);
            k = k / 2;
        }
    }

    private void resizing(int capacity)
    {
        Key[] copy = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++)
            copy[i] = pq[i];
        pq = copy;
    }

    private boolean greater(int i, int j)
    { return pq[i].compareTo(pq[j]) > 0; }

    private void exch(int i, int j)
    { Key tmp = pq[i]; pq[i] = pq[j]; pq[j] = tmp; }

    public Iterator<Key> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Key>
    {
        private MinPQGeneric<Key> copy;
        public HeapIterator()
        {
            copy = new MinPQGeneric<Key>(size());
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()
        { return !copy.isEmpty(); }

        public void remove()
        { throw new UnsupportedOperationException(); }

        public Key next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    /**
     *  Unit tests
     */
    public static void main(String[] args)
    {
        MinPQGeneric<String> pq = new MinPQGeneric<String>();
        In in = new In("test.txt");
        while (!in.isEmpty())
        {
            String item = in.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMin() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
        StdOut.println("Items in the heap: (in ascending order)");
        for (String item : pq)
            StdOut.print(item + " ");
        StdOut.println();
    }
}
