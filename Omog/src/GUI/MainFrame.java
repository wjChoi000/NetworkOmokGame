package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainFrame extends JFrame{
	//get screen size
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	//panel
	private Login loginPanel;
	private Signup signPanel;
	private BorderWaitingRoom borderW;
	private BorderPlayGame borderP;
	
	MainFrame(){
		init();
	}
	
	private void init(){
		setTitle("OmogGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//layout
		setLayout(null);
	
		loginPanel = new Login(this);
		borderW  = new BorderWaitingRoom(this);
		
		//add Login Panel
		loginPanel.setLocation(d.width/2-150,d.height/2-150);
		add(loginPanel);
		
		setResizable(false);
		setSize(d.width,d.height);
		setVisible(true);
	}
	

	public void goWaitingRoom(){
		getContentPane().removeAll();
		getContentPane().add(borderW);
		revalidate();
		repaint();
	}
	
	static public void main(String[] args){
		MainFrame mp = new MainFrame();
		
	}
	public Dimension getDimension(){
		return this.d;
	}
}

