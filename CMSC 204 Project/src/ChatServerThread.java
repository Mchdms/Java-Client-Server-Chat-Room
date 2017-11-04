import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread that will run for each client that connects to the server socket. Keeps a list of all other threads created in order to broadcast its message.
 * @author Daniel Wunderink, Micah Adams, and David Riadi
 *
 */
public class ChatServerThread extends Thread{
	
	PrintWriter outputToClient;
    BufferedReader inputFromClient;
    
    String clientName;
    
	Socket clientSocket;
	
	//this list is used to send messages to all clients and protect against simultaneous editing and accessing
	private static CopyOnWriteArrayList<ChatServerThread> connections = new CopyOnWriteArrayList<ChatServerThread>();
	
	/**
	 * Constructor that adds this thread to the static list.
	 * @param clientSocket - the socket for this Thread's client.
	 */
	public ChatServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		connections.add(this);
		System.out.println("Client has connected: "+connections);
	}
	
	/**
	 * Prints a message to the output stream that goes to the client attached to this thread.
	 * @param message - A string message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
        outputToClient.println(message);
	}
	
	/**
	 * Accesses all other threads to print a message to each client.
	 * @param message
	 * @throws IOException
	 */
	public void broadcastMessage(String message) throws IOException {
		for (ChatServerThread connection : connections) {
			connection.sendMessage(message);
		}
	}
	
	/**
	 * Listens for client input, and broadcasts it to all other clients.
	 */
	public void run() {
		//print message for a client connecting and gets the client name
		try {
			outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
			inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//initial protocol
			clientName = inputFromClient.readLine();
			broadcastMessage(clientName+" has connected to the Chat Room");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//sends the client input to every other client
		String clientInput;
		try {
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
