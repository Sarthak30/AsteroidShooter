import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.imageio.ImageIO;


public class SpaceApp extends JPanel implements KeyListener,MouseMotionListener {
	private SpaceShuttle myShuttle;
	private Timer ShuttleMover;
	private int appW, appH;
	private JFrame parentFrame;
	private SpaceBckgrnd mySpcBckgrnd;
	private Timer ObstacleMover;
	private Timer EnemyMover;
	private Timer EnemyCreator;
	private Timer MeteorCreator;
	private Timer MissileMover;
	private boolean shuttleCanMove = false;
	private ArrayList<Meteor> meteors;
	private ArrayList<Missile> missiles;
	private ArrayList<Enemy> enemies;
	private Random myRandomFriend;
	private boolean movingRight,movingLeft,movingForward,movingBack;
	private boolean display,display2,display3,FireballShow,FireShow;
	private int fY,fX,f2X,f2Y,x,y;
	private boolean crosshairShowing;
	public int Score;
	public int Lives;
	private int start;
	private BufferedImage Keyboard;
	private BufferedImage Spacebar;
	private BufferedImage Shift,Fireball,Fire;
	
	private static final String IMAGE_PATH = "./images/";
	
	public SpaceApp(JFrame parentFrame, int appW, int appH){
		super();
		File imageFile=new File(IMAGE_PATH+"arrowkeys.png");
		File imageFile2=new File(IMAGE_PATH+"spacebar.png");
		File imageFile3=new File(IMAGE_PATH+"shift.png");
		File imageFile4=new File(IMAGE_PATH+"fireball3.png");
		File imageFile5=new File(IMAGE_PATH+"fireball2.png");
		try{
			Keyboard = ImageIO.read(imageFile);
			Spacebar = ImageIO.read(imageFile2);
			Shift = ImageIO.read(imageFile3);
			Fireball = ImageIO.read(imageFile4);
			Fire= ImageIO.read(imageFile5);
		}catch (Exception e) {
			Keyboard =null;
			Spacebar =null;
			Shift =null;
			Fireball =null;
			Fire =null;
			e.printStackTrace();
		}
		myRandomFriend = new Random();
		this.meteorCreatorStart();
		this.enemyCreatorStart();
		meteors = new ArrayList<Meteor>();
		missiles= new ArrayList<Missile>();
		enemies= new ArrayList<Enemy>();
		this.appW=appW;
		this.appH=appH;
		this.parentFrame = parentFrame;
		this.setSize(appW,appH);
		myShuttle= new SpaceShuttle();
		mySpcBckgrnd= new SpaceBckgrnd();
		parentFrame.addMouseMotionListener(this);
		parentFrame.addKeyListener(this);
		movingRight=false;
		movingLeft=false;
		movingForward=false;
		movingBack=false;
		this.startShuttleMover();
		this.startObstacleMover();
		crosshairShowing=false;
		display=false;
		Score=0;
		Lives=3;
		
	}

	private void LifeBonus(){
		if (Score % 400 == 0){
			Lives++;
			display3 = true;
			display2=false;
		}
	}

	private void meteorCreatorStart(){
		MeteorCreator=new Timer(800,new ActionListener(){
			public void actionPerformed (ActionEvent e){
				int width_range = 1000;
				int height_range = 100;

				Meteor myMeteor = new Meteor(myRandomFriend.nextInt(width_range), 
						myRandomFriend.nextInt(height_range));
				meteors.add(myMeteor);
			}
		});
		MeteorCreator.start();
	}
	private void enemyCreatorStart(){
		EnemyCreator=new Timer(1000,new ActionListener(){
			public void actionPerformed (ActionEvent e){
				int width_range = 1000;
				int height_range = 100;

				Enemy myEnemy = new Enemy(myRandomFriend.nextInt(width_range), 
						myRandomFriend.nextInt(height_range));
				enemies.add(myEnemy);
			}
		});
		EnemyCreator.start();
	}

	private void meteorCreatorStop(){
		if (MeteorCreator == null){
			return;
		}

		MeteorCreator.stop();
		MeteorCreator = null;
	}
	private void enemyCreatorStop(){
		if (EnemyCreator == null){
			return;
		}

		EnemyCreator.stop();
		EnemyCreator = null;
	}
	
