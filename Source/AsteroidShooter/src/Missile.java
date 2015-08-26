import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Missile {

	private int imgW;
	private int imgH;
	private double imageScale;
	private int hPos, vPos;
	private int speed;
	private int hSize,vSize;
	private BufferedImage missile;
	
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

	public Missile(int missileX, int missileY){
		hPos=missileX;
		vPos= missileY;
		speed=10;
		//speed = 1;
		hSize=vSize=100;
		
		File imageFile=new File(IMAGE_PATH+"f3.png");
		try{
			missile = ImageIO.read(imageFile);
			imgW=missile.getWidth();
			imgH=missile.getHeight();
			imageScale=0.25;
		}catch (Exception e) {
			missile =null;
			e.printStackTrace();
		}
	}
	
	public void setPosition(int x, int y){
		this.hPos = x;
		this.vPos = y;
	}
	
	public void move(){
		vPos-= speed;
	}

	public int getHPos() {
		// TODO Auto-generated method stub
		return hPos;
	}
	public int getVPos(){
		return vPos;
	}
	public Rectangle getBounds(){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		return new Rectangle(hPos, vPos, scaledW, scaledH);
	}
	public void paintMissile (Graphics g){
		int scaledW=(int) (imgW* imageScale);
		int scaledH=(int)(imgH*imageScale);
		g.drawImage(missile,hPos,  vPos, scaledW, scaledH, null);

	}

}
