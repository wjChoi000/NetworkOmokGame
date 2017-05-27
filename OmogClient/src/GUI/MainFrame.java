package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Soket.ClientMsgProtocol;
import Soket.ClientSocket;
import model.User;


public class MainFrame extends JFrame{
	//get screen size
	private Dimension d;
	
	//panel
	private Login loginPanel;
	private Signup signupPanel;
	private BorderWaitingRoom borderW;
	private BorderPlayGame borderP;
	private BPCheckerboard checker;
	private DialogConfirmClose dialogClose;
	
	private User user = new User();
	
	private ClientSocket clientSocket;
	private ClientMsgProtocol clientMsgProtocol = new ClientMsgProtocol();
	MainFrame(){
		init();
	}
	
	
	public static final int checkerWidth = BPCheckerboard.w;
	public static final int height=BPCheckerboard.h;
	public static final int chattingWidth = 300;
	public static final int chattingHeight = 300;
	public static final int userProfileHeight = height-chattingHeight;
	public static final int width = checkerWidth+chattingWidth;
	public static final int m =5;
	
	private void init(){
		setTitle("OmogGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//init variable
		d = new Dimension(width, height);
		loginPanel = new Login(this);
		loginPanel.setLocation(d.width/2-150,d.height/2-150);
	
		//layout
		setLayout(null);
		add(loginPanel);
		
		pack();
		setResizable(false);
		setSize(width+5,height+70);
		setVisible(true);
		
		//Socket Programing
		clientSocket = new ClientSocket();
		
		//close event
		dialogClose = new DialogConfirmClose(this);
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				clientSocket.closeSocket();
				//dialogClose.setVisible(true);
			}
		} );
	}
	
	/*
	 *  move page 
	 */
	public void goWaitingRoom(){
		borderW  = new BorderWaitingRoom(this);
		borderW.setLocation(0,0);
		
		getContentPane().removeAll();
		getContentPane().add(borderW);
		revalidate();
		repaint();
	}
	public void goSignup(){
		getContentPane().removeAll();
		getContentPane().add(signupPanel);
		revalidate();
		repaint();
	}
	public void goLogin(){
		loginPanel = new Login(this);
		loginPanel.setLocation(d.width/2-150,d.height/2-150);

		getContentPane().removeAll();
		getContentPane().add(loginPanel);
		revalidate();
		repaint();
	}
	public void goPlayGame(){
		borderP = new BorderPlayGame(this);
		borderP.setLocation(0,0);

		getContentPane().removeAll();
		getContentPane().add(borderP);
		revalidate();
		repaint();
	}
	
	/*
	 * get, set method
	 */
	/*
	public Dimension getDimension(){
		return this.d;
	}
	public static int GetScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}
*/	
	
	/*
	 * start application
	 */
	static public void main(String[] args){
		MainFrame mp = new MainFrame();
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ClientSocket getClientSocket() {
		return clientSocket;
	}

	public ClientMsgProtocol getClientMsgProtocol() {
		return clientMsgProtocol;
	}

	public void setClientMsgProtocol(ClientMsgProtocol clientMsgProtocol) {
		this.clientMsgProtocol = clientMsgProtocol;
	}
	
}

