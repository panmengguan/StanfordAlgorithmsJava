/**
 * Created by pmg on 2015/11/15.
 */
public class EdgeWeightedDigraph
{
    private int V;
    private int E;
    private Bag<DirectedEdge>[] adj;

    // 将algs4中的EdgeWeightedDigraph按照题目要求的输入格式作修改
    public EdgeWeightedDigraph(String filename)
    {
        In in = new In(filename);
        int maxVertexIndex = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] elements = line.split("\t");
            int v = Integer.parseInt(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                String[] edgeInfo = elements[i].split(",");   // format : vertex,weight
                int w = Integer.parseInt(edgeInfo[0]);
                if (v > w && v > maxVertexIndex) maxVertexIndex = v;
                else if (w > v && w > maxVertexIndex) maxVertexIndex = w;
            }
        }
        in.close();
        this.V = maxVertexIndex;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<DirectedEdge>();

        in = new In(filename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] elements = line.split("\t");
            int v = Integer.parseInt(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                String[] edgeInfo = elements[i].split(",");   // format : vertex,weight
                int w = Integer.parseInt(edgeInfo[0]);
                double weight = Double.parseDouble(edgeInfo[1]);
                addEdge(new DirectedEdge(v-1, w-1, weight));    // the input is a undirected graph.
                addEdge(new DirectedEdge(w-1, v-1, weight));    // 1 ~ 200 ===> 0 ~ 199
            }
        }
        in.close();
    }

    public void addEdge(DirectedEdge edge)
    {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v)
    { return adj[v]; }

    public Iterable<DirectedEdge> edges()
    {
        Bag<DirectedEdge> b = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge edge : adj[v])
            b.add(edge);
        return b;
    }

    public String toString()
    {
        String s = V + ", vertices, " + E + " edges.\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (DirectedEdge edge : adj(v))
                s += edge.to() + " ";
            s += "\n";
        }
        return s;
    }

    public int V()
    { return V; }

    public int E()
    { return E; }

    public static void main(String[] args)
    {
        EdgeWeightedDigraph ewg = new EdgeWeightedDigraph("dijkstraData.txt");
        StdOut.println(ewg);
    }
}
