package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Soket.ClientMsgProtocol;
import Static.UtilSocketMode;

public class Login extends JPanel {
	private MainFrame mainFrame;
	private JLabel labelId;
	private JLabel labelPassword;
	private JTextField inputId;
	private JTextField inputPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	
	private Signup signupDialog;
	
	private ClientMsgProtocol sendMsg;
	private ClientMsgProtocol receiveMsg;
	
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
			boolean validity =true;
			String errorMsg = null;
			
			String id = inputId.getText().trim();
			String pw = inputPassword.getText().trim();
			
			if(id.length() >20 || pw.length()>20){
				validity = false;
				errorMsg = "Enter text length under 20!";
			}
			if(validity){
				byte[] b =null;
				
				try{
					byte[] bid = id.getBytes("UTF-8");
					byte[] bpw = pw.getBytes("UTF-8");
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				sendMsg = new ClientMsgProtocol(UtilSocketMode.LOGIN_MOD,0,0, b.length,b);
				receiveMsg =mainFrame.getClientSocket().sendMessage(sendMsg);
				System.out.print("read : ");
				receiveMsg.print();
				if(receiveMsg.getMod() == UtilSocketMode.LOGIN_MOD){
					mainFrame.goWaitingRoom();				
					return;
				}
				else{
					errorMsg = "Wrong id or password!";
					validity =false;
				}
			}
			
			if(!validity ){
				JOptionPane.showMessageDialog(null, errorMsg ,"error", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	
	}
}