package EX1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import EX0.NodeData;
import EX0.node_data;

/**
 * @author Bar Ben-Amo date:02/11/2020 A class to represents a graph.
 *
 */
public class Wgraph_DS implements weighted_graph {
	private HashMap<Integer, node_info> g;// data structure to represent a graph
	private HashMap<Integer, Edge_node> edges;

	private int mc;// changes counter
	private int numofedges;// number of edges in a graph
	
	public class Node_inf implements node_info {
		private int key;
		private double tag;
		private String info;
		private node_info parent;
		

		public Node_inf() {//Default constructor
			super();
			this.key=(int)(Math.random()*Integer.MAX_VALUE);
			this.tag=-1;
			this.info="";
			this.parent=null;
		}
		

		public Node_inf(int key) {//key- based constructor 
			super();
			this.key = key;
			this.tag=-1;
			this.info="";
			this.parent=null;
		}
		public Node_inf(node_info n)
		{
			this.key=n.getKey();
			this.info=n.getInfo();
			this.tag=n.getTag();
		}


		@Override
		public int getKey() {//key getter (data)
			return this.key;
		}

		@Override
		public String getInfo() {//info getter (meta-data)
			
			return this.info;
		}

		@Override
		public void setInfo(String s) {//info setter (meta-data)
			this.info=s;
			
		}

		@Override
		public double getTag() {
			return this.tag;
			
		}


		@Override
		public void setTag(double t) {
			this.tag=t;
			
		}


		public node_info getParent() {
			return parent;
		}


		public void setParent(node_info parent) {
			this.parent = parent;
		}
	}

	public class Edge_node  {
		private node_info src;
		private node_info dst;
		private double weight;
		private HashMap<node_info,Double> neighbors;
		
		
		public Edge_node() {
			
			super();
			this.neighbors=new HashMap<>();
			
		}
		
		
		public int getSrc() {
			
			return this.src.getKey();
		}

		
		public int getDest() {
			
			return this.dst.getKey();
		}
		/**
		 * @author Bar Ben Amo
		 *get weight function
		 *assuming the weight is non-negative!
		 */

		public double getWeight(node_info n) {
			if(hasNei(n)) {
				return neighbors.get(n);
			}
			return -1;
		}

		
		public boolean hasNei(node_info n) {
			if(neighbors.containsKey(n)) {
				return true;
			}
			return false;
		}

		
		public Collection<node_info> getNei() {
			
			return neighbors.keySet();
		}

		
		public void removeEdge(node_info n) {
			if(hasNei(n)) {
				neighbors.remove(n);
			}
			else {
				return;
			}
			
		}
		
		public void addNi(node_info n, double weight) {
			neighbors.put(n, weight);
			
		}

	}


	/**
	 * @author Bar Ben-Amo default constructor
	 *
	 */
	public Wgraph_DS() {
		super();
		this.g = new HashMap<>();
		this.edges = new HashMap<>();
		this.mc = 0;
		this.numofedges = 0;
	}

	public Wgraph_DS(int numofedges) {
		super();
		this.g = new HashMap<>();
		this.edges = new HashMap<>();
		this.numofedges = numofedges;
		this.mc = 0;

	}

	

