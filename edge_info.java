package EX1;

import java.util.Collection;

/**
 * @author Bar Ben Amo
 *this is an interface to apply the edges
 */
public interface edge_info {
	
	/**
	 * @author Bar Ben Amo
	 *get source function
	 */
	public int getSrc();

	/**
	 * @author Bar Ben Amo
	 *get destination function
	 */
	public int getDest();
	
	/**
	 * @author Bar Ben Amo
	 * get weight function
	 */
	public double getWeight(node_info n);
	/**
	 * @author Bar Ben Amo
	 *find if a specific destination exists.
	 */
	public boolean hasNei(node_info n);
	/**
	 * @author Bar Ben Amo
	 *returns the neighborhood.
	 */
	public Collection<node_info> getNei();
	/**
	 * @author Bar Ben Amo
	 *disconnect two vertices.
	 */
	public void removeEdge(node_info n);
	
	public void addNi(node_info n,double weight);
	
	
}