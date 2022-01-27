package game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import graph.DirectedWeightedGraph;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;

public class Agent {
	//	private static int counter = 0;
	private int id;
	private double value;
	private Node currentV;
	private Edge currentE;
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
			if (id == this.getId() || this.getId() == -1) {
				if (this.getId() == -1) {
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
		int src = this.currentV.getKey();
		this.currentE = _gg.getEdge(src, dest);
		if (currentE != null) {
			ans = true;
		} else {
			currentE = null;
		}
		return ans;
	}

	public boolean isMoving() {
		return this.currentE != null;
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

	public Node getCurrentV() {
		return this.currentV;
	}

	public Edge getCurrentE() {
		return this.currentE;
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

	public void setCurrentV(Node updatedCurrentV) {
		this.currentV = updatedCurrentV;
	}

	public void setCurrentE(Edge updatedCurrentE) {
		this.currentE = updatedCurrentE;
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
		if (this.currentE == null) {
			ans = -1;
		} else {
			ans = this.currentE.getDest();
		}
		return ans;
	}

//	public void set_SDT(long ddtt) {
//		long ddt = ddtt;
//		if (this.currentE != null) {
//			double w = getCurrentE().getWeight();
//			GeoLocation dest = _gg.getNode(getCurrentE().getDest()).getLocation();
//			GeoLocation src = _gg.getNode(getCurrentE().getSrc()).getLocation();
//			double de = src.distance(dest);
//			double dist = pos.distance(dest);
//			if (this.get_curr_fruit().getedge() == this.getCurrentE()) {
//				dist = _curr_fruit.getLocation().distance(this.pos);
//			}
//			double norm = dist / de;
//			double dt = w * norm / this.getSpeed();
//			ddt = (long) (1000.0 * dt);
//		}
//		this.set_sg_dt(ddt);
//	}
}