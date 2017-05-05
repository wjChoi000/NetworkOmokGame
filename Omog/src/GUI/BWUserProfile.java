package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.User;

public class BWUserProfile extends JPanel{
	private Dimension d;
	private int width;
	private int height;
	
	//user information
	private JPanel userPanel;
	private JLabel labelID;
	private JLabel labelName;
	
	private JLabel inputID;
	private JLabel inputName;
	private JLabel inputHistory;
	
	private JButton btnMakeRoom;
	
	private JPanel usersList;
	private JScrollPane scrollPane;
	
	private User user;
	
	private BWWaitingRoom waitPanel;
	BWUserProfile(Dimension d, BWWaitingRoom waitPanel){
		this.waitPanel = waitPanel;
		this.d = d;
		init();
	}
	private void init(){

		width = 400;
		height = d.height-600;
		
		setLayout(null);
		
		AddUserPanel();
		AddUsersList();
		
		btnMakeRoom = new JButton("MAKE ROOM");
		btnMakeRoom.setBounds(200, height-100, 180, 80);
		add(btnMakeRoom);
		
		setSize(width,height);
		
		btnMakeRoom.addActionListener(new ActionListenerMakeRoom());
	}
	
	void AddUsersList(){
		
		usersList = new JPanel();
		usersList.add(new JButton("hi"));
		usersList.setPreferredSize(new Dimension(width-40,height-220));
		usersList.setBackground(Color.WHITE);
		scrollPane = new JScrollPane(usersList);
		scrollPane.setBounds(10,130,width-20,height-250);

		add(scrollPane);
	}
	
	void AddUserPanel(){

		//user information
		userPanel = new JPanel();
		userPanel.setLayout(null);
		user = new User("wj@naver.com"," ","wj",1,1,1);
		
		inputID = new JLabel(user.getId());
		inputName = new JLabel(user.getName());
		inputHistory = new JLabel(user.getWin()+" win "+user.getLose()+" lose "+user.getDraw()+" draw");
		
		labelID = new JLabel("ID");
		labelName = new JLabel("Name");
	
		labelID.setBounds(10,10,80,25);
		inputID.setBounds(100,10, width-100,25);
		
		labelName.setBounds(10,50,80,25);
		inputName.setBounds(100,50,width-100,25);
		
		inputHistory.setBounds(10,90,width-20,25);
		
		add(labelID);
		add(labelName);
		add(inputID);
		add(inputName);
		add(inputHistory);
		
		userPanel.setSize(width,100);
		userPanel.setLocation(0,0);
		add(userPanel);
	
	}

	class ActionListenerMakeRoom implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			waitPanel.makeRoom("makeroom click");
		}
		
	}
}

