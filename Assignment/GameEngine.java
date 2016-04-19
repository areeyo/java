import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;



public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> item = new ArrayList<Item>();
	private SpaceShip v;	
	
	private Timer timer;

	private long score = 0;
	private double difficulty = 0.1;
	private int time = 0;
	private int b = 380;
	private int hearth = 0;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		

		gp.sprites.add(v);

		timer = new Timer(50, new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent arg0) {
 				process();
 				process2();
 			}
 		});
 		timer.setRepeats(true);
	}
	
	public void start(){
		timer.start();
	}

	private void generateEnemy(){
 		Enemy e = new Enemy((int)(Math.random()*390), 30);
 		gp.sprites.add(e);
 		enemies.add(e);
 	}
 	
 	private void process(){
 		if(Math.random() < difficulty){
 			generateEnemy();
 		}

 		if(time>0){
			
			time--;
		}
 		
 		Iterator<Enemy> e_iter = enemies.iterator();
 		while(e_iter.hasNext()){
 			Enemy e = e_iter.next();
 			e.proceed();
 			
 			if(!e.isAlive()){
 				e_iter.remove();
 				gp.sprites.remove(e);
 				score += 100;
 			}
 		}
 		
 		gp.updateGameUI(this);
 		
 		Rectangle2D.Double vr = v.getRectangle();
 		Rectangle2D.Double er;
 		for(Enemy e : enemies){
 			er = e.getRectangle();
 			if(er.intersects(vr)){
 				if(time == 0){
					Timer timer2 = new Timer(200, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {

						}
					});
					timer2.setRepeats(false);
			        timer2.start();
					b -= 380/5;
					time = 5;
					if(b <= 75){
						die();
					}
					return;
				}
 			}
 		}
 		gp.bloodSpaceShip(b);
 	}

 	private void generateItem(){
		Item e = new Item((int)(Math.random()*390), 2);
		gp.sprites.add(e);
		item.add(e);
	}
	
	private void process2(){
		if(Math.random() < difficulty/10){
			generateItem();
		}
		
		if(hearth>0)
			hearth--;
		
		Iterator<Item> e_iter = item.iterator();
		while(e_iter.hasNext()){
			Item e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				
			}
			
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Item e : item){
			er = e.getRectangle();
			if(er.intersects(vr)){
				if(hearth == 0){
					b = 380;
					hearth = 10;
					return;
				}
				
			}
		}
		gp.bloodSpaceShip(b);
	}

	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1,0);
			break;
		case KeyEvent.VK_UP:
			v.move(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);
			break;
		case KeyEvent.VK_D:
 			difficulty += 0.1;
 			break;
 		case KeyEvent.VK_ESCAPE:
			timer.stop();
			break;
		case KeyEvent.VK_ENTER:
			timer.start();
			break;
		}
	}

	public void die(){
		gp.bloodSpaceShip(b);
 		timer.stop();
 	}

 	public long getScore(){
 		return score;
 	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}