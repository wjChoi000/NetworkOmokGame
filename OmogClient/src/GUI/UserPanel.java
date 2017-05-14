package GUI;

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



	UserPanel(String title, User user){
		this.title = title;
		this.user = user;

		setLayout(null);

		labelTitle = new JLabel(title);
		inputID = new JLabel(user.getId());
		inputName = new JLabel(user.getName());
		inputHistory = new JLabel(user.getWin()+" win "+user.getLose()+" lose "+user.getDraw()+" draw");

		labelID = new JLabel("ID");
		labelName = new JLabel("Name");

		labelTitle.setBounds(m,m,width-2*m,h);

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
	
	public int getHeight(){return height;}
}
