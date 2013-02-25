package littlejgl;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;




public abstract class GClient extends Canvas implements Runnable {
	
	//SETTINGS - 
	private boolean 	game_setting_debug_mode = false;
	
	private int 		game_setting_tickrate 	= 30;
	private double 		game_setting_ticklength = 1000000000.0 / game_setting_tickrate;
	
	private int 		game_setting_width 		= 400;
	private int 		game_setting_height 	= 400;
	
	//TIME - updated each cycle
	private long 		time_millis;
	private long		time_nanos;
	private long		time_seconds;
	
	//GAME INFORMATION
	private int 		game_info_playtime		= 0;
   	private int 		game_info_fps 			= 0;
   	private int 		game_info_cps 			= 0;
   	

   	private boolean 	game_var_running 		= true;
   	int sleep_millis = 0;
	int sleep_nanos = 0;
	int cycle_fix = 0;
   	public Rectangle view = new Rectangle(0,0,game_setting_width,game_setting_height);
   	
   	
   	//OTHER
	private static final long serialVersionUID = 1l;
	
	public GClient(){
		
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				view.x = getWidth()/2 - view.width/2;
				view.y = getHeight()/2 - view.height/2;
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				view.x = getWidth()/2 - view.width/2;
				view.y = getHeight()/2 - view.height/2;
			}

			
			public void componentResized(ComponentEvent arg0) {
				view.x = getWidth()/2 - view.width/2;
				view.y = getHeight()/2 - view.height/2;
				requestFocusInWindow();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				view.x = getWidth()/2 - view.width/2;
				view.y = getHeight()/2 - view.height/2;
				requestFocusInWindow();
			}
     });
		
		this.setSize(new Dimension(game_setting_width,game_setting_height));
		
		this.setPreferredSize(new Dimension(game_setting_width, game_setting_height));
        this.setMinimumSize(new Dimension(game_setting_width, game_setting_height));
        this.setMaximumSize(new Dimension(game_setting_width, game_setting_height));
        
		this.setVisible(true);
		this.requestFocus();
		this.requestFocusInWindow();
			
	}
	
	public final void start(){
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    // @SuppressWarnings("unused")
	public final void run(){
    	
    	int numNotTicked=0;

    	double numNewTicks=0;
    	int numToTick=0;
    	
    	this.createBufferStrategy(3);
    	BufferStrategy strategy = this.getBufferStrategy();
    	
    	int cycles_this_second = 0;
    	int frames_this_second = 0;
    	
    	time_nanos = System.nanoTime();
    	time_millis = time_nanos/1000000;
    	time_seconds = time_millis/1000;
    
    	while(game_var_running){
    		//TIME UPDATE
    		long time_nanos_previous = time_nanos;
    		//long time_millis_previous = time_millis;
    		long time_seconds_previous = time_seconds;
    		time_nanos = System.nanoTime();
        	time_millis = time_nanos/1000000;
        	time_seconds = time_millis/1000;
        	
        	
        	
        	//System.out.println(time_nanos + " " + time_millis + " " + time_seconds + " " + numNewTicks);
        	
        	
        	
        	if(time_seconds_previous != time_seconds){
        		
        		game_info_fps = frames_this_second;
    			frames_this_second = 0;
    			
    			game_info_cps = cycles_this_second;
    			cycles_this_second = 0;
    			sleepupdate();
    			game_info_playtime++;
        	}
        	
    		cycles_this_second++;
        	
        	numNewTicks += ((time_nanos - time_nanos_previous)/game_setting_ticklength); 

    		//DO ALL TICKS TO UPDATE GAME
    		numNotTicked += (int) numNewTicks; 
    		numNewTicks -= (int)numNewTicks;
    		
    		numToTick = numNotTicked;
    		if(numToTick<3 && numToTick>0)
    			{numToTick = 1;}
    		if(numNotTicked > 20){
    			numNotTicked = 20;
    		}
    		boolean tick_happened = false;
    		for(int i = 0; i < numToTick; i++){
    			numNotTicked--;
    			tick();
    			tick_happened = true;
    		}
    		//END TICKING

    		
    		
    		//RENDER PROCESS
    		if(tick_happened && this.isShowing() && this.isValid()){
    			frames_this_second++;
    			try{
    				Graphics g = strategy.getDrawGraphics();
	    			g.setColor(Color.BLACK);
	    			g.fillRect(0, 0, getWidth(), getHeight());
	    			
	    			render(g);
	    			debugrender(g);
	    			strategy.show();
    			}
    			catch(Exception e1){
    				System.out.println("e1");
    				try{
    					createBufferStrategy(3);
    					strategy = getBufferStrategy();
    					}
    				catch(Exception e2 ){System.out.println("e2");}
    			}
    		}

    		
    		 try {
     			//   Thread.sleep(0);
                    Thread.sleep(sleep_millis,sleep_nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    		
    	}

    }
    
	 private void sleepupdate(){
	    	int goal_cycles = game_setting_tickrate*5;
			int goal_range = 60;
			
			if(game_info_cps < goal_cycles-goal_range){
				cycle_fix = 2;
			}else if(game_info_cps > goal_cycles + goal_range){
				cycle_fix = 1;
			}
			
			if(game_info_cps > goal_cycles && cycle_fix==1){
				sleep_nanos+=10000;
				if(sleep_nanos >= 1000000){
					sleep_millis++;
					sleep_nanos = 0;
				}
				
			}else if(cycle_fix==1){
				cycle_fix = 0;
			}
			else if(game_info_cps < goal_cycles && cycle_fix == 2){
				sleep_nanos-=10000;
				if(sleep_nanos <0 && sleep_millis >= 1){
					
					sleep_millis--;
					sleep_nanos = 999000;
				}else{sleep_nanos =0;}
			}else if(cycle_fix == 2){
				cycle_fix = 0;
			}
	    	
	    }
	
	public void debugrender(Graphics g){
		if(game_setting_debug_mode){
			String stringFPS = "FPS: " + game_info_fps;
			String stringPlayTime = "Playtime: " + game_info_playtime;
			//String stringControls = "Direction: " + player.direction + " Motion: "  + controls.ctrl_p_motion;
			String stringTicks = "Ticks: " + "unknown"/*numToTick*/ + "\t\t cycles/sec: " + game_info_cps + " \t\t" + sleep_millis + ":" + sleep_nanos + "    " + this.getHeight()+":"+this.getWidth();;
			
			g.setColor(new Color(255, 255, 255, 60));
			g.fillRect(1, 1, 300, 110);
			g.setColor(Color.white);
			g.drawString(stringPlayTime, 5, 15);
			g.drawString(stringFPS, 5, 30);
			//g.drawString(stringControls, 5, 45);
			g.drawString(stringTicks, 5, 60);
			//g.drawString("click: " + controls.mouse_x + ":" + controls.mouse_y , 5, 75);
			//g.drawString("Player: x=" + (int)player.x + " y = " +(int)player.y, 5, 90);
			//g.drawString("world: x = " + world.position.x + " y = " + world.position.y, 5, 105);
			
			//if(controls.control_mode == Controls.MODE_TYPING){
		//	g.setColor(new Color(0,0,0,65));
			//g.fillRect(env.view.x + 5, env.view.y+env.view.height - 25, env.view.width-10, 20);
			//g.setColor(Color.WHITE);
			//g.drawString(controls.temp_string, env.view.x + 10 , env.view.y+env.view.height-10);
			//}
		}
		
		
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	
    public final int getFPS()			{return game_info_fps;		}
    public final int getCPS()			{return game_info_cps;		}
    public final int getPlaytime()		{return game_info_playtime;	}	
    public final long getTimeMillis()	{return time_millis;		}
    public final long getTimeNanos()	{return time_nanos;			}
    public final long getTimeSeconds(){return time_seconds;			}
    
}
