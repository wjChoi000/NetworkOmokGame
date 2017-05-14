package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

public class UserPanel extends JPanel{
	private int m = MainFrame.m;

	private int labelWidth =70;
	private int inputWidth = 100;
	private int h=25;	
	private int btnMakeRoomH = 50;
	private int userProfileH = h*4+m*5;
	private int btnPanelH = btnMakeRoomH+2*m;
	private int width = MainFrame.chattingWidth;
	private int height = userProfileH;

	private String title;
	private User user;

	private JLabel labelTitle;
	private JLabel labelID;
	private JLabel labelName;

	private JLabel inputID;
	private JLabel inputName;
	private JLabel inputHistory;

	private JButton btnLogout;
	private boolean logoutFlag;

	UserPanel(String title, User user, boolean logoutFlag){
		this.title = title;
		this.user = user;
		this.logoutFlag = logoutFlag;
		
		createAndShowGUI();
	}
	private void createAndShowGUI(){

		setLayout(null);
		labelTitle = new JLabel(title);
		if(logoutFlag){
			int logoutWidth =80;
			labelTitle.setBounds(m,m,width-2*m-logoutWidth,h);
			btnLogout = new JButton("Logout");
			btnLogout.setBounds(width-2*m-logoutWidth, m,logoutWidth,h);
			add(btnLogout);
		}else{
			labelTitle.setBounds(m,m,width-2*m,h);
		}
		inputID = new JLabel(user.getId());
		inputName = new JLabel(user.getName());
		inputHistory = new JLabel(user.getWin()+" win "+user.getLose()+" lose "+user.getDraw()+" draw");

		labelID = new JLabel("ID");
		labelName = new JLabel("Name");

		
		labelID.setBounds(m,h+2*m,labelWidth,h);
		inputID.setBounds(labelWidth+2*m,h+2*m, width-labelWidth-2*m,h);

		labelName.setBounds(m,h*2+3*m,labelWidth,h);
		inputName.setBounds(labelWidth+2*m,h*2+3*m,width-labelWidth-2*m,h);

		inputHistory.setBounds(m,h*3+4*m,width-2*m,h);

		add(labelTitle);
		add(labelID);
		add(labelName);
		add(inputID);
		add(inputName);
		add(inputHistory);

		setSize(width,h*4+m*5);		
	}
	
	public int getH(){return height;}
	public JButton getBtnLogout(){return btnLogout;}
}
