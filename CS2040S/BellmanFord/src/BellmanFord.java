import java.util.ArrayList;
import java.util.Arrays;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    // TODO: add additional attributes and/or variables needed here, if any
    ArrayList<ArrayList<IntPair>> adjList;
    int[] dist;
    int nodesLen;
    boolean hasNegativeCycle;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        // TODO: initialize your attributes here, if any
        this.adjList = adjList;
        this.nodesLen = adjList.size();
    }

    public boolean relax(int u, IntPair vPair) {
        if (dist[vPair.first] > dist[u] + vPair.second) {
            dist[vPair.first] = dist[u] + vPair.second;
            return true;
        } else {
            return false;
        }
    }

    // TODO: add additional methods here, if anyS
    public void computeShortestPaths(int source) {
        this.dist = new int[nodesLen];
        Arrays.fill(dist, INF);
        this.dist[source] = 0;

        for (int i = 0; i < this.nodesLen - 1; i++) {
            boolean hasRelaxed = false;
            for (int u = 0; u < nodesLen; u++) {
                if (dist[u] != INF) {
                    for (IntPair neighbour : this.adjList.get(u)) {
                        // hasRelaxed = relax(u, neighbour);
                        if (dist[neighbour.first] > dist[u] + neighbour.second) {
                            dist[neighbour.first] = dist[u] + neighbour.second;
                            hasRelaxed = true;
                        }
                    }
                }
            }
            if (!hasRelaxed) break;
            // terminate early when an entire sequence of |E| relax operations have no effect
        }
        for (int i = 0; i < this.nodesLen - 1; i++) {
            for (int u = 0; u < nodesLen; u++) {
                if (dist[u] != INF) {
                    for (IntPair neighbour : this.adjList.get(u)) {
                        if (dist[u] == NEGINF || (dist[neighbour.first] > dist[u] + neighbour.second)) {
                            dist[neighbour.first] = NEGINF;
                        }
                    }
                }
            }
        }
    }

    public int getDistance(int node) { 
        // TODO: implement your getDistance operation here
//        if (hasNegativeCycle) {
//            return NEGINF;
//        } else {
//            return dist[node];
//        }
        if (node < 0 || node >= nodesLen) {
            return INF;
        }
        return dist[node];
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<IntPair>> adjList = new ArrayList<>();
        ArrayList<IntPair> zero = new ArrayList<>();
        ArrayList<IntPair> one = new ArrayList<>();
        ArrayList<IntPair> two = new ArrayList<>();
        ArrayList<IntPair> three = new ArrayList<>();
        ArrayList<IntPair> four = new ArrayList<>();
        ArrayList<IntPair> five = new ArrayList<>();
        ArrayList<IntPair> six = new ArrayList<>();
        ArrayList<IntPair> seven = new ArrayList<>();
        ArrayList<IntPair> eight = new ArrayList<>();
        ArrayList<IntPair> nine = new ArrayList<>();
        ArrayList<IntPair> ten = new ArrayList<>();

//        zero.add(new IntPair(1, -1));
//        zero.add(new IntPair(2, 4));
//        one.add(new IntPair(2, 3));
//        one.add(new IntPair(3, 2));
//        one.add(new IntPair(4, 2));
//        three.add(new IntPair(2, 5));
//        three.add(new IntPair(1, 1));
//        four.add(new IntPair(3, -3));

//        adjList.add(five);

//        zero.add(new IntPair(1, 16));
//        zero.add(new IntPair(2, 0));
//        one.add(new IntPair(2, -32));
//        two.add(new IntPair(3, 8));
//        two.add(new IntPair(4, 0));
//        three.add(new IntPair(4, -16));
//        four.add(new IntPair(5, 4));
//        four.add(new IntPair(6, 0));
//        five.add(new IntPair(6, -8));
//        six.add(new IntPair(7,2));
//        six.add(new IntPair(8,0));
//        seven.add(new IntPair(8,-4));
//        eight.add(new IntPair(9, 1));
//        eight.add(new IntPair(10, 0));
//        nine.add(new IntPair(10, -2));

        zero.add(new IntPair(3, 2));
        one.add(new IntPair(2, -20));
        two.add(new IntPair(5, 2));
        five.add(new IntPair(2, 1));
        two.add(new IntPair(1,1));
//        three.add(new IntPair(0,0));
//        zero.add(new IntPair(5, 2));
        adjList.add(zero);
        adjList.add(one);
        adjList.add(two);
        adjList.add(three);
        adjList.add(four);
        adjList.add(five);
//        adjList.add(six);
//        adjList.add(seven);
//        adjList.add(eight);
//        adjList.add(nine);
//        adjList.add(ten);


        BellmanFord test = new BellmanFord(adjList);

        for (int i = 0; i < adjList.size(); i++) {
            test.computeShortestPaths(i);
            for (int j = 0; j < adjList.size(); j++) {
                System.out.println(i + " -> " + j+ " distance: " + test.getDistance(j) );
            }
        }
    }

}
//                    if (relax(u, neighbour)) {
//                        dist[neighbour.first] = NEGINF;
//                    }