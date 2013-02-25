package littlejgl.example;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.*;
import littlejgl.GClient;
import static littlejgl.example.GameController.*;

public class GameClient extends GClient{
	private static final long serialVersionUID = 1L;
	
	
	public static GameImages gi = new GameImages();
	private GameController gc = new GameController(this);
	
	private double pixelman_x = 0.0;
	private double pixelman_y = 0.0;
	
	@Override
	public void render(Graphics g) {
		Color c = g.getColor();
		g.setColor(new Color(0,66,0));
		g.drawImage(gi.get("world"),view.x,view.y,400,400,null);
		g.setColor(Color.ORANGE);		
		g.fillOval(view.x+view.width/2-4+(int)pixelman_x, view.y+view.height/2-14-(int)pixelman_y, 8, 8);
		g.setColor(Color.BLUE);
		g.fillRect(view.x+view.width/2-2+(int)pixelman_x, view.y+view.height/2-6-(int)pixelman_y, 4, 6);
		//g.drawImage(img_terrain.sheet[0][0],200,200,50,50,null);
		g.drawRect(view.x, view.y, view.width-1, view.height-1);
		g.setColor(c);
		
	
	}

	@Override
	public void tick() {
		double speed = .5;
		double ox = pixelman_x;
		double oy = pixelman_y;
		gc.tick();
		if(gc.m){
			if(gc.m_dir == G_N ){
				pixelman_y = pixelman_y+speed;
			}
			else if(gc.m_dir == G_NE ){
				pixelman_x = pixelman_x+speed*0.7071;
				pixelman_y = pixelman_y+speed*0.7071;
			}
			else if(gc.m_dir == G_E ){
				pixelman_x = pixelman_x+speed;
			}
			else if(gc.m_dir == G_SE ){
				pixelman_x = pixelman_x+speed*0.7071;
				pixelman_y = pixelman_y-speed*0.7071;
			}
			else if(gc.m_dir == G_S ){
				pixelman_y = pixelman_y-speed;
			}
			else if(gc.m_dir == G_SW ){
				pixelman_x = pixelman_x-speed*0.7071;
				pixelman_y = pixelman_y-speed*0.7071;
				
			}
			else if(gc.m_dir == G_W ){
				pixelman_x = pixelman_x-speed;
			}
			else if(gc.m_dir == G_NW ){
				pixelman_x = pixelman_x-speed*0.7071;
				pixelman_y = pixelman_y+speed*0.7071;
			}
			
			if(sqrt(pixelman_x * pixelman_x + pixelman_y *pixelman_y) > 148){
				pixelman_x = ox;
				pixelman_y = oy;
			}
		}
	}
}
