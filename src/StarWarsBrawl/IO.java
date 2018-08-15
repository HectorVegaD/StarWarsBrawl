package StarWarsBrawl;
import java.util.Scanner;

public class IO {
	// Having this utility class will allow for centralized control of a single
	// Scanner object. This helps avoid the issue of having multiple
	// Scanner objects.
	private static Scanner scan = new Scanner(System.in);
	public static Scanner getScanner(){
		return scan;
	}
	
	public static void closeScanner(){
		scan.close();
	}
}
