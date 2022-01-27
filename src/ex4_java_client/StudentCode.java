package ex4_java_client; /**
 * @author AchiyaZigi
 * A trivial example for starting the server and running all needed commands
 */
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import game.Agent;
import game.Game;
import graph.Edge;
import graph.Node;
import gui.GUI;


public class StudentCode implements Runnable{
	private static GUI gui;
	private static Game game;
	//    public static game_service game;
	public static int numGame;


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



	/**
	 * An auxiliary function - moves the Agent from the method "next node" to the closest Pokemon.
	 *
	 * @param ag           - the Agent to move
	 * @param shortestPath - the route that the robot should to pass.
	 * @param catchPokemon - the pokemon that the agent need to cath.
	 */
	public static void Run_To_Pokemon_Agent(Agent ag, List<Node> shortestPath, Edge catchPokemon) {
		for (Node n : shortestPath) {
			if(n!=null)
				game.chooseNextEdge(ag.getId(), n.getKey());


		}

	}

	/**
	 * This function check the num of sleep for the run function.
	 *
	 * @return the num of sleep.
	 */
	public int numOfSleep() {
		int ans = 100;
		if (this.numGame == 0) return 147;
		if (this.numGame == 1) return 108;
		if (this.numGame == 2) return 119;
		if (this.numGame == 3) return 103;
		if (this.numGame == 4) return 111;
		if (this.numGame == 5) return 113;
		if (this.numGame == 6) return 400;
		if (this.numGame == 7) return 104;
		if (this.numGame == 8) return 145;
		if (this.numGame == 9) return 127;
		if (this.numGame == 10) return 278;
		if (this.numGame == 11) return 101;
		if (this.numGame == 12) return 155;
		if (this.numGame == 13) return 101;
		if (this.numGame == 14) return 180;
		if (this.numGame == 15) return 110;
		for (Agent agent : game.getAgents()) {
			List<Edge> edgeFruit = getListOfEdgeF();
			for (Edge e : edgeFruit) {
				if (agent.getCurrentV().getKey() == e.getSrc()) {
					return 80;
				}
			}
		}
		return ans;
	}

	/**
	 * This function takes from the server as points earned in the game.
	 *
	 * @param server is the type of game.
	 * @return the grade of game.
	 */
	public double myGrade(game_service server) {
		double myGrade = 0;
		try {
			String json = server.toString();
			JSONObject gameJson = new JSONObject(json);
			JSONObject gameServer = gameJson.getJSONObject("GameServer");
			myGrade = gameServer.getDouble("grade");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myGrade;
	}

	/**
	 * Function to return how many moves have done during the game.
	 *
	 * @param server is the type of the game.
	 * @return how many moves have done.
	 */
	public double numOfMoves(game_service server) {
		double myMoves = 0;
		try {
			String json = server.toString();
			JSONObject gameJson = new JSONObject(json);
			JSONObject gameServer = gameJson.getJSONObject("GameServer");
			myMoves = gameServer.getDouble("moves");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myMoves;
	}

	@Override
	public void run() {
		game.login(id);

		String g = game.getGraph();
		String pks = game.getPokemons();
		directedWeightedGraph gg = game.getJava_Graph_Not_to_be_used();
		init(game);

		game.startGame();
		File file = new File("doc/musicGame.wav");
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clip.start();



		gui.setTitle("Ex2 - OOP: The Challenge Pokemon Game");
		int ind = 0;

		long time = game.timeToEnd();
		while (game.isRunning()) {

			moveAgants(game, game.getGraph());
			try {
				if (ind % 1 == 0) {
					game.setTimeGame("Time Left :" + (double)game.timeToEnd()/1000);
					gui.repaint();
				}
				Thread.sleep(numOfSleep());
				ind++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String res = game.toString();

		System.out.println(res);
		//  JFrame jj =new JFrame();
		ImageIcon imageIcon2= new ImageIcon("doc/tenor 2.gif");
		//   JOptionPane.showMessageDialog(jj,imageIcon2);
		//   jj.createImage(50,50);

		JFrame jf = new JFrame();
		jf.setIconImage(imageIcon2.getImage());
		jf.createImage(50,50);
		jf.setBounds(0,100,80,80);
		JOptionPane.showMessageDialog(jf,"THE GAME IS OVER" + "\n" + "YOUR GRADE IS : " + myGrade(game) + "\n" + "YOUR MOVES IS :" + " " + numOfMoves(game));
		JOptionPane.showMessageDialog(jf,imageIcon2);
		System.exit(0);
	}

}
