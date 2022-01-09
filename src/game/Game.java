package game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.google.gson.JsonIOException;

import graph.DirectedWeightedGraph;
import graph.DirectedWeightedGraphAlgorithms;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;
import json.JsonGameHandler;
import json.JsonGraphHandler;
import space.GeoSpace1D;
import space.GeoSpace2D;
import space.Range2Range;

/**
 * @author Yehiel Siri
 * 
 * This class represents a Pokemon game which contain set of agents that move on a graph, grab Pokemons and avoid the Zombies.
 */

public class Game {
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;

	private DirectedWeightedGraphAlgorithms graph;
	private ArrayList<Agent> agents;
	private ArrayList<Pokemon> pokemons;

	private ArrayList<String> info;
	private String timeGame;
	private Hashtable<Integer, Edge> nextDest;
	private Hashtable<Integer, ArrayList<Node>> pathToDest;
	private static GeoLocation MIN = new GeoLocation(0, 100,0);
	private static GeoLocation MAX = new GeoLocation(0, 100,0);

	public Game() {
		this.graph = new DirectedWeightedGraphAlgorithms();
		this.agents = new ArrayList<Agent>();
		this.pokemons = new ArrayList<Pokemon>();

		this.info = new ArrayList<String>();
		this.timeGame = "";
		this.nextDest = new Hashtable<Integer, Edge>();
		this.pathToDest = new Hashtable<Integer, ArrayList<Node>>();
	}

	public Game(DirectedWeightedGraphAlgorithms graph, ArrayList<Agent> agents, ArrayList<Pokemon> pokemons, ArrayList<String> info) {
		this.graph = graph;
		this.agents = agents;
		this.pokemons = pokemons;

		this.info = info;
		this.timeGame = "";
		this.nextDest = new Hashtable<Integer, Edge>();
		this.pathToDest = new Hashtable<Integer, ArrayList<Node>>();
	}

	public Game(Game game) {
		// TODO Auto-generated constructor stub
	}

	private void init() {
		MIN = MAX = null;

		double x0 = 0, x1 = 0, y0 = 0, y1 = 0, z = 0;
		Iterator<Node> iter = this.graph.getGraph().nodeIter();

		while(iter.hasNext()) {
			GeoLocation current = iter.next().getLocation();
			if(MIN == null) {
				x0 = x1 = current.x();
				y0 = y1 = current.y();
				MIN = new GeoLocation(x0,y0, z);}
			//Update minimum
			if(current.x() < x0) x0 = current.x();
			if(current.y() < y0) y0 = current.y();
			//Update maximum
			if(current.x() > x1) x1 = current.x();
			if(current.y() > y1) y1 = current.y();
		}
		double dx = x1-x0, dy = y1-y0;
		MIN = new GeoLocation(x0-dx/10,y0-dy/10, z);
		MAX = new GeoLocation(x1+dx/10,y1+dy/10, z);

	}

	public Game copy() {
		return new Game(this);
	}

	public boolean load(String graphFile, String agentsFile, String pokemonsFile, String infoFile) {
		return (JsonGameHandler.JsonGraphDeserializer(graphFile, this.graph)
				&& JsonGameHandler.JsonAgentsDeserializer(agentsFile, this.graph, this.agents)
				&& JsonGameHandler.JsonPokemonsDeserializer(pokemonsFile,this, this.graph, this.pokemons)
				&& JsonGameHandler.JsonInfoDeserializer(infoFile, this.info));
	}

	public DirectedWeightedGraphAlgorithms getGraph() {
		return this.graph;
	}

