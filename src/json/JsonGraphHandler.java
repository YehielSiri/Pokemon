package json;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import com.google.gson.*;

import graph.DirectedWeightedGraph;
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
 * @since  06/12/2021
 * 
 * A Json type files handle class for 'graph' package
 */


public class JsonGraphHandler {

	/**
	 * JsonDeserializer(json_file, graph) - extracts the graph details from Json file and load to 'graph'
	 * 
	 * @param json_file - file name
	 * @param g			- a directed weighted graph
	 * @return boolean	- true <-> file has deserialized
	 */
	public static boolean JsonDeserializer(String json_file, DirectedWeightedGraph graph) {
		File jsonFile = new File(json_file);

		JsonElement fileElement;
		JsonObject fileObject;
		JsonParser parser = new JsonParser();

		JsonArray nodes;
		JsonArray edges;

		try {
			fileElement = parser.parse(new FileReader(jsonFile));
			fileObject = fileElement.getAsJsonObject();

			//System.out.println("Deserializing graph date from Json file!");
			//System.out.println("Nodes:");
			nodes = fileObject.get("Nodes").getAsJsonArray();
			for (JsonElement nodeElement : nodes) {
				JsonObject NodeObject = nodeElement.getAsJsonObject();

				//Extract data:
				String[] position = NodeObject.get("pos").getAsString().split(",");
				//				String[] xyz = pos.split(",");
				//				double x = Double.parseDouble(xyz[0]);
				//				double y = Double.parseDouble(xyz[1]);
				//				double z = Double.parseDouble(xyz[2]);
				double x = Double.parseDouble(position[0]);
				double y = Double.parseDouble(position[1]);
				double z = Double.parseDouble(position[2]);

				GeoLocation loc = new GeoLocation(x, y, z);

				//				System.out.println("Position: " + pos);
				int id = NodeObject.get("id").getAsInt();
				//              System.out.println("ID: " + id);
				Node n = new Node(id, loc);
				graph.addNode(n);
			}

			//			System.out.println();
			//			System.out.println("Edges:");
			edges = fileObject.get("Edges").getAsJsonArray();
			for (JsonElement edgeElement : edges) {
				JsonObject EdgeObject = edgeElement.getAsJsonObject();

				//Extract data:
				int source = EdgeObject.get("src").getAsInt();
				//System.out.println("Source: " + source);
				double weight = EdgeObject.get("w").getAsDouble();
				//System.out.println("Weight: " + weight);
				int destination = EdgeObject.get("dest").getAsInt();
				//System.out.println("Destination: " + destination);
				graph.connect(source, destination, weight);
			}


		} catch (Exception e) {
			// TODO: file has not found
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * JsonSerializer(json_file, graph) - exports the graph details from 'graph' and load to a 'Json' file.
	 * 
	 * @param json_file - file name
	 * @param graph		- a directed weighted graph
	 * @return boolean	- true <-> file has serialized
	 */
	public static boolean JsonSerializer(String json_file, DirectedWeightedGraph graph) {
		G_S_Handler dwg = new G_S_Handler(graph);

		try (Writer writer = new FileWriter(json_file)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(dwg, writer);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

/**
 * An auxiliary class's Json_Handler
 * Directed weighted Graph Serialization handler
 * 
 * @author Yehiel Siri
 * @since  06/12/2021
 */
class G_S_Handler implements Serializable {
	private static final long serialVersionUID = 1L;

	List<Edge> Edges;
	List<Node_Serialization> Nodes;

	/**
	 * Makes use of edges and nodes Iterators implemented in Directed_Weighted_Graph
	 *
	 * @param graph - Directed_Weighted_Graph object which is this class serialized
	 */
	public G_S_Handler(DirectedWeightedGraph graph) {
		Iterator<Node> nodesPointer = graph.nodeIter();
		Iterator<Edge> edgesPointer = graph.edgeIter();

		Edges = new ArrayList<Edge>();
		Nodes = new ArrayList<Node_Serialization>();

		while (edgesPointer.hasNext()) {
			this.Edges.add(edgesPointer.next());
		}

		Node node;
		while (nodesPointer.hasNext()) {
			node = nodesPointer.next();
			this.Nodes.add(new Node_Serialization(node.getKey(), node.getLocation()));
		}
	}
}

/**
 * An auxiliary class's Json_Handler
 * 
 * @author Yehiel Siri
 * @since  06/12/2021
 */
class Node_Serialization implements Serializable {
	private static final long serialVersionUID = 1L;

	String pos;
	int key;

	/**
	 * This is a constructor method, unmentioned parameters are not required for serialization
	 *
	 * @param key - key of node for serialization
	 * @param pos - a geographic location of the node
	 */
	public Node_Serialization(int key, GeoLocation pos) {
		this.key = key;
		this.pos = pos.toString();
	}
}