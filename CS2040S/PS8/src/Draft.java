import java.util.*;

public class Draft implements IApproximateTSP {
    public HashMap<Integer, ArrayList<Integer>> adjList;
    public double[][] adjMatrix;

    public class Point implements Comparable<Point> {
        public double x;
        public double y;
        public int id;

        public Point(double x, double y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

        @Override
        public int hashCode(){
            long hash = (Double.doubleToLongBits(x) ^ Double.doubleToLongBits(y));
            return (int) (hash ^ (hash >>> 32));
        }

        @Override
        public boolean equals(Object other){
            if (this == other) {
                return true;
            } else if (other instanceof Point) {
                Point temp = (Point) other;
                return temp.id == this.id;
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(Point point) {
            int compareX = Double.compare(this.x, point.x);
            int compareY = Double.compare(this.y, point.y);
            return compareX == 0 ? compareY : compareX;
        }

        @Override
        public String toString() {
            //return String.format("(%3.2f, %3.2f)", this.x, this.y);
            return String.valueOf(this.id);
        }
    }

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        int numPoints = map.getCount();
        this.adjMatrix = new double[numPoints][numPoints];
        this.adjList = new HashMap<>();
        for (int i = 0; i < numPoints; i++) {
            adjList.put(i, new ArrayList<>());
        }

        // priority queue to pick the min weight/dist edge
        TreeMapPriorityQueue<Double, Point> pq = new TreeMapPriorityQueue<>();
        for (int i = 0; i < numPoints; i++) {
            TSPMap.Point pointTSP = map.getPoint(i);
            Point p = new Point(pointTSP.getX(), pointTSP.getY(), i);
            pq.add(p, Double.MAX_VALUE);
        }
        Point start = new Point(map.getPoint(0).getX(), map.getPoint(0).getY(), 0);
        pq.decreasePriority(start, 0.0);

        // array to store the visited points which are in the MST
        boolean[] visitedMST = new boolean[numPoints];
        visitedMST[start.id] = true;

        // parent hash map to store constructed MST
        // key: child, value: parent
        HashMap<Point, Point> parent = new HashMap<>();
        parent.put(start, null);

        while (!pq.isEmpty()) {
            Point v = pq.extractMin();
            visitedMST[v.id] = true;

            for (int i = 0; i < numPoints; i++) {
                // edgelist
                TSPMap.Point nbrTSP = map.getPoint(i);
                Point neighbour = new Point(nbrTSP.getX(), nbrTSP.getY(), i);
                double dist = map.pointDistance(v.id, i);

                if (v.id != i && !visitedMST[i] && dist < pq.lookup(neighbour)) {
                    // is an unvisited neighbour
                    pq.decreasePriority(neighbour, dist);
                    parent.put(neighbour, v);
                }
            }
        }

        for (Map.Entry<Point, Point> mapElem : parent.entrySet()) {
            if (mapElem.getValue() != null) {
                Point child = mapElem.getKey();
                Point corrParent = mapElem.getValue();

                map.setLink(child.id, corrParent.id, false);

//                this.adjMatrix[child.id][corrParent.id] = map.pointDistance(child.id, corrParent.id);
//                this.adjMatrix[corrParent.id][child.id] = map.pointDistance(child.id, corrParent.id);

                adjList.get(corrParent.id).add(child.id);
                adjList.get(child.id).add(corrParent.id);

                // {0=null, 3=0, 1=2, 7=8, 6=9, 8=0, 4=6, 2=5, 5=3, 9=1}
                //map.setLink(corrParent.id, child.id, false);
            }
        }
        // System.out.println(parent);
        // System.out.println(Arrays.toString(adjMatrix[1]));
        map.redraw();
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        int numPoints = map.getCount();
        ArrayList<Integer> path = new ArrayList<>();
        for (int i = 0; i < numPoints; i++) {
            map.eraseLink(i, false);
        }
        // boolean[][] visited = new boolean[numPoints][numPoints];
        boolean[] visitedArr = new boolean[numPoints];

        DFS(path, visitedArr, 0);
        // DFS(map, visited, 0, 0);

        for (int i = 0; i < numPoints - 1; i++) {
            map.setLink(path.get(i), path.get(i+1), false);
        }
        map.setLink(path.get(numPoints - 1), path.get(0));


    }
    public void DFS(ArrayList<Integer> path, boolean[] visited, int curr) {
        path.add(curr);
        visited[curr] = true;
        for (int next : adjList.get(curr)) {
            if (!visited[next]) {
                DFS(path, visited, next);
            }
        }
    }

//    public void DFS(TSPMap map, boolean[][] visited, int curr, int prev) {
//        visited[prev][curr] = true;
//        for (int next : adjList.get(curr)) {
//            if (!visited[curr][next]) {
//                DFS(map, visited, next, curr);
//            }
//        }
//    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        // Hamiltonian cycle: every node (vertex) is visited exactly once
        // a path traveling from a point back to itself, visiting every node en route.
        HashMap<Integer, Integer> table = new HashMap<>();
        // curr point, next point id
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
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "D:/Program Files/NUS School Works/CS2040S/PS8/fiftypoints.txt");
        TSPGraph graph = new TSPGraph();
        // System.out.println(map.getPoint(0));


        graph.MST(map);
        //graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}
//        TSPMap.Point last = map.getPoint(numPoints - 1);
//        Point lastParent = new Point(last.getX(), last.getY(), numPoints - 1);
//        Point lastChild = parent.get(lastParent);
//        parent.put(start, lastChild);
//        // set link using the K-V pair in hash map
//        System.out.println(parent);
//        parent.forEach((k, v) -> map.setLink(k.id, v.id, false));
//        map.redraw();

//        Stack<Integer> stack = new Stack<>();
//        stack.push(0);
//
//        while (!stack.isEmpty()) {
//            int curr = stack.pop();
//            visited[curr] = true;
//            System.out.println("curr = " + curr);
//
//            for (int next : adjList.get(curr)) {
//                System.out.println(adjList.get(curr).toString());
//                if (!visited[next]) {
//                    stack.push(next);
//                    map.setLink(curr, next, false);
//                }
//            }
//        }