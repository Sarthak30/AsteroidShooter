import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy {

	private int imgW;
	private int imgH;
	private double imageScale;
	private int hPos, vPos;
	private int speed;
	private int hSize,vSize;
	private BufferedImage Enemy;
	private String[] EimageURLs;
	public static boolean NITRO_ON = false;
	
	private final int NITRO_SPEED = -4;
	
	private final int DEFAULT_SPEED = -1;
	
	private static final String IMAGE_PATH = "./images/";

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
	public Enemy(int horizontal, int vertical){
		hPos=horizontal;
		vPos= vertical;

		if (NITRO_ON){
			speed=NITRO_SPEED;
		}
		
		else{
			speed = DEFAULT_SPEED; 
		}

		hSize=vSize=2000;
		EimageURLs = new String[] {
				  IMAGE_PATH+"e.png"
		};
		int imageIndex = new Random().nextInt(EimageURLs.length);
		File imageFile = new File(EimageURLs[imageIndex]);
		try{
			Enemy = ImageIO.read(imageFile);
			imgW=Enemy.getWidth();
			imgH=Enemy.getHeight();
			imageScale=.1;

		}catch (Exception e) {
			EimageURLs =null;
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
	
	public void paintEnemy(Graphics g){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		g.drawImage(Enemy,hPos,  vPos, scaledW, scaledH, null);

	}

}