import java.awt.*;
import java.io.File;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class SpaceShuttle {
	private int horizontalPosition;
	private int verticalPosition;
	private int speed;
	private int horizShiftDist;
	private BufferedImage shuttle;
	private BufferedImage flameImage;
	private BufferedImage flameImage2;
	private boolean nitroMode = false;
	private boolean ultimatenitroMode=false;
	private int nitroW, nitroH;
	private final int DEFAULT_SPEED = 10;
	private final int NITRO_SPEED = 15;
	
	private static final String IMAGE_PATH = "./images/";
	
	private int imgW;
	private int imgH;
	private double imageScale;
	SpaceShuttle(){
		speed=DEFAULT_SPEED;
		this.horizontalPosition =670;
		horizShiftDist = DEFAULT_SPEED;
		verticalPosition=670;
		File imageFile=new File(IMAGE_PATH+"r4.png");
		File flameFile = new File(IMAGE_PATH+"nF.png");
		
		
		try{
			shuttle = ImageIO.read(imageFile);
			imgW=shuttle.getWidth();
			imgH=shuttle.getHeight();
			imageScale=0.15;
			
			flameImage = ImageIO.read(flameFile);
			nitroW = (int) (flameImage.getWidth() * .5);
			nitroH = (int) (flameImage.getHeight() * .5);

		}
		catch (Exception e) {
			shuttle =null;
			flameImage = null;
			flameImage2=null;
			e.printStackTrace();
		}
	}
	public int getX(){
		return horizontalPosition;
	}
	public int getY(){
		return verticalPosition;
	}
	public int getWidth(){
		return imgW;
	}
	public int getHeight(){return imgH;
	}
	public void moveForward(){
		verticalPosition-=speed;
	}
	public void moveBack(){
		verticalPosition+=speed;
	}
	public void moveLeft(){
		horizontalPosition -= horizShiftDist;
	}
	public void turnOnNitro(){
		this.nitroMode = true;
		this.speed = NITRO_SPEED;
		horizShiftDist = NITRO_SPEED;
	}
	public void turnOffNitro(){
		this.nitroMode = false;
		this.speed = DEFAULT_SPEED;
		horizShiftDist = DEFAULT_SPEED;
	}

	public void moveRight(){
		horizontalPosition += horizShiftDist;
	}

	public void setHorizontalPosition(int newPos){
		horizontalPosition = newPos;
	}
	
	public Rectangle getBounds(){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		int contractPixels = 15;
		return new Rectangle(horizontalPosition+contractPixels, verticalPosition, scaledW-2*contractPixels, scaledH);
	}

	double image2Scale=.25;
	public void paintShuttle(Graphics g){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		g.drawImage(shuttle,horizontalPosition,  verticalPosition, scaledW, scaledH, null);
		
		if (nitroMode){
			g.drawImage(flameImage, horizontalPosition+21, verticalPosition + scaledH - 1, nitroW, nitroH, null);
		}
		

	}
	public Shape getBoundingPolygon() {
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		
		Polygon p = new Polygon();
		p.addPoint(horizontalPosition, verticalPosition + scaledH-10);
		p.addPoint(horizontalPosition + scaledW/2, verticalPosition);
		p.addPoint(horizontalPosition + scaledW, verticalPosition + scaledH-10);
		
		return p;
	}
}


