/**
 * Created by pmg on 2015/1/27.
 */
public class ProgramQuestion1
{
    public static void main(String[] args)
    {
        String filename = "IntegerArray.txt";
        In in = new In(filename);
        Integer[] a = new Integer[100000];
        for (int i = 0; !in.isEmpty(); i++)
        {
            int num = in.readInt();
            a[i] = num;
        }
        long counts = CountInversions.countInvs(a);
        StdOut.println(counts);
    }
}
