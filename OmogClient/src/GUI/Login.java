package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Soket.ClientMsgProtocol;
import Static.UtilSocketMode;
import model.User;

public class Login extends JPanel {
	private MainFrame mainFrame;
	private JLabel labelId;
	private JLabel labelPassword;
	private JTextField inputId;
	private JTextField inputPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	
	private Signup signupDialog;
	private User user;
	
	Login(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		user= mainFrame.getUser();
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
			String msg = id+"$"+pw;
			
			if(id.length() >20 || pw.length()>20){
				validity = false;
				errorMsg = "Enter text length under 20!";
			}
			if(id.length() ==0 || pw.length() ==0){
				validity = false;
				errorMsg = "Enter id and password!";
			}
			if(validity){
				byte[] b = ClientMsgProtocol.getByteToString(msg);
				
				mainFrame.getClientMsgProtocol().setMod(UtilSocketMode.LOGIN_MOD);
				mainFrame.getClientMsgProtocol().setMsgByteSize(b.length);
				mainFrame.getClientMsgProtocol().setMsg(b);
				
				
				int result = mainFrame.getClientSocket().sendLoginMessage(mainFrame.getClientMsgProtocol(),user);
				System.out.print("read : "+result);
				
				if(result != -1){
					user.setId(id);
					user.print();
					mainFrame.getClientMsgProtocol().setMySid(result);
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