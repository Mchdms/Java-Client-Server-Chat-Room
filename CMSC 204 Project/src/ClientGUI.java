import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import javax.swing.JDesktopPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JList;
import javax.swing.JSeparator;

public class ClientGUI {

	JFrame frame;
	private JTextField txtuserWillBe;
	static JTextArea txtpnserverDisplayOf = new JTextArea();


	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(10, 11, 414, 206);
		frame.getContentPane().add(verticalBox);
		
		txtpnserverDisplayOf.setText("Enter your name: ");
		//txtpnserverDisplayOf.setBounds(10, 11, 414, 199);
		//frame.getContentPane().add(txtpnserverDisplayOf);
		verticalBox.add(txtpnserverDisplayOf);
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(91, 228, 178, 22);
		frame.getContentPane().add(horizontalBox);
		
		txtuserWillBe = new JTextField();
		txtuserWillBe.setText("#Client Message");
		horizontalBox.add(txtuserWillBe);
		txtuserWillBe.setColumns(10);
		
		Button button = new Button("Send");
		button.setBounds(268, 228, 70, 22);
		frame.getContentPane().add(button);
	}
}