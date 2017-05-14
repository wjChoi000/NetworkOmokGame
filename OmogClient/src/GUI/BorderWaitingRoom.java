package GUI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class BorderWaitingRoom extends JPanel {
	private MainFrame mainFrame;
	private BorderLayout bl;
	private Chatting chatPanel;
	private BWWaitingRoom waitPanel;
	private BWUserProfile userPanel;
	
	BorderWaitingRoom(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	private void init(){
		
		//init variable
		chatPanel = new Chatting(mainFrame);
		waitPanel = new BWWaitingRoom(mainFrame);
		userPanel = new BWUserProfile(mainFrame,waitPanel);
		
		
		//Layout
		/*setLayout(bl);
		add(waitPanel, BorderLayout.CENTER);
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BorderLayout(5,5));
		
		rightPane.add(userPanel,BorderLayout.CENTER);
		rightPane.add(chatPanel, BorderLayout.SOUTH);
		add(rightPane, BorderLayout.EAST);
		 */

		setLayout(null);
		
		waitPanel.setLocation(0,0);
		userPanel.setLocation(mainFrame.checkerWidth,0);
		chatPanel.setLocation(mainFrame.checkerWidth,mainFrame.userProfileHeight);
		
		add(waitPanel);
		add(chatPanel);
		add(userPanel);
		setSize(mainFrame.width,mainFrame.height);
		
	}
}
