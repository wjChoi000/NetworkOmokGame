package OmogGame;

import java.awt.*;
import javax.swing.*;

public class Login extends JPanel {
	private JLabel labelId;
	private JLabel labelPassword;
	private JTextField inputId;
	private JTextField inputPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	Login(){
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
		labelPassword.setBounds(0,40,80,25);
		inputPassword = new JTextField(20);
		inputPassword.setBounds(100,40,160,25);
		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(180,80,80,25);
		btnSignup = new JButton("Sign up");
		btnSignup.setBounds(10,80, 100,25);
		
		add(labelId);
		add(inputId);
		add(labelPassword);
		add(inputPassword);
		add(btnLogin);
		add(btnSignup);
		setSize(300,150);
	}
}
