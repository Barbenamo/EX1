package EX1;

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
