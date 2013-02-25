package littlejgl;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;





public class GWindow extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private GraphicsDevice device;
	private DisplayMode screen_original_mode;
	private DisplayMode[] dmodes;
	int currentmode = 0;
	int dsize;
	
	public void startWindow(GClient component) {
		
		 	GraphicsEnvironment g_env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 	device = g_env.getDefaultScreenDevice();
		 	//for(GraphicsDevice device:devices){
		 	screen_original_mode = g_env.getDefaultScreenDevice().getDisplayMode();
		 		dmodes = g_env.getDefaultScreenDevice().getDisplayModes();
		 	
		 		
		 			 	//}	
		 		
		 		
		 	KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		    manager.addKeyEventDispatcher(new MyDispatcher(this));
		 	
		 	
	        panel = new JPanel(new BorderLayout());
	        panel.add(component);
	     
	        panel.setMinimumSize(new Dimension(800,600));
	        
	        this.setContentPane(panel);
	        this.addKeyListener(this);
	        
	       // this.setAlwaysOnTop(true);
	        this.pack();
	        this.setFocusable(true);
	        this.setResizable(false);
	        this.setBounds(0,0,800,600);
	        this.pack();
	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
	        component.requestFocus();
	        this.setBackground(Color.BLACK);
	        component.start();
	      //  System.out.println("game started");
	        
	    }
	
	private class MyDispatcher implements KeyEventDispatcher {
		
		private JFrame f;
		public MyDispatcher(JFrame f){this.f=f;}
        public boolean dispatchKeyEvent(KeyEvent e) {
        	
        	if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F11){
        		
        		//gc.setEnabled(false);
        		
    			System.out.println("here");
    			if(device.getFullScreenWindow()==null){
    			boolean isFullScreenSupported = device.isFullScreenSupported();   
    		        if (isFullScreenSupported) {
    		        	f.setVisible(false);
    		        	f.dispose();
    		        	f.setResizable(false);
    		        	f.setUndecorated(true);
    		        	
    		            device.setFullScreenWindow(f);
    		            device.setDisplayMode(screen_original_mode);
    		            
    		            
    		            f.setVisible(true); f.validate();
    		        	}
    				}
    			else{
    				f.setVisible(false);
    				f.dispose();
    				device.setFullScreenWindow(null);
    				f.setUndecorated(false);
    				//f.setResizable(true);
    				f.validate();
    				f.setVisible(true);
    				
    				
    				}
    			//gc.setEnabled(true);
    		}
        	if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F10){
        		
        		//gc.setEnabled(false);
        		
        		if(device.getFullScreenWindow()!=null){
        			currentmode++;		
        			if(currentmode==dmodes.length){currentmode=0;}
        			device.setDisplayMode(dmodes[currentmode]);
        			f.validate();
        		}
        		//gc.setEnabled(true);
        	}
        	if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F9){
        		//gc.setEnabled(false);
        		
        		if(device.getFullScreenWindow()!=null){
        		device.setDisplayMode(screen_original_mode);
        		}
        		//gc.setEnabled(true);
        	}
        	if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE){
        		System.exit(0);
        	}
        	
            return false;
        }
	}
	
	public GClient getGameComponent(){return null;}
	
	public void keyPressed(KeyEvent e) {
		//System.out.println("here1");	
	}
	public static void start(GClient component){
		GWindow window = new GWindow();
        window.startWindow(component);
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}
