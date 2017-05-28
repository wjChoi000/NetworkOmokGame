package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Soket.ClientMsgProtocol;
import Soket.ClientSocket;
import Static.UtilLayout;
import Static.UtilSocketMode;
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
	private JButton btnStart;
	
	private JButton btnDraw;
	private JButton btnGiveup;
	
	//-------------------------------------------------
	private final int WAIT =1;
	private final int READY = 2;
	private final int START = 3;
	
	private int myState = WAIT;
	private int youState = WAIT;
	
	private ClientSocket socket;
	private ClientMsgProtocol protocol;
	BPGameMenu(MainFrame mf, BPCheckerboard checker){
		this.mf = mf;
		this.checker = checker;
		socket = mf.getClientSocket();
		protocol = mf.getClientMsgProtocol();
		
		init();
	}
	
	private User opposite = null;
	
	private void init(){
		setLayout(null);
		
		myInfo = new UserPanel("My information",mf.getUser(),false);
		youInfo = new UserPanel("Opposite info",opposite,false);
		myInfo.setBounds(m, m, width-2*m, userProfileH);
		youInfo.setBounds(m, m*2+userProfileH, width-2*m, userProfileH);
		
		add(myInfo);
		add(youInfo);
		btnPanelAdd();
		
		
		setSize(width,height);
	}
	
	private void updateUserPanel(){
		
		if(opposite == null)
			youInfo = new UserPanel("Opposite info",new User("","","",0,0,0),false);
		else
			youInfo = new UserPanel("Opposite info",opposite,false);
		youInfo.setBounds(m, m*2+userProfileH, width-2*m, userProfileH);
		add(youInfo);
		myInfo.updateUI();
		youInfo.updateUI();
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
		//btnDraw.addActionListener(new ActionListenerDraw());
		//btnGiveup.addActionListener(new ActionListenerGiveup());
		
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
	public void btnPanelReady(){
		btnStart.setText("Ready");
		myState = READY;
	}
	public void btnPanelWait(){
		btnStart.setText("Wait");
		myState = WAIT;
	}
	private int mod;
	
	class ActionListenerStartGame implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(youState == WAIT){
				if(myState == READY ){
					mod=UtilSocketMode.READY_GAME_MOD;
					btnPanelWait();
					youState = WAIT;
				}
				else if(myState == WAIT ){
					mod = UtilSocketMode.WAIT_GAME_MOD;
					myState = READY;
					btnPanelReady();
				}
				//send mseeage
				//protocol.setMod(mod);
				//int result = socket.sendMessage(protocol);
				
			}
			else if(youState == READY || myState == WAIT){
				//start game
				checker.startGame();
				btnPanelStartGame();
			}
			else{
				System.out.println("startGame btn error");
				return;
			}
		}
	}
	class ActionListenerExit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mod = UtilSocketMode.GO_OUT_ROOM_MOD ;
			protocol.setMod(mod);
			//int result = mf.getClientSocket().sendMessage(protocol);
			
			int result = 1;
			System.out.print("read : "+result);
			if(result != 0){
				mf.goWaitingRoom();	
			}
			else{
				System.out.println("startGame btn socket error");
			}
			return;
		}
	}
	class ActionListenerDraw implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			protocol.setMod(UtilSocketMode.DRAW_REQUEST_MOD);
			int result = mf.getClientSocket().sendMessage(protocol);
			System.out.print("read : "+result);
			//draw accept
			if(result == 1){
				JOptionPane.showMessageDialog(null, "draw" ,"accept", JOptionPane.INFORMATION_MESSAGE);
				checker.endGame();
				btnPanelEndGame();
			}
			else if(result == -1){
				JOptionPane.showMessageDialog(null, "rejected" ,"rejected", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else{
				System.out.println("draw btn socket error");
			}
			return;
		}
	}
	class ActionListenerGiveup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			protocol.setMod(UtilSocketMode.DROP_OUT_GAME_MOD);
			int result = mf.getClientSocket().sendMessage(protocol);
			System.out.print("read : "+result);
			if(result == 1){
				JOptionPane.showMessageDialog(null, "give up" ,"give up", JOptionPane.INFORMATION_MESSAGE);
				checker.endGame();
				btnPanelEndGame();
			}
			else{
				System.out.println("give up btn socket error");
			}
			return;
		}
	}
}
