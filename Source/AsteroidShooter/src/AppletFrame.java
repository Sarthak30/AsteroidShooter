import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class AppletFrame extends JFrame {
	
	public static void main (String[] args){
		
		JFrame myFrame = new JFrame("Armageddon");
		
		myFrame.setSize(1440, 900);
	    myFrame.setBackground(Color.black);
		SpaceApp app1 = new SpaceApp(myFrame, 1400,900);
		
	    myFrame.add(app1,BorderLayout.CENTER);
		myFrame.setVisible(true);
		myFrame.setCursor(getCrossHairImgCursor());
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit (0);
			};
		});
	}
	
	private static Cursor getCrossHairImgCursor(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Image cursorImg = tk.createImage("./images/crosshairs3.png");
	
		Cursor custom = tk.createCustomCursor(cursorImg, new Point(0, 0), "crossHairs");
		return custom;
	
	}

	
}