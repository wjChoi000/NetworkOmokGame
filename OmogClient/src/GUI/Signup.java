package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Soket.ClientMsgProtocol;


public class Signup extends JDialog {
	
	private MainFrame mf;
	
	private JLabel labelId;
	private JLabel labelPassword;
	private JLabel labelPasswordCheck;
	private JLabel labelName;
	
	private JTextField inputId;
	private JTextField inputPassword;
	private JTextField inputPasswordCheck;
	private JTextField inputName;
	
	private JButton btnSubmit;
	private JButton btnCancel;
	
	private int width;
	private int height;
	
	private int Margin = 20;
	
	private int geb = 10;
	private int labelWidth =80;
	private int inputWidth = 160;
	private int labelHeight = 25;
	
	private ClientMsgProtocol msg;
	
	Signup(MainFrame mf){
		this.mf = mf;
		width = Margin*2+labelWidth+inputWidth+geb;
		height = 2*Margin+5*(geb+labelHeight)+100;
		init();
	}
	private void init(){
		//set layout
		setLayout(null);
		
		//contents
		labelId = new JLabel("ID");
		labelId.setBounds(Margin,Margin,labelWidth,labelHeight);
		inputId = new JTextField(20);
		inputId.setBounds(Margin+labelWidth+geb,Margin,inputWidth,labelHeight);
		
		labelPassword = new JLabel("PASSWORD");
		labelPassword.setBounds(Margin,Margin+1*(geb+labelHeight),labelWidth,labelHeight);
		inputPassword = new JTextField(20);
		inputPassword.setBounds(Margin+labelWidth+geb,Margin+1*(geb+labelHeight),inputWidth,labelHeight);
		
		labelPasswordCheck = new JLabel("PW Check");
		inputPasswordCheck = new JTextField(20);
		labelPasswordCheck.setBounds(Margin,Margin+2*(geb+labelHeight),labelWidth,labelHeight);
		inputPasswordCheck.setBounds(Margin+labelWidth+geb,Margin+2*(geb+labelHeight),inputWidth,labelHeight);
		
		labelName = new JLabel("Name");
		labelName.setBounds(Margin,Margin+3*(geb+labelHeight),labelWidth,labelHeight);
		inputName= new JTextField(20);
		inputName.setBounds(Margin+labelWidth+geb,Margin+3*(geb+labelHeight),inputWidth,labelHeight);
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(width-Margin-2*labelWidth-geb, Margin+4*(geb+labelHeight),labelWidth ,labelHeight);		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(width-Margin-labelWidth,Margin+4*(geb+labelHeight),labelWidth,labelHeight);

		btnCancel.addActionListener(new CancelActionListener());
		btnSubmit.addActionListener(new SubmitActionListener());
		
		add(labelId);
		add(inputId);
		add(labelPassword);
		add(inputPassword);
		add(inputPasswordCheck);
		add(labelPasswordCheck);
		add(labelName);
		add(inputName);
		add(btnSubmit);
		add(btnCancel);
		
		this.setLocation(mf.width/2 -width/2, mf.height/2-height/2);
		
		this.setSize(width, height);
		this.setModal(true);
		
		//setPreferredSize(new Dimension(300,150));
	}
	
	public void callSignup(){
		inputId.setText("");
		inputPassword.setText("");
		inputPasswordCheck.setText("");
		inputName.setText("");
	}
	
	
	class SubmitActionListener implements ActionListener{
		
		private String id;
		private String password;
		private String passwordCheck;
		private String name;
		private boolean validity;
		private String msg;
		public void actionPerformed(ActionEvent e){
			id = inputId.getText();
			password = inputPassword.getText();
			passwordCheck = inputPasswordCheck.getText();
			name = inputName.getText();
			validity = true;
			
			if(validity && !enterAllField()){
				msg = "Enter all elements.";
				validity = false;
			}
			
			if(validity && haveSpacingWord()){
				msg = "Do not enter a space.";
				validity = false;
			}
			
			if(validity && !passwordCheck()){
				msg = "Confrim your Password.";
				validity = false;
			}
			
			
			if(!validity){
				JOptionPane.showMessageDialog(null, msg ,"error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{
				
				//network
				//id Duplication check;
				//DB save
				
				
				JOptionPane.showMessageDialog(null, "Sign up is success." ,"SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
			}
		}
		
		private boolean enterAllField(){
			if(id.trim().length() ==0  || password.trim().length() ==0 
					|| password.trim().length() ==0 ||password.trim().length() ==0  ){
				return false;
			}
			return true;
		}
		private boolean passwordCheck(){
			if(password.equals(passwordCheck)){
				return true;
			}
			return false;
		}
		private boolean haveSpacingWord(){
			if(spaceCheck(id) || spaceCheck(password) || spaceCheck(passwordCheck)||spaceCheck(name)){
				return true;
			}
			return false;
		}
		
		private boolean spaceCheck(String spaceCheck)
		{
		    for(int i = 0 ; i < spaceCheck.length() ; i++)
		    {
		        if(spaceCheck.charAt(i) == ' ')
		            return true;
		    }
		    return false;
		}


	}
	class CancelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
		}
	}
}
