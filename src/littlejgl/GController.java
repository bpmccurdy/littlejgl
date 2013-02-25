package littlejgl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;


public abstract class GController implements  MouseMotionListener, KeyListener, MouseListener{
	
	private HashMap<String , Integer > controls = new HashMap<String , Integer >();
	private HashMap<Integer , Boolean > keys = new HashMap<Integer ,Boolean >();
	
	public void add(String name, int key ){
		controls.put(name,key);
		keys.put(key, false);
	}
	
	public boolean get(String name){
		if(controls.containsKey(name)){
			return keys.get(controls.get(name));
		}
		else{
			return false;
		}
	}
	
	public abstract void tick();
	/*
	private HashMap<Integer,HashSet<KeyAction>> keyToAction  = new HashMap<Integer,HashSet<KeyAction>>();
	private HashMap<String ,Action> nameToAction = new HashMap<String ,Action>();
	
	public void addKeyToAction(int key, KeyAction action){
		if(keyToAction.containsKey(key)){
			keyToAction.get(key).add(action);
		}
	}
	
	public void addControl(int key, String name, KeyAction action){
		if(nameToAction.containsKey(name))
		
		if(keyToAction.containsKey(key)){
			keyToAction.get(key).add(action);
		}
		else{
		
			
			
		}
	}
	
	public int getControl(String name){
		return nameToAction.get(name).getValue();
	}
	
	public interface Action{
		public int getValue();
	}
	
	public abstract class MultiKeyAction implements Action{
		
	}
	
	public abstract class KeyAction implements Action{
		protected int val = 0;
		public KeyAction(int val){this.val = val;}
		public abstract void keydown();
		public abstract void keyup();
		public int getValue(){return val;}
	}
	
	public class ButtonAction extends KeyAction{
		public ButtonAction(int val) {
			super(0);
		}
		@Override
		public void keydown() {
			val = 1;	
		}
		@Override
		public void keyup() {
			val = 0;	
		}
		
	}
	
	public class ToggleAction extends KeyAction{
		public ToggleAction(int val) {
			super(0);
			
		}
		@Override
		public void keydown() {
			if(val == 0){
				val = 1;
			}
			else{
				val = 0;
			}
		}
		@Override
		public void keyup(){}
	}
	
	*/
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(keys.containsKey(e.getKeyCode())){
			keys.put(e.getKeyCode(), true);
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(keys.containsKey(e.getKeyCode())){
			keys.put(e.getKeyCode(), false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
