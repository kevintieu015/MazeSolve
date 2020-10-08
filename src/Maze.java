import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * This class is the graph class that creates a matrix of the given size. There
 * are methods here that help create the random maze. This project will use
 * Breadth first search and Depth first search to solve the maze.
 */
class Maze {
	public Random myRandGen;

	// The colors being declared will be used to compare
	public static final int GRAY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;

	// the variables used in the graph class
	public int length;
	public int depthTime, breadthTime, depthFirstVisit;
	public Queue<Node> index;
	public Node[][] matrix;
	public Edge[] list;
	public String displayGraph;
	public Node node;

	/**
	 * The constructor for graph class
	 * 
	 * @param x
	 *            - the size of the matrix that is created
	 */
	public Maze(int x) {
		length = x;

		index = new LinkedList<Node>();

		matrix = new Node[length][length];

		list = new Edge[length * length];
		for (int i = 0; i < length * length; i++) {
			list[i] = null;
		}
		myRandGen = new Random();
	}

	/**
	 * Does breadth first search according to the random maze that was generated
	 * used the psuedo code from the book assigned the class and used online
	 * sources to help further understand
	 */
	public void breadthFirst() {
		int breadthCount = 0;
		Node first = matrix[0][0];

		first.color = GRAY;
		first.p = null;
		index.add(first);

		while (!index.isEmpty()) {
			Node fq = index.remove();// Front of the queue
			Edge p = list[fq.label - 1];

			while (p != null) {
				if (p.node.color == WHITE) {
					p.node.color = GRAY;
					breadthCount++;
					p.node.visitedOrder = breadthCount;
					p.node.distance = fq.distance + 1;
					p.node.p = fq;
					index.add(p.node);

				}
				p = p.next;
			}
		}
		breadthTime = breadthCount;
	}

