package OmogGame;

import java.awt.*;
import javax.swing.*;

public class BorderWaitingRoom extends JPanel {
	private BorderLayout bl;
	
	BorderWaitingRoom(){
		init();
	}
	private void init(){
		bl = new BorderLayout();
		setLayout(bl);
	}
	
}
