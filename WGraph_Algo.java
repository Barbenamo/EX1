package EX1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import EX1.Wgraph_DS.Node_inf;
/**
 * @author Bar Ben-Amo date:05/11/2020
 */

public class WGraph_Algo implements weighted_graph_algorithms {
	private weighted_graph g;

	/**
	 * @author Bar Ben-Amo default constructor
	 */
	public WGraph_Algo() {
		super();
		this.g = new Wgraph_DS();
	}

	/**
	 * @author Bar Ben-Amo copy constructor for a graph, given graph g
	 */
	public WGraph_Algo(weighted_graph g) {
		super();
		this.g = g;
	}

	/**
	 * @author Bar Ben-Amo initiation function
	 */
	@Override
	public void init(weighted_graph g) {
		this.g = g;

	}

	/**
	 * @author Bar Ben-Amo get a graph function.
	 */
	@Override
	public weighted_graph getGraph() {

		return this.g;
	}

	/**
	 * @author Bar Ben-Amo copy function, creates a clone using deep copy.
	 */
	@Override
	public weighted_graph copy() {
		weighted_graph c = new Wgraph_DS();
		for (node_info iterator : g.getV()) {
			c.addNode(iterator.getKey());
		}
		for (int key : ((Wgraph_DS) g).getE().keySet()) {
			for (EX1.Wgraph_DS.Edge_node nei : ((Wgraph_DS) g).getE().values()) {
				for (node_info p2 : nei.getNei()) {
					if (((Wgraph_DS) g).hasEdge(p2.getKey(), key)) {
						c.connect(p2.getKey(), key, g.getEdge(p2.getKey(), key));
					}
				}
			}
		}
		return c;
	}

	@Override
	public boolean isConnected() {// using BFS on the edges
		if(g.nodeSize()<=1)
		{
			return true;
		}
		if (g.edgeSize() == 0 || g.edgeSize() + 1 < g.nodeSize()) {
			return false;
		}
		
		Queue<node_info> q = new LinkedList<>();
		weighted_graph c = copy();
		for (node_info iterator : c.getV()) {
			q.add(iterator);
			q.peek().setInfo("grey");
			break;
		}
		while (!q.isEmpty()) {
			node_info temp = q.peek();
			for (node_info iterator : ((Wgraph_DS) c).getE().get(temp.getKey()).getNei()) {
				if (iterator.getInfo() == "white") {
					iterator.setInfo("grey");
					q.add(iterator);
				}
			}
			q.peek().setInfo("black");
			q.poll();
		}
		boolean flag = true;
		for (node_info iterator : c.getV()) {
			if (iterator.getInfo().equals("white")) {
				flag = false;
			}
			iterator.setInfo("white");
		}
		return flag;
	}

	/**
	 * @author Bar Ben-Amo shortest distance between a source and destination. using
	 *         dijkstra's algorithm also using a comparator function
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if (g.getNode(dest) == null || g.getNode(src) == null)// nodes are not exists
		{
			return -1;
		}
		if (src == dest) {
			return 0;// same node
		}
		PriorityQueue<node_info> p = new PriorityQueue<>(new comparingTags());
		weighted_graph c = copy();
		c.getNode(src).setTag(0);
		p.add(c.getNode(src));
		while (!p.isEmpty()) {
			node_info t = p.poll();
			for (node_info iterator : ((Wgraph_DS) c).getE().get(t).getNei()) {
				double k = t.getTag() + c.getEdge(t.getKey(), iterator.getKey());
				if (iterator.getInfo().equals("white") || iterator.getTag() > k) {
					iterator.setTag(k);
					iterator.setInfo("grey");
					p.add(iterator);
				}
			}
			p.peek().setInfo("black");
			if (t.getKey() == dest) {
				return t.getTag();
			}

		}
		return -1;// in case iterator didnt make it to dest

	}

	/**
	 * @author Bar Ben-Amo shortest distance between a source and destination. using
	 *         dijkstra's algorithm also using a comparator function this function
	 *         returns a list that contains the chain of nodes of the shortest path
	 *         between src to dest
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		LinkedList<node_info> l = new LinkedList<>();

		if (g.getNode(dest) == null || g.getNode(src) == null)// nodes are not exists
		{
			return null;
		}
		if (src == dest) {
			l.add(g.getNode(dest));
			return l;// same node
		}
		PriorityQueue<node_info> p = new PriorityQueue<>(new comparingTags());

		g.getNode(src).setTag(0);
		p.add(g.getNode(src));
		while (!p.isEmpty()) {
			node_info t = p.poll();
			for (node_info iterator : ((Wgraph_DS) g).getE().get(t).getNei()) {
				double k = t.getTag() + g.getEdge(t.getKey(), iterator.getKey());
				if (iterator.getInfo().equals("white") || iterator.getTag() > k) {
					iterator.setTag(k);
					iterator.setInfo("grey");
					((Node_inf) iterator).setParent(t);
					p.add(iterator);
				}
			}
			p.peek().setInfo("black");
			if (t.getKey() == dest) {
				l.addFirst(t);
				while (((Node_inf) t).getParent() != null) {
					t = ((Node_inf) t).getParent();
					l.addFirst(t);
				}
				Reset();
				return l;

			}
		}
		return null;// in case there is no connection between src and dest.
	}

	/**
	 * @author Bar Ben Amo date:12/11/2020 this function resets the graph after
	 *         using ShortestPath method.
	 */
	public void Reset() {
		for (node_info iterator : g.getV()) {
			iterator.setInfo("white");
			iterator.setTag(0);
			((Node_inf) iterator).setParent(null);
		}
	}

	@Override
	public boolean save(String file) {
		((Wgraph_DS)g).writeFile(file);
		return false;
	}

	
	@Override
	public boolean load(String file) {
		((Wgraph_DS)g).readFile(file);
		
		return false;
	}

	/**
	 * @author Bar Ben-Amo a sub class to apply the minheap function using tag field
	 *         that belongs to each node. this class implements Comparator in order
	 *         to create the SPD function. date:08/11/2020.
	 */
	private class comparingTags implements Comparator<node_info> {

		@Override
		public int compare(node_info o1, node_info o2) {
			if (o1.getTag() > o2.getTag()) {
				return 1;
			} else if (o1.getTag() < o2.getTag()) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	public static void main(String[] args) {
		weighted_graph g = new Wgraph_DS();

		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		// g.getNode(1).setTag(20);
		g.connect(1, 2, 4);
		// g.getNode(2).setTag(4);
		g.connect(1, 3, 3);
		// g.getNode(3).setTag(3);
		g.connect(3, 4, 2);

		// g.getNode(4).setTag(5);
		// PriorityQueue <node_info> q = new PriorityQueue<>(/new compareTag()/);
		/*
		 * q.add(g.getNode(1)); //20 q.add(g.getNode(3));//3 q.add(g.getNode(4));//5
		 * q.add(g.getNode(2));//4 //3 --> 2 --> 4--> 1 /*while(!q.isEmpty()) {
		 * System.out.print(q.poll().getKey() +" --> "); }
		 */

		weighted_graph_algorithms ga = new WGraph_Algo();
		ga.init(g);
		weighted_graph g2 = ga.copy();
	
		((Wgraph_DS) g).writeFile("mygraph");
		((Wgraph_DS) g).ToString();

	}

}
