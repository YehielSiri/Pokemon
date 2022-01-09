package game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import graph.DirectedWeightedGraph;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;

public class Agent {
//	public static final double EPS = 0.0001;
//	private static int counter = 0;
//	private static int seed = 3331;
	private int id;
	private double value;
	private Node src;
	private Node dest;
	private double speed;
	private GeoLocation pos;
//	private Pokemon currFruit;
//	private long _sg_dt;
//	private String pic;

	/**
	 * A defalt constructor
	 */
	public Agent() {
		this.id = 0;
		this.value = 0;
		this.src = null;
		this.dest = null;
		this.speed = 0;
		this.pos = null;
	}

	public Agent(int id, double value, Node src, Node dest, double speed, GeoLocation pos) {
		this.id = id;
		this.value = value;
		this.src = src;
		this.dest = dest;
		this.speed = speed;
		this.pos = pos;
//		this._curr_node = _gg.getNode(start_node);
//		this._curr_edge = null;
	}

	public boolean update(String json) {
		JsonObject line;
		try {
			// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
			line = new JsonObject(json);
			JsonObject ttt = line.getJSONObject("Agent");
			JsonElement id = ttt.get("id");
			if (id == this.getID() || this.getID() == -1) {
				if (this.getID() == -1) {
					_id = id;
				}
				double speed = ttt.getDouble("speed");
				String p = ttt.getString("pos");
				GeoLocation pp = new GeoLocation(p);
				int src = ttt.get("src");
				int dest = ttt.get("dest");
				double value = ttt.getDouble("value");
				this.pos = pp;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setNextNode(dest);
				this.setValue(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean setNextNode(int dest) {
		boolean ans = false;
		int src = this._curr_node.getKey();
		this._curr_edge = _gg.getEdge(src, dest);
		if (_curr_edge != null) {
			ans = true;
		} else {
			_curr_edge = null;
		}
		return ans;
	}

//	public void setCurrNode(int src) {
//		this._curr_node = _gg.getNode(src);
//	}
//
	public boolean isMoving() {
		return this._curr_edge != null;
	}

	@Override
	public String toString() {
		return "" + this.getId() + "," + pos + ", " + isMoving() + "," + this.getValue();
	}

	public String toJsonString() {
		return toJson();
	}

	public String toJson() {
		int d = this.getNextNode();
		String ans = "{\"Agent\":{"
				+ "\"id\":" + this.getId() + ","
				+ "\"value\":" + this.getValue() + ","
				+ "\"src\":" + this.getSrc().getKey() + ","
				+ "\"dest\":" + this.getDest().getKey() + ","
				+ "\"speed\":" + this.getSpeed() + ","
				+ "\"pos\":\"" + this.getPos().toString() + "\""
				+ "}"
				+ "}";
		return ans;
	}

	public int getId() {
		return this.id;
	}

	public double getValue() {
		return this.value;
	}

	public Node getSrc() {
		return this.src;
	}

	private Node getDest() {
		return this.dest;
	}

	public GeoLocation getPos() {
		return pos;
	}

	public double getSpeed() {
		return this.speed;
	}

	private void setValue(double value) {
		this.value = value;
	}

	private void setSrc(Node src) {
		this.src = new Node(src);
	}
	
	private void setDest(Node dest) {
		this.dest = new Node(dest);
	}
	
	private void setSpeed(double v) {
		this.speed = v;
	}
	
	private void setPos(GeoLocation pos) {
		this.pos = new GeoLocation(pos);
	}

//	public Pokemon get_curr_fruit() {
//		return this._curr_fruit;
//	}
//
//	public void set_curr_fruit(Pokemon curr_fruit) {
//		this._curr_fruit = curr_fruit;
//	}
//
//	public Edge get_curr_edge() {
//		return this._curr_edge;
//	}
//
//	public void set_curr_edge(Edge curr_edge) {
//		this._curr_edge = curr_edge;
//	}
//	
//	public long get_sg_dt() {
//		return _sg_dt;
//	}
//
//	public void set_sg_dt(long _sg_dt) {
//		this._sg_dt = _sg_dt;
//	}
//

	public int getNextNode() {
		int ans = -2;
		if (this._curr_edge == null) {
			ans = -1;
		} else {
			ans = this._curr_edge.getDest();
		}
		return ans;
	}

	public void set_SDT(long ddtt) {
		long ddt = ddtt;
		if (this._curr_edge != null) {
			double w = get_curr_edge().getWeight();
			GeoLocation dest = _gg.getNode(get_curr_edge().getDest()).getLocation();
			GeoLocation src = _gg.getNode(get_curr_edge().getSrc()).getLocation();
			double de = src.distance(dest);
			double dist = _pos.distance(dest);
			if (this.get_curr_fruit().getedge() == this.get_curr_edge()) {
				dist = _curr_fruit.getLocation().distance(this._pos);
			}
			double norm = dist / de;
			double dt = w * norm / this.getSpeed();
			ddt = (long) (1000.0 * dt);
		}
		this.set_sg_dt(ddt);
	}
}