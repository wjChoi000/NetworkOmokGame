package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JPanel {
	private MainFrame mainFrame;
	private JLabel labelId;
	private JLabel labelPassword;
	private JTextField inputId;
	private JTextField inputPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	Login(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	private void init(){
		//set layout
		setLayout(null);
		
		//contents
		labelId = new JLabel("ID");
		labelId.setBounds(10,10,80,25);
		inputId = new JTextField(20);
		inputId.setBounds(100,10,160,25);
		
		labelPassword = new JLabel("PASSWORD");
		labelPassword.setBounds(10,40,80,25);
		inputPassword = new JTextField(20);
		inputPassword.setBounds(100,40,160,25);
		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(180,80,80,25);
		btnSignup = new JButton("Sign up");
		btnSignup.setBounds(10,80, 100,25);
	
		btnLogin.addActionListener(new LoginActionListener());
		
		add(labelId);
		add(inputId);
		add(labelPassword);
		add(inputPassword);
		add(btnLogin);
		add(btnSignup);
		
		setBackground(Color.blue);
		setSize(300,150);
		//setPreferredSize(new Dimension(300,150));
	}
	public JButton getLoginButton(){
		return btnLogin;
	}
	public JButton getSignButton(){
		return btnSignup;
	}
	
	class LoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			mainFrame.goWaitingRoom();
		}
	}
}