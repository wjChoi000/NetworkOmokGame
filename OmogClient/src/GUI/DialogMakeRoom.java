package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import GUI.DialogConfirmClose.NoActionListener;
import GUI.DialogConfirmClose.YesActionListener;

public class DialogMakeRoom extends JDialog {
	
	private MainFrame mf;
	
	private int width;
	private int height;
	
	private int Margin = 20;
	private int btnW = 80;
	private int btnH= 25;
	private int textW = 200;
	

	private JLabel labelMsg;
	private JButton btnMake;
	private JTextField textTitle;
	
	private int currentH=0;
	private int currentW=0;
	DialogMakeRoom(MainFrame mf){
		this.mf = mf;
		width = Margin*3+btnW + textW;
		init();
	}
	private void init(){
		//set layout
		setLayout(null);
		
		currentH+=Margin;
		labelMsg = new JLabel("Enter the title of room.");
		addLabel(labelMsg, Margin, currentH,width -Margin*2,btnH);
		
		currentH += btnH+Margin;
		textTitle = new JTextField();
		addText(textTitle,Margin,currentH, textW,btnH);
		
		btnMake = new JButton("Make");
		addButton(btnMake, Margin*2+textW,currentH, btnW,btnH);
		currentH += btnH+Margin;
		
		this.setSize(width, currentH);
		this.setModal(true);
		
		btnMake.addActionListener(new MakeRoomActionListener());
	}
	
	public void addButton(JButton btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		add(btn);
	}
	
	public void addLabel(JLabel btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		add(btn);
	}
	public void addText(JTextField btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		add(btn);
	}
	
	class MakeRoomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
	
			setVisible(false);
			mf.goPlayGame();
		}
	}
}

