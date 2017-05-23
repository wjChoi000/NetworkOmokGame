package Static;

import javax.swing.*;

import GUI.BPCheckerboard;

public class UtilLayout {
	public static final int checkerWidth = BPCheckerboard.w;
	public static final int height=BPCheckerboard.h;
	public static final int chattingWidth = 200;
	public static final int chattingHeight = 300;
	public static final int userProfileHeight = height-chattingHeight;
	public static final int width = checkerWidth+chattingWidth;
	public static final int m =5;

	public static void addButton(JPanel panel, JButton btn,int x, int y,int w, int h){
		btn.setBounds(x,y,w,h);
		panel.add(btn);
	}
}
