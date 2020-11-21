package EX1;

import java.util.Collection;
import java.util.HashMap;

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
