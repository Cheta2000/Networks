package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Network {
	public final static int NODES = 20;
	public final static int INF = 99999;
	public final static int SIZE=1000;
	int[] nodes = new int[NODES];
	int[][] connections = new int[NODES][NODES];
	ArrayList<Edge> edges = new ArrayList();
	Map<ArrayList<Integer>,ArrayList<Edge>> paths=new HashMap();

	public Network(ArrayList<Edge> edges) {
		super();
		for (int i = 0; i < NODES; i++) {
			nodes[i] = i + 1;
		}
		this.edges = edges;
	}
	
	public Network() {}
	
	public Network copy(){
		Network copy=new Network();
		copy.nodes=nodes;
		ArrayList<Edge> cpy=new ArrayList(edges);
		for(Edge edge:cpy) {
			edge.resetA();
		}
		copy.edges=cpy;
		return copy;
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public void deleteEdges() {
		Random rand = new Random();
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).p < rand.nextDouble()) {
				edges.remove(i);
			}
		}
	}
	
	public int averageC() {
		int sum=0;
		for(int i=0;i<edges.size();i++) {
			sum+=edges.get(i).c;
		}
		return sum/edges.size();
	}
	
	public int averageP() {
		int sum=0;
		for(int i=0;i<edges.size();i++) {
			sum+=edges.get(i).p;
		}
		return sum/edges.size();
	}
	
	public boolean edgeExists(int node1,int node2) {
		for(int i=0;i<edges.size();i++) {
			if (edges.get(i).node1 == node1) {
					if (edges.get(i).node2==node2) {
						return true;
					}
			}
			if (edges.get(i).node1 == node2) {
				if (edges.get(i).node2==node1) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkNetwork() {
		int[] check = new int[NODES];
		check[0] = 1;
		int position = 1;
		for (int i = 0; i < position; i++) {
			for (int j = 0; j < edges.size(); j++) {
				boolean added = false;
				if (edges.get(j).node1 == check[i]) {
					for (int k = 0; k < position; k++) {
						if (check[k] == edges.get(j).node2) {
							added = true;
							break;
						}
					}
					if (!added) {
						check[position] = edges.get(j).node2;
						position++;
					}
				}
				if (edges.get(j).node2 == check[i]) {
					for (int k = 0; k < position; k++) {
						if (check[k] == edges.get(j).node1) {
							added = true;
							break;
						}
					}
					if (!added) {
						check[position] = edges.get(j).node1;
						position++;
					}

				}
			}
		}
		if (position == NODES) {
			return true;
		}
		return false;
	}

	public void createConnectionsMatrix() {
		for (int i = 0; i < NODES; i++) {
			for (int j = 0; j < NODES; j++) {
				int k;
				if (i == j) {
					connections[i][j] = 0;
				} else {
					for (k = 0; k < edges.size(); k++) {
						if (edges.get(k).node1 == i + 1) {
							if (edges.get(k).node2 == j + 1) {
								connections[i][j] = 1;
								break;
							}
						}
						if (edges.get(k).node2 == i + 1) {
							if (edges.get(k).node1 == j + 1) {
								connections[i][j] = 1;
								break;
							}
						}
					}
					if (k == edges.size()) {
						connections[i][j] = INF;
					}
				}
			}
		}
	}

	public Edge findEdgeBetween(int node1, int node2) {
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).node1 == node1) {
				if (edges.get(i).node2 == node2) {
					return edges.get(i);
				}
			}
			if (edges.get(i).node2 == node1) {
				if (edges.get(i).node1 == node2) {
					return edges.get(i);
				}
			}
		}
		return null;
	}

	public void shortestPath() {
		for (int i = 0; i < NODES; i++) {
			for (int j = 0; j < NODES; j++) {
				for (int k = 0; k < NODES; k++) {
					ArrayList<Edge> onPath = new ArrayList();
					ArrayList<Integer> nums = new ArrayList();
					nums.add(j + 1);
					nums.add(k + 1);
					if (connections[j][i] + connections[i][k] < connections[j][k]) {
						connections[j][k] = connections[j][i] + connections[i][k];
						Edge newPath1 = findEdgeBetween(j + 1, i + 1);
						Edge newPath2 = findEdgeBetween(i + 1, k + 1);
						if (newPath1 == null) {
							ArrayList<Integer> tmp = new ArrayList();
							tmp.add(j + 1);
							tmp.add(i + 1);
							onPath.addAll(paths.get(tmp));
						} else {
							onPath.add(newPath1);
						}
						if (newPath2 == null) {
							ArrayList<Integer> tmp = new ArrayList();
							tmp.add(i+1);
							tmp.add(k+1);
							onPath.addAll(paths.get(tmp));
						} else {
							onPath.add(newPath2);
						}
					}
					if (onPath.size() > 0) {
						paths.put(nums, onPath);
					}
				}
			}
		}
	}
	
	public void setADueToMatrix(int[][] matrix) {
		for (Map.Entry<ArrayList<Integer>,ArrayList<Edge>> entry : paths.entrySet()){
			int node1=entry.getKey().get(0);
			int node2=entry.getKey().get(1);
			int matrixValue=matrix[node1-1][node2-1];
			for(Edge edge: entry.getValue()) {
				edge.incrementA(matrixValue);
			}
		}
	}

	public double countTime(int matrix[][]) {
		double sum=0;
		for(Edge edge: edges) {
			if(edge.c/SIZE<=edge.a) {
				return -1;
			}
			sum+=(edge.a/((edge.c/SIZE)-edge.a));
		}
		sum*=1.0/sumOfMatrix(matrix);
		return sum;
	}
	
	private int sumOfMatrix(int[][] matrix) {
		int sum = 0;
		for (int i = 0; i < NODES; i++) {
			for (int j = i; j < NODES; j++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}

}
