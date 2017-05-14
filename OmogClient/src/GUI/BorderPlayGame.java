package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class BorderPlayGame extends JPanel {
	private MainFrame mainFrame;
	private BorderLayout bl;
	private BPCheckerboard checkerPanel;
	private Chatting chatPanel;
	private BPGameMenu menuPanel;
	
	BorderPlayGame(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	private void init(){
		bl = new BorderLayout();
		
		setLayout(null);
		checkerPanel = new BPCheckerboard();
		checkerPanel.setLocation(0,0);
		add(checkerPanel);
		
		chatPanel = new Chatting(mainFrame);
		chatPanel.setLocation(BPCheckerboard.w,BPCheckerboard.h-MainFrame.chattingHeight);
		add(chatPanel);
		
		menuPanel = new BPGameMenu(mainFrame,checkerPanel);
		menuPanel.setLocation(BPCheckerboard.w,0);
		add(menuPanel);
		checkerPanel.setBPGameMenu(menuPanel);
		
		setSize(mainFrame.width,mainFrame.height);
	}
}
