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
			while (!(clientInput = inputFromClient.readLine()).equalsIgnoreCase("exit")) {
	        	System.out.println("Client: "+clientInput);
	        	broadcastMessage(clientInput);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
