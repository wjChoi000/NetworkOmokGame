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
	
	private Signup signupDialog;
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
		
		btnSignup = new JButton("Sign up");
		btnSignup.setBounds(90,80, 80,25);
		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(180,80,80,25);
		
		btnLogin.addActionListener(new LoginActionListener());
		btnSignup.addActionListener(new SignupActionListener());
		
		add(labelId);
		add(inputId);
		add(labelPassword);
		add(inputPassword);
		add(btnLogin);
		add(btnSignup);
		
		setBackground(Color.red);
		setSize(270,120);
		//setPreferredSize(new Dimension(300,150));
		
		signupDialog = new Signup(mainFrame);
	}
	public JButton getLoginButton(){
		return btnLogin;
	}
	public JButton getSignButton(){
		return btnSignup;
	}
	
	class SignupActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			signupDialog.callSignup();
			signupDialog.setVisible(true);
		}
	}
	
	class LoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			//network
			//id & password check
			
			mainFrame.goWaitingRoom();
		}
	}
}