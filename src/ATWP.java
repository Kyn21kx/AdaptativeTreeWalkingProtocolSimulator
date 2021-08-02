import java.util.*;
import java.io.File;
import tools.*;
import java.util.ArrayList;

public class ATWP {

	private static int indexHolder;
	private static ArrayList<WorkstationNode> sent = new ArrayList<>();

	public static void main(String[] args) {
		//Travel through the tree from left to right (depth first)
		//The division continues until there's only one node
		WorkstationNode[] workstations;
		BinaryTree<WorkstationNode> tree = new BinaryTree<>(null);
		//Change the filename string for any other input file
		workstations = GetWorkstationInfo("Input.txt");

		//Go down left until you find null, and create for 4/2 steps (8 total, over at least 2 branches = 4, over 2 nodes = 2)
		int steps = GetSteps(workstations.length);
		CreateDepthFirst(steps, tree);
		//Insert the stations at the end of each branch
		InsertStationDepthFirst(tree, workstations);
		//Success is defined by performing the same depth first search and retrieving all the stations under the current node
		CheckForCollisions(tree);
	}

	private static void CheckForCollisions(BinaryTree tree) {
		indexHolder = 0;
		RecursiveCollisionCheck(tree.top);
	}

	private static void RecursiveCollisionCheck(Node node) {
		if (node == null) return;
		ArrayList<WorkstationNode> senderStations = GetSendersUnderCurrentBranch(node);
		if (senderStations.size() < 1) return;
		String sendingState = "Conflict";
		if (senderStations.size() < 2) {
			sendingState = "Success";
			sent.add(senderStations.get(0));
		}
		System.out.print("Timeslot " + indexHolder + ": ");
		for (int i = 0; i < senderStations.size(); i++) {
			System.out.print(senderStations.get(i).getName() + ", ");
		}
		System.out.println("=> " + sendingState);
		indexHolder++;
		RecursiveCollisionCheck(node.getLeft());
		RecursiveCollisionCheck(node.getRight());
	}

	private static ArrayList<WorkstationNode> GetSendersUnderCurrentBranch(Node currentNode) {
		//Traverse from the current node
		ArrayList<WorkstationNode> list = new ArrayList<>();
		AddSendersRecursive(currentNode, list);
		return list;
	}

	private static void AddSendersRecursive(Node currentNode, ArrayList list) {
		if (currentNode == null || list == null) return;
		WorkstationNode nodeElement = (WorkstationNode)currentNode.getElement();
		if (nodeElement.isSender() && !sent.contains(nodeElement)) {
			list.add(nodeElement);
			return;
		}
		AddSendersRecursive(currentNode.getLeft(), list);
		AddSendersRecursive(currentNode.getRight(), list);
	}

	private static void InsertStationDepthFirst(BinaryTree tree, WorkstationNode[] elements) {
		indexHolder = 0;
		RecursiveInsertStation(tree.top, elements);
	}

	private static void RecursiveInsertStation(Node node, WorkstationNode[] elements) {
		if (elements == null || node == null) return;
		if (node.getElement() == null) {
			node.setElement(elements[indexHolder]);
			indexHolder++;
			return;
		}
		RecursiveInsertStation(node.getLeft(), elements);
		RecursiveInsertStation(node.getRight(), elements);
	}

	private static void CreateDepthFirst(int steps, BinaryTree tree) {
		RecursiveCreateDepthFirst(steps, tree.top);
	}

	private static void RecursiveCreateDepthFirst(int currentStep, Node currentNode) {
		if (currentNode == null) return;
		//Check for null
		if (currentNode.getElement() == null && currentStep > 0) {
			//Create technically empty node
			WorkstationNode node = new WorkstationNode();
			currentNode.setElement(node);
			currentNode.setLeft(new Node<>(null));
			currentNode.setRight(new Node<>(null));
		}
		RecursiveCreateDepthFirst(currentStep - 1, currentNode.getLeft());
		RecursiveCreateDepthFirst(currentStep - 1, currentNode.getRight());
	}

	private static int GetSteps(int length) {
		int result = 1;
		while(length > 2) {
			result++;
			length /= 2;
		}
		return result;
	}

	private static WorkstationNode[] GetWorkstationInfo(String fileName) {
		try {
			File inputFile = new File(fileName);
			Scanner reader = new Scanner(inputFile);
			//The first line is the amount of workstations
			int nWorkstations = Integer.parseInt(reader.nextLine());
			WorkstationNode[] workstationsRef = new WorkstationNode[nWorkstations];
			//The second line are the workstations that will send data
			String[] sendersRef = reader.nextLine().split(",");
			System.out.println("The number of stations in the network under investigation is: " + sendersRef.length);
			System.out.print("The stations that have a frame to send are: ");
			for (int i = 0; i < sendersRef.length; i++) {
				System.out.print(sendersRef[i] + ", ");
			}
			System.out.println();
			for (int i = 0; i < workstationsRef.length; i++) {
				String name = Character.toString((char)(65 + i));
				boolean sender = false;
				for (int j = 0; j < sendersRef.length; j++) {
					if (name.equals(sendersRef[j])){
						sender = true;
						break;
					}
				}
				WorkstationNode node = new WorkstationNode(name, sender);
				workstationsRef[i] = node;
			}

			return workstationsRef;
		}
		catch(Exception err) {
			System.out.println(err.getMessage());
			return null;
		}
	}

}
