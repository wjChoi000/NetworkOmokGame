package OmogGame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	//get screen size
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private Login loginPanel;
	
	MainFrame(){
		init();
		
	}
	
	private void init(){
		setTitle("OmogGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//layout
		setLayout(null);
		
		loginPanel = new Login();
		loginPanel.setLocation(d.width/2-150,d.height/2-150);
		add(loginPanel);
		
		setSize(d.width,d.height);
		setVisible(true);
	}
	
	static public void main(String[] args){
		MainFrame mp = new MainFrame();
	}
	
	
}
