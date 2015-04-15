package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
public class Item extends Sprite{
	public static final int Y_TO_INIT = 400;
	public static final int Y_TO_KEEP = 600;
	
	private int step = 11;
	private boolean undie = true;



	public Item(int x, int y) {
		super(x, y, 10, 12);
	}
	@Override
	public void draw(Graphics2D g) {
		
		if(y < Y_TO_INIT)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_KEEP - y)/(Y_TO_KEEP - Y_TO_INIT)));
		}
		g.setColor(Color.BLUE);
		g.fillRect(x+1, y, width, height);
	}
	public void proceed(){
		y += step;
		if(y > Y_TO_KEEP){
			undie = false;
		}
	}
	
	public boolean isUndie(){
		return undie;
	}
	
	

}