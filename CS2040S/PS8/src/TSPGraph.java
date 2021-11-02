import java.util.*;

public class TSPGraph implements IApproximateTSP {
    public HashMap<Integer, ArrayList<Integer>> adjList;

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        int numPoints = map.getCount();
        this.adjList = new HashMap<>();
        for (int i = 0; i < numPoints; i++) {
            adjList.put(i, new ArrayList<>());
        }

        // priority queue to pick the min weight/dist edge
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        for (int i = 0; i < numPoints; i++) {
            pq.add(i, Double.MAX_VALUE);
        }
        pq.decreasePriority(0, 0.0);

        // array to store the visited points which are in the MST
        boolean[] visitedMST = new boolean[numPoints];
        visitedMST[0] = true;

        // parent hash map to store constructed MST
        // key: child, value: parent
        HashMap<Integer, Integer> parent = new HashMap<>();
        parent.put(0, -1);

        while (!pq.isEmpty()) {
            Integer v = pq.extractMin();
            visitedMST[v] = true;

            for (int i = 0; i < numPoints; i++) {
                // edgelist
                double dist = map.pointDistance(v, i);

                if (v != i && !visitedMST[i] && dist < pq.lookup(i)) {
                    // is an unvisited neighbour
                    pq.decreasePriority(i, dist);
                    parent.put(i, v);
                }
            }
        }

        for (Map.Entry<Integer, Integer> mapElem : parent.entrySet()) {
            if (mapElem.getValue() != -1) {
                Integer child = mapElem.getKey();
                Integer corrParent = mapElem.getValue();

                map.setLink(child, corrParent, false);
                adjList.get(corrParent).add(child);
            }
        }
        map.redraw();
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        int numPoints = map.getCount();
        ArrayList<Integer> path = new ArrayList<>();
//        for (int i = 0; i < numPoints; i++) {
//            map.eraseLink(i, false);
//        }

        boolean[] visitedArr = new boolean[numPoints];
        DFS(path, visitedArr, 0,  map);

        map.setLink(path.get(numPoints - 1), path.get(0));
    }


    public void DFS(ArrayList<Integer> path, boolean[] visited, int curr, TSPMap map) {
        if (path.size() > 0) {
            int previous = path.get(path.size() - 1);
            map.setLink(previous, curr, false);
            //System.out.println("prev = " + previous + " curr = " + curr);
        }

        path.add(curr);
        visited[curr] = true;
        for (int next : adjList.get(curr)) {
            if (!visited[next]) {
                DFS(path, visited, next, map);
            }
        }
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        // Hamiltonian cycle: every node (vertex) is visited exactly once
        // a path traveling from a point back to itself, visiting every node en route.

        // curr point, next point id
        HashMap<Integer, Integer> table = new HashMap<>();

        int numPoints = map.getCount();
        TSPMap.Point curr = map.getPoint(0);
        int link = curr.getLink();
        table.put(0, link);

        for (int i = 1; i < numPoints; i++) {
            if (link != -1) {
                curr = map.getPoint(link);
                link = curr.getLink();
                if (!table.containsKey(i) && !table.containsValue(link)) {
                    table.put(i, link);
                }
            } else {
                return false;
            }
        }
        return table.size() == numPoints && link == 0;
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        double sum = 0.0;
        if (isValidTour(map)) {
            for (int i = 0; i < map.getCount(); i++) {
                TSPMap.Point curr = map.getPoint(i);
                int link = curr.getLink();
                sum += map.pointDistance(i, link);
            }
            return sum;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "D:/Program Files/NUS School Works/CS2040S/PS8/twentypoints.txt");
        TSPGraph graph = new TSPGraph();
        // System.out.println(map.getPoint(0));


        //graph.MST(map);
        graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}