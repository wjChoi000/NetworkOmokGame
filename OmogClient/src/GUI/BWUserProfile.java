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
	
	private MainFrame mf;
	private BWWaitingRoom waitPanel;
	
	private int width = MainFrame.chattingWidth;
	private int height = MainFrame.userProfileHeight;
	private int m = MainFrame.m;
	private int labelWidth =70;
	private int inputWidth = 100;
	private int h=25;	
	private int btnMakeRoomH = 50;
	private int userProfileH = h*4+m*5;	
	private int usersListH = height -userProfileH - btnMakeRoomH;
	private int  currentH =0;
	
	
	BWUserProfile(MainFrame mf, BWWaitingRoom waitPanel){
		this.waitPanel = waitPanel;
		this.mf = mf;
		init();
	}
	
	
	private void init(){


		
		setLayout(null);
		
		//AddUserPanel();
		userPanel =new UserPanel("My Information",new User("wj@naver.com"," ","wj",1,1,1));
		userPanel.setBounds(m, m, width-2, userPanel.getHeight());
		add(userPanel);
		currentH = m+userPanel.getHeight();
		
		AddUsersList();
		
		btnMakeRoom = new JButton("MAKE ROOM");
		btnMakeRoom.setBounds(m, currentH, width-2*m, btnMakeRoomH-m);
		add(btnMakeRoom);
		
		setSize(width,height);
		
		btnMakeRoom.addActionListener(new ActionListenerMakeRoom());
		
	}
	void AddUsersList(){
		
		usersList = new JPanel();
		usersList.add(new JButton("hi"));
		usersList.setPreferredSize(new Dimension(width-4*m,usersListH-2*m));
		usersList.setBackground(Color.WHITE);
		scrollPane = new JScrollPane(usersList);
		scrollPane.setBounds(m,currentH,width-2*m,usersListH-2*m);
		currentH = currentH +usersListH;
		
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
	

		
		labelID.setBounds(m,m,labelWidth,h);
		inputID.setBounds(labelWidth+2*m,m, width-labelWidth-2*m,h);
		
		labelName.setBounds(m,h+2*m,labelWidth,h);
		inputName.setBounds(labelWidth+2*m,h+2*m,width-labelWidth-2*m,h);
		
		inputHistory.setBounds(m,h*2+3*m,width-2*m,h);
		
		add(labelID);
		add(labelName);
		add(inputID);
		add(inputName);
		add(inputHistory);
		
		userPanel.setSize(width,h*3+m*4);
		userPanel.setLocation(0,0);
		add(userPanel);
	
	}

	class ActionListenerMakeRoom implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			waitPanel.makeRoom("makeroom click");
			mf.goPlayGame();
		}
		
	}
}

