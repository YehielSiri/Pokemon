package game;

import java.io.File;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import graph.DirectedWeightedGraph;
import graph.DirectedWeightedGraphAlgorithms;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;

public class Agent {
	//	private static int counter = 0;
	private int id;
	private double value;
	private Node currentV;
	private Edge currentE;
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
		this.currentV = null;
		this.currentE = null;
		this.dest = null;
		this.speed = 0;
		this.pos = null;
	}

	public Agent(int id, double value, Node src, Node dest, double speed, GeoLocation pos) {
		this.id = id;
		this.value = value;
		this.currentV = src;
		this.currentE = null;
		this.dest = dest;
		this.speed = speed;
		this.pos = pos;
	}

	/**
	 * One from two ways to keep the data updated.
	 * The usual & cheap way is to update the data online into the client; While the client calculate his
	 * steps, update graph & Agents & Pokemons status, before answering to server.
	 * But, maybe to let the server updating the data every step and just taking it from him, is more
	 * simple. We have to remember that it is given in a json format.
	 * @param json
	 * @return
	 */
	public boolean update(String json, DirectedWeightedGraphAlgorithms graph) {
		File jsonFile = new File(json);

		JsonElement fileElement;
		JsonObject fileObject;
		JsonParser parser = new JsonParser();

		JsonArray agentsFromFile;
		//		JsonObject line;
		try {
			fileElement = parser.parse(new FileReader(jsonFile));
			fileObject = fileElement.getAsJsonObject();

			JsonObject agent = fileObject.get("Agent").getAsJsonObject();

			int id = agent.get("id").getAsInt();
			//Make sure this data belongs to this agent or at least this agent had not constructed.
			if (id == this.getId() || this.getId() == -1) {

				//If had not constructed, update the id:
				if (this.getId() == -1) {
					this.id = id;
				}

				//Any way update everything else:
				this.setValue( agent.get("value").getAsDouble() );
				int src = agent.get("src").getAsInt();
				this.setSrc(graph.getGraph().getNode(src));
				int dest = agent.get("dest").getAsInt();
				this.setDest(graph.getGraph().getNode(dest));
				this.setSpeed( agent.get("speed").getAsDouble() );

				String[] position = agent.get("pos").getAsString().split(",");
				double x = Double.parseDouble(position[0]);
				double y = Double.parseDouble(position[1]);
				double z = Double.parseDouble(position[2]);

				GeoLocation pos = new GeoLocation(x, y, z);

				this.setPos(pos);

				this.setCurrNode(src);
				this.setNextNode(dest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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