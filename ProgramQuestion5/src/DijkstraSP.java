/**
 * Created by pmg on 2015/11/16.
 */
public class DijkstraSP
{
    private double[] distance;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> unreached;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        int V = G.V();
        distance = new double[V];
        edgeTo = new DirectedEdge[V];
        unreached = new IndexMinPQ<Double>(V);

        for (int i = 0; i < V; i++)
            distance[i] = Double.POSITIVE_INFINITY;

        distance[s] = 0.0;
        unreached.insert(s, 0.0);
        while (!unreached.isEmpty())
            relax(G, unreached.delMin());
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (distance[w] > distance[v] + edge.weight()) {
                distance[w] = distance[v] + edge.weight();
                edgeTo[w] = edge;
                if (unreached.contains(w)) unreached.changeKey(w, distance[w]);
                else                       unreached.insert(w, distance[w]);
            }
        }
    }

    public double distTo(int v) {
        return distance[v];
    }

    public boolean hasPathTo(int v) {
        return distance[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> stk = new Stack<DirectedEdge>();
        DirectedEdge edge = edgeTo[v];
        for (; edge != null; edge = edgeTo[edge.from()])
            stk.push(edge);
        return stk;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph("dijkstraData.txt");
        int s = 1 - 1;    // the number in the question has to minus 1 to fit into our EdgeWeightedDigraph class

        DijkstraSP sp = new DijkstraSP(G, s);

        In query = new In("Dijkstra_QueryVertices.txt");
        String all = query.readLine();
        String[] queryVertices = all.split(",");
        for (String q : queryVertices) {
            // the number in the question has to minus 1 to fit into our EdgeWeightedDigraph class
            int v = Integer.parseInt(q) - 1;
            if (!sp.hasPathTo(v)) StdOut.print(1000000);   // use this to represent not reachable in the answer.
            else StdOut.printf("%.0f", sp.distTo(v));
            StdOut.print(",");
        }

    }
}
