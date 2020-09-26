import java.util.*;
import java.io.*;

public class HW1_Q4 {
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("Q4.dat");
		Scanner kb = new Scanner(file);
		
		int adjMatrix[][] = null;
		int heuristic[] = null;
		String states[] = null;
		int inputSize = 0;
		
		
		// Reading the data from the file
		while (kb.hasNextLine()) {
			String input = kb.nextLine();
			
			if (input.equals("states")) {
				input = kb.nextLine();
				states = input.split(" ");
				inputSize = states.length;
			} else if (input.equals("heuristic")) {
				input = kb.nextLine();

				heuristic = strToIntArr(input);
			} else if (input.equals("weights")) {
				adjMatrix = new int[inputSize][inputSize];
				int index = 0;
				while (kb.hasNextLine()) {
					input = kb.nextLine();
					adjMatrix[index] = strToIntArr(input);
					index++;
				}
			}
		}
		

		Node nodes[] = new Node[states.length];
		
		for (int i = 0; i < states.length; i++) {
			nodes[i] = new Node(i, states[i], heuristic[i]);
		}
		
		Set<String> closedSet = new HashSet<String>();
		
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
		
		Node expandNode = nodes[0];
		expandNode.path = "S";
		closedSet.add(expandNode.letter);
		while (expandNode.heuristic != 0) {
			System.out.println("Node expanded = " + expandNode.letter);
			System.out.println("f(n) cost  = " + expandNode.fn);
			System.out.println();
			
			for (int i = 0; i < nodes.length; i++) {
				if (adjMatrix[expandNode.index][i] > 0) {
					// Calculate the cost f(n) = g(n) + h(n)
					nodes[i].gn = expandNode.gn + adjMatrix[expandNode.index][i];
					nodes[i].fn = nodes[i].gn + nodes[i].heuristic;
					nodes[i].path = expandNode.path + "->" + nodes[i].letter; 
					priorityQueue.add(nodes[i]);
				}
			}
			
			
			// Before expanding the node check if it is already expanded or not
			// By checking the node in the closed set
			while(closedSet.contains(expandNode.letter))
				expandNode = priorityQueue.poll();
			
			closedSet.add(expandNode.letter);
		}
		System.out.println("_______________________________________________________________\n");
		System.out.println("Goal reached = " + expandNode.letter);
		System.out.print("Path: ");
		System.out.println(expandNode.path);
		System.out.println("A-Star search cost = " + expandNode.fn);
		
		
		kb.close();
	}
	
	// If the input of the file is array of string then read it as one string
	// Then convert it to array of integeres
	public static int[] strToIntArr(String input) {
		String inputs[] = input.split(" ");
		int[] heuristic = new int[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			heuristic[i] = Integer.parseInt(inputs[i]);
		}
		return heuristic;
	}
}



class Node implements Comparable<Node> {
	int index;
	String letter;
	int heuristic;
	int gn = 0;
	int fn;
	String path = "";
	
	public Node(int index, String letter, int heuristic) {
		this.index = index;
		this.letter = letter;
		this.heuristic = heuristic;
		this.fn = heuristic;
	}
	
	// Method to compare the value of f to be sorted in the priority queue
	@Override
	public int compareTo(Node other) {
		if (Integer.valueOf(fn).compareTo(other.fn) == 0) {
			return this.letter.compareTo(other.letter);
		} else 
			return Integer.valueOf(fn).compareTo(other.fn);
	}
	
}

