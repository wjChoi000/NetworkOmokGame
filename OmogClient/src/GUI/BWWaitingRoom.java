package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;

public class BWWaitingRoom extends JPanel{

	private Dimension d;
	
	private int width;
	private int height;
	
	private JScrollPane scrollPane;
	private JPanel panel;
	
	private JButton button;
	private int btnNum =0;
	private int btnSize = 150;
	
	private int vMargin =20;
	private int hMargin =20;
	
	private int titleHeight=100;
	BWWaitingRoom(Dimension d){
		this.d = d;
		init();
	}
	private void init(){
		width = d.width-400;
		height = d.height;
		setLayout(null);
		
		JLabel title = new JLabel("title");
		title.setBounds(10,10,width-20, titleHeight);
		
		panel = new JPanel( new FlowLayout(FlowLayout.LEFT,0,hMargin));
		panel.setBackground(Color.WHITE);
				
		scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10,titleHeight+10,width-20,height-110);
		
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
		room.setPreferredSize(new Dimension(width-20,btnSize));
		panel.add(room);
		
		int newHeight = (btnSize+hMargin)*btnNum; 
		if( newHeight < height)
			panel.setPreferredSize(new Dimension(width,height-(titleHeight+10)));
		else
			panel.setPreferredSize(new Dimension(width,newHeight+hMargin*3));
		panel.updateUI();
	}
	
}
