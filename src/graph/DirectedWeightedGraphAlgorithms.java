package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import json.JsonGraphHandler;

public class DirectedWeightedGraphAlgorithms {
	private DirectedWeightedGraph graph;

	public void init(DirectedWeightedGraph g) {
		//		g = new Directed_Weighted_Graph();
		this.graph = (DirectedWeightedGraph)g;
	}

	public DirectedWeightedGraph getGraph() {
		return this.graph;
	}

	public DirectedWeightedGraph copy() {
		return new DirectedWeightedGraph(this.graph);
	}

	public boolean isConnected() {
		Iterator<Node> it = graph.nodeIter();
		while (it.hasNext()) if(!BFS(it.next())) return false;
		return true;
	}

	public double shortestPathDist(int src, int dest) {
		Dijkstra d = new Dijkstra(graph);
		return d.getShortestPathDist(src, dest);
	}

	public List<Node> shortestPath(int src, int dest) {
		Dijkstra d = new Dijkstra(graph);
		return d.getShortestPath(src, dest);
	}

	/**
	 * Center is the closest vertex to each other vertices.
	 * Dijkstra will be useful to us.
	 * 
	 * @return center
	 */
	public Node center() {
		if (!this.isConnected()) return null;
		return findCenter();
	}

	/**
	 * Play Dijkstra for each vertex and find the max distance from it.
	 * 
	 * @return center - the minimum one
	 */
	private Node findCenter() {
		double min = Integer.MAX_VALUE;
		Node ans = null;
		Dijkstra d = new Dijkstra(this.graph);
		for (Node n : this.graph.getNodes().values()) {
			double max = d.getLongestPath(n.getKey());
			if (max < min) {
				min = max;
				ans = n;
			}
		}
		return ans;
	}

	/**
	 * 
	 * 
	 * @param cities
	 * @return ans - List<Node>
	 */
	public List<Node> tsp(List<Node> cities) {
		if (cities.size() == 0)
			return null;
		List<Node> ans = new LinkedList<>();
		List<Node> temp = new ArrayList<>(cities);
		Node p = temp.remove(0);
		ans.add(p);
		while (temp.size() >= 1) {
			int key = p.getKey();
			Dijkstra d = new Dijkstra(this.graph, key);
			HashMap<Integer, Double> dist = d.getDists();
			Node t = null;
			double min = (double) Integer.MAX_VALUE;
			for (Node n : temp) {
				if (dist.get(n.getKey()) < min) {
					min = dist.get(n.getKey());
					t = n;
				}
			}
			if (t == null)
				return null;
			List<Node> path = new LinkedList<>();
			HashMap<Integer, Node> prev = d.getPrevs();
			Node c = t;
			if (prev.get(t.getKey()) != null || t.getKey() == key)
				while (prev.get(t.getKey()) != null) {
					path.add(0, t);
					t = prev.get(t.getKey());
				}
			p = c;
			temp.remove(c);
			ans.addAll(path);
		}
		return ans;
	}

	public boolean save(String file) {
		return JsonGraphHandler.JsonSerializer(file, this.graph);
	}

	public boolean load(String file) {
		return JsonGraphHandler.JsonDeserializer(file, this.graph);
	}

	@Override
	public String toString() {
		return this.graph.toString();
	}

	public Boolean BFS(Node vertex){
		long startTime = System.currentTimeMillis();
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Integer> closedList = new HashSet<>();
		Iterator<Edge> it;

		queue.add(vertex);
		closedList.add(vertex.getKey());

		while (!(queue.isEmpty())) {
			Node temp = queue.poll();
			it = graph.edgeIter(temp.getKey());
			while (it.hasNext()) {
				Node dest = graph.getNode(it.next().getDest());
				if( !(closedList.contains(dest.getKey())) ) {
					queue.add(dest);
					closedList.add(dest.getKey());
				}
			}
		}
		return closedList.size() == graph.nodeSize();
	}

}
