package EX1;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;

import org.junit.jupiter.api.Test;



class WGraph_DS_Test {
	@Test
	void CheckRemoveNode() {
		weighted_graph g=new Wgraph_DS();
		g.addNode(5);
		g.addNode(10);
		g.connect(5, 10, 1);
		g.removeNode(5);
		if(g.getNode(5)!=null)
		{
			fail("this node were meant to be deleted");
		}
		if(g.hasEdge(10, 5))
		{
			fail("this node were meant to be deleted");
		}
	}
	@Test
	void CheckRemoveEdge()
	{
		weighted_graph g=new Wgraph_DS();
		g.addNode(5);
		g.addNode(10);
		g.connect(5, 10, 1);
		g.removeEdge(10, 5);
		if(g.hasEdge(10, 5)==true)
		{
			fail("this edge has been deleted!");
		}
		if(((Wgraph_DS)g).getE().containsKey(1)==true)
		{
			fail("this edge has been deleted!");
		}
	}
	@Test
	void CheckConnect()
	{
		weighted_graph g=new Wgraph_DS();
		g.addNode(5);
		g.addNode(10);
		g.connect(5, 10, 1);
		assertTrue(g.hasEdge(10, 5));
		assertEquals(1,((Wgraph_DS)g).getEdge(10, 5));
	}
	@Test
	void CheckConnectToItself()
	{
		weighted_graph g=new Wgraph_DS();
		g.addNode(5);
		g.connect(5, 5, 1);
		assertFalse(g.hasEdge(5, 5));
		assertEquals(-1,g.getEdge(5,5));
		assertEquals(0,g.edgeSize());
	}
	@Test
	void CheckCreate1M()//runtime test
	{
		long start=new Date().getTime();
		weighted_graph g=new Wgraph_DS();
		for(int i=0;i<1000000;i++)
		{
			g.addNode(i);
		}
		assertEquals(1000000,g.nodeSize());
		int num1 = 0;
		int num2 = 1;
		int edges = 0;
		while (edges < 10000000) {
			double w = Math.random() * 99 + 1;
			g.connect(num1, num2++, w);
			edges++;
			if (num2 == 999999) {
				num1++;
				num2 = num1 + 1;
			}
		}

		long end=new Date().getTime();
		assertFalse((end-start)/1000.0>30,"takes too long");
	}
}
