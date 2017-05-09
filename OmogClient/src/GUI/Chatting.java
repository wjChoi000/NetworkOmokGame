package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chatting extends JPanel implements ActionListener {
	
	private JLabel labelName;
	private JTextArea textArea;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JButton btnSend;
	private Dimension d;
	private BorderLayout bl;
	
	private int width= 400;
	private int height= 600;
	Chatting(Dimension d){
		this.d = d;
		init();
	}
	private void init(){
		setLayout(null);

		labelName = new JLabel("chatting");
		textField = new JTextField();
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		btnSend = new JButton("Send");
		
		labelName.setBounds(10,10,width-20, 25);
		scrollPane.setBounds(10,45,width-20,height-100-55);
		textField.setBounds(10,500, width -120,25);
		btnSend.setBounds(300,500,90,25);
		
		add(labelName);
		add(scrollPane);
		add(textField);
		add(btnSend);
		
		textField.addActionListener(this);
		setSize(width,height);
	}
	
	// message 
	public void actionPerformed(ActionEvent e){
		
	}
}
