package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class BorderPlayGame extends JPanel {
	private MainFrame mainFrame;
	private BorderLayout bl;
	private BPCheckerboard chekerPanel;
	private Chatting chatPanel;
	private BPGameMenu menuPanel;
	
	BorderPlayGame(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	private void init(){
		bl = new BorderLayout();
		
		setLayout(bl);
		add(new JButton("checker"), BorderLayout.CENTER);
		
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPane.setContinuousLayout(true);
		rightPane.setTopComponent(new JButton("user profile"));
		rightPane.setBottomComponent(new JButton("chatting"));
		
		add(rightPane, BorderLayout.EAST);

		Dimension d = mainFrame.getDimension();
		setSize(d.width,d.height);
	}
}
