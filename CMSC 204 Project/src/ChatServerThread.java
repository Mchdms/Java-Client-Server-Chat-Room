import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Thread that will run for each client that connects to the server socket. Keeps a list of all other threads created in order to broadcast its message.
 * Edited to reference an observable list of thread rather than a static list.
 * @author Daniel Wunderink, Micah Adams, and David Riadi
 *
 */
public class ChatServerThread extends Thread implements Observer{
	
//	PrintWriter outputToClient;
//    BufferedReader inputFromClient;
	
	ObjectOutputStream os;
	ObjectInputStream is;
    
    String clientName;
    
	Socket clientSocket;
	
	ServerThreadList threadList;
	
	//this list is used to send messages to all clients and protect against simultaneous editing and accessing
	//private static CopyOnWriteArrayList<ChatServerThread> connections = new CopyOnWriteArrayList<ChatServerThread>();
	
	ArrayList<ChatServerThread> connections = new ArrayList<ChatServerThread>();
	
	/**
	 * Constructor that adds this thread to the observable list.
	 * @param clientSocket - the socket for this Thread's client.
	 */
	public ChatServerThread(Socket clientSocket, ServerThreadList threadList) {
		this.clientSocket = clientSocket;
		this.threadList = threadList;
		threadList.addObserver(this);
		threadList.addThread(this);
		connections = threadList.threadList;
		System.out.println("Client has connected: "+connections);
	}

	@Override
	public void update(Observable threadList, Object arg1) {
		if (threadList.getClass() == ServerThreadList.class) {
			connections = ((ServerThreadList) threadList).threadList;
		}
	}
	
	/**
	 * Constructor that adds this thread to the static list.
	 * @param clientSocket - the socket for this Thread's client.
	 */
//	public ChatServerThread(Socket clientSocket) {
//		this.clientSocket = clientSocket;
//		connections.add(this);
//		System.out.println("Client has connected: "+connections);
//	}
	
	/**
	 * Prints a message to the output stream that goes to the client attached to this thread.
	 * @param message - A string message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
//        outputToClient.println(message);
		os.writeObject(new DummyObjectToSend(message));
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
//			outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
//			inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			is = new ObjectInputStream(clientSocket.getInputStream());
			
			//initial protocol
//			clientName = inputFromClient.readLine();
			clientName = ((DummyObjectToSend) is.readObject()).message;
			broadcastMessage(clientName+" has connected to the Chat Room");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//sends the client input to every other client
		String clientInput;
		try {
//			while ((clientInput = inputFromClient.readLine()) != null) {
			while ((clientInput = ((DummyObjectToSend) is.readObject()).message) != null) {
				if (clientInput.startsWith("/")) {
					String command = clientInput.substring(1, clientInput.length());
					if (command.equalsIgnoreCase("exit")) {
						break;
					} else {
						sendMessage("Type /exit to exit the room");
					}
				}
	        	System.out.println("Client "+clientName+": "+clientInput);
	        	broadcastMessage(clientName+": "+clientInput);
	        }
			broadcastMessage(clientName+" has left the chat room.");
			System.out.println(clientName + " has left the chat room");
			threadList.removeThread(this);
			threadList.deleteObserver(this);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
