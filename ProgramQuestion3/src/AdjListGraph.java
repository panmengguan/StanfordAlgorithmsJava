import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by pmg on 2015/11/7.
 */

/**
 *  Graph implementation for the minimum cut problem
 *  Uses both a hash table of adjacency list and a linked-list of edges underlying.
 *  In fact, either of these two is already a sufficient representation of a graph,
 *  but for the minimum cut application, we need to uniformly select an edge and
 *  apply contraction operation, maintaining a list of edges is useful for this.
 *  So we need to update both of the data structures at each graph operation.
 *  This implementation of graph allows parallel edges which is crucial to minimum
 *  cut problem, but we will delete self loop when the graph is constructing and
 *  at each step of graph operation (mainly the contraction operation).
 */
public class AdjListGraph
{
    class Node
    {
        int item;
        Node next;
        public Node(int item) {
            this.item = item;
            this.next = null;
        }
        public Node(int item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    private HashMap<Integer, Node> adj;
    private LinkedList<Edge> edges;

    public AdjListGraph() {
        adj = new HashMap<Integer, Node>();
        edges = new LinkedList<Edge>();
    }

    public AdjListGraph(int V) {
        adj = new HashMap<Integer, Node>(V);
        edges = new LinkedList<Edge>();
        for (int v = 0; v < V; v++)
            adj.put(v, new Node(-1));    // sentinel Node
    }

    /**
     * build a graph from a file containing adjacent list.
     * @param filename each line of the file is a adjacent list of the first element
     *                 separated by \t character.
     * @throws IOException
     */
    public AdjListGraph(String filename) throws IOException {
        adj = new HashMap<Integer, Node>();
        edges = new LinkedList<Edge>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] vertex = line.split("\t");
            int v = Integer.parseInt(vertex[0]);
            if (!adj.containsKey(v)) adj.put(v, new Node(-1));
            for (int i = 1; i < vertex.length; i++) {
                int w = Integer.parseInt(vertex[i]);
                if (w == v) continue;    // exclude the self loop
                Node vHead = adj.get(v);
                vHead.next = new Node(w, vHead.next);    // add at the top
                if (v < w) edges.add(new Edge(v, w));    // add once
            }
        }
    }

    public int V() { return adj.size(); }

    public int E() { return edges.size(); }

    /**
     * Contraction operation
     * For the specified edge (v, w), 1-4 are the processing of adjacency lists,
     * 5, 6 are the processing of the edges list.
     * 1. append the adjacency list of w to the adjacency list of v.
     * 2. delete the w entry in the adj hash table.
     * 3. delete the self-loop generated at the first step.
     * 4. set each appearance of w vertex in all the adjacency lists to v vertex.
     * 5. remove the specified edge (v, w) in the edges list.
     * 6. set each appearance of w vertex in the edges list to v vertex.
     * @param edgeIndex the index of deleting edge in the edges list
     */
    public void contraction(int edgeIndex) {
        Edge edge = edges.get(edgeIndex);
        int v = edge.either();
        int w = edge.other(v);

        // 1. append the adjacency list of w to the adjacency list of v.
        Node vHead = adj.get(v);
        while (vHead.next != null)
            vHead = vHead.next;
        vHead.next = adj.get(w).next;

        // 2. delete the w entry in the adj hash table
        adj.remove(w);

        // 3. delete the self-loop generated at the first step.
        // 由于edge是从图中现有的边中随机选出的一个，所以vHead和wHead不可能同时为空
        // 实际上 对于无向图，vHead和wHead应该都不为空
        vHead = adj.get(v);    // reset!
        Node p = vHead.next;
        while (p != null) {    // delete self loop
            if (p.item == v || p.item == w) vHead.next = p.next;
            else vHead = vHead.next;
            p = p.next;
        }

        // 4. set each appearance of w vertex in all the adjacency lists to v vertex.
        for (Node node : adj.values()) {
            Node x = node;
            while (x != null) {
                if (x.item == w) {
                    x.item = v;
                }
                x = x.next;
            }
        }

        // 5. remove the specified edge (v, w) in the edges list.
        while (edges.remove(edge)) ;

        // 6. set each appearance of w vertex in the edges list to v vertex.
        for (Edge e : edges) {
            int x = e.either();
            int y = e.other(x);
            if (x == w) e.reset(v, y);
            else if (y == w) e.reset(x, v);
        }

        // while (edges.remove(new Edge(v, v))) ; // redundant operation
    }
}
