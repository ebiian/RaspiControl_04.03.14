package gui;

import org.tw.pi.framebuffer.FrameBuffer;


public class LcdPanel {
	private static FrameBuffer INSTANCE;
	
	public static FrameBuffer getInstance() {
	      if(INSTANCE == null) {
	    	  INSTANCE = new FrameBuffer("/dev/fb1");
	       }
		return INSTANCE;
	}
	

}
