package graph;

import java.util.HashMap;

/**
 * @author Yehiel Siri
 * @since 06/12/2021
 * 
 * A directed weighted graph node representation class-object.
 * Represents set of operations applicable on a node (vertex) in 
 * a (directional) weighted graph by implementing the NodeData interface.
 */
public class Node {
	private GeoLocation location;
	private final int key;								//the node ID
	private transient double weight = Double.MAX_VALUE;
	private transient String info = "Unvisited";
	private transient int tag = -1;						//Represent the color

	private HashMap<Integer,Edge> edges;
	private HashMap<Integer,Integer> neighbors;

	/**
	 * Equivalent to a default constructor.
	 * The ID parameter is must in Node building.
	 * 
	 * @param key - id
	 */
	public Node(int key) {
		this.key = key;
		this.location = new GeoLocation();
		this.edges = new HashMap<>();
		this.neighbors = new HashMap<>();
	}

	/**
	 * Constructor
	 *
	 * @param key - id
	 * @param location
	 */
	public Node(int key, GeoLocation location) {
		this.key = key;
		this.location = new GeoLocation(location);
		this.edges = new HashMap<>();
		this.neighbors = new HashMap<>();
	}

	/**
	 * Copy constructor
	 *
	 * @param other
	 */
	public Node(Node other) {
		this.key = other.getKey();
		this.location = new GeoLocation(other.getLocation());
		this.weight = other.getWeight();
		this.info = other.getInfo();
		this.tag = other.getTag();
		this.edges = other.getEdges();
		this.neighbors = other.getNeighbors();
	}

	public int getKey() {
		return this.key;
	}

	public GeoLocation getLocation() {
		return this.location;
	}

	public void setLocation(GeoLocation p) {
		this.location = (GeoLocation)p;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double w) {
		this.weight = w;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String s) {
		this.info = s;
	}

	public int getTag() {
		return this.tag;
	}

	public void setTag(int t) {
		this.tag = t;
	}

	public HashMap<Integer, Edge> getEdges() {
		return this.edges;
	}

	public HashMap<Integer, Integer> getNeighbors() {
		return this.neighbors;
	}

	@Override
	public String toString() {
		return "{Position:" + this.location + ", ID:" + this.key + ", Weight:" + this.weight + '}';
	}

	/**
	 * Equivalence checking function between this and other nodes:
	 * Two nodes are equal iff the all five are equal to their counterparts in 
	 * the other; location, id, weight, edges and neighbors.
	 * In fact, id=id is enough.
	 * 
	 * @return boolean
	 */
	public boolean isEqual(Node other) {
		//		return (this.location.isEqual((Geo_Location) other.getLocation()) 
		//				&& this.key == other.getKey()) && (this.weight == other.getWeight()) 
		//				&& this.edges.equals(other.getEdges()) && (this.neighbors.equals(other.getNeighbors()) );
		return this.key == other.getKey();
	}

}
