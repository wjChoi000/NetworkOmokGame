package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Static.UtilLayout;

public class DialogConfirmClose extends JDialog {
	
	private MainFrame mf;
	
	private int width;
	private int height;
	
	private int Margin = 20;
	private int btnWidth = 100;
	private int btnHeight = 50;


	private JLabel labelMsg;
	private JButton btnYes;
	private JButton btnNo;
	
	private int currentH=0;
	private int currentW=0;
	DialogConfirmClose(MainFrame mf){
		this.mf = mf;
		width = Margin*3+btnWidth*2;
		init();
	}
	private void init(){
		//set layout
		setLayout(null);
		
		currentH+=Margin;
		labelMsg = new JLabel("Do you realy want to close?");
		addLabel(labelMsg, Margin, currentH,btnWidth*2+Margin,btnHeight/2);
		currentH+= Margin+btnHeight/2;
		
		currentW +=Margin;
		btnYes = new JButton("YES");
		addButton(btnYes, currentW , currentH, btnWidth, btnHeight);
		currentW += btnWidth+Margin;
		btnNo = new JButton("NO");
		addButton(btnNo,currentW,currentH,btnWidth,btnHeight);
		currentH+= Margin+btnHeight;
		
		this.setSize(width, currentH);
		this.setModal(true);
		
		btnYes.addActionListener(new YesActionListener() );
		btnNo.addActionListener(new NoActionListener());
	}
	
	public void addButton(JButton btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		add(btn);
	}
	
	public void addLabel(JLabel btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		add(btn);
	}
	
	class YesActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
			System.exit(0);
		}
	}
	class NoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
		}
	}
	
}
