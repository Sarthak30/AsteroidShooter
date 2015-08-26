
import java.awt.Color;
import java.awt.Graphics;

public class Planet1 {
	private int hPos, vPos;
	private int speed;
	private int hSize,vSize;
	public Planet1(){
		hPos=1000;
		vPos=700;
		speed=-5;

		hSize=vSize=100;
	}
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
	public Planet1(int horizontal, int vertical){
		hPos=horizontal;
		vPos= vertical;
		speed=-5;
		hSize=vSize=100;
	}
	public void move(){
		hPos += speed;
	}


	public void paintPlanet(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(hPos, vPos, hSize, vSize);
	}
	public int getHPos() {
		// TODO Auto-generated method stub
		return hPos;
	}

}
