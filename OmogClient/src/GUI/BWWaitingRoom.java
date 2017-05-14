package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;

public class BWWaitingRoom extends JPanel{

	private MainFrame mf;
	private Dimension d;
	
	private int width = MainFrame.checkerWidth;
	private int height = MainFrame.checkerWidth;
	private int m = MainFrame.m;
	
	private JScrollPane scrollPane;
	private JPanel panel;
	
	private JButton button;
	private int btnNum =0;
	private int btnSize = 100;
	
	private int vMargin =20;
	private int hMargin =20;
	
	private int titleHeight=25;
	private ArrayList<String> roomList;
	BWWaitingRoom(MainFrame mf){
		this.mf = mf;
		roomList = new ArrayList<String>();
		for(int i=0; i<15; i++){
			roomList.add("room "+i);
		}
		init();
		
		updatRoomList();
	}
	private void init(){
		setLayout(null);
		
		JLabel title = new JLabel("title");
		title.setBounds(m,m,width-m*2, titleHeight);
		
		panel = new JPanel( new FlowLayout(FlowLayout.LEFT,0,0));
		panel.setBackground(Color.WHITE);
				
		scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(m,titleHeight+m,width-2*m,height-2*m-titleHeight);
		
		add(title);
		add(scrollPane);
		
		setSize(width, height);
		
	}
	
	public void updatRoomList(){
		int ch =0;
		for(String item: roomList){
			JButton room = new JButton( item);
			room.addActionListener(new ActionListenerEnterRoom());
			room.setPreferredSize(new Dimension(width-2*m,btnSize));
			panel.add(room);
			ch += btnSize;
		}
				 
		if( ch < (height-2*m-titleHeight))
			panel.setPreferredSize(new Dimension(width,height-3*m-titleHeight));
		else
			panel.setPreferredSize(new Dimension(width,ch));
		
		updateUI();
	}
	
	class ActionListenerEnterRoom implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mf.goPlayGame();
		}
	}
	
}
