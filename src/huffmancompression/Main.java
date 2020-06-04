package huffmancompression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	private static final Integer ContentDisplaySize = 50;
	private static final Integer ContentPrefixSize = 35;
	
	public static void main(String[] args) {
		var arguments = Arguments.parse(args);
		var content = readFile(arguments.getFilePath());
		
		displayContent("Original content", content);
		
		var compression = new CompressionTree(content);
		var encodedContent = compression.encode();
		
		displayContent("Encoded content", encodedContent);
		
		var tree = compression.getTree();
		var decodedContent = CompressionTree.decode(encodedContent, tree);
		
		displayContent("Decoded content", decodedContent);
	}
	
	private static void displayContent(String contentType, String content) {
		var display = new StringBuilder(contentType)
			.append(String.format(" (%d bytes):", content.getBytes().length));
	
		if (display.length() < ContentPrefixSize) {
			var diff = ContentPrefixSize - display.length();
			for (int i = 0; i < diff; i++) {
				display.append(" ");
			}
		}
		
		display.append(cropToSize(content, ContentDisplaySize));
		
		System.out.println(display.toString());
	} 
	
	private static String cropToSize(String content, Integer size) {		
		if (content.length() < size) {
			return content;
		} 
		
		return new StringBuilder()
			.append(content.substring(0, size - 3))
			.append("...")
			.toString();
	}
	
	private static String readFile(String filePath) {
		FileReader reader = null;
		try {
			var file = new File(filePath);
			reader = new FileReader(file);
			var builder = new StringBuilder();
			
			int c;
			while ((c = reader.read()) != -1) {
				builder.append((char) c);
			}
			
			return builder.toString();			
		} catch (FileNotFoundException ex) {
			System.out.printf("The file `%s` does not exist", filePath);
			return null;
		} catch (IOException ex) {
			System.out.printf("Failed to read `%s`", filePath);
			return null;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
