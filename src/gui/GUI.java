package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ex4_java_client.Client;
import game.Agent;
import game.Game;
import game.Pokemon;
import graph.DirectedWeightedGraph;
import graph.Edge;
import graph.GeoLocation;
import graph.Node;
import space.GeoSpace1D;
import space.GeoSpace2D;
import space.Range2Range;

/**
 * This class represents a very simple GUI for game which is based on a graph.
 */
public class GUI extends JFrame implements Serializable, MouseListener {
	private BufferedImage getPokemon, getgrpah, getAgent;
	private Client client;
	private int minWindowHeight = 10;
	private int minWindowWidth = 10;
	private Graphics2D g2D;

	private Graphics graphics;

	private Game game;
	private Range2Range gameToFrame;


	public GUI(String a) {
		super(a);
		resize();
		this.setLayout(null);

		setTitle("The Challenge Pokemon");


	}

	public void resize() {


		setSize(1000, 750);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentresizble(ComponentEvent e) {
				Component c = (Component) e.getSource();

			}


		});
	}

	public GUI() {

	}


	public void update(Game game) {
		this.game = game;
		updateFrame();
	}

	private void updateFrame() {
		GeoSpace1D rx = new GeoSpace1D(20, this.getWidth() - 20);
		GeoSpace1D ry = new GeoSpace1D(this.getHeight() - 10, 150);
		GeoSpace2D frame = new GeoSpace2D(rx, ry);
		DirectedWeightedGraph graph = game.getGraph().getGraph();
		gameToFrame = game.toFrame(graph, frame);
	}

	public void paint(Graphics g) {
		int w = minWindowHeight;
		int h = minWindowWidth;

		try {
			this.getSize();
			BufferedImage image1 = ImageIO.read(new File("doc/background.png"));
			g.drawImage(image1, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		paintComponents(g);
		Image image = createImage(w, h);
		Graphics graphics = image.getGraphics();
		paintComponents(graphics);

		g.drawImage(image, 0, 0, this);


		//	updateFrame();


	}

	@Override
	public void paintComponents(Graphics g) {
		// super.paintComponents(g);
		drawPokemon(g);
		drawInfo1(g);
		drawGraph(g);
		drawAgents(g);
		drawInfo(g);
		drawA(g);
	}

	void drawInfo(Graphics g) {

		if (game != null) {
			List<String> str = game.getInfo();
			String dt = "none";
			for (int i = 0; i < str.size(); i++) {
				g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
			}


		}
	}

	void drawGraph(Graphics g) {
		if (game != null) {
			DirectedWeightedGraph gameGraph = game.getGraph().getGraph();
			Iterator<Node> iter = gameGraph.nodeIter();
			while (iter.hasNext()) {
				Node n = iter.next();
				g.setColor(Color.BLUE);
				drawNode(n, 5, g);
				Iterator<Edge> itr = gameGraph.edgeIter(n.getKey());
				while (itr.hasNext()) {
					Edge e = itr.next();
					g.setColor(Color.GREEN);
					drawEdge(e, g);
				}
			}
		}
	}

	void drawPokemon(Graphics g) {
		if (game != null) {
			List<Pokemon> fs = game.getPokemons();
			if (fs != null) {
				Iterator<Pokemon> itr = fs.iterator();

				while (itr.hasNext()) {

					Pokemon f = itr.next();
					GeoLocation c = f.getPos();

					int r = 32;
					//    g.drawImage(this.getPokemon, (int) c.x(), (int) c.y(), null);
					g.setColor(Color.green);

					if (f.getType() < 0 && c != null) {
						//      g.drawImage(this.getPokemon, (int) c.x(), (int) c.y(), null);
						g.setColor(Color.orange);
						try {
							BufferedImage bufferedImage = ImageIO.read(new File("doc/bulbasaur.png"));
							GeoLocation fp = this.gameToFrame.world2frame(c);
							g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (c != null && f.getType() > 0) {
						//   geo_location fp = this._w2f.world2frame(c);
						try {
							BufferedImage bufferedImage = ImageIO.read(new File("doc/mewtoo.png"));
							GeoLocation fp = this.gameToFrame.world2frame(c);
							g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
						} catch (IOException e) {
							e.printStackTrace();
						}


					}
				}
			}
		}
	}

	void drawAgents(Graphics g) {


		if (game != null) {
			List<Agent> agents = game.getAgents();
			g.setColor(Color.red);
			int i = 0;
			while (agents != null && i < agents.size()) {
				GeoLocation c = agents.get(i).getPos();
				int r = 32;
				i++;
				if (c != null) {
					try {
						BufferedImage bufferedImage = ImageIO.read(new File("doc/output-onlinepngtools.png"));
						GeoLocation fp = this.gameToFrame.world2frame(c);
						g.drawImage(bufferedImage, (int) fp.x() - r, (int) fp.y() - r, null);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	private void drawNode(Node n, int r, Graphics g) {
		GeoLocation pos = n.getLocation();
		GeoLocation fp = this.gameToFrame.world2frame(pos);
		g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
		g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
	}

	private void drawEdge(Edge e, Graphics g) {
		DirectedWeightedGraph gameGraph = game.getGraph().getGraph();
		GeoLocation s = gameGraph.getNode(e.getSrc()).getLocation();
		GeoLocation d = gameGraph.getNode(e.getDest()).getLocation();
		GeoLocation s0 = this.gameToFrame.world2frame(s);
		GeoLocation d0 = this.gameToFrame.world2frame(d);
		g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
		//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}

	private void drawInfo1(Graphics graphics) {
		if (game != null) {
			String str = game.getTimeGame();
			String dt = "Time ";
			graphics.setColor(Color.cyan);
			graphics.setFont(new Font("Ariel", Font.BOLD, 24));
			graphics.drawString(str, 0, 105);
		}
	}

	private void drawA(Graphics graphics) {
		if (game != null && game.getAgents() != null) {
			List<Agent> ffs = game.getAgents();

			if (ffs.size() == 1) {
				Agent agent = ffs.get(0);
				int id = agent.getId();
				int dest = agent.getNextNode();
				double v = agent.getValue();
				String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
				graphics.setColor(Color.CYAN);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				graphics.drawString(str1, 550, 110);
			}
			if (ffs.size() == 2) {
				Agent agent = ffs.get(1);
				int id = agent.getId();
				int dest = agent.getNextNode();
				double v = agent.getValue();
				String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
				graphics.setColor(Color.CYAN);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				graphics.drawString(str1, 550, 115);

				Agent ag1 = ffs.get(0);
				int id1 = ag1.getId();
				int dest1 = ag1.getNextNode();
				double v1 = ag1.getValue();
				String str11 = "Agent: " + id1 + ", val: " + v1 + "  turned to node: " + dest1 + "\n";
				graphics.setColor(Color.GREEN);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				// graphics.drawString(str , 100, 60 + 2 * 20);
				graphics.drawString(str11, 550, 70);
			}
			if (ffs.size() == 3) {
				Agent agent = ffs.get(1);
				int id = agent.getId();
				int dest = agent.getNextNode();
				double v = agent.getValue();
				String str1 = "Agent: " + id + ", val: " + v + "  turned to node: " + dest + "\n";
				graphics.setColor(Color.CYAN);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				graphics.drawString(str1, 550, 105);

				Agent ag1 = ffs.get(0);
				int id1 = ag1.getId();
				int dest1 = ag1.getNextNode();
				double v1 = ag1.getValue();
				String str11 = "Agent: " + id1 + ", val: " + v1 + "  turned to node: " + dest1 + "\n";
				graphics.setColor(Color.MAGENTA);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				graphics.drawString(str11, 550, 65);

				Agent ag2 = ffs.get(2);
				int id2 = ag2.getId();
				int dest2 = ag2.getNextNode();
				double v2 = ag2.getValue();
				String str2 = "Agent: " + id2 + ", val: " + v2 + "  turned to node: " + dest2 + "\n";
				graphics.setColor(Color.GREEN);
				graphics.setFont(new Font("Ariel", Font.BOLD, 25));
				graphics.drawString(str2, 550, 145);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}