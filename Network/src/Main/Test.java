package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Test {
	public final static int NODES = 20;
	public final static double MAX_T = 0.5;

	private static Random rand = new Random();

	private static Network createNetwork() {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(1, 2, 500000, 0, 0.85));
		edges.add(new Edge(1, 14, 600000, 0, 0.98));
		edges.add(new Edge(2, 3, 800000, 0, 0.95));
		edges.add(new Edge(2, 16, 450000, 0, 0.98));
		edges.add(new Edge(2, 17, 300000, 0, 0.99));
		edges.add(new Edge(3, 4, 600000, 0, 0.89));
		edges.add(new Edge(4, 8, 800000, 0, 0.9));
		edges.add(new Edge(5, 6, 800000, 0, 0.9));
		edges.add(new Edge(5, 8, 300000, 0, 0.87));
		edges.add(new Edge(6, 7, 1000000, 0, 0.95));
		edges.add(new Edge(7, 20, 350000, 0, 0.7));
		edges.add(new Edge(7, 19, 500000, 0, 0.96));
		edges.add(new Edge(7, 9, 700000, 0, 0.96));
		edges.add(new Edge(9, 10, 550000, 0, 0.84));
		edges.add(new Edge(9, 12, 250000, 0, 0.98));
		edges.add(new Edge(10, 11, 300000, 0, 0.96));
		edges.add(new Edge(11, 12, 400000, 0, 0.98));
		edges.add(new Edge(11, 13, 800000, 0, 0.99));
		edges.add(new Edge(12, 18, 800000, 0, 0.98));
		edges.add(new Edge(13, 14, 1000000, 0, 0.94));
		edges.add(new Edge(13, 18, 200000, 0, 0.92));
		edges.add(new Edge(14, 15, 250000, 0, 0.99));
		edges.add(new Edge(15, 18, 500000, 0, 0.95));
		edges.add(new Edge(16, 19, 400000, 0, 0.96));
		edges.add(new Edge(16, 20, 500000, 0, 0.94));
		edges.add(new Edge(17, 19, 300000, 0, 0.95));
		edges.add(new Edge(18, 19, 750000, 0, 0.93));

		Network network = new Network(edges);

		return network;
	}

	private static void printNetwork(Network network) {
		System.out.println("Węzeł 1;Węzeł 2;Przepustowość;Przepływ;Niezawodność");
		for (int i = 0; i < network.edges.size(); i++) {
			System.out.println(network.edges.get(i).node1+";"+network.edges.get(i).node2+";"+network.edges.get(i).c+";"+network.edges.get(i).a+";"+network.edges.get(i).p);
		}
	}

	private static void generateMatrix() {
		int[][] matrix = new int[NODES][NODES];
		for (int i = 0; i < NODES; i++) {
			for (int j = i; j < NODES; j++) {
				if (i == j) {
					matrix[i][j] = 0;
				} else {
					int value = 1 + rand.nextInt(9);
					matrix[i][j] = value;
					matrix[j][i] = value;
				}
			}
		}

	}

	private static int[][] createMatrix() {
		int[][] matrix = { { 0, 2, 5, 4, 7, 7, 8, 8, 8, 1, 6, 2, 7, 4, 1, 1, 7, 6, 2, 8 },
				{ 2, 0, 6, 3, 9, 2, 8, 4, 6, 2, 7, 1, 2, 6, 2, 6, 2, 6, 5, 9 },
				{ 5, 6, 0, 9, 4, 7, 9, 2, 4, 6, 3, 5, 6, 9, 3, 5, 7, 7, 5, 5 },
				{ 4, 3, 9, 0, 8, 1, 7, 8, 8, 2, 3, 3, 8, 6, 7, 6, 5, 8, 2, 4 },
				{ 7, 9, 4, 8, 0, 5, 3, 1, 7, 1, 3, 9, 3, 1, 5, 1, 3, 6, 4, 1 },
				{ 7, 2, 7, 1, 5, 0, 8, 2, 6, 1, 4, 3, 5, 5, 3, 4, 9, 7, 7, 1 },
				{ 8, 8, 9, 7, 3, 8, 0, 8, 2, 5, 7, 2, 2, 9, 8, 1, 2, 6, 7, 9 },
				{ 8, 4, 2, 8, 1, 2, 8, 0, 6, 4, 1, 3, 3, 7, 1, 7, 4, 5, 5, 7 },
				{ 8, 6, 4, 8, 7, 6, 2, 6, 0, 1, 3, 9, 7, 6, 3, 6, 4, 2, 5, 5 },
				{ 1, 2, 6, 2, 1, 1, 5, 4, 1, 0, 9, 3, 6, 9, 4, 6, 4, 6, 4, 7 },
				{ 6, 7, 3, 3, 3, 4, 7, 1, 3, 9, 0, 2, 7, 8, 6, 8, 6, 6, 8, 9 },
				{ 2, 1, 5, 3, 9, 3, 2, 3, 9, 3, 2, 0, 1, 8, 5, 3, 9, 9, 3, 3 },
				{ 7, 2, 6, 8, 3, 5, 2, 3, 7, 6, 7, 1, 0, 7, 8, 9, 3, 3, 5, 1 },
				{ 4, 6, 9, 6, 1, 5, 9, 7, 6, 9, 8, 8, 7, 0, 1, 2, 1, 9, 1, 7 },
				{ 1, 2, 3, 7, 5, 3, 8, 1, 3, 4, 6, 5, 8, 1, 0, 7, 3, 2, 2, 3 },
				{ 1, 6, 5, 6, 1, 4, 1, 7, 6, 6, 8, 3, 9, 2, 7, 0, 1, 3, 8, 9 },
				{ 7, 2, 7, 5, 3, 9, 2, 4, 4, 4, 6, 9, 3, 1, 3, 1, 0, 7, 8, 7 },
				{ 6, 6, 7, 8, 6, 7, 6, 5, 2, 6, 6, 9, 3, 9, 2, 3, 7, 0, 9, 4 },
				{ 2, 5, 5, 2, 4, 7, 7, 5, 5, 4, 8, 3, 5, 1, 2, 8, 8, 9, 0, 7 },
				{ 8, 9, 5, 4, 1, 1, 9, 7, 5, 7, 9, 3, 1, 7, 3, 9, 7, 4, 7, 0 } };
		return matrix;
	}

	private static void printMatrix(int[][] matrix) {
		for (int i = 1; i <= NODES; i++) {
			System.out.print(i + ";");
		}
		System.out.println("");
		for (int i = 1; i <= NODES; i++) {
			System.out.print(i + ";");
			for (int j = 0; j < NODES; j++) {
				System.out.print(matrix[i - 1][j] + ";");
			}
			System.out.println("");
		}
	}

	private static void test1(int[][] matrix) {
		for (int j = 0; j < 20; j++) {
			double success = 0;
			int connect=0;
			for (int i = 0; i < 1000; i++) {
				Network network = createNetwork();
				network.deleteEdges();
				if (!(network.checkNetwork())) {
					continue;
				}
				connect++;
				network.createConnectionsMatrix();
				network.shortestPath();
				network.setADueToMatrix(matrix);
				double time = network.countTime(matrix);
				if (time >= 0 && time < MAX_T) {
					success++;
				}
			}
			System.out.println("Niezerwane połączenia: "+connect);
			System.out.println(success / 1000.0);
		}
	}

	private static void test2(int[][] matrix) {
		for (int j = 0; j < 20; j++) {
			randomMatrixIncrement(matrix);
			double success = 0;
			int connect=0;
			for (int i = 0; i < 1000; i++) {
				Network network = createNetwork();
				network.deleteEdges();
				if (!(network.checkNetwork())) {
					continue;
				}	
				connect++;
				network.createConnectionsMatrix();
				network.shortestPath();
				network.setADueToMatrix(matrix);
				double time = network.countTime(matrix);
				if (time >= 0 && time < MAX_T) {
					success++;
				}
			}
			System.out.println("Niezerwane połączenia: "+connect);
			System.out.println(success / 1000.0);
		}
	}

	private static void test3(int[][] matrix) {
		Network network = createNetwork();
		for (int i = 0; i < 20; i++) {
			double success = 0;
			int connect=0;
			randomCIncrement(network);
			for (int j = 0; j < 1000; j++) {
				Network copy = network.copy();
				copy.deleteEdges();
				if (!(copy.checkNetwork())) {
					continue;
				}
				connect++;
				copy.createConnectionsMatrix();
				copy.shortestPath();
				copy.setADueToMatrix(matrix);
				double time = copy.countTime(matrix);
				if (time >= 0 && time < MAX_T) {
					success++;
				}
			}
			System.out.println("Niezerwane połączenia: "+connect);
			System.out.println(success / 1000.0);
		}
	}

	private static void test4(int[][] matrix) {
		Network network = createNetwork();
		for (int i = 0; i < 20; i++) {
			double success = 0;
			int connect=0;
			randomAddEdges(network);
			for (int j = 0; j < 1000; j++) {
				Network copy = network.copy();
				randomAddEdges(copy);
				copy.deleteEdges();
				if (!(copy.checkNetwork())) {
					continue;
				}
				connect++;
				copy.createConnectionsMatrix();
				copy.shortestPath();
				copy.setADueToMatrix(matrix);
				double time = copy.countTime(matrix);
				if (time >= 0 && time < MAX_T) {
					success++;
				} else {
				}
			}
			System.out.println("Niezerwane połączenia: "+connect);
			System.out.println(success / 1000.0);
		}
	}

	private static void randomCIncrement(Network network) {
		for (int i = 0; i < 5; i++) {
			network.edges.get(rand.nextInt(network.edges.size())).incrementC();
		}
	}

	private static void randomAddEdges(Network network) {

		int rand1 = 1 + rand.nextInt(NODES - 1);
		int rand2 = 1 + rand.nextInt(NODES - 1);
		while (network.edgeExists(rand1, rand2) || rand1 == rand2) {
			rand1 = 1 + rand.nextInt(NODES - 1);
			rand2 = 1 + rand.nextInt(NODES - 1);
		}
		Edge edge = new Edge(rand1, rand2, network.averageC(), 0, network.averageP());
		network.addEdge(edge);
	}

	private static void randomMatrixIncrement(int[][] matrix) {
		for (int i = 0; i < 25; i++) {
			matrix[rand.nextInt(20)][rand.nextInt(20)]++;
		}
	}

	public static void main(String[] args) {
		int[][] matrix = createMatrix();
		printMatrix(matrix);
		Network network=createNetwork();
		printNetwork(network);
		Scanner scan = new Scanner(System.in);
		int option = 1;
		while (option != 0) {
			option = scan.nextInt();
			switch (option) {
			case 1:
				System.out.println("Test sieci");
				test1(matrix);
				break;
			case 2:
				System.out.println("Test sieci przy zwiększaniu wartości w macierzy natężeń");
				test2(matrix);
				break;
			case 3:
				System.out.println("Test sieci przy stopniowemu zwięszkaniu przepustowości");
				test3(matrix);
				break;
			case 4:
				System.out.println("Test sieci przy stopniowym dodawaniu krawędzi");
				test4(matrix);
				break;
			}
		}
	}

}
