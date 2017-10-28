import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
       // InetAddress host = new InetAddress(10.0.29.99);
    	
    	Scanner reader = new Scanner(System.in);  // Reading from System.in
    	System.out.println("Enter Server IP: ");
    	String input = reader.next(); // Scans the next token of the input as an int.
    	//once finished
    	
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        while(true){
            //establish socket connection to server
            socket = new Socket(input, 25566);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            String userMessage = reader.nextLine();
            oos.writeObject(""+userMessage);
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);
            //close resources
            ois.close();
            oos.close();
            Thread.sleep(100);
            if(userMessage.equals("exit")){
            	break;
            }
        }    
    }
}
