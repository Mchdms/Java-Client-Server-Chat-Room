import java.awt.EventQueue;
import java.util.Scanner;

/**
 * This class generates a new chat client that can connect to the server
 *
 */
public class RunChatClient {

	public static void main(String[] args) {
		ChatClient client;
		Scanner input = new Scanner(System.in);
		System.out.print("Graphical (G) or Text (T) based client?");
		String userInput = input.nextLine();
		
		if (userInput.equalsIgnoreCase("G")) {
			//If the user chooses G, set up swing for the GUI, then create the GUIChatClient
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ClientGUI window = new ClientGUI();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			client = new GUIChatClient();
		} else {
			client = new TextChatClient();
		}
		client.initialize();
	}

}
