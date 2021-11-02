import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean[][] visited;
	private int[] numObtainable;
	private int row;
	private int col;


	class Coordinate {
		int x, y;
		Coordinate parent;

		public Coordinate(int x, int y, Coordinate parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
	}

	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.row = maze.getRows();
		this.col = maze.getColumns();
		this.visited = new boolean[this.row][this.col];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		Coordinate shortestCoord = null;

		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= this.row || startCol >= this.col ||
				endRow < 0 || endCol < 0 || endRow >= this.row || endCol >= this.col) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		this.visited[startRow][startCol] = true;
		this.maze.getRoom(startRow, startCol).onPath = true;
		this.numObtainable = new int[row * col];
		this.numObtainable[0] = 1;

		Queue<Coordinate> frontier = new LinkedList<>();
		frontier.add(new Coordinate(startRow, startCol, null));

		while (!frontier.isEmpty()) {
			Coordinate curr = frontier.remove();

			if (curr.x == endRow && curr.y == endCol) {
				shortestCoord = curr;
			}

			for (int direction = 0; direction < 4; direction++) {
				if (canGo(curr.x, curr.y, direction)) {
					int nextX = curr.x + DELTAS[direction][0];
					int nextY = curr.y + DELTAS[direction][1];
					if (!visited[nextX][nextY]) {
						visited[nextX][nextY] = true;
						Coordinate next = new Coordinate(nextX, nextY,  curr);
						// this.numObtainable[tracePath(next, -1)]++;
						this.numObtainable[tracePath(next)]++;
						frontier.add(next);
					}

				}
			}
		}

		// Integer count = trackPath(shortestCoord, -1);
		return shortestCoord == null ? null : trackPath(shortestCoord);
	}

//	public Integer trackPath(Coordinate coord, int count) {
////		int count = -1;
////		Coordinate curr = coord;
////
////		while (curr != null) {
////			count++;
////			this.maze.getRoom(curr.x, curr.y).onPath = true;
////			curr = curr.parent;
////		}
////		return count;
//		if (coord != null) {
//			maze.getRoom(coord.x, coord.y).onPath = true;
//			return trackPath(coord.parent, count + 1);
//		}
//		return count;
//	}
	public Integer trackPath(Coordinate coord) {
		int count = -1;
		Coordinate curr = coord;

		while (curr != null) {
			count++;
			this.maze.getRoom(curr.x, curr.y).onPath = true;
			curr = curr.parent;
		}
		return count;
	}
//	public Integer tracePath(Coordinate coord, int count) {
//		if (coord != null) {
//			return tracePath(coord.parent, count + 1);
//		}
//		return count;
//	}
	public Integer tracePath(Coordinate coord) {
		int count = -1;
		Coordinate curr = coord;

		while (curr != null) {
			count++;
			curr = curr.parent;
		}
		return count;
	}


	private boolean canGo(int row, int col, int dir) {
		switch (dir) {
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

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k < 0) {
			throw new Exception("Invalid k input");
		} else if (k >= this.numObtainable.length) {
			return 0;
		}
		return this.numObtainable[k];
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);
			ImprovedMazePrinter.printMaze(maze);
			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
//			for (int i = 0; i < maze.getRows(); ++i) {
//				for (int j = 0; j < maze.getColumns(); ++j) {
//					maze.getRoom(i, j).onPath = false;
//				}
//			}
		while (!frontier.isEmpty()) {
			Coordinate curr = frontier.remove();

			if (visited[curr.x][curr.y]) {
				continue;
			}

			if (curr.x == endRow && curr.y == endCol && shortestCoord == null) {
				shortestCoord = curr;
			}

			for (int direction = 0; direction < 4; direction++) {
				if (canGo(curr.x, curr.y, direction)) {
					visited[curr.x][curr.y] = true;
					Coordinate next = new Coordinate(curr.x + DELTAS[direction][0], curr.y + DELTAS[direction][1], curr);
					frontier.add(next);
				}
			}
			this.numObtainable[tracePath(curr)]++;

		}

		Integer count = trackPath(shortestCoord);
		return count == -1 ? null : count;
	}
 */