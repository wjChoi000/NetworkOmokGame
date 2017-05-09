package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainFrame extends JFrame{
	//get screen size
	private Dimension d;
	
	//panel
	private Login loginPanel;
	private Signup signupPanel;
	private BorderWaitingRoom borderW;
	private BorderPlayGame borderP;
	
	private BPCheckerboard checker;
	MainFrame(){
		init();
	}
	
	private void init(){
		setTitle("OmogGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//init variable
		d= new Dimension(GetScreenWorkingWidth(),GetScreenWorkingHeight());
		loginPanel = new Login(this);
		borderW  = new BorderWaitingRoom(this);
		
		
		//layout
		setLayout(null);
	
		//add Login Panel
		/*loginPanel.setLocation(d.width/2-150,d.height/2-150);
		add(loginPanel);
		*/
		
		checker = new BPCheckerboard();
		checker.setLocation(10, 10);
		add(checker);
		
		/*
		 * Chatting chatting = new Chatting(d);
		 chatting.setLocation(d.width/2-150,d.height/2-150);
		add(chatting);
		*/
		
		pack();
		setResizable(false);
		setSize(d.width,d.height);
		setVisible(true);
	}
	
	/*
	 *  move page 
	 */
	public void goWaitingRoom(){
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
		getContentPane().removeAll();
		getContentPane().add(loginPanel);
		revalidate();
		repaint();
	}
	public void goPlayGame(){
		getContentPane().removeAll();
		getContentPane().add(borderP);
		revalidate();
		repaint();
	}
	
	/*
	 * get, set method
	 */
	public Dimension getDimension(){
		return this.d;
	}
	
	
	/*
	 * start application
	 */
	static public void main(String[] args){
		MainFrame mp = new MainFrame();
		
	}
	public static int GetScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}
}

