import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * Text-based chat client. Gets user input and sends it to the server. Prints what the server outputs.
 * @author Daniel Wunderink, Micah Adams, and David Riadi
 *
 */
public class TextChatClient implements ChatClient{
	
	String name;

	@Override
	public void startConnection(String ip, int port) {
		try(//new socket for the server
				Socket socket = new Socket(ip, port);
//                //Create a PrintWriter to write what the client says to the server
//				PrintWriter clientOutputToServer = new PrintWriter(socket.getOutputStream(), true);
//				//Create a BufferedReader to read what the server says
//				BufferedReader clientInputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			){
			
			Scanner userInputToClient = new Scanner(System.in);
		    String userInput = "";
	        String exitToken = "exit";
	        //ServerTextPrinter printInputFromServer = new ServerTextPrinter(clientInputFromServer, exitToken);
	        ServerTextPrinter printInputFromServer = new ServerTextPrinter(is, exitToken);
	        //initial protocol
	        printInputFromServer.start();
//	        clientOutputToServer.println(name);
	        os.writeObject(new DummyObjectToSend(name));
	        //main loop
	        do {
	        	if (userInput.startsWith("/")) {
					String command = userInput.substring(1, userInput.length());
					if (command.equalsIgnoreCase("exit")) {
						break;
					}
				}
	        	userInput = userInputToClient.nextLine();
//	        	clientOutputToServer.println(userInput);
		        os.writeObject(new DummyObjectToSend(userInput));
	        } while (userInput != null);
	        
	        is.readObject();
	        socket.close();
	        userInputToClient.close();
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize() {
		Scanner initialInput = new Scanner(System.in);
		System.out.print("Enter your name: ");
		name = initialInput.nextLine();
		System.out.print("Enter the ip of the server: ");
		String ip = initialInput.nextLine();
		System.out.print("Enter the port of the server: ");
		int port = initialInput.nextInt();
		
		startConnection(ip, port);
	}
	
	/**
	 * Thread to print server messages. Needs to be in a thread since it is a while loop that prints whenever the server outputs something.
	 * @author Daniel Wunderink, Micah Adams, and David Riadi
	 *
	 */
	class ServerTextPrinter extends Thread {
//		BufferedReader inputFromServer;
		ObjectInputStream inputFromServer;
		
//		public ServerTextPrinter(BufferedReader clientInputFromServer, String exitToken) {
//			inputFromServer = clientInputFromServer;
//			System.out.println("SYSTEM: Created server print thread");
//		}
		
		public ServerTextPrinter(ObjectInputStream clientInputFromServer, String exitToken) {
			inputFromServer = clientInputFromServer;
			System.out.println("SYSTEM: Created server print thread");
		}
		
		public void run() {
//			String input;
			Object input;
			try {
//				while ((input = inputFromServer.readLine()) != null) {
				while ((input = inputFromServer.readObject()) != null) {
		        	System.out.println(((DummyObjectToSend) input).message);
		        }
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("SYSTEM: Ended server print thread");
		}
	}

}
