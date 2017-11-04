import java.awt.Button;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GUIChatClient  extends JFrame implements ChatClient {
	
	public static String ServerOutput;
	String name;
	private JTextField userInputText;
	JTextArea displayTextBox = new JTextArea();

	@Override
	public void startConnection(String ip, int port) {
		try(//new socket for the server
				Socket socket = new Socket(ip, port);
                //Create a PrintWriter to write what the client says to the server
				PrintWriter clientOutputToServer = new PrintWriter(socket.getOutputStream(), true);
				//Create a BufferedReader to read what the server says
				BufferedReader clientInputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			
			Scanner userInputToClient = new Scanner(System.in);
		    String userInput = "";
	        String exitToken = "exit";
	        ServerTextPrinter printInputFromServer = new ServerTextPrinter(clientInputFromServer, exitToken);
	        
	        //initial protocol
	        printInputFromServer.start();
	        clientOutputToServer.println(name);
	        
	        //main loop
	        do {
	        	if (userInput.startsWith("/")) {
					String command = userInput.substring(1, userInput.length());
					if (command.equalsIgnoreCase("exit")) {
						break;
					}
				}
	        	userInput = userInputToClient.nextLine();
	        	clientOutputToServer.println(userInput);
	        } while (userInput != null);
	        
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
		displayTextBox.setText("Enter the ip of the server: ");
		System.out.print("Enter the ip of the server: ");
		String ip = initialInput.nextLine();
		displayTextBox.setText("Enter the port of the server: ");
		System.out.print("Enter the port of the server: ");
		int port = initialInput.nextInt();
		
		this.setVisible(true);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(10, 11, 414, 206);
		this.getContentPane().add(verticalBox);
		
		displayTextBox.setText("Enter your name: ");
		//txtpnserverDisplayOf.setBounds(10, 11, 414, 199);
		//frame.getContentPane().add(txtpnserverDisplayOf);
		verticalBox.add(displayTextBox);
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(91, 228, 178, 22);
		this.getContentPane().add(horizontalBox);
		
		userInputText = new JTextField();
		userInputText.setText("#Client Message");
		horizontalBox.add(userInputText);
		userInputText.setColumns(10);
		
		Button button = new Button("Send");
		button.setBounds(268, 228, 70, 22);
		this.getContentPane().add(button);
		
		startConnection(ip, port);
	}
	

	class ServerTextPrinter extends Thread {
		BufferedReader inputFromServer;
		
		public ServerTextPrinter(BufferedReader clientInputFromServer, String exitToken) {
			inputFromServer = clientInputFromServer;
			System.out.println("SYSTEM: Created server print thread");
		}
		
		public void run() {
			String input;
			try {
				while ((input = inputFromServer.readLine()) != null) {
		        	System.out.println(input);
		        	displayTextBox.append(input + "\r\n");
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("SYSTEM: Ended server print thread");
		}
	}

}

