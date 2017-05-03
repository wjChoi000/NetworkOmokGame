package GUI;

import java.awt.*;
import javax.swing.*;

public class BorderWaitingRoom extends JPanel {
	private MainFrame mainFrame;
	private BorderLayout bl;
	private Chatting chatPanel;
	private WaitingRoom waitPanel;
	private UserProfile userPanel;
	
	BorderWaitingRoom(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	private void init(){
		bl = new BorderLayout();
		
		setLayout(bl);
		add(new JButton("waiting room"), BorderLayout.CENTER);
		
		JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPane.setContinuousLayout(true);
		rightPane.setTopComponent(new JButton("user profile"));
		rightPane.setBottomComponent(new JButton("chatting"));
		
		add(rightPane, BorderLayout.EAST);

		Dimension d = mainFrame.getDimension();
		setSize(d.width,d.height);
	}
	
}
