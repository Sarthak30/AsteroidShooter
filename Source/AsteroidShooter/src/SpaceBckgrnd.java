import java.awt.*;
import java.io.File;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class SpaceBckgrnd {
	private BufferedImage SpaceBckgrnd;
	private SpaceBckgrnd mySpcBckgrnd;
	
	private static final String IMAGE_PATH = "./images/";
	
	private int start;
	public void init(){
		mySpcBckgrnd= new SpaceBckgrnd();
	}
	SpaceBckgrnd(){
		File imageFile=new File(IMAGE_PATH+"w4.jpg");
		try{
			SpaceBckgrnd = ImageIO.read(imageFile);
		}catch (Exception e) {
			SpaceBckgrnd =null;
			e.printStackTrace();
		}
	}
	public void paintSpcBckgrnd(Graphics g){
		g.drawImage(SpaceBckgrnd,start,  0, 1250, 900, null);
	}

}


