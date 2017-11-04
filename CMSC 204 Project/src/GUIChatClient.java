import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class GUIChatClient  extends JFrame implements ChatClient {
	
	public static String ServerOutput;
	String name;
	private JTextField userInputText;
	JTextArea displayTextBox = new JTextArea();
	Button button;
	String userInput = "";
	boolean running = true;

	@Override
	public void startConnection(String ip, int port) {
		try(//new socket for the server
				Socket socket = new Socket(ip, port);
                //Create a PrintWriter to write what the client says to the server
				PrintWriter clientOutputToServer = new PrintWriter(socket.getOutputStream(), true);
				//Create a BufferedReader to read what the server says
				BufferedReader clientInputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
			
			Scanner userInputToClient = new Scanner(System.in);
			
		    
	        String exitToken = "exit";
	        ServerTextPrinter printInputFromServer = new ServerTextPrinter(clientInputFromServer, exitToken);
	        
	        //initial protocol
	        printInputFromServer.start();
	        clientOutputToServer.println(name);
	        
	        button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (userInput.startsWith("/")) {
						String command = userInput.substring(1, userInput.length());
						if (command.equalsIgnoreCase("exit")) {							
							running = false;
						}
					}
					userInput = userInputText.getText();
					clientOutputToServer.println(userInput);
					
				}	        	
	        });
	        
	        while (running) {
	        	//run the client
	        }	        
			socket.close();
	        userInputToClient.close();
	        System.exit(1);
	        
	        
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
		System.out.print("Enter the ip of the server: ");
		String ip = initialInput.nextLine();
		System.out.print("Enter the port of the server: ");
		int port = initialInput.nextInt();
		
		this.setVisible(true);
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(10, 11, 414, 206);
		this.getContentPane().add(verticalBox);
		
		
		displayTextBox.setLineWrap(true);
	    displayTextBox.setEditable(false);
	    displayTextBox.setVisible(true);
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
		
		
		button = new Button("Send");
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