	/**
	 * Does depth first search according to the random maze that was generated
	 * used the pseudo code from the book assigned the class and used online
	 * sources to help further understand
	 */
	public void depthFirst() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Node u = matrix[i][j];
				u.color = WHITE;
				u.p = null;
			}
		}
		depthTime = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Node u = matrix[i][j];
				if (u.color == WHITE)
					depthVisit(u);
			}
		}
	}

	// checks if its last edge
	public Edge lastEdge(Edge n) {
		while (n.next != null) {
			n = n.next;
		}
		return n;
	}

	/**
	 * method used to use dfs pseudo code found in book and used online for
	 * support
	 * 
	 * @param newNode
	 *            - 
	 */
	private void depthVisit(Node newNode) {

		depthTime++;
		newNode.visitedOrder = depthFirstVisit;
		depthFirstVisit++;
		newNode.color = GRAY;
		newNode.discTime = depthTime;

		Edge p = list[newNode.label - 1];
		while (p != null) {
			if (p.node.color == WHITE) {
				p.node.p = newNode;
				depthVisit(p.node);
			}
			p = p.next;
		}

		depthTime++;
		newNode.finTime = depthTime;
		newNode.color = BLACK;
	}

	// draws the empty Matrix
	public void showEmpty(int row, int column) {
		if (matrix[row][column].leftExist) {
			String string = matrix[row][column].inLine ? "|#" : "| ";
			System.out.print(string);
			displayGraph += string;
		} else {
			String string = matrix[row][column].inLine ? " #" : "  ";
			System.out.print(string);
			displayGraph += string;
		}
	}

	// method that shows if it has visited
	public boolean setVisit(boolean bool) {
		if (bool != false) {
			return false;
		} else
			return true;
	}

	// helper method for the removeWall method
	private boolean checkWalls(boolean bool) {
		if (!bool) {
			return true;
		} else
			return false;
	}

	// displays the breadth matrix for the main test
	public void showBreadth(int row, int col) {
		if (matrix[row][col].leftExist) {
			String string = (matrix[row][col].visitedOrder <= matrix[length - 1][length - 1].visitedOrder)
					? "|" + String.format("%1s", matrix[row][col].visitedOrder % 10) : "| ";
			System.out.print(string);
			displayGraph = displayGraph + string;
		} else {
			String string = (matrix[row][col].visitedOrder <= matrix[length - 1][length - 1].visitedOrder)
					? "" + String.format("%2s", matrix[row][col].visitedOrder % 10) : "  ";
			System.out.print(string);
			displayGraph = displayGraph + string;
		}
	}

	// displays the depth matrix for the main test
	public void showDepth(int row, int column) {
		if (matrix[row][column].leftExist) {
			String string = matrix[row][column].discTime <= matrix[length - 1][length - 1].discTime
					? "|" + String.format("%1s", matrix[row][column].visitedOrder % 10) : "| ";
			System.out.print(string);
			displayGraph = displayGraph + string;
		} else {
			String string = matrix[row][column].discTime <= matrix[length - 1][length - 1].discTime
					? "" + String.format("%2s", matrix[row][column].visitedOrder % 10) : "  ";
			System.out.print(string);
			displayGraph = displayGraph + string;
		}
	}

	// checks the int of neighbors
	public int checkNeighbors(Node v) {
		for (int i = 0; i < v.adjacent.size(); i++) {
			if (v.adjacent.get(i).wasVisited) {
				v.adjacent.remove(v.adjacent.get(i));
			}
		}
		int adjacent = 0;
		for (int i = 0; i < v.adjacent.size(); i++) {
			if (!v.adjacent.get(i).wasVisited) {
				adjacent++;
			}
		}
		return adjacent;
	}

	// checks to see if it is the last one
	boolean checkLast(Edge n) {
		return (n != null && n.next == null);
	}

	// sets the adjacent node
	public void nextAdjacent(Node vert) {

		if (vert.Trow == false) {
			Node topVert = matrix[vert.horizontalPosition][vert.verticalPosition - 1];
			vert.adjacent.add(topVert);
		}

		if (vert.Brow == false) {
			Node bottomVert = matrix[vert.horizontalPosition][vert.verticalPosition + 1];
			vert.adjacent.add(bottomVert);
		}

		if (vert.Rcol == false) {
			Node rightVert = matrix[vert.horizontalPosition + 1][vert.verticalPosition];
			vert.adjacent.add(rightVert);
		}

		if (vert.Lcol == false) {
			Node leftVert = matrix[vert.horizontalPosition - 1][vert.verticalPosition];
			vert.adjacent.add(leftVert);
		}

	}

	// removes the wall if it exists
	public void removeWall(Node i, Node j) {
		if (i.label + length == j.label) {
			j.topExist = checkWalls(j.topExist);
			i.bottomExist = checkWalls(i.bottomExist);
			i.adjacent.remove(j);
			j.adjacent.remove(i);
		} else if (i.label + 1 == j.label) {
			j.leftExist = checkWalls(j.leftExist);
			i.rightExist = checkWalls(i.rightExist);
			i.adjacent.remove(j);
			j.adjacent.remove(i);
		} else if (i.label - 1 == j.label) {
			j.rightExist = checkWalls(j.rightExist);
			i.leftExist = checkWalls(i.leftExist);
			i.adjacent.remove(j);
			j.adjacent.remove(i);
		} else if (i.label - length == j.label) {
			j.bottomExist = checkWalls(j.bottomExist);
			i.topExist = checkWalls(i.topExist);
			i.adjacent.remove(j);
			j.adjacent.remove(i);
		}
	}

}
/**
 * This is a class that is used to help draw the maze in the correct format 
 * there are methods in here that are used in the main method to display and test
 * both the breadth first and the depth first. This class also contains the main method that is used
 * to shows the random maze, the dfs, the bfs, and the single path. 
 */
class GraphDrawer {

	private Maze graph;

	/**
	 * Constructor that initializes the graph from the graph class
	 * 
	 * @param graph
	 *            - the graph/maze it takes
	 */
	public GraphDrawer(Maze graph) {
		this.graph = graph;
	}

	// randomly generates the maze using assistance found from online examples of
	// mazes.
	private void randomMazeGenerator() {

		Stack<Node> cellStack = new Stack<Node>();
		Node currentCell = graph.matrix[0][0];

		currentCell.wasVisited = graph.setVisit(currentCell.wasVisited);
		int visitedCells = 1;

		int totalCells = graph.length * graph.length;
		while (visitedCells < totalCells) {

			if (graph.checkNeighbors(currentCell) >= 1) {
				int random = graph.myRandGen.nextInt(graph.checkNeighbors(currentCell));
				Node randomNeighbor = currentCell.adjacent.get(random);
				graph.removeWall(randomNeighbor, currentCell);

				Edge neighborNode = new Edge();
				neighborNode.node = randomNeighbor;
				Edge currentNode = graph.list[currentCell.label - 1];

				if (!exists(currentNode)) {
					currentNode = graph.lastEdge(currentNode);
					if (graph.checkLast(currentNode)) {
						currentNode.next = neighborNode;
					}
				}

				else if (exists(currentNode)) {
					currentNode = neighborNode;
					nodeToEdge(currentCell, currentNode);
				}

				Edge currentCellNode = new Edge();
				currentCellNode.node = currentCell;
				currentNode = graph.list[randomNeighbor.label - 1];
				if (!exists(currentNode)) {
					currentNode = graph.lastEdge(currentNode);
					if (graph.checkLast(currentNode)) {
						currentNode.next = currentCellNode;
					}
				} else if (exists(currentNode)) {
					currentNode = currentCellNode;
					nodeToEdge(randomNeighbor, currentCellNode);
				}

				visitedCells = visitedCells + 1;
				cellStack.push(currentCell);
				currentCell = randomNeighbor;
				currentCell.wasVisited = true;
			} else
				currentCell = cellStack.pop();
		}
	}

