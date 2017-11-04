/**
 * Interface for Text and GUI Chat Clients. Allows for creation of either with the same ChatClient variable.
 * @author Daniel Wunderink, Micah Adams, and David Riadi
 *
 */
public interface ChatClient {
	
	/**
	 * Starts the connection with the server on the specified IP and port. Listens for client input and sends it to the server.
	 * @param ip - The IP of the server socket.
	 * @param port - The port of the server socket.
	 */
	public void startConnection(String ip, int port);
	
	/**
	 * Asks the user for a name, server ip and port. Starts a connection with the server.
	 */
	public void initialize();
    
}
