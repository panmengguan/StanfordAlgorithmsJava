import java.io.IOException;
import java.util.Random;

/**
 * Created by pmg on 2015/11/7.
 */
public class KargerMinCut
{
    private int cutNum;
    // private int callNumber;

    public KargerMinCut(AdjListGraph G)
    {
        // callNumber = 0;
        contracting(G);
    }

    private void contracting(AdjListGraph G)
    {
        if (G.V() == 2)
            cutNum = G.E();
        else {
            Random rand = new Random();
            int edgeIndex = rand.nextInt(G.E());
            ////// for debug
            // int edgeIndex;
            // if (callNumber == 0) edgeIndex = 3;
            // else edgeIndex = 0;
            /////
            G.contraction(edgeIndex);
            // callNumber++;
            // System.out.println(callNumber++);
            contracting(G);
        }
    }

    public int getCutNum()
    { return cutNum; }

    public static void main(String[] args) throws IOException
    {
        // KargerMinCut kmc = new KargerMinCut(new AdjListGraph("tinyG.txt"));
        int min = 10000;
        for (int i = 0; i < 1000; i++) {
            KargerMinCut kmc = new KargerMinCut(new AdjListGraph("kargerMinCut.txt"));
            min = kmc.getCutNum() < min ? kmc.getCutNum() : min;
        }
        System.out.println(min);    // 17
    }
}
