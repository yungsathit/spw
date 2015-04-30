package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter, GameReporter2{
	GamePanel gp,gg;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Item> item = new ArrayList<Item>();	
	private ArrayList<ItemGun> itemGuns = new ArrayList<ItemGun>();
	private ArrayList<Gun> guns = new ArrayList<Gun>();
	

	private SpaceShip v;	
	

	private Timer timer;
	
	private int hp = 4;
	private int itemType = 0;
	private long score = 0;
	private double difficulty = 0.1;
	private double difficulty2 = 0.01;
	private double gunDropRate = 0.008;
	
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
	private void generateItem(){
		Item i = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(i);
		item.add(i);
	}
	
	private void generateItemGun(){
		ItemGun i = new ItemGun((int)(Math.random()*390), 30);
		gp.sprites.add(i);
		itemGuns.add(i);
		//i.setType();
	}

	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}

		if(Math.random() < gunDropRate){
			generateItemGun();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 1;
			}
		}

		Iterator<ItemGun> ig_iter = itemGuns.iterator();
		while(ig_iter.hasNext()){
			ItemGun i = ig_iter.next();
			i.proceed();

			if(!i.isAlive()){
				ig_iter.remove();
				gp.sprites.remove(i);
				score -= 0;
			}
		}

		Iterator<Gun> g_iter = guns.iterator();
		while(g_iter.hasNext()){
			Gun g = g_iter.next();
			g.proceed();
			}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double gr;
		Rectangle2D.Double gn;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				getHp();
				if(hp<0){
					die();
				}
				
				return;
			}

			for(Gun g : guns){
				gr = g.getRectangle();
				if(gr.intersects(er)){
					gp.sprites.remove(e);
					e.isDie();
					return;
				}

			}

			for(ItemGun i : itemGuns){
				gn = i.getRectangle();
				if(gn.intersects(vr)){	
					gp.sprites.remove(i);
					//i.deleteItem();
					//itemType = i.getType();
					return;
				}
			}
		}	
	}
	
	private void process2(){
		if(Math.random() < difficulty2){
			generateItem();
		}
		
		Iterator<Item> e_iter = item.iterator();
		while(e_iter.hasNext()){
			Item i = e_iter.next();
			i.proceed();
			
			

			if(!i.isUndie()){
				e_iter.remove();
				gp.sprites.remove(i);
				score += 10000;
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;

		for(Item i : item){
			er = i.getRectangle();
			if(er.intersects(vr)){
				gp.updateGameUI(this);
				undie();
				return;
			}
		}
	}

	
	public void die(){
		timer.stop();
	}
	
	public void undie(){
		timer.start();
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
		case KeyEvent.VK_SPACE:
			if(itemType == 0)
				shoot1();
			else if(itemType == 1)
				shoot3();
			break;
		case KeyEvent.VK_R:
			allDie();
			break;
		}
	}

	public void allDie(){
			for(Enemy e : enemies){			
				gp.sprites.remove(e);
				e.isDie();
				return;
			}
				
   	}

	public void shoot1(){
            Gun a = new Gun((v.x)+12, v.y-20);
            
            	a.setType(itemType);
            gp.sprites.add(a);
            guns.add(a);
        }

    public void shoot3(){
            Gun a = new Gun((v.x)+22, v.y-20);
            Gun b = new Gun((v.x)+12, v.y-20);
            Gun c = new Gun((v.x), v.y-20);
            
            	a.setType(itemType);
            	b.setType(itemType);
            	c.setType(itemType);
            
            gp.sprites.add(a);
            guns.add(a);
            gp.sprites.add(b);
            guns.add(b);
            gp.sprites.add(c);
            guns.add(c);
        }


	public long getScore(){
		return score;
	}

	public int getHp(){
		return hp--;
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
