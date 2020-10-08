
/**
 * This class is a helper class to the edge class used to write the graph class.
 */
public class Edge {
 
	Edge next = null;
	Node node;

	public Edge(Edge next, Node node) {
		this.next = next;
		this.node = node;
	}

	public Edge() {

	}

	public Edge getNext() {
		return next;
	}

	public Node getVertex() {
		return node;
	}

	public void setNext(Edge next) {
		this.next = next;
	}

	public void setVertex(Node node) {
		this.node = node;
	}

}
