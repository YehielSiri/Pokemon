package ex4_java_client; /**
 * @author AchiyaZigi
 * A trivial example for starting the server and running all needed commands
 */
import java.io.IOException;
import java.util.Scanner;

import game.Game;
import graph.DirectedWeightedGraphAlgorithms;
import gui.GUI;

public class StudentCode {
	public static void main(String[] args) {

		Client client = new Client();

		//Start connection to Pokemon server:
		try {
			client.startConnection("127.0.0.1", 6666);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Collect & build game data:
		String graphJson = client.getGraph();
		String gameInfoJson = client.getInfo();
		String agentsJson = client.getAgents();
		String pokemonsJson = client.getPokemons();
		String isRunningJson = client.isRunning();

		Game game = new Game();
		game.load(graphJson, agentsJson, pokemonsJson, gameInfoJson);
		//		DirectedWeightedGraphAlgorithms gameGraph = new DirectedWeightedGraphAlgorithms();
		//		gameGraph.load(graphJson);
		//		Game game = new Game(gameGraph);
		//		game.updateInfo(gameInfoJson);
		//		game.updatePokemons(pokemonsJson);
		//		game.updateAgents(agentsJson);


		//Display GUI:
		GUI window = new GUI();

		//Play:
		client.start();

		while (client.isRunning().equals("true")) {
			client.move();
			System.out.println(client.getAgents());
			System.out.println(client.timeToEnd());

			Scanner keyboard = new Scanner(System.in);
			System.out.println("enter the next dest: ");
			int next = keyboard.nextInt();
			client.chooseNextEdge("{\"agent_id\":0, \"next_node_id\": " + next + "}");

		}

		//Stop connection to Pokemon server:
		try {
			client.stopConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Stop GUI:

	}
}
