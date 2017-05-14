package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.User;

public class BWUserProfile extends JPanel{
	//user information
	private UserPanel userPanel;
	private JLabel labelID;
	private JLabel labelName;
	
	private JLabel inputID;
	private JLabel inputName;
	private JLabel inputHistory;
	
	private JButton btnMakeRoom;
	private JButton btnLogout;
	
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
	private int usersListH = height -userProfileH - btnMakeRoomH -h;
	private int  currentH =0;
	private ArrayList<User> userlist;
	
	BWUserProfile(MainFrame mf, BWWaitingRoom waitPanel){
		this.waitPanel = waitPanel;
		this.mf = mf;
		userlist = new ArrayList<User>();
		
		userlist.add(new User("a","","a",1,2,3));
		userlist.add(new User("b","","b",3,0,3));
		userlist.add(new User("a","","c",1,10,3));
		userlist.add(new User("a","","d",10,2,3));
		userlist.add(new User("a","","a",1,2,3));
		userlist.add(new User("b","","b",3,0,3));
		userlist.add(new User("a","","c",1,10,3));
		userlist.add(new User("a","","d",10,2,3));
		userlist.add(new User("a","","a",1,2,3));
		userlist.add(new User("b","","b",3,0,3));
		userlist.add(new User("a","","c",1,10,3));
		userlist.add(new User("a","","d",10,2,3));
		
		createAndShowGUI();
	}
	
	
	private void createAndShowGUI(){
		setLayout(null);
		
		//AddUserPanel();
		userPanel =new UserPanel("My Information",new User("wj@naver.com"," ","wj",1,1,1),true);
		userPanel.setBounds(m, m, width-2, userPanel.getHeight());
		add(userPanel);
		
		btnLogout = userPanel.getBtnLogout();
		currentH = m+userPanel.getH();
		btnLogout.addActionListener(new ActionListenerLogout());
		
		AddUsersList();
		
		btnMakeRoom = new JButton("MAKE ROOM");
		btnMakeRoom.setBounds(m, currentH, width-2*m, btnMakeRoomH-m);
		add(btnMakeRoom);
		
		setSize(width,height);
		
		btnMakeRoom.addActionListener(new ActionListenerMakeRoom());
		updateUserList();
		
	}
	
	private JLabel labelNick =new JLabel("Nickname",SwingConstants.CENTER);
	private JLabel labelRating = new JLabel("win/lose/draw",SwingConstants.CENTER);
	
	void AddUsersList(){		
		labelNick.setBackground(new Color(0xD99F00));
		labelRating.setBackground(new Color(0xD99F00));
		labelNick.setBounds(m, currentH, labelWidth, h);
		labelRating.setBounds(labelWidth+m, currentH, width-labelWidth-2*m, h);
		
		Border borderLeft = BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black);
		Border borderRight = BorderFactory.createMatteBorder(1, 0, 0, 1, Color.black);
		labelNick.setBorder(borderLeft);
		labelRating.setBorder(borderRight);
		
		labelNick.setOpaque(true);
		labelRating.setOpaque(true);
	
		add(labelNick);
		add(labelRating);
		currentH += h;

			
	/*	usersList = new JPanel();
		usersList.setLayout(null);
		
		
		scrollPanel = new JPanel();
		scrollPanel.setLayout(null);
		scrollPanel.setBackground(Color.white);
		scrollPanel.setSize(width-2*m,usersListH-2*m-h);
		
		updateUserList();
		
		scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setBounds(0,h,width-2*m,usersListH-2*m-h);
		usersList.add(scrollPane);
		
		usersList.setSize(width,usersListH);
		usersList.setBounds(m,currentH,width-2*m,usersListH-2*m);
		currentH += usersListH;
		add(usersList);
	*/	
		usersList = new JPanel();
		usersList.setLayout(null);	
		usersList.setSize(new Dimension(width-2*m,usersListH-2*m));
		usersList.setBackground(Color.WHITE);
		scrollPane = new JScrollPane(usersList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(m,currentH,width-2*m,usersListH-2*m);
		currentH = currentH +usersListH;
		add(scrollPane);
	}
	
	void updateUserList(){
		usersList.removeAll();
		int cHight=0;
		Border borderLeft = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black);
		Border borderRight = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
	
		for(User item: userlist ){
			JLabel nickName = new JLabel(item.getName());
			JLabel rating = new JLabel(item.toStringRating());
			nickName.setBorder(borderLeft);
			rating.setBorder(borderRight);
			nickName.setBounds(0,cHight,labelWidth,h);
			rating.setBounds(labelWidth,cHight,width-labelWidth,h);
			cHight +=h;
			usersList.add(nickName);
			usersList.add(rating);
		}
		
		if(cHight <= usersListH-2*m-h){
			usersList.setSize(width-2*m,usersListH-2*m-h);
		
		}else{
			System.out.println((usersListH-2*m-h)+", oversize "+cHight);
			usersList.setPreferredSize(new Dimension(width-2*m,cHight));
		}
		usersList.updateUI();
	}
/*	void AddUserPanel(){
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
*/
	class ActionListenerMakeRoom implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			waitPanel.updatRoomList();
			mf.goPlayGame();
		}
		
	}
	
	class ActionListenerLogout implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mf.goLogin();
		}
		
	}
}

