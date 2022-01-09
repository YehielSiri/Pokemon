package json;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import com.google.gson.*;

import game.Game;
import game.Agent;
import game.Pokemon;
import graph.DirectedWeightedGraphAlgorithms;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yehiel Siri
 * @since  09/01/2022
 * 
 * A Json type files handle class for 'game' package
 */


public class JsonGameHandler {

	/**
	 * JsonGraphDeserializer(json_file, graph) - extracts the graph details from Json file and load to 'graph'
	 * 
	 * @param json_file - a directed weighted graph as a Json string format
	 * @param graph		- a directed weighted graph object
	 * @return boolean	- true <-> file has deserialized
	 */
	public static boolean JsonGraphDeserializer(String json_file, DirectedWeightedGraphAlgorithms graph) {
		return JsonGraphHandler.JsonDeserializer(json_file, graph.getGraph());
	}

	/**
	 * JsonAgentsDeserializer(json_file, graph, agents) - extracts agents details from Json file and load to the agents list
	 * 
	 * @param json_file	- an agents list as a Json string format
	 * @param graph		- a directed weighted graph object
	 * @param agents	- an agents list
	 * @return boolean	- true <-> file has deserialized
	 */
	public static boolean JsonAgentsDeserializer(String json_file, DirectedWeightedGraphAlgorithms graph, ArrayList<Agent> agents) {
		File jsonFile = new File(json_file);

		JsonElement fileElement;
		JsonObject fileObject;
		JsonParser parser = new JsonParser();

		JsonArray agentsFromFile;

		try {
			fileElement = parser.parse(new FileReader(jsonFile));
			fileObject = fileElement.getAsJsonObject();

			//System.out.println("Deserializing agents from Json file!");
			//System.out.println("Agents:");
			agentsFromFile = fileObject.get("Agents").getAsJsonArray();
			for (JsonElement agentElement : agentsFromFile) {
				JsonObject current = agentElement.getAsJsonObject();

				//Extract data:
				int id = current.get("id").getAsInt();
				double value = current.get("value").getAsDouble();
				int source = current.get("src").getAsInt();
				Node src = graph.getGraph().getNode(source);
				int destination = current.get("dest").getAsInt();
				Node dest = graph.getGraph().getNode(destination);
				double speed = current.get("speed").getAsDouble();

				String[] position = current.get("pos").getAsString().split(",");
				double x = Double.parseDouble(position[0]);
				double y = Double.parseDouble(position[1]);
				double z = Double.parseDouble(position[2]);

				GeoLocation pos = new GeoLocation(x, y, z);

				Agent agent = new Agent(id, value, src, dest, speed, pos);
				agents.add(agent);
			}
		} catch (Exception e) {
			// TODO: file has not found
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * JsonPokemonsDeserializer(json_file, graph, pokemons) - extracts Pokemons details from Json file and load to the Pokemons list
	 * 
	 * @param json_file	- a Pokemons list as a Json string format
	 * @param graph		- a directed weighted graph object
	 * @param pokemons	- a Pokemons list
	 * @return boolean	- true <-> file has deserialized
	 */
	public static boolean JsonPokemonsDeserializer(String json_file, Game game, DirectedWeightedGraphAlgorithms graph, ArrayList<Pokemon> pokemons) {
		File jsonFile = new File(json_file);

		JsonElement fileElement;
		JsonObject fileObject;
		JsonParser parser = new JsonParser();

		JsonArray pokemonsFromFile;

		try {
			fileElement = parser.parse(new FileReader(jsonFile));
			fileObject = fileElement.getAsJsonObject();

			//System.out.println("Deserializing pokemons from Json file!");
			//System.out.println("Pokemons:");
			pokemonsFromFile = fileObject.get("Pokemons").getAsJsonArray();
			for (JsonElement pokemonElement : pokemonsFromFile) {
				JsonObject current = pokemonElement.getAsJsonObject();

				//Extract data:
				//int id = current.get("id").getAsInt();
				Edge edge  = new Edge();
				double value = current.get("value").getAsDouble();
				int type = current.get("type").getAsInt();

				String[] position = current.get("pos").getAsString().split(",");
				double x = Double.parseDouble(position[0]);
				double y = Double.parseDouble(position[1]);
				double z = Double.parseDouble(position[2]);

				GeoLocation pos = new GeoLocation(x, y, z);
				edge = game.findEdge(pos, type);

				Pokemon pokemon = new Pokemon(edge, value, type, pos);
				pokemons.add(pokemon);
			}
		} catch (Exception e) {
			// TODO: file has not found
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * JsonPokemonsDeserializer(json_file, graph, pokemons) - extracts Pokemons details from Json file and load to the Pokemons list
	 * 
	 * @param json_file	- a Pokemons list as a Json string format
	 * @param graph		- a directed weighted graph object
	 * @param pokemons	- a Pokemons list
	 * @return boolean	- true <-> file has deserialized
	 */
	public static boolean JsonInfoDeserializer(String json_file, ArrayList<String> info) {
		// TODO Auto-generated method stub
		return false;
	}
}