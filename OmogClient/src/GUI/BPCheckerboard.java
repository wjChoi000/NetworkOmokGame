package GUI;

import java.awt.*;

import javax.swing.*;

import GUI.Login.LoginActionListener;
import GUI.Login.SignupActionListener;

public class BPCheckerboard extends JPanel {

	private int w = 1100;
	private int h = 1100;
	
	private int m =100;
	BPCheckerboard(){
		
		init();
		
	}
	private void init(){
		this.setLayout(null);
		
		

		this.setSize(w,h);
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//set background
		g.setColor(new Color(237,202,119));
		g.fillRect(0, 0, getWidth(), getHeight());
	
		//draw checker 
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.black);
		
		int bgeb = 50;
		//18*50 = 900
		for(int i =0; i<19; i++){
			g2.drawLine(m, m+i*bgeb, m+bgeb*18, m+i*bgeb);
			g2.drawLine(m+i*bgeb,m,m+i*bgeb,m+bgeb*18);
		}
		
	}
	
}
