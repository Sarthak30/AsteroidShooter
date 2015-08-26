
import java.awt.geom.Ellipse2D;
import java.awt.image.*;

import java.io.File;
import java.awt.*;
import java.util.Random;

import javax.imageio.ImageIO;

public class Meteor {

	private int imgW;
	private int imgH;
	private double imageScale;
	private int hPos, vPos;
	public  int speed;
	private int hSize,vSize;
	private BufferedImage meteor;
	private String[] imageURLs;
	public static boolean NITRO_ON = false;
	
	private final int NITRO_SPEED = -4;
	
	private static final String IMAGE_PATH = "./images/";
	
	private final int DEFAULT_SPEED = -1;
	private Random SizeRandom;

	public int getX(){
		return hPos;
	}
	public int getY(){
		return vPos;
	}
	public int getWidth(){
		return vSize;
	}
	public int getHeight(){
		return hSize;
	}
	public Meteor(int horizontal, int vertical){
		hPos=horizontal;
		vPos= vertical;

		if (NITRO_ON){
			speed=NITRO_SPEED;
		}
		
		else{
			speed = DEFAULT_SPEED; 
		}

		hSize=vSize=2;
		imageURLs = new String[] {
				IMAGE_PATH+"a2.png",
				  IMAGE_PATH+"a22.png",
				  IMAGE_PATH+"a3.png",
				  IMAGE_PATH+"a33.png",
				  IMAGE_PATH+"a333.png",
				  IMAGE_PATH+"a3333.png"
				   };
		int imageIndex = new Random().nextInt(imageURLs.length);
		File imageFile = new File(imageURLs[imageIndex]);
		try{
			
			SizeRandom=new Random();
			meteor = ImageIO.read(imageFile);
			imgW=meteor.getWidth();
			imgH=meteor.getHeight();
			double AverageSize=.7;
			imageScale= SizeRandom.nextDouble()*AverageSize;

			if (imageScale < 0) imageScale *= -1;

		}catch (Exception e) {
			imageURLs =null;
			e.printStackTrace();
		}
	}
	public void move(){
		if (NITRO_ON){
			vPos -= NITRO_SPEED;
		}
		
		else
			vPos -= DEFAULT_SPEED;
	}

	public int getHPos() {
		// TODO Auto-generated method stub
		return hPos;
	}
	public int getVPos(){
		return vPos;
	}
	public Rectangle getBounds(){
		int scaledW= (int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		return new Rectangle(hPos, vPos, scaledW, scaledH);
	}

	public Shape getAccurateBounds(){
		Ellipse2D.Double bounds = new Ellipse2D.Double();
		bounds.setFrame(getBounds());
		return bounds;
	}
	
	public void paintMeteor(Graphics g){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		g.drawImage(meteor,hPos,  vPos, scaledW, scaledH, null);

	}

}
