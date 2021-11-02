import java.util.*;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private int[][] cumulativeFear;
	private int row;
	private int col;

	class Coordinate implements Comparable<Coordinate> {
		int x, y;
		int fear;

		public Coordinate(int x, int y, int fear) {
			this.x = x;
			this.y = y;
			this.fear = fear;
		}

		@Override
		public int compareTo(Coordinate coordinate) {
			int compareFear = Integer.compare(this.fear, coordinate.fear);
			int compareX = Integer.compare(this.x, coordinate.x);
			int compareY = Integer.compare(this.y, coordinate.y);
			return compareFear == 0
					? compareX == 0
						? compareY
						: compareX
					: compareFear;
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
		this.cumulativeFear = new int[this.row][this.col];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= this.row || startCol >= this.col ||
				endRow < 0 || endCol < 0 || endRow >= this.row || endCol >= this.col) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				this.cumulativeFear[i][j] = Integer.MAX_VALUE;
			}
		}

		PriorityQueue<Coordinate> pq = new PriorityQueue<>();
		pq.offer(new Coordinate(startRow, startCol, 0));
		this.cumulativeFear[startRow][startCol] = 0;

		while (!pq.isEmpty()) {
			Coordinate curr = pq.poll();

			if (curr.x == endRow && curr.y == endCol) {
				return cumulativeFear[curr.x][curr.y];
			}

			if (curr.fear == cumulativeFear[curr.x][curr.y]) {
				// prevent a processed room to be re-processed again
				for (int direction = 0; direction < 4; direction++) {
					int nextX = curr.x + DELTAS[direction][0];
					int nextY = curr.y + DELTAS[direction][1];
					if (!(nextX < 0 || nextY < 0 || nextX >= this.row || nextY >= this.col)) {
						int currFear = WALL_FUNCTIONS.get(direction).apply(maze.getRoom(curr.x, curr.y));

						if (currFear == EMPTY_SPACE) {
							// add 1 fear level to next room
							int fearEst = cumulativeFear[curr.x][curr.y] + 1;

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}
						} else if (currFear != TRUE_WALL) {
							// add corresponding fear level
							int fearEst = cumulativeFear[curr.x][curr.y] + currFear;

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}

						}
					}
				}
			}
		}
		// return cumulativeFear[endRow][endCol] < Integer.MAX_VALUE ? cumulativeFear[endRow][endCol] : null;
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= this.row || startCol >= this.col ||
				endRow < 0 || endCol < 0 || endRow >= this.row || endCol >= this.col) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				this.cumulativeFear[i][j] = Integer.MAX_VALUE;
			}
		}

		PriorityQueue<Coordinate> pq = new PriorityQueue<>();
		pq.offer(new Coordinate(startRow, startCol, 0));
		this.cumulativeFear[startRow][startCol] = 0;

		while (!pq.isEmpty()) {
			Coordinate curr = pq.poll();

			if (curr.x == endRow && curr.y == endCol) {
				return cumulativeFear[curr.x][curr.y];
			}

			if (curr.fear == cumulativeFear[curr.x][curr.y]) {
				// prevent a processed room to be re-processed again
				for (int direction = 0; direction < 4; direction++) {
					int nextX = curr.x + DELTAS[direction][0];
					int nextY = curr.y + DELTAS[direction][1];
					if (!(nextX < 0 || nextY < 0 || nextX >= this.row || nextY >= this.col)) {
						int currFear = WALL_FUNCTIONS.get(direction).apply(maze.getRoom(curr.x, curr.y));

						if (currFear == EMPTY_SPACE) {
							// add 1 fear level to next room
							int fearEst = cumulativeFear[curr.x][curr.y] + 1;

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}
						} else if (currFear != TRUE_WALL) {
							// add corresponding fear level
							int fearEst = Math.max(cumulativeFear[curr.x][curr.y], currFear);

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}

						}
					}
				}
			}
		}
		// return cumulativeFear[endRow][endCol] < Integer.MAX_VALUE ? cumulativeFear[endRow][endCol] : null;
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= this.row || startCol >= this.col ||
				endRow < 0 || endCol < 0 || endRow >= this.row || endCol >= this.col) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		if (startRow == endRow && startRow == sRow && startCol == endCol && startCol == sCol) {
			return 0;
		}

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				this.cumulativeFear[i][j] = Integer.MAX_VALUE;
			}
		}

		PriorityQueue<Coordinate> pq = new PriorityQueue<>();
		pq.offer(new Coordinate(startRow, startCol, 0));
		this.cumulativeFear[startRow][startCol] = 0;

		while (!pq.isEmpty()) {
			Coordinate curr = pq.poll();

			if (curr.fear == cumulativeFear[curr.x][curr.y]) {
				for (int direction = 0; direction < 4; direction++) {
					int nextX = curr.x + DELTAS[direction][0];
					int nextY = curr.y + DELTAS[direction][1];
					if (!(nextX < 0 || nextY < 0 || nextX >= this.row || nextY >= this.col)) {
						int currFear = WALL_FUNCTIONS.get(direction).apply(maze.getRoom(curr.x, curr.y));

						if (currFear == EMPTY_SPACE) {
							// add 1 fear level to next room
							if (nextX == sRow && nextY == sCol) {
								cumulativeFear[nextX][nextY] = -1; // relax immediately
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							} else {
								int fearEst = cumulativeFear[curr.x][curr.y] + 1;

								if (cumulativeFear[nextX][nextY] > fearEst) {
									cumulativeFear[nextX][nextY] = fearEst; // relax
									pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
								}
							}
						} else if (currFear != TRUE_WALL) {
							// add corresponding fear level
							if (nextX == sRow && nextY == sCol) {
								cumulativeFear[nextX][nextY] = -1; // relax immediately
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							} else {
								int fearEst = Math.max(cumulativeFear[curr.x][curr.y], currFear);

								if (cumulativeFear[nextX][nextY] > fearEst) {
									cumulativeFear[nextX][nextY] = fearEst; // relax
									pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
								}
							}
						}
					}
				}
			}
		}
		return cumulativeFear[endRow][endCol] < Integer.MAX_VALUE ? cumulativeFear[endRow][endCol] : null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-simple.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			//System.out.println(WALL_FUNCTIONS.get(3).apply(maze.getRoom(0, 0)));
