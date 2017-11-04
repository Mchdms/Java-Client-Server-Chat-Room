import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is a thread that will run for each ServerSocket connected to a client.
 * @author Daniel
 *
 */
public class ChatServerThread extends Thread{
	
	PrintWriter outputToClient;
    BufferedReader inputFromClient;
    
    String clientName;
    
	Socket clientSocket;
	
	private static CopyOnWriteArrayList<ChatServerThread> connections = new CopyOnWriteArrayList<ChatServerThread>();
	
	public ChatServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		connections.add(this);
		System.out.println("Client has connected: "+connections);
	}
	
	public void sendMessage(String message) throws IOException {
        outputToClient.println(message);
	}
	
	public void broadcastMessage(String message) throws IOException {
		for (ChatServerThread connection : connections) {
			connection.sendMessage(message);
		}
	}
	
	public void run() {
		try {
			outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
			inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			broadcastMessage("A new Client has connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String clientInput;
		try {
			clientName = inputFromClient.readLine();
			while ((clientInput = inputFromClient.readLine()) != null) {
				if (clientInput.startsWith("/")) {
					String command = clientInput.substring(1, clientInput.length());
					if (command.equalsIgnoreCase("exit")) {
						break;
					} else {
						sendMessage("Type /exit to exit the room");
					}
				}
	        	System.out.println("Client: "+clientInput);
	        	broadcastMessage(clientName+": "+clientInput);
	        }
			broadcastMessage(clientName+" has left the chat room.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
