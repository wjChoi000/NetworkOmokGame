package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	BWWaitingRoom(MainFrame mf){
		this.mf = mf;
		init();
	}
	private void init(){
		setLayout(null);
		
		JLabel title = new JLabel("title");
		title.setBounds(m,m,width-m*2, titleHeight);
		
		panel = new JPanel( new FlowLayout(FlowLayout.LEFT,0,m));
		panel.setBackground(Color.WHITE);
				
		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(m,titleHeight+2*m,width-2*m,height-2*m-titleHeight);
		
		add(title);
		add(scrollPane);
		
		setSize(width, height);
		

		for(int i =0; i< 5; i++){
			makeRoom(i+" room,"+width+","+height);

		}
	}
	
	public void makeRoom(String name){
		btnNum++;
		
		JButton room = new JButton( name);
		room.addActionListener(new ActionListenerEnterRoom());
		room.setPreferredSize(new Dimension(width-4*m,btnSize));
		panel.add(room);
		
		int newHeight = (2*m)*btnNum; 
		if( newHeight < height)
			panel.setPreferredSize(new Dimension(width,height-(titleHeight+m)));
		else
			panel.setPreferredSize(new Dimension(width,newHeight+m*3));
		panel.updateUI();
	}
	
	class ActionListenerEnterRoom implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mf.goPlayGame();
		}
	}
	
}
