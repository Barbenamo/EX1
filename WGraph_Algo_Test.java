package EX1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class WGraph_Algo_Test {

	@Test
	void CheckEquals()//checks the copy
	{
		weighted_graph g=new Wgraph_DS();		
		weighted_graph_algorithms g1=new WGraph_Algo();
		g1.init(g);
	
		for(int i=0;i<100;i++)
		{
			g.addNode(i);
		}
		int num1 = 0;
		int num2 = 1;
		int edges = 0;
		while (edges < 100) {
			double w = Math.random() * 99 + 1;
			g.connect(num1, num2++, w);
			edges++;
			if (num2 == 99) {
				num1++;
				num2 = num1 + 1;
			}
		}
		weighted_graph c1=g1.copy();
		
		assertTrue(g.equals(c1));
		
		
	}
	@Test
	void CheckIsConnected() {
	weighted_graph g1=new Wgraph_DS();		
	weighted_graph_algorithms g=new WGraph_Algo();
	
	g.init(g1);
	assertTrue(g.isConnected());
	g1.addNode(5);
	assertTrue(g.isConnected());
	g1.addNode(10);
	assertFalse(g.isConnected());
	g1.connect(10, 5, 2);
	assertTrue(g.isConnected());	
	}
	

}
