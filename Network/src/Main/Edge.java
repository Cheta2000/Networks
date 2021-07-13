package Main;

public class Edge {
	int node1;
	int node2;
	int c;
	int a;
	double p;
	
	public Edge(int node1, int node2, int c, int a, double p) {
		super();
		this.node1 = node1;
		this.node2 = node2;
		this.c = c;
		this.a = a;
		this.p = p;
	}
		
	public void incrementA(int value) {
		a+=value;
	}
	
	public void resetA() {
		a=0;
	}
	
	public void incrementC() {
		c+=100000;
	}
}
