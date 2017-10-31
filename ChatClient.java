
public interface ChatClient {
	
	/**
	 * Starts the connection with the server on the specified IP and port. Listens for client input and sends it to the server.
	 */
	public void startConnection(String ip, int port);
	
	/**
	 * Asks the user for a name, server ip and port, and starts a connection with the server.
	 */
	public void initialize();
    
}
