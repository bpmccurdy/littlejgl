package littlejgl;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public abstract class GImages{
	 
	private HashMap<String, BufferedImage[][]> imgMap = new HashMap<String, BufferedImage[][]>();
	
	private GraphicsConfiguration gc = 
			GraphicsEnvironment
			.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice()
			.getDefaultConfiguration(); 
	
	public BufferedImage get(String name){
		return get(name,0,0);
	}
	
	public BufferedImage get(String name, int tx){
		return get(name,tx,0);
	}
	
	public BufferedImage get(String name, int tx, int ty){
		return imgMap.get(name)[ty][tx];
	}
	
	public void load(String name, String location, int tx, int ty){
		if(!location.startsWith("/")){
			location = "/" + location;
		}
		
		BufferedImage[][] sheet = new BufferedImage[ty][tx];

		BufferedImage img = null;
		
		try {
			img = ImageIO.read(this.getClass().getResource(location));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = img.getWidth();
		int h = img.getHeight();
		int tw = w/tx;
		int th = h/ty;

		for(int i=0;i<tx;i++)
		{
			for(int j=0;j<ty;j++)
			{
				BufferedImage bi = gc.createCompatibleImage(tw, th, Transparency.BITMASK);
				Graphics g = bi.createGraphics();  
				g.drawImage(img.getSubimage(i*tw, j*th, tw, th), 0, 0, null);
				sheet[j][i]= bi;
			}
		}
		imgMap.put(name, sheet);
		
	}
	
	public void load(String name, String location, int tx){
		load(name,location,tx,1);
	}
	
	public void load(String name,String location){
	
		load(name,location,1,1);
	}

}
