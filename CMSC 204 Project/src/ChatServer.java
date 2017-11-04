import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Main chat server class. Listens for connections, and creates a new ChatServerThread for each client that connects to the server socket.
 * @author Daniel Wunderink, Micah Adams, and David Riadi
 *
 */
public class ChatServer {
	
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 12345;
    
    public static void main(String args[]) {
    	try {
    		//create the socket server object
    		server = new ServerSocket(port);
            boolean running = true;
            while(running){
                System.out.println("Waiting for client request");
                //creating socket and waiting for client connection
                Socket clientSocket = server.accept();
                (new ChatServerThread(clientSocket)).start();
            }
    	} catch (IOException e){
    		e.printStackTrace();
    	}
        
    }

}
