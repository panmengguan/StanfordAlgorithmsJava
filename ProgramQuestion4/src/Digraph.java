/**
 * Created by pmg on 2015/9/25.
 */
public class Digraph
{
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V)
    {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<Integer>();
    }

    public Digraph(String filename)
    {
        In in = new In(filename);
        int maxVertexIndex = 0;
        while (!in.isEmpty()) {
            int v = in.readInt();
            int w = in.readInt();
            if (v > w && v > maxVertexIndex) maxVertexIndex = v;
            else if (w > v && w > maxVertexIndex) maxVertexIndex = w;
        }
        in.close();
        this.V = maxVertexIndex;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<Integer>();

        in = new In(filename);
        while (!in.isEmpty()) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v-1, w-1);
        }
        in.close();
    }

    public void addEdge(int v, int w)
    {
        adj[v].add(w);
        E++;
    }

    public int V()
    { return V; }

    public int E()
    { return E; }

    public Iterable<Integer> adj(int v)
    { return adj[v]; }

    public Digraph reverse()
    {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                R.addEdge(w, v);
        return R;
    }

    public String toString()
    {
        String s = V + ", vertices, " + E + " edges.\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args)
    {
        Digraph DG = new Digraph("SCC.txt");
    }
}