	/**
	 * @author Bar Ben-Amo get a node from the graph
	 *
	 */
	@Override
	public node_info getNode(int key) {
		if (g.containsKey(key)) {
			return g.get(key);
		} else {
			return null;
		}
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         checks if two nodes has an edge between them
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		if (g.isEmpty() || !g.containsKey(node1) || !g.containsKey(node2)||node1==node2) {
			return false;
		}
		return edges.get(node1).hasNei(g.get(node2));
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         get the edge between two vertices
	 */
	@Override
	public double getEdge(int node1, int node2) {
		if (hasEdge(node1, node2)) {
			return edges.get(node1).getWeight(g.get(node2));
		} else {
			return -1;// edge does not exists
		}
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         add a node to a graph. considering whether the node exists already.
	 */
	@Override
	public void addNode(int key) {
	
		if (g.containsKey(key)) {
			node_info temp = new Node_inf();
			while (g.containsKey(temp.getKey()))
				temp = new Node_inf();
			g.put(temp.getKey(), temp);
			edges.put(temp.getKey(), new Edge_node());
			mc++;
			return;
		}
		g.put(key, new Node_inf(key));
		edges.put(key, new Edge_node());
		mc++;
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         connect two nodes via weighted edge checks first if they exists or
	 *         connected.
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if (!g.containsKey(node1) || !g.containsKey(node2) || hasEdge(node1, node2)||node1==node2) {
			return;// there are no such nodes in the graph or they are already connected
		} else {
			edges.get(node1).addNi(g.get(node2), w);
			edges.get(node2).addNi(g.get(node1), w);
			numofedges++;
			mc++;
		}

	}

	/**
	 * @author Bar Ben-Amo return a collection of all the vertices in a graph
	 *
	 */
	@Override
	public Collection<node_info> getV() {
		return g.values();
	}

	/**
	 * @author Bar Ben-Amo return a collection of all neighbors of an vertex
	 *
	 */
	@Override
	public Collection<node_info> getV(int node_id) {
		if (g.containsKey(node_id))
			return edges.get(node_id).getNei();
		else {
			return null;// the vertex is not exists
		}
	}

	/**
	 * @author Bar Ben-Amo return a collection of all edges on a graph.
	 *
	 */
	public HashMap<Integer, Edge_node> getE() {
		return this.edges;
	}

	/**
	 * @author Bar Ben-Amo removes a vertex from a graph
	 *
	 */
	@Override
	public node_info removeNode(int key) {
		if (g.containsKey(key)) {
			Collection<node_info> Nei = edges.get(key).getNei();
			Iterator<node_info> i = Nei.iterator();
			while (i.hasNext()) {
				node_info nxt = i.next();
				edges.get(nxt.getKey()).removeEdge(g.get(key));
				i.remove();
				numofedges--;
				this.mc++;
			}
			this.mc++;
			edges.remove(key);
			return g.remove(key);
		} else {
			return null;
		}

	}

	/**
	 * @author Bar Ben-Amo disconnect two vertices
	 *
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		if (node1 == node2) {
			return;
		}
		if (!g.containsKey(node1) || !g.containsKey(node2)) {
			return;
		}
		edges.get(node1).removeEdge(g.get(node2));
		edges.get(node2).removeEdge(g.get(node1));
		this.mc++;
		numofedges--;
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         returns the number of vetices in a graph
	 */
	@Override
	public int nodeSize() {
		return g.size();
	}

	/**
	 * @author Bar Ben-Amo
	 * 
	 *         returns the number of edges in a graph
	 */
	@Override
	public int edgeSize() {

		return numofedges;
	}

	/**
	 * @author Bar Ben-Amo 
	 * returns the number of changes made on the graph
	 *
	 */
	@Override
	public int getMC() {

		return this.mc;
	}
	/**
	 * @author Bar Ben-Amo 
	 * A to-String method, used in save and load function.
	 *
	 *
	 */
	public void ToString() {
		System.out.println("The vertices are: ");
		for(node_info count:g.values()) {
			System.out.print(count.getKey()+",");
		}
		System.out.println();
		for (node_info num : g.values()) {
			System.out.print("vertix is: " + num.getKey() + ", neighbors are:{ ");
			for (node_info num1 : edges.get(num.getKey()).getNei())
				System.out.print(num1.getKey()+"("+getEdge(num.getKey(),num1.getKey())+")" + ", ");
			System.out.print("}");
			System.out.println();
		}
			System.out.println("the edges are: ");
			for (node_info j : g.values()) {
				
				if( edges.get(j.getKey()).getNei()!=null) {
				for (node_info i : edges.get(j.getKey()).getNei()) {
					System.out.println("the vertex "+j.getKey()+" has an edge that weighs "+getEdge(i.getKey(),j.getKey())+" connected to "+i.getKey());
				}
				}
			}
		
		System.out.println("There are " + numofedges + " edges, The num of operations is: " + mc);
	}

	/**
	 * @author Bar Ben-Amo 
	 * a function to write to a file and save it.
	 *
	 */
	
	public void writeFile(String fileName) {
		// try write to the file
		try {
			FileWriter fw = new FileWriter(fileName);
			PrintWriter outs = new PrintWriter(fw);
			
			outs.println("The vertices are: ");
			outs.print("[");
			for(node_info count:g.values()) {
				outs.print(count.getKey()+",");
			}
			outs.print("]");
			
			outs.println();
			for (node_info num : g.values()) {
				outs.print("vertex key is: " + num.getKey() + ", his neighbors are:{ ");
				for (node_info num1 : edges.get(num.getKey()).getNei())
					outs.print(num1.getKey() +"("+getEdge(num.getKey(),num1.getKey())+")" + ",");
				outs.print("}");
				outs.println();
			}
			
			outs.println("There are " + numofedges + " edges, The num of operations is: " + mc);
			outs.print("the edges are: ");
			for (node_info j : g.values()) {
				
				if( edges.get(j.getKey()).getNei()!=null) {
				for (node_info i : edges.get(j.getKey()).getNei()) {
					outs.println("the vertex "+j.getKey()+" has an edge that weighs "+getEdge(i.getKey(),j.getKey())+" connected to "+i.getKey());
				}
				}
			}
			outs.close();
			fw.close();
		} catch (IOException ex) {
			System.out.print("Error writing file\n" + ex);
		}
	}
	/**
	 * @author Bar Ben-Amo 
	 * date:14/11/2020.
	 * a function that reads from a file
	 *
	 */
	public  void readFile (String fileName){
		// try read from the file
		try {
		weighted_graph r = new Wgraph_DS();//creating a graph called r
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String str;
		str = br.readLine();
		
//		System.out.println(0+") "+str);
		str=br.readLine();
		for(int i=1; str!=null; i=i+1) {
			String t=str;
			if(t.charAt(0)=='[')
			{
			String num="";
			for (int j = 1; j < t.length(); j++) {
				while(t.charAt(j)!=','&& t.charAt(j)!=']')
				{
					num+=t.charAt(j);
					j++;
				}
				if(num!="") {
				r.addNode(Integer.parseInt(num));
				num="";
				}
			}
			}
			if(t.charAt(0)=='v')
			{
				String nodekey="";
				int q=15;
				while(t.charAt(q)!=',')
				{
					nodekey+=t.charAt(q);
					q++;
				}
			while(t.charAt(q)!='{') {
				q++;
			}
			q=q+2;
			String nei="";
			while(t.charAt(q)!='(') {
				nei+=t.charAt(q);
				q++;
			}
			
			String weight="";
			if(t.charAt(q)=='(') {
				q++;
				while(t.charAt(q)!=')') {
					weight+=t.charAt(q);
					q++;
				}
			}
			r.connect(Integer.parseInt(nodekey),Integer.parseInt(nei), Double.parseDouble(weight));
			
			}
			
			
		str = br.readLine();
		if (str != null){

		}
		}
		//------------------

		//the following part creates a graph based on the data stored in the file.
		
		
		BufferedReader ln = new BufferedReader(fr);
		String s;
		s=ln.readLine();
		//block for observation
		for (node_info n:((Wgraph_DS)r).getV()) {
			System.out.print(n.getKey()+",");
		}
			
		
		ln.close();
		br.close();
		fr.close();
		}
		catch(IOException ex) {
		System.out.print("Error reading file\n" + ex);
		 System.exit(2);
		}
		}
	/**
	 * @author Bar Ben-Amo 
	 * date:19/11/2020.
	 * a function Overrides the superclass equals.
	 * used mainly in tests
	 *
	 */
	@Override
	public boolean equals(Object e) {
		if(!(e instanceof weighted_graph)) return false;
		weighted_graph c=(Wgraph_DS)e;
		System.out.println(c.edgeSize());
		System.out.println(this.edgeSize());
		if(c.edgeSize()!=this.edgeSize()) return false;
		if(c.nodeSize()!=this.nodeSize()) return false;
		for(node_info iterator:c.getV())
		{
			if(!g.containsKey(iterator.getKey()))
			{
				return false;
			}
			if((c.getV(iterator.getKey())!=null&&c.getV(iterator.getKey()).size()!=this.getV(iterator.getKey()).size())) {
				return false;
			}
			if(c.getV(iterator.getKey())!=null)
			{
				for(node_info i:c.getV(iterator.getKey())) {
					if(!this.hasEdge(iterator.getKey(), i.getKey())) {
						return false;
					}
					else if(c.getEdge(iterator.getKey(), i.getKey())!=this.getEdge(iterator.getKey(), i.getKey())) {
						return false;
					}
				}
			}
		}
		return true;	
	}
	
}

