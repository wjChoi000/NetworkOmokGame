package GUI;

import java.awt.*;
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
		bl = new BorderLayout(5,5);
		
		//init variable
		Dimension d = mainFrame.getDimension();
		chatPanel = new Chatting(d);
		waitPanel = new BWWaitingRoom(d);
		userPanel = new BWUserProfile(d,waitPanel);
		
		
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
		chatPanel.setLocation(d.width-400,d.height-600);
		userPanel.setLocation(d.width-400,0);
		
		add(waitPanel);
		add(chatPanel);
		add(userPanel);
		setSize(d.width,d.height);
	}
	
}
