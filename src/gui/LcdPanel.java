package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.tw.pi.framebuffer.FrameBuffer;

import data.DataHandler;
import data.types.Value;


public class LcdPanel {
	private static LcdPanel INSTANCELcdPanel;
	
	public static LcdPanel getInstanceLcdPanel() {
	      if(INSTANCELcdPanel == null) {
	    	  INSTANCELcdPanel = new LcdPanel();
	       }
		return INSTANCELcdPanel;
	}

	private static FrameBuffer INSTANCE;
	
	public static FrameBuffer getInstance() {
	      if(INSTANCE == null) {
	    	  INSTANCE = new FrameBuffer("/dev/fb1");
	       }
		return INSTANCE;
	}	

	public void LcdPanelTest() {		
		FrameBuffer   fb = LcdPanel.getInstance();
		BufferedImage img = fb.getScreen();
		Graphics2D    g   = img.createGraphics();
		int w = img.getWidth();
		int h = img.getHeight();
		
		// RenderingHints.VALUE_ANTIALIAS_ON must before rotate !
		// Rotated font drawing behaves strange without that....
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, w, h);
		int x = 298;
		int y = 2;
		
		//flagge zeigen:
		g.setColor(Color.RED);
		g.fillRect(x, y, 20,5);
		y += 6;
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 20,5);
		y += 6;
		g.setColor(Color.RED);
		g.fillRect(x, y, 20,5);
		y += 6;
		
		System.out.println("395: Lcd flagge fertig gezeichnet !");
			
		g.setFont(new Font("Tahoma", Font.PLAIN, 14));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 225, 320, 1);
		g.drawString("ebiian                            10.3.2014", 2, 238);
		
		List<Value> alleParameter = DataHandler.getInstance().getParams();

		TimerTask taskLcd = new TimerTask() {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd.MM.yyyy  HH:mm:ss");

			@Override
			public void run() {
				date.setTime(System.currentTimeMillis());

				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 0, 298, 15);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 17, 298, 1);
				g.drawString((dateFormat.format(date)), 5, 15);
				
				for(Value parameter :alleParameter){
//					System.out.println("395: "+parameter.getVarName()+" "+parameter.getValue()+parameter.getUnitText());
					if(parameter.getVarName().equals("valueTemp1"))			
					{
//						System.out.println("395: gefunden !!!  "+ parameter.getValue().toString());
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(0, 20, 300, 20);
						g.setColor(Color.DARK_GRAY);
						g.drawString(parameter.getLongText()+"  "+parameter.getValue().toString()+parameter.getUnitText(), 10, 40);

/*					if(parameter.getValue() instanceof Integer)
					{
						parameter.setValue(new Integer(23));
					
					}else
					{
					if(parameter.getValue() instanceof Double)
					{
						parameter.setValue(new Double(23.9));
						
					}
				}*/
			}
		}
			}
		};
		Timer timer = new Timer();
		timer.schedule(taskLcd, 0, 1000);
		
		
	}
	

}
