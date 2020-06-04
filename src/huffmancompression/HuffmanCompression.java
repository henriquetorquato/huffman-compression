package huffmancompression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HuffmanCompression {

	public static void main(String[] args) {
		var arguments = Arguments.parse(args);
		var content = readFile(arguments.getFilePath());
		
		var tree = new CompressionTree(content);
		var compressed = tree.compress();
		
		System.out.printf("Original content lenght: %d characters", content.length());
		System.out.println();
		System.out.printf("Compressed content lenght: %d characters", compressed.length());
		System.out.println();
		System.out.println();
		System.out.println(content);
		System.out.println(compressed);
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
