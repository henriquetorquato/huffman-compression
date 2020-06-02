package huffmancompression;

public class Arguments {

	public static final String HelpCommand = "help";
	public static final String Usage = "Usage: HuffmanCompression.exe [filePath]";
	
	private String filePath;
	
	public Arguments(String[] args) {
		this.filePath = args[0];
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public static Arguments parse(String[] args) {
		if (args.length == 0 || args[0] == Arguments.HelpCommand) {
			printUsage();
		}
		
		return new Arguments(args);
	}
	
	private static void printUsage() {
		System.out.println(Arguments.Usage);
		System.exit(0);
	}

}
