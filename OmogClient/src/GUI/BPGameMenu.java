package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

public class BPGameMenu extends JPanel {
	private int width = MainFrame.chattingWidth;
	private int height = MainFrame.userProfileHeight;
	private int m = MainFrame.m;

	private int labelWidth =70;
	private int inputWidth = 100;
	private int h=25;	
	private int btnMakeRoomH = 50;
	private int userProfileH = h*4+m*5;
	private int btnPanelH = btnMakeRoomH+2*m;

	
	private MainFrame mf;
	private BPCheckerboard checker;
	
	private JPanel myInfo;
	private JPanel youInfo;
	
	
	private JPanel btnPanel;
	private JButton btnExit;
	private	JButton btnStart;
	
	private JButton btnDraw;
	private JButton btnGiveup;
	
		
	BPGameMenu(MainFrame mf, BPCheckerboard checker){
		this.mf = mf;
		this.checker = checker;
		
		init();
	}
	private void init(){
		setLayout(null);
		
		myInfo = new UserPanel("My information",new User("swelo","","wj",2,2,2));
		youInfo = new UserPanel("Opposite info",new User("op","","op",10,0,0));
		myInfo.setBounds(m, m, width-2*m, userProfileH);
		youInfo.setBounds(m, m*2+userProfileH, width-2*m, userProfileH);
		
		add(myInfo);
		add(youInfo);
		btnPanelAdd();
		
		
		setSize(width,height);
	}
	
	private void btnPanelAdd(){
		btnPanel = new JPanel();
		btnPanel.setLayout(null);
		btnExit = new JButton("Exit");
		btnStart = new JButton("Ready");
		btnStart.setBounds(m, m, (width-m*3)/2, btnMakeRoomH);
		btnExit.setBounds(2*m+(width-m*3)/2, m, (width-m*3)/2, btnMakeRoomH);
		
		btnDraw = new JButton("Draw Request");
		btnGiveup = new JButton("Give up");
		btnDraw.setBounds(m, m, (width-m*3)/2, btnMakeRoomH);
		btnGiveup.setBounds(2*m+(width-m*3)/2, m, (width-m*3)/2, btnMakeRoomH);
		btnPanel.setSize(width,btnPanelH);
		
		
		btnStart.addActionListener(new ActionListenerStartGame());
		btnExit.addActionListener(new ActionListenerExit());
		btnDraw.addActionListener(new ActionListenerDraw());
		btnGiveup.addActionListener(new ActionListenerGiveup());
		
		btnPanel.setBounds(0, m*3+userProfileH*2, width, height);
		btnPanelEndGame();
		add(btnPanel);
	}
	public void btnPanelStartGame(){
		btnPanel.removeAll();
		btnPanel.add(btnGiveup);
		btnPanel.add(btnDraw);
		btnPanel.revalidate();
		btnPanel.repaint();
	}
	public void btnPanelEndGame(){
		btnPanel.removeAll();
		btnPanel.add(btnStart);
		btnPanel.add(btnExit);
		btnPanel.revalidate();
		btnPanel.repaint();
	}
	
		
	class ActionListenerStartGame implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checker.startGame();
			btnPanelStartGame();
		}
	}
	class ActionListenerExit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mf.goWaitingRoom();
		}
	}
	class ActionListenerDraw implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checker.endGame();
			btnPanelEndGame();
		}
	}
	class ActionListenerGiveup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checker.endGame();
			btnPanelEndGame();
		}
	}
}
