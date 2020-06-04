package huffmancompression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompressionTree {
	
	private String content;
	private String[] tokens;
	private Map<String, Integer> occurrences;
	
	public CompressionTree(String content) {
		this.content = content;
		this.tokens = this.tokenize();
		this.occurrences = this.mapOccurrences();
	}
	
	public String compress() {
		// Get the compression tree
		var root = buildNodeTree();
		var compressed = new StringBuilder();
		
		// Map every token code
		for (var token : this.tokens) {
			var node = root;
			var finished = false;
			
			while (!finished) {				
				if (node.getContent().length == 1)
				{
					compressed.append(" ");
					finished = true;
				}
				else if (node.isLeftNode(token))
				{				
					compressed.append(0);
					node = node.getLeftNode();
				}
				else if (node.isRightNode(token))
				{			
					compressed.append(1);
					node = node.getRightNode();
				}
			}
		}
		
		return compressed.toString();
	}
	
	private Node buildNodeTree() {	
		// Create a node for each token
		var nodes = new ArrayList<Node>();
		for (var token : this.occurrences.keySet()) {
			var occurrence = this.occurrences.get(token);
			var node = new Node(token, occurrence);
			
			nodes.add(node);
		}
		
		// While there is no root node
		while (nodes.size() > 1) {
			// Get the two least occurring words
			var second = getLessProbableNode(nodes);
			var first = getLessProbableNode(nodes);
			
			// Create a new node with two children
			var node = new Node(first, second);
			
			// Add new node to the node list
			nodes.add(node);
		}
		
		// The single node on the list is the root node
		return nodes.get(0);
	}
	
	// Split the content into it's tokens
	private String[] tokenize() {
		// Split content into characters
		var characters = this.content.toCharArray();
		
		var token = new StringBuilder();
		var tokens = new ArrayList<String>();
		
		// Iterates over each character		
		for (var i = 0; i < characters.length; i++) {
			var character = characters[i];
			// Check if the character is from a word or a digit
			if (Character.isLetterOrDigit(character))
			{
				token.append(character);
			}
			// If it is a space, then the word is over
			else if (Character.isSpaceChar(character))
			{
				tokens.add(token.toString());
				token = new StringBuilder();
			}
			// Special characters
			else
			{		
				var isLastCharacter = i >= characters.length - 1;
				
				// If next character is not `space`, then add this character to this token
				// ex: "word,"
				if (!isLastCharacter && !Character.isSpaceChar(characters[i + 1])) {
					token.append(character);
				}
				
				// Add new token and reset value
				tokens.add(token.toString());
				token = new StringBuilder();
				
				// If next character is `space`, the create a new token
				// ex: ", "
				if (!isLastCharacter && Character.isSpaceChar(characters[i + 1])) {
					token.append(character);
					token.append(" ");
				}
			}
		}
		
		if (token.length() > 0) {
			tokens.add(token.toString());
		}
		
		return tokens.toArray(new String[tokens.size()]);
	}
	
	private Map<String, Integer> mapOccurrences() {
		var occurrences = new HashMap<String, Integer>();
		for (var token : this.tokens) {
			// Check if node already exists
			if (occurrences.containsKey(token)) {
				// If node exists then increment occurrence				
				var occurrence = occurrences.get(token);
				occurrences.replace(token, ++occurrence);
			} else {
				// Else create a new node for the word
				occurrences.put(token, 1);
			}
		}
		
		return occurrences;
	}
	
	private Node getLessProbableNode(List<Node> nodes) {	
		// Start checking by the first node
		var chosenIndex = 0;
		
		// Check every node after it
		for (int i = 1; i < nodes.size(); i++) {
			// Check the probability of a node to occur
			if (nodes.get(i).getProbability() < nodes.get(chosenIndex).getProbability()) {
				chosenIndex = i;
			}
		}
		
		/*
		 * Remove node from collection.
		 * This guarantees that a loop selecting the same node will never happen.
		 */
		var node = nodes.get(chosenIndex);
		nodes.remove(chosenIndex);
		
		return node;
	}
}
