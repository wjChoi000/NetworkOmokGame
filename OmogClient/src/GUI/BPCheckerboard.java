package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.*;

import GUI.Login.LoginActionListener;
import GUI.Login.SignupActionListener;
import model.StoneArray;

public class BPCheckerboard extends JPanel {
	
	public static final int m =100;
	public static final int bgeb = 30;
	public static final int w = m*2+bgeb*18;
	public static final int h = w;
	
	private int ovalSize = 10;
	
	private String currentPath = this.getClass().getResource("").getPath();
	private String blackStonePath="image/blackStone.png";
	private String whiteStonePath="image/whiteStone.png";
	private ImageIcon blackStoneIcon = new ImageIcon(currentPath+blackStonePath);
	private ImageIcon whiteStoneIcon = new ImageIcon(currentPath+whiteStonePath);
	private Image blackStoneImage = blackStoneIcon.getImage();
	private Image whiteStoneImage = whiteStoneIcon.getImage();
	
	private boolean startGame = false;
	private int currentColor = StoneArray.BLACK_STONE;
	private StoneArray stone;
	private Vector<Point> whitePV = new Vector<Point>();
	private Vector<Point> blackPV = new Vector<Point>();
	
	BPCheckerboard(){
		init();
		
	}

	private void init(){
		this.setLayout(null);
		stone = new StoneArray();
		
		startGame();
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e){
				Point p =putStonePoint(e.getPoint());
				if(p != null){
					if(currentColor == StoneArray.WHITE_STONE)
						whitePV.add(p);
					else
						blackPV.add(p);
					
					//change color
					if(currentColor ==StoneArray.BLACK_STONE)
						currentColor = StoneArray.WHITE_STONE;
					else
						currentColor = StoneArray.BLACK_STONE;
					
					repaint();
					
					int flag = stone.endGame();
					if(flag !=0){
						JOptionPane.showMessageDialog(null, "end Game." ,"end", JOptionPane.INFORMATION_MESSAGE);
						restart();
					}
				}
			}
		});
		this.setSize(w,h);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//set background
		g.setColor(new Color(237,202,119));
		g.fillRect(0, 0, getWidth(), getHeight());
	
		//draw checker 
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.black);
		
		g2.drawLine(m, m+0*bgeb, m+bgeb*18, m+0*bgeb);
		g2.drawLine(m, m+18*bgeb, m+bgeb*18, m+18*bgeb);
		g2.drawLine(m+0*bgeb,m,m+0*bgeb,m+bgeb*18);
		g2.drawLine(m+18*bgeb,m,m+18*bgeb,m+bgeb*18);

        g2.setStroke(new BasicStroke(2));
		
		for(int i =0; i<19; i++){
			g2.drawLine(m, m+i*bgeb, m+bgeb*18, m+i*bgeb);
			g2.drawLine(m+i*bgeb,m,m+i*bgeb,m+bgeb*18);
		}
		
		g.setColor(Color.black);

		for(int i =0; i<3; i++)
			for(int j =0; j <3; j++)
				g.fillOval(m+bgeb*(3+i*6)-ovalSize/2,m+bgeb*(3+j*6)-ovalSize/2,ovalSize,ovalSize);

		for(int i=0; i < whitePV.size();i++){
			Point p = whitePV.elementAt(i);
			g.drawImage(whiteStoneImage,(int)p.getX() ,(int)p.getY() , bgeb,bgeb,this);
		}
		for(int i =0; i< blackPV.size(); i++){
			Point p = blackPV.elementAt(i);
			g.drawImage(blackStoneImage, (int)p.getX(), (int)p.getY(), bgeb,bgeb,this);
		}
	}
	
	private Point putStonePoint(Point p){
		int start = m-bgeb/2;
		int x = (int)p.getX();
		int y =(int)p.getY();
		
		if(x >=start && x<=start+bgeb*19 && y>=start && y<=start+bgeb*19){
			x = (x-start)/bgeb;
			y = (y-start)/bgeb;
			
			if(stone.addStone(x, y, currentColor) == 0)
				return null;
			
			Point result;	
			result = new Point(start+bgeb*x,start+bgeb*y);
			System.out.println(x+", " +y);
			System.out.println(result.toString());
			return result;
		}
		return null;
		
	}
	
	public void restart(){
		stone = new StoneArray();
		whitePV = new Vector<Point>();
		blackPV = new Vector<Point>();
		repaint();
	}
	public void startGame(){startGame=true;}
	public void endGame(){startGame=false;}
	
}
