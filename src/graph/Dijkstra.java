package graph;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Yehiel Siri
 * @since 06/12/2021
 * 
 * The algorithm implement for a directed weighted graph, for OOP, Ex2. 
 */
public class Dijkstra {
	private DirectedWeightedGraph graph;

	private HashMap<Integer, Double> dists;		//distance. For any vertex, keeps the distance from the source one.
	private HashMap<Integer, Node> prevs;	//previous. For any vertex, keeps from what vertex did we reach it.
	private PriorityQueue<Node> queue;


	public Dijkstra(DirectedWeightedGraph graph) {
		this.graph = graph;

		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node one, Node another) {
				if (dists.get(one.getKey()) > dists.get(another.getKey()))
					return 1;
				if (dists.get(one.getKey()) < dists.get(another.getKey()))
					return -1;
				return 0;
			}
		};

		this.dists = new HashMap<>();
		this.prevs = new HashMap<>();
		this.queue = new PriorityQueue<>(comparator);

		Iterator<Node> it = this.graph.nodeIter();
		while (it.hasNext()) {
			Node n = it.next();
			int index = n.getKey();
			this.dists.put(index, (double) Integer.MAX_VALUE);
			this.prevs.put(index, null);
		}
	}

	public Dijkstra(DirectedWeightedGraph graph, int src) {
		new Dijkstra(graph);
		start(src);
	}

	public HashMap<Integer,Double> getDists() {
		return this.dists;
	}

	public HashMap<Integer,Node> getPrevs() {
		return this.prevs;
	}

	public double getShortestPathDist(int src, int dest) {
		return getDist(src, dest);
	}

	public List<Node> getShortestPath(int src, int dest) {
		return start(src, dest);
	}

	public double getLongestPath(int src) {
		start(src);
		double max = Integer.MIN_VALUE;
		for (double d : this.dists.values())
			if (d > max) max = d;
		return max;
	}

	/**
	 * A full play of Dijkstra algo.
	 * Finding distance from each vertex to the source one.
	 * 
	 * @param src
	 */
	private void start(int src) {
		//		init();
		this.dists.replace(src, 0.0);
		this.queue.add(this.graph.getNode(src));
		while (!queue.isEmpty()) {
			Node current = this.queue.remove();
			if (this.dists.get(current.getKey()) == Integer.MAX_VALUE)
				return;
			play(current);
		}


	}

	/**
	 * Play of Dijkstra algorithm.
	 * Finding shortest path from source vertex to the destination one and the and the distance itself.
	 * Start from source and stop when reaching the destination
	 * 
	 * @param src
	 * @param dest
	 * @return path - 'null' -> there is no path src-dest
	 */
	private List<Node> start(int src, int dest) {
		this.dists.replace(src, 0.0);
		this.queue.add(this.graph.getNode(src));
		while (!this.queue.isEmpty()) {
			Node current = this.queue.remove();
			if (this.dists.get(current.getKey()) == Integer.MAX_VALUE)
				return null;
			if (current.getKey() == dest) {
				List<Node> path = new LinkedList<>();
				if (prevs.get(current.getKey()) != null || current.getKey() == src)
					while (prevs.get(current.getKey()) != null) {
						path.add(0, current);
						current = prevs.get(current.getKey());
					}
				path.add(0, this.graph.getNodes().get(src));
				return path;
			}
			play(current);
		}
		return null;
	}

	/**
	 * Calculate the smallest distance between two vertex
	 * 
	 * @param src
	 * @param dest
	 * @return distance
	 */
	public double getDist(int src, int dest) {
		this.dists.replace(src, 0.0);
		this.queue.add(graph.getNode(src));
		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if (this.dists.get(current.getKey()) == Integer.MAX_VALUE)
				return -1;
			if (current.getKey() == dest) {
				return this.dists.get(current.getKey());
			}
			play(current);
		}
		return -1;
	}

	/**
	 * Play the Dijkstra algorithm for a current vertex.
	 * 'Make a step' for it relative to his neighbors.
	 * 
	 * @param current
	 */
	private void play(Node current) {
		Iterator<Edge> it = graph.edgeIter(current.getKey());
		while (it.hasNext()) {
			Edge edge = (Edge) it.next();
			double distance = dists.get(current.getKey()) + edge.getWeight();
			if (distance < dists.get(edge.getDest())) {
				dists.replace(edge.getDest(), distance);
				prevs.replace(edge.getDest(), current);
				queue.add(graph.getNode(edge.getDest()));
			}
		}
	}
}