	private void nodeToEdge(Node a, Edge b) {
		graph.list[a.label - 1] = b;
	}

	// generates the graph
	public void graphGenerator() {
		int label = 1;
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				Node vert = new Node(graph, i, j, label);
				graph.matrix[i][j] = vert;
				label++;
			}
		}

		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				Node current = graph.matrix[i][j];
				graph.nextAdjacent(current);
			}
		}
		randomMazeGenerator();
	}

	// checks if it exists
	private boolean exists(Edge n) {
		return n == null;
	}

	public void showPath(Node n1, Node n2) {

		n2.inLine = true;
		// if label of first does not equal label of second recursivy call
		// method
		if (n1.label != n2.label)
			showPath(n1, n2.p);
	}

	// displays the maze in the proper format that is given in the instructions
	public void showMaze(String show) {
		System.out.println("");
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (graph.matrix[i][j].topExist) {
					String t = (graph.matrix[i][j] == graph.matrix[0][0]) ? "+ " : "+-";
					System.out.print(t);
					graph.displayGraph += t;
				} else {
					System.out.print("+ ");
				}
			}
			System.out.println("+ ");
			for (int j = 0; j < graph.length; j++) {
				switch (show) {
				default: {
					graph.showEmpty(i, j);
					break;
				}
				case "Breadth": {
					graph.showBreadth(i, j);
					break;
				}
				case "Depth": {
					graph.showDepth(i, j);
					break;
				}
				}
			}
			System.out.println("|");
		}

		for (int j = 0; j < graph.length; j++) {
			String string = j == graph.length - 1 ? "+ " : "+-";
			System.out.print(string);
			graph.displayGraph += string;
		}
		System.out.println("+ ");

	}

	// the main method that is used to shows the graphs.
	public static void main(String[] arg) {

		// the size of the matrix, SIZE x SIZE
		int SIZE = 5;

		long startDFS = System.currentTimeMillis();
		Maze graphDFS = new Maze(SIZE);
		GraphDrawer mazeDFS = new GraphDrawer(graphDFS);
		mazeDFS.graphGenerator();

		// displays the maze
		System.out.println("Starting DFS...");
		System.out.print("Depth First Search Maze");
		mazeDFS.showMaze("");

		// displays the maze with the dfs numbers
		graphDFS.depthFirst();
		System.out.print("Depth First Search: ");
		mazeDFS.showMaze("Depth");

		// displays the maze with the single shortest solution path
		mazeDFS.showPath(graphDFS.matrix[0][0], graphDFS.matrix[SIZE - 1][SIZE - 1]);
		System.out.print("Single Path :");
		mazeDFS.showMaze("");

		System.out.println("Ending DFS...");
		System.out.println("The run time for DFS is: " + (System.currentTimeMillis() - startDFS) + " miliseconds");

		// creates the maze for BFS
		Maze graphBFS = new Maze(SIZE);
		GraphDrawer mazeBFS = new GraphDrawer(graphBFS);
		mazeBFS.graphGenerator();

		// displays the maze
		System.out.println("\n\n\nStarting BFS...");
		System.out.print("Maze for Breadth First Search");
		long startBFS = System.currentTimeMillis();
		mazeBFS.showMaze("");

		// Shows the BFS on maze
		graphBFS.breadthFirst();
		System.out.print("Breadth First Search: ");
		mazeBFS.showMaze("Breadth");

		// shows the single path of maze
		mazeBFS.showPath(graphBFS.matrix[0][0], graphBFS.matrix[SIZE - 1][SIZE - 1]);
		System.out.print("Single Path: ");
		mazeBFS.showMaze("");

		System.out.println("Ending BFS...");
		System.out.println("The run time for BFS is: " + (System.currentTimeMillis() - startBFS) + " miliseconds"); // calculate
																													// run
																													// time
																													// for
																													// BFS
	}

}