	public void setGraph(DirectedWeightedGraphAlgorithms graph) {
		this.graph = graph;
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public ArrayList<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(ArrayList<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public ArrayList<String> getInfo() {
		return this.info;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = info;
	}

	public String getTimeGame() {
		return timeGame;
	}

	public void setTimeGame(String time) {
		this.timeGame = time;
	}

	public Hashtable<Integer, Edge> getNextDest() {
		return this.nextDest;
	}

	public void setNextDest(Hashtable<Integer, Edge> nextDestToSet) {
		this.nextDest = nextDestToSet;
	}

	public Hashtable<Integer, ArrayList<Node>> getPathToDest() {
		return this.pathToDest;
	}

	public void setPathToDest(Hashtable<Integer, ArrayList<Node>> pathToDestToSet) {
		this.pathToDest = pathToDestToSet;
	}

	//	public static ArrayList<Agent> getAgents(String aa, DirectedWeightedGraph gg) {
	//		ArrayList<Agent> ans = new ArrayList<Agent>();
	//		try {
	//			JSONObject ttt = new JSONObject(aa);
	//			JSONArray ags = ttt.getJSONArray("Agents");
	//			for(int i=0;i<ags.length();i++) {
	//				Agent c = new Agent(gg,0);
	//				c.update(ags.get(i).toString());
	//				ans.add(c);
	//			}
	//		} catch (JsonIOException e) {
	//			e.printStackTrace();
	//		}
	//		return ans;
	//	}
	//
	//	public static ArrayList<Pokemon> json2Pokemons(String fs) {
	//		ArrayList<Pokemon> ans = new  ArrayList<Pokemon>();
	//		try {
	//			JSONObject ttt = new JSONObject(fs);
	//			JSONArray ags = ttt.getJSONArray("Pokemons");
	//			for(int i=0;i<ags.length();i++) {
	//				JSONObject pp = ags.getJSONObject(i);
	//				JSONObject pk = pp.getJSONObject("Pokemon");
	//				int t = pk.getInt("type");
	//				double v = pk.getDouble("value");
	//				//double s = 0;//pk.getDouble("speed");
	//				String p = pk.getString("pos");
	//				Pokemon f = new Pokemon(new GeoLocation(p), t, v, 0, null);
	//				ans.add(f);
	//			}
	//		}
	//		catch (JsonIOException e) {
	//			e.printStackTrace();
	//		}
	//		return ans;
	//	}
	//
	public Edge findEdge(GeoLocation pokemonPos, int pokemonType) {
		Iterator<Edge> iter = graph.getGraph().edgeIter();
		while (iter.hasNext()) {
			Edge currentEdge = iter.next();
			Node edgeSrc = graph.getGraph().getNode(currentEdge.getSrc());
			Node edgeDest = graph.getGraph().getNode(currentEdge.getDest());
			GeoLocation srcPos = edgeSrc.getLocation();
			GeoLocation destPos = edgeDest.getLocation();
			boolean flagForEdge = pokemonType > 0 && edgeSrc.getKey() < edgeDest.getKey() || pokemonType < 0 && edgeSrc.getKey() > edgeDest.getKey();
			if (isOnThisEdge(pokemonPos, srcPos, destPos) && flagForEdge) {
				return currentEdge;
			}
		}
		return null;
	}

	public void updateEdge(Pokemon pokemonToAdd, DirectedWeightedGraph graph) {
		Iterator<Edge> iterator = graph.edgeIter();
		while(iterator.hasNext()) {
			Edge edge = iterator.next();
			if(isOnThisEdge(pokemonToAdd.getPos(), edge, pokemonToAdd.getType(), graph)) {
				pokemonToAdd.setEdge(edge);
			}
		}
	}

	private boolean isOnThisEdge(GeoLocation pos, GeoLocation src, GeoLocation dest) {
		double dist = src.distance(dest);
		double distThroughPos = src.distance(pos) + pos.distance(dest);

		if(dist > distThroughPos-EPS2) return true;

		return false;
	}

	private boolean isOnThisEdge(GeoLocation pos, int src, int dest, DirectedWeightedGraph graph) {
		GeoLocation source = graph.getNode(src).getLocation();
		GeoLocation destination = graph.getNode(dest).getLocation();

		return isOnThisEdge(pos, source, destination);
	}

	private boolean isOnThisEdge(GeoLocation pos, Edge edge, int type, DirectedWeightedGraph graph) {
		int src = graph.getNode(edge.getSrc()).getKey();
		int dest = graph.getNode(edge.getDest()).getKey();

		if ((type < 0 && dest > src) || (type > 0 && src > dest))	// It is cannot be Pokemon is on this edge, because edge is rising while Pokemon's edge is going down, or vice versa.
			return false;
		return isOnThisEdge(pos, src, dest, graph);
	}

	public Range2Range toFrame(DirectedWeightedGraph graph, GeoSpace2D frame) {
		GeoSpace2D world = GraphRange(graph);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

	private GeoSpace2D GraphRange(DirectedWeightedGraph graph) {
		Iterator<Node> iter = graph.nodeIter();
		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		boolean first = true;
		while(iter.hasNext()) {
			GeoLocation p = iter.next().getLocation();
			if(first) {
				x0 = p.x();
				x1 = x0;
				y0 = p.y();
				y1 = y0;
				first = false;
			}
			else {
				if(p.x() < x0) x0 = p.x();
				if(p.x() > x1) x1 = p.x();
				if(p.y() < y0) y0 = p.y();
				if(p.y() > y1) y1 = p.y();
			}
		}
		GeoSpace1D xRange = new GeoSpace1D(x0,x1);
		GeoSpace1D yRange = new GeoSpace1D(y0,y1);
		return new GeoSpace2D(xRange, yRange);
	}
}