//			for (int x = 0; x < maze.getColumns(); x ++) {
//				for (int y = 0; y < maze.getColumns(); y ++) {
//					for (int z = 0; z < maze.getColumns(); z ++) {
//						System.out.println("x = " + x + " y = " + y + " z = " + z + " result = "+
//								solver.bonusSearch(0, x, 0, y, 0, z));
//					}
//				}
//			}
			//System.out.println(solver.bonusSearch(0, 0, 0, 3, 0, ));
//			for (int x = 0; x < maze.getColumns(); x ++) {
//				for (int y = 0; y < maze.getColumns(); y ++) {
//					for (int z = 0; z < maze.getColumns(); z ++) {
//						System.out.printf("%d, %d, %d, %d \n", x, y, z, solver.bonusSearch(0, x, 0, y, 0, z));
//					}
//				}
//			}

			for (int x = 0; x < maze.getColumns(); x ++) {
				for (int y = 0; y < maze.getColumns(); y ++) {
					System.out.printf("%d, %d, %d \n", x, y, solver.pathSearch(0, x, 0, y));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
/*
public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= this.row || startCol >= this.col ||
				endRow < 0 || endCol < 0 || endRow >= this.row || endCol >= this.col) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		if (endRow == sRow && endCol == sCol) {
			return -1;
		}

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				this.cumulativeFear[i][j] = Integer.MAX_VALUE;
			}
		}

		PriorityQueue<Coordinate> pq = new PriorityQueue<>();
		pq.offer(new Coordinate(startRow, startCol, 0));
		this.cumulativeFear[startRow][startCol] = 0;

		while (!pq.isEmpty()) {
			Coordinate curr = pq.poll();

			if (curr.fear == cumulativeFear[curr.x][curr.y]) {
				for (int direction = 0; direction < 4; direction++) {
					int nextX = curr.x + DELTAS[direction][0];
					int nextY = curr.y + DELTAS[direction][1];
					if (!(nextX < 0 || nextY < 0 || nextX >= this.row || nextY >= this.col)) {
						int currFear = WALL_FUNCTIONS.get(direction).apply(maze.getRoom(curr.x, curr.y));

						if (nextX == sRow && nextY == sCol) {
							cumulativeFear[nextX][nextY] = -1; // relax immediately
							pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
						} else if (currFear == EMPTY_SPACE) {
							// add 1 fear level to next room
							int fearEst = cumulativeFear[curr.x][curr.y] + 1;

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}
						} else if (currFear != TRUE_WALL) {
							// add corresponding fear level
							int fearEst = Math.max(cumulativeFear[curr.x][curr.y], currFear);

							if (cumulativeFear[nextX][nextY] > fearEst) {
								cumulativeFear[nextX][nextY] = fearEst; // relax
								pq.offer(new Coordinate(nextX, nextY, cumulativeFear[nextX][nextY]));
							}

						}
					}
				}
			}
		}
		return cumulativeFear[endRow][endCol] < Integer.MAX_VALUE ? cumulativeFear[endRow][endCol] : null;
	}
 */