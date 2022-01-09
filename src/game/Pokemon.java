package game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import graph.Edge;
import graph.GeoLocation;

public class Pokemon {
	private Edge edge;
	private double value;
	private int type;
//	private boolean isEaten;
	private GeoLocation pos;
//	private double min_dist;
//	private int min_ro;
//	private String pic;
	
	public Pokemon() {
		this.edge = new Edge();
		this.value = 0;
		this.type = 0;
		this.pos = new GeoLocation();
	}

	public Pokemon(Edge edge, double value, int type, GeoLocation pos) {
		this.edge = edge;
		this.value = value;
		this.type = type/*edge.getDest() - edge.getSrc()*/;
		this.pos = pos;
//		_speed = s;
//		min_dist = -1;
//		min_ro = -1;
	}

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public double getValue() {
		return value;
	}

	public int getType() {
		return type;
	}

	public GeoLocation getPos() {
		return pos;
	}

//	public double getMin_dist() {
//		return min_dist;
//	}
//
//	public void setMin_dist(double mid_dist) {
//		this.min_dist = mid_dist;
//	}
//
//	public int getMin_ro() {
//		return min_ro;
//	}
//
//	public void setMin_ro(int min_ro) {
//		this.min_ro = min_ro;
//	}
//	

	public String toJson() {
		return "F:{v=" + value + ", t=" + type + "}";
	}
	
	@Override
	public String toString() {
		return "Edge: " + this.getEdge().toString() + ", Value: " + this.getValue();
	}
}
