import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.JDesktopPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JList;
import javax.swing.JSeparator;

public class Frame1 {

	private JFrame frame;
	private JTextField txtuserWillBe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
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
		verticalBox.setBounds(89, 41, 248, 163);
		frame.getContentPane().add(verticalBox);
		
		JTextPane txtpnserverDisplayOf = new JTextPane();
		txtpnserverDisplayOf.setText("#Server Display of Client Message");
		verticalBox.add(txtpnserverDisplayOf);
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(89, 203, 178, 22);
		frame.getContentPane().add(horizontalBox);
		
		txtuserWillBe = new JTextField();
		txtuserWillBe.setText("#Client Message");
		horizontalBox.add(txtuserWillBe);
		txtuserWillBe.setColumns(10);
		
		Button button = new Button("Send");
		button.setBounds(267, 203, 70, 22);
		frame.getContentPane().add(button);
	}
}
