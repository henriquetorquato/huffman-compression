package huffmancompression;

import java.util.ArrayList;
import java.util.Collections;

// Represents a tree node
public class Node {

	private String[] contents;
	private Integer occurrence;
	
	// For value 0
	private Node leftNode;
	// For value 1
	private Node rightNode;
	
	// Constructor for creating a node without children
	public Node(String content) {
		this.leftNode = null;
		this.rightNode = null;
		this.occurrence = 1;
		this.contents = new String[] { content };
	}
	
	// Constructor for creating a node with children
	public Node(Node leftNode, Node rightNode) {
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		
		// New node occurrence is the sum of it's children occurrence
		this.occurrence = leftNode.occurrence + rightNode.occurrence;
		this.contents = new String[leftNode.contents.length + rightNode.contents.length];
		
		// Copy children content to current node content
		System.arraycopy(leftNode.contents, 0, this.contents, 0, leftNode.contents.length);
		System.arraycopy(rightNode.contents, 0, this.contents, leftNode.contents.length, rightNode.contents.length);
	}
	
	public String[] getContent() {
		return this.contents;
	}
	
	public Integer getOccurrence() {
		return this.occurrence;
	}
	
	public void incrementOccurrence(Integer ocurrence) {
		this.occurrence += this.occurrence;
	}
	
	public String toString() {
		return String.join(", ", this.contents);
	}
	
}
