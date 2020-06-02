package huffmancompression;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompressionTree {

	private String content;
	private Integer totalWords;
	private Map<String, Node> nodes;
	
	private Node root;
	
	public CompressionTree(String content) {
		this.content = content;
		this.totalWords = 0;
		this.root = null;
		this.nodes = new HashMap<String, Node>();
		
		this.checkOccurrences();
	}
	
	public Node getTree() {
		// Check if the root was discovered before
		if (this.root != null) {
			return this.root;
		}
	
		// Make copy of the node list
		var nodes = new ArrayList<Node>(this.nodes.values());
		
		// While there is no root node
		while (nodes.size() > 1) {
			// Get the two least occurring words
			var first = getLessOccurrenceNode(nodes);
			var second = getLessOccurrenceNode(nodes);
			
			// Create a new node with two children
			var node = new Node(first, second);
			
			// Add new node to the node list
			nodes.add(node);
		}
		
		// The single node on the list is the root node
		this.root = (Node) nodes.toArray()[0];
		return this.root;
	}
	
	// Map word nodes and check occurrences
	private void checkOccurrences() {
		// Split the content into words
		var words = this.content.split(" ");
		for (var word : words) {
			addOrUpdateWord(word);
			this.totalWords++;
		}
	}
	
	// Add or update the word node
	private void addOrUpdateWord(String word) {
		// Check if node already exists
		if (this.nodes.containsKey(word)) {
			// If node exists then increment occurrence
			var node = this.nodes.get(word);
			node.incrementOccurrence(1);
			this.nodes.replace(word, node);
		} else {
			// Else create a new node for the word
			this.nodes.put(word, new Node(word));
		}
	}
	
	private Node getLessOccurrenceNode(Collection<Node> nodeCollection) {
		var nodes = nodeCollection.toArray(new Node[nodeCollection.size()]);
		
		// Start checking by the first node
		var chosenIndex = 0;
		
		// Check every node after it
		for (int i = 1; i < nodes.length; i++) {
			// Check the probability of a node to occur
			if (nodes[chosenIndex].getOccurrence() < nodes[i].getOccurrence()) {
				chosenIndex = i;
			}
		}
		
		/*
		 * Remove node from collection.
		 * This guarantees that a loop selecting the same node will never happen.
		 */
		Node node = nodes[chosenIndex];
		nodeCollection.remove(node);
		
		return node;
	}	
}
