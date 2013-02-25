package littlejgl.example;

import littlejgl.GController;

import java.awt.event.KeyEvent;

public class GameController extends GController{
	
	public static int G_N = 		0;
	public static int G_NE = 		1;
	public static int G_E = 		2;
	public static int G_SE = 		3;
	public static int G_S = 		4;
	public static int G_SW = 		5;
	public static int G_W = 		6;
	public static int G_NW = 		7;
	
	public boolean m = false;
	public int m_dir = G_N;
	
	public boolean s = false;
	public int s_dir = G_N;
	
	public GameController(GameClient gc) {
		gc.addKeyListener(this);
		add("north" , KeyEvent.VK_W);
		add("south" , KeyEvent.VK_S);
		add("east" , KeyEvent.VK_D);
		add("west" , KeyEvent.VK_A);
		
		add("strafe" , KeyEvent.VK_CONTROL);
	}

	@Override
	public void tick() {
		
		if(get("north") || get("east") || get("south") || get("west")){
			m = true;
		}
		else{
			m = false;
		}
		if(get("strafe")){
			s = true;
		}
		else{
			s = false;
		}
		
		if(m){
			if(get("north") && !get("east") && !get("south") && !get("west")){
				m_dir = G_N;
			}
			else if(get("north") && get("east") && !get("south") && !get("west")){
				m_dir = G_NE;
			}
			else if(!get("north") && get("east") && !get("south") && !get("west")){
				m_dir = G_E;
			}
			else if(!get("north") && get("east") && get("south") && !get("west")){
				m_dir = G_SE;
			}
			else if(!get("north") && !get("east") && get("south") && !get("west")){
				m_dir = G_S;
			}
			else if(!get("north") && !get("east") && get("south") && get("west")){
				m_dir = G_SW;
			}
			else if(!get("north") && !get("east") && !get("south") && get("west")){
				m_dir = G_W;
			}
			else if(get("north") && !get("east") && !get("south") && get("west")){
				m_dir = G_NW;
			}
			if(!s){
				s_dir = m_dir;
			}
		}
		else{
			m_dir = s_dir;
		}		
	}
	
	
	

}