	private void startShuttleMover(){
		if (ShuttleMover!=null)return;
		ShuttleMover=new Timer(10, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(movingForward){

					shuttleCanMove=true;
					myShuttle.moveForward();}

				if(movingBack){
					if(myShuttle.getY() <740){
						shuttleCanMove=true;
						myShuttle.moveBack();
					}}
				if(movingRight){if(myShuttle.getX() <1180){
					shuttleCanMove=true;
					myShuttle.moveRight();}}
				if(movingLeft){
					if(myShuttle.getX() >0){
						shuttleCanMove=true;
						myShuttle.moveLeft();}}
				if(shuttleCanMove=true){
					repaint();}
			}}
		);		
		ShuttleMover.start();
	}


	private void startObstacleMover(){
		ObstacleMover= new Timer(10,new ActionListener(){
			public void actionPerformed(ActionEvent evt){

				//move meteors
				Meteor offscreenMeteor = null;

				for (Meteor m: meteors){
					m.move();

					if (m.getVPos() > appH + m.getHeight()){
						offscreenMeteor = m;
					}
				}
				
				//move enemies
				Enemy offscreenEnemy = null;

				for (Enemy e: enemies){
					e.move();

					if (e.getVPos() > appH + e.getHeight()){
						offscreenEnemy = e;
					}
				}
				
				//move missiles
				Missile offscreenMissile = null;
				
				for(Missile mi: missiles){
					mi.move();


					if (mi.getY() + mi.getHeight() <= 0 ){
						offscreenMissile = mi;
					}
				}

				if (offscreenMissile != null){
					missiles.remove(offscreenMissile);
				}
				
				if (offscreenMeteor != null){
					meteors.remove(offscreenMeteor);
				}
				if (offscreenEnemy != null){
					enemies.remove(offscreenEnemy);
				}
				
				checkCollisions();

				repaint();
			}
		});
		ObstacleMover.start();
	}


	private void checkCollisions() {
		boolean isCollision = false;
		boolean isMissileCollision = false;
		Meteor blownUpMeteor = null;
		Missile deadMissile = null;
		Enemy blownUpEnemy=null;
		if (myShuttle == null){
			return;
		}

		Rectangle shipBounds = myShuttle.getBounds();
		Rectangle missileBounds = null;
		Shape meteorBounds = null;
		Shape enemyBounds=null;
		for (Meteor m: meteors){
			meteorBounds = m.getAccurateBounds();
			//check collision between ship and this meteor
			if (meteorBounds.intersects(shipBounds)){
				blownUpMeteor = m;
				isCollision = true;
				break;
			}

			for (Missile mi: missiles){
				missileBounds = mi.getBounds();
				//check collision between this meteor and ALL missiles
				if (missileBounds.intersects(meteorBounds.getBounds())){
					blownUpMeteor = m;
					deadMissile = mi;
					isMissileCollision = true;
				}
			}
		}
		for (Enemy e: enemies){
			enemyBounds = e.getAccurateBounds();	
			//check collision between ship and this meteor
			if (enemyBounds.intersects(shipBounds)){
				blownUpEnemy = e;
				isCollision = true;
				break;
			}
			for (Missile mi: missiles){
				missileBounds = mi.getBounds();
				//check collision between this enemy and ALL missiles
				if (missileBounds.intersects(enemyBounds.getBounds())){
					blownUpEnemy = e;
					deadMissile = mi;
					isMissileCollision = true;
				}
			}
		}

		if (isCollision){
			Lives=Lives-1;
			meteors.remove(blownUpMeteor);
			shuttleCanMove = false;
			enemies.remove(blownUpEnemy);
			display2=true;
			display3=false;
			if(Lives==0){
				shuttleCanMove = false;
				stopObstacleMover(); 
				stopShuttleMover();
				meteorCreatorStop();
				enemyCreatorStop();
				display=true;
				display2=false;
				display3=false;
			}
		}
		if (isMissileCollision){
			enemies.remove(blownUpEnemy);
			meteors.remove(blownUpMeteor);
			missiles.remove(deadMissile);
			if(Meteor.NITRO_ON==true){
				Score+=40;
				LifeBonus();
			}
			else{
				Score+=20;
				LifeBonus();
			}
							
		}
	}


	private void stopShuttleMover(){
		if (ShuttleMover == null){
			return;
		}

		ShuttleMover.stop();
		ShuttleMover=null;
	}


	private void stopObstacleMover(){
		if (ObstacleMover == null){
			return;
		}

		ObstacleMover.stop();
		ObstacleMover=null;
	}
	

	@Override
	public void keyPressed(KeyEvent a) {
		int keycode = a.getKeyCode();

		if (!shuttleCanMove) return;

		if (keycode == KeyEvent.VK_RIGHT){

			movingRight=true;}

		else if(keycode== KeyEvent.VK_LEFT){
			movingLeft=true;
		}
		else if (keycode== KeyEvent.VK_UP)
			movingForward=true;
		else if (keycode == KeyEvent.VK_DOWN)
		{movingBack=true;}
		else if (keycode== KeyEvent.VK_SPACE){
			fireMissile();
		}
		else if (keycode == KeyEvent.VK_SHIFT){
			myShuttle.turnOnNitro();
			turnOnObstacleNitro();
		}


	}


	private void fireMissile(){
		Missile newMissile = new Missile(myShuttle.getX(), myShuttle.getY());
		this.missiles.add(newMissile);
	}

	@Override
	public void keyReleased(KeyEvent a) {
		int keycode = a.getKeyCode();

		if (!shuttleCanMove) return;

		if (keycode == KeyEvent.VK_RIGHT){
			{
				movingRight=false;}
		}
		else if(keycode== KeyEvent.VK_LEFT){

			movingLeft=false;}

		else if (keycode== KeyEvent.VK_UP)
			movingForward=false;
		else if (keycode == KeyEvent.VK_DOWN){
			movingBack=false;
		}
		else if (keycode == KeyEvent.VK_SHIFT){
			myShuttle.turnOffNitro();
			turnOffObstacleNitro();
		}
	}


	private void turnOnObstacleNitro(){
		Meteor.NITRO_ON = true;
	}
	private void turnOffObstacleNitro(){
		Meteor.NITRO_ON = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	public void paintComponent(Graphics g1){
		super.paintComponent(g1);

		Graphics2D g = (Graphics2D) g1;
		mySpcBckgrnd.paintSpcBckgrnd(g);
		myShuttle.paintShuttle(g);

		g.setColor(Color.WHITE);
		//g.draw(myShuttle.getBounds());

		for(Missile mi:missiles){
			mi.paintMissile(g);
		}

		for (Meteor m : meteors){
			m.paintMeteor(g);
			//g.draw(m.getAccurateBounds());
		}

		for (Enemy e: enemies){
			e.paintEnemy(g);
		}


		g.setColor(Color.BLACK);

		Font f = new Font("Baveuse",Font.BOLD,19);
		g.setFont(f);
		g.drawString(" WELCOME TO",1250,20 );
		g.drawString(" ARMAGEDDON",1250,40 );
		g.drawString(" Score : "+ Score,1250,80 );
		g.drawString(" Lives   : "+ Lives,1250,100 );
		if(display2==true){
			g.drawString(" Lost Life...",1250,135 );
			
		}
		if(display3==true){
			g.drawString(" New Life...",1250,135 );
		}
		if(display==true){
			g.drawString(" You Lost...",1250,200 );
			g.setColor(Color.RED);
			g.drawString(" GAME OVER",550,450 );
		}
		
		
		g.drawImage(Keyboard, null, 1250, 260);
		Font f2 = new Font("Baveuse",Font.BOLD,16);
		g.setFont(f2);
		g.drawString(" Regular Keys",1250,360 );
		g.drawString(" For Movement",1250,380 );
		g.drawImage(Spacebar, null, 1250, 400);
		g.drawString("SpaceBar To",1250,460 );
		g.drawString("Shoot Meteors",1250,480 );
		g.drawImage(Shift, null, 1250, 500);
		g.drawString("Shift > Boost",1250,570 );
		g.drawString("Shooting with",1250,600 );
		g.drawString("Boost Gives",1250,620 );
		g.drawString("More Points..",1250,640 );
		g.drawString(" MADE BY: DA",1250,800 );
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}



}
