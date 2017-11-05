import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private JTextField userInputText = new JTextField(20);
	JTextArea displayTextBox = new JTextArea();
	JButton button = new JButton("Send");
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
	        
	        userInputText.addKeyListener(new GUIKeyListener(clientOutputToServer));
	        
	        button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					userInput = userInputText.getText();
					clientOutputToServer.println(userInput);
					userInputText.setText("");
					
				}	        	
	        });
 
	        while (running) {
	        	System.out.println();
	        	if(userInput.equalsIgnoreCase("/exit")){
	        		System.out.println("Closing");

	    			socket.close();
	    	        userInputToClient.close();
	    	        System.exit(1);
	        		
	        	}
	        }
	        
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
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        displayTextBox.setWrapStyleWord(true);
        displayTextBox.setEditable(false);
        displayTextBox.setFont(Font.getFont(Font.SANS_SERIF));
        JScrollPane scroller = new JScrollPane(displayTextBox);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        DefaultCaret caret = (DefaultCaret) displayTextBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(scroller);
        inputpanel.add(userInputText);
        inputpanel.add(button);
        panel.add(inputpanel);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.pack();
        this.setSize(700, 500);
        this.setVisible(true);
        this.setResizable(true);
        userInputText.requestFocus();
		
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
	/**
	 * A custom key listener for the GUI. Listens for the user to hit enter. Takes a PrintWriter for output to the server
	 *
	 */
	class GUIKeyListener implements KeyListener{
		PrintWriter clientOutputToServer;
        public GUIKeyListener(PrintWriter clientOutputToServerKey) {
        	clientOutputToServer = clientOutputToServerKey;
		}

		public void keyTyped(KeyEvent e) {           
        }

        public void keyPressed(KeyEvent e) {
           if(e.getKeyCode() == KeyEvent.VK_ENTER){

				if (userInput.startsWith("/")) {
					String command = userInput.substring(1, userInput.length());
					if (command.equalsIgnoreCase("exit")) {							
						running = false;
						System.out.println("Exiting");
					}
				}
				userInput = userInputText.getText();
				clientOutputToServer.println(userInput);
				userInputText.setText("");
				
           }
        }

		public void keyReleased(KeyEvent arg0) {			
		}

     }

}

