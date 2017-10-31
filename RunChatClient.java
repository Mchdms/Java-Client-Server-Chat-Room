import java.util.Scanner;

/**
 * This class generates a new chat client that can connect to the server
 * @author Daniel
 *
 */
public class RunChatClient {

	public static void main(String[] args) {
		ChatClient client;
		Scanner input = new Scanner(System.in);
		System.out.print("Graphical (G) or Text (T) based client?");
		String userInput = input.nextLine();
		if (userInput.equalsIgnoreCase("G")) {
			client = new GUIChatClient();
		} else {
			client = new TextChatClient();
		}
		client.initialize();
	}

}
