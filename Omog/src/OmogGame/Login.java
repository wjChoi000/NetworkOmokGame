package OmogGame;

import java.awt.*;
import javax.swing.*;

public class Login extends JPanel {
	private JLabel labelId;
	private JLabel labelPassword;
	private JTextField inputId;
	private JTextField inputPassword;
	private JButton btnLogin;
	
	Login(){
		init();
	}
	private void init(){
		//set layout
		GridLayout grid = new GridLayout(3,2);
		grid.setVgap(5);
		setLayout(grid);
		
		//contents
		labelId = new JLabel("ID");
		labelPassword = new JLabel("PASSWORD");
		inputId = new JTextField("");
		inputPassword = new JTextField("");
		btnLogin = new JButton("LOGIN");
		
		add(labelId);
		add(inputId);
		add(labelPassword);
		add(inputPassword);
		add(btnLogin);
		
		setSize(400,200);
	}
}
