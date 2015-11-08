/**
 * Created by pmg on 2015/11/7.
 */

public class Edge
{
    private int v;
    private int w;
    public Edge(int v, int w)
    {
        this.v = v;
        this.w = w;
    }

    public int either()
    { return v; }

    public int other(int vertex)
    {
        if (vertex == v)       return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public void reset(int v, int w)
    {
        this.v = v;
        this.w = w;
    }
    public String toString()
    { return String.format("%d-%d", v , w); }

    public boolean equals(Object x)
    {
        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        Edge that = (Edge) x;
        if (this.v == that.v && this.w == that.w) return true;
        else if (this.v == that.w && this.w == that.v) return true;
        else return false;
    }

    public static void main(String[] args)
    {
        Edge e1 = new Edge(1, 3);
        Edge e2 = new Edge(1, 3);
        Edge e3 = new Edge(1, 3);
        System.out.println(e1.equals(e2));
    }

}
