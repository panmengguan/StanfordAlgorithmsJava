/**
 * Created by pmg on 2015/11/16.
 */
public class DirectedEdge
{
    private final int v;
    private final int w;
    private final double weight;
    public DirectedEdge(int v, int w, double weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight()
    { return weight; }

    public int from()
    { return v; }

    public int to()
    { return w; }

    public String toString()
    { return String.format("%d -> %d %4.2f", v , w, weight); }

    public static void main(String[] args)
    {
        DirectedEdge e = new DirectedEdge(12, 13, 3.14);
        StdOut.println(e);
    }
}
