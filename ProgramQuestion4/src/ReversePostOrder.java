/**
 * Created by pmg on 2015/9/26.
 */
public class ReversePostOrder
{
    private Stack<Integer> reversePost;
    private boolean[] marked;
    public ReversePostOrder(Digraph G)
    {
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost()
    { return reversePost; }

    public static void main(String[] args)
    {
        // 注意对同一个DAG，这几个输出都不唯一，和adj表中元素的先后有关（即图建立过程中addEdge的顺序有关）
        // Digraph G = new Digraph(new In("tinyDAG.txt"));
    }

}
