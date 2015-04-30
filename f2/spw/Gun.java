package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class Gun extends Sprite{
	public static final int Y_TO_DIE = 40;
	
	private int step = 10; // SpeedOfShooting
	private boolean alive = true;
	private int itemType = 0;
	
	public Gun(int x, int y) {
			super(x, y, 5, 15);
	}

	@Override
	public void draw(Graphics2D g) {
		if(itemType == 0){
			g.setColor(Color.BLUE);
			g.fillRect(x,y, width, height);
		}
        else if(itemType == 1){
        	g.setColor(Color.PINK);
        	g.fillRect(x,y, width, height);
        }
        
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void setType(int x){
		itemType = x;
	}
	
	public int getType(){
		return itemType;
	}
}
 
