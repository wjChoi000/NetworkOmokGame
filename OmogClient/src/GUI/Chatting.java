package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Chatting extends JPanel implements KeyListener,ActionListener{
	
	private int width;
	private int height;
	private int m;
	
	private JLabel labelName;
	private JTextArea textArea;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JButton btnSend;
	private BorderLayout bl;
	
	private MainFrame mf;
	private String name;
	Chatting(MainFrame mf){
		this.mf = mf;
		name = mf.getUser().getName();
		init();
	}
	private void init(){
		width= MainFrame.chattingWidth;
		height = MainFrame.chattingHeight;
		m = MainFrame.m;
		int btnSize =50;
		int labelh = 25;
		
		setLayout(null);
		
		labelName = new JLabel("Chatting");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		textField = new JTextField();
		btnSend = new JButton("Send");
	
		textArea.setEditable(false);
		
		labelName.setBounds(m,m,width-2*m, labelh);
		scrollPane.setBounds(m,2*m+labelh,width-2*m,height-2*(2*m+labelh));
		textField.setBounds(m,height-labelh-m, width-btnSize-3*m,labelh);
		btnSend.setBounds(width-m-btnSize,height-labelh-m,btnSize,labelh);
	
		
		add(labelName);
		add(scrollPane);
		add(textField);
		add(btnSend);
	
		setSize(width,height);
	
		btnSend.addActionListener(this);
		textField.addKeyListener(this);
		textField.requestFocusInWindow();
	}
	
	public JButton getBtnsend(){return btnSend;}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String message = textField.getText();
		if(message.trim().length() >0){
			textField.setText("");
			textArea.setText(textArea.getText()+name+" : "+message+"\n");
		}
	
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
			System.out.println("Chatting : enter press");
			btnSend.doClick();
	    }	
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
