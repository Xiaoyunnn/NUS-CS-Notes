import java.util.LinkedList;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
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
		int numPowerLeft;

		public Coordinate(int x, int y, Coordinate parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}

		public Coordinate(int x, int y, Coordinate parent, int numPowerLeft) {
			this.x = x;
			this.y = y;
			this.parent = parent;
			this.numPowerLeft = numPowerLeft;
		}
	}

	public MazeSolverWithPower() {
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
						this.numObtainable[tracePath(next)]++;
						frontier.add(next);
					}

				}
			}
		}

		return shortestCoord == null ? null : trackPath(shortestCoord);
	}

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
//		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
//		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

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

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		// update numReacheable array
		// this.visitedSP = new boolean[this.row][this.col][superpowers + 1];
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
				maze.getRoom(i, j).visited = false;
				maze.getRoom(i, j).power = new boolean[superpowers + 1];
			}
		}

		this.visited[startRow][startCol] = true;
		this.maze.getRoom(startRow, startCol).onPath = true;
		this.maze.getRoom(startRow, startCol).visited = true;
		this.maze.getRoom(startRow, startCol).power[superpowers] = true;
		this.numObtainable = new int[row * col];
		this.numObtainable[0] = 1;

		Queue<Coordinate> frontier = new LinkedList<>();
		frontier.add(new Coordinate(startRow, startCol, null, superpowers));

		while (!frontier.isEmpty()) {
			Coordinate curr = frontier.remove();

			if (curr.x == endRow && curr.y == endCol && shortestCoord == null) {
				shortestCoord = curr;
			}

			for (int direction = 0; direction < 4; direction++) {
				int nextX = curr.x + DELTAS[direction][0];
				int nextY = curr.y + DELTAS[direction][1];

				if (canGo(curr.x, curr.y, direction)) {
					Coordinate nextCanGo = new Coordinate(nextX, nextY, curr, curr.numPowerLeft);

					if (!visited[nextX][nextY] || !maze.getRoom(nextX, nextY).power[nextCanGo.numPowerLeft]) {
						visited[nextX][nextY] = true;
						maze.getRoom(nextX, nextY).power[nextCanGo.numPowerLeft] = true;

						if (!this.maze.getRoom(nextX, nextY).visited) {
							this.numObtainable[tracePath(nextCanGo)]++;
							this.maze.getRoom(nextX, nextY).visited = true;
						}

						frontier.add(nextCanGo);
					}
				} else if (!(nextX < 0 || nextY < 0 || nextX >= this.row || nextY >= this.col) && curr.numPowerLeft > 0) {
					// demolish the wall
					Coordinate next = new Coordinate(nextX, nextY,  curr, curr.numPowerLeft - 1);

					if (!visited[nextX][nextY] || !maze.getRoom(nextX, nextY).power[next.numPowerLeft]) {
						visited[nextX][nextY] = true;
						maze.getRoom(nextX, nextY).power[next.numPowerLeft] = true;

						if (!this.maze.getRoom(nextX, nextY).visited) {
							this.numObtainable[tracePath(next)]++;
							this.maze.getRoom(nextX, nextY).visited = true;
						}

						frontier.add(next);
					}
				}
			}
		}
		return shortestCoord == null ? null : trackPath(shortestCoord);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-custom.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 0, 2));
			MazePrinter.printMaze(maze);
			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
			System.out.println(solver.pathSearch(0, 0, 4, 0, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}