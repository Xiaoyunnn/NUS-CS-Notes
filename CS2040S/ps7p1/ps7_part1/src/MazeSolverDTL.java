import java.util.LinkedList;
import java.util.Queue;
public class MazeSolverDTL implements IMazeSolver {
    private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
    private static int[][] DELTAS = new int[][] {
            { -1, 0 }, // North
            { 1, 0 }, // South
            { 0, 1 }, // East
            { 0, -1 } // West
    };
    private Maze maze;
    private boolean[][] visited;
    private int endRow, endCol;
    private int[] reach;

    private class Point {
        int x;
        int y;
        int dist;
        Point previous;
        public Point(int x, int y, int d, Point previous) {
            this.x = x;
            this.y = y;
            this.dist = d;
            this.previous = previous;
        }
    }
    public MazeSolverDTL () {
        // TODO: Initialize variables.
        maze = null;
    }
    @Override
    public void initialize(Maze maze) {
        // TODO: Initialize the solver.
        this.maze = maze;
        visited = new boolean[maze.getRows()][maze.getColumns()];
    }
    @Override
    public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
        // TODO: Find shortest path.
        if (maze == null) {
            throw new Exception("You cannot call me without initializing the maze!");
        }
        if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
                endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
            throw new IllegalArgumentException("Invalid starting/end coordinate");
        }
        reach = new int[(maze.getRows() * maze.getColumns())];
        // set all visited flag to false
        // before we begin our search
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getColumns(); j++) {
                this.visited[i][j] = false;
                maze.getRoom(i, j).onPath = false;
            }
        }
        this.endRow = endRow;
        this.endCol = endCol;
        return solve(startRow,startCol);
    }
    // extract from naive attempt
    private boolean canGo(int row, int col, int direction) {
        switch (direction) {
            case NORTH:
                return !maze.getRoom(row, col).hasNorthWall();
            case SOUTH:
                return !maze.getRoom(row, col).hasSouthWall();
            case EAST:
                return !maze.getRoom(row, col).hasEastWall();
            case WEST:
                return !maze.getRoom(row, col).hasWestWall();
        }
        return false;
    }
    public void fixPath(Point point) {

//        Point curr = point;
//        while (curr != null) {
//            curr.dist++;
//            maze.getRoom(curr.x, curr.y).onPath = true;
//            curr = curr.previous;
//        }
        if (point == null){
            return;
        }
        maze.getRoom(point.x, point.y).onPath = true;
        fixPath(point.previous);
    }
    private Integer solve(int startRow, int startCol) {
        visited[startRow][startCol] = true;
        maze.getRoom(startRow, startCol).onPath = true;
        this.reach[0] = 1;

        Queue<Point> q = new LinkedList<>();
        q.add(new Point(startRow,startCol,0, null));
        int result = -1;
        while (!q.isEmpty()) {
            // peak the current room
            Point curr = q.peek();
            // is end?
            if (curr.x == this.endRow && curr.y == this.endCol) {
                // fix the path && return result
                fixPath(curr);
                result = curr.dist;
            }
            // Otherwise dequeue & find next level
            q.remove();
            for (int direction = 0; direction < 4; ++direction) {
                // if adjacent room can go
                if (canGo(curr.x, curr.y, direction)) {
                    // set next room
                    int nextRow = curr.x + DELTAS[direction][0];
                    int nextCol = curr.y + DELTAS[direction][1];
                    // if next room is unvisited
                    if (!visited[nextRow][nextCol]) {
                        this.reach[curr.dist + 1] += 1;
                        // visit the next room
                        visited[nextRow][nextCol] = true;
                        Point nextRoom = new Point(nextRow, nextCol, curr.dist + 1, curr);
                        // enqueue
                        q.add(nextRoom);
                    }
                }
            }
        }
        // no solution returned within loop -> not found
        return result == -1 ? null : result;
    }
    @Override
    public Integer numReachable(int k) throws Exception {
        // TODO: Find number of reachable rooms.
        if (k < 0) {
            throw new Exception("Invalid k input");
        } else if (k >= this.reach.length) {
            return 0;
        }
        return this.reach[k];
    }
    public static void main(String[] args) {
        try {
            Maze maze = Maze.readMaze("maze-empty.txt");
            IMazeSolver solver = new MazeSolverDTL();
            solver.initialize(maze);
            System.out.println(solver.pathSearch(0, 0, 1, 3));
            MazePrinter.printMaze(maze);
            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
