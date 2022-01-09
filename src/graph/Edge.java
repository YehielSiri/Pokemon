package graph;

import java.io.Serializable;

/**
 * @author Yehiel Siri
 * @since 06/12/2021
 * 
 * A directed weighted graph edge representation class-object.
 * Represents set of operations applicable on a edge in 
 * a (directional) weighted graph by implementing the EdgeData interface.
 */
public class Edge implements Serializable {
	private final int src, dest;
	private final double weight;

	private transient String info;
	private transient int tag = 0;	//color; -1=black 0=white 1=gray

	/**
	 * A default constructor.
	 */
	public Edge() {
		this.src = 0;
		this.dest = 0;
		this.weight = 0;
	}

	/**
	 * Constructor
	 *
	 * @param source
	 * @param destination
	 * @param weight
	 */
	public Edge(int src, int dest, double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}

	/**
	 * Copy constructor
	 *
	 * @param other
	 */
	public Edge(Edge other) {
		this.src = other.getSrc();
		this.dest = other.getDest();
		this.weight = other.getWeight();
		this.info = other.getInfo();
		this.tag = other.getTag();
	}

	public int getSrc() {
		return this.src;
	}

	public int getDest() {
		return this.dest;
	}

	public double getWeight() {
		return this.weight;
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

	@Override
	public String toString() {
		//		return "{ Source = " + this.getSrc() + ", Weight = " + this.getWeight() + ", Destination = " + this.getDest() + " }";
		return this.getSrc() + "-" + this.getDest();
	}

	/**
	 * Equivalence checking function between this and other edges:
	 * Two edges are equal iff the all three are equal to their counterparts in 
	 * the other; source, destination and weight.
	 * 
	 * @param other - Edge_Data object
	 * @return boolean
	 */
	public boolean isEqual(Edge other) {
		return ( this.src == other.getSrc() ) && ( this.weight == other.getWeight() ) && ( this.dest == other.getDest() );
	}

	/**
	 * Weight equivalence checking function between this and other edges:
	 * 
	 * @param other - Edge_Data object
	 * @return boolean
	 */
	public boolean isWeightedEqual(Edge other) {
		return (this.weight == other.getWeight());
	}
}
