package huffmancompression;

// Represents a tree node
public class Node {

	private String[] contents;
	private Integer probability;
	
	// For value 0
	private Node leftNode;
	// For value 1
	private Node rightNode;
	
	// Constructor for creating a node without children
	public Node(String content, Integer probability) {
		this.leftNode = null;
		this.rightNode = null;
		this.probability = probability;
		this.contents = new String[] { content };
	}
	
	// Constructor for creating a node with children
	public Node(Node leftNode, Node rightNode) {
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		
		// New node occurrence is the sum of it's children occurrence
		this.probability = leftNode.probability + rightNode.probability;
		this.contents = new String[leftNode.contents.length + rightNode.contents.length];
		
		// Copy children content to current node content
		System.arraycopy(leftNode.contents, 0, this.contents, 0, leftNode.contents.length);
		System.arraycopy(rightNode.contents, 0, this.contents, leftNode.contents.length, rightNode.contents.length);
	}
	
	public String[] getContent() {
		return this.contents;
	}
	
	public Integer getProbability() {
		return this.probability;
	}
	
	public void incrementOccurrence(Integer ocurrence) {
		this.probability += this.probability;
	}
	
	public Node getLeftNode() {
		return this.leftNode;
	}
	
	public Node getRightNode() {
		return this.rightNode;
	}
	
	public Boolean isLeftNode(String word) {
		return arrayContains(word, this.leftNode.contents);
	}
	
	public Boolean isRightNode(String word) {
		return arrayContains(word, this.rightNode.contents);
	}
	
	public String toString() {
		return String.join(", ", this.contents);
	}
	
	private Boolean arrayContains(String value, String[] items) {
		for (var item : items) {
			if (value.equals(item)) {
				return true;
			}
		}
		
		return false;
	}
	
}
