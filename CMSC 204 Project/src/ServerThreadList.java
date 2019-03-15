import java.util.ArrayList;
import java.util.Observable;

/**
 * Contains a list of all the server threads that are hosting a client. Allows threads to access each other.
 * @author Daniel
 *
 */
public class ServerThreadList extends Observable{
	
	ArrayList<ChatServerThread> threadList = new ArrayList<ChatServerThread>();

	public void addThread(ChatServerThread serverThread) {
		threadList.add(serverThread);
	}
	
	public void removeThread(ChatServerThread serverThread) {
		threadList.remove(serverThread);
	}

}
