package gui.widgets;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/*******************************************************************************
 * Class for displaying a mark
 */
public class BooleanSwitch extends JComponent {
	public BooleanSwitch() {
	}

	private static final long serialVersionUID = -695054119642765418L;
	private Color fillColor = Color.white;
	private boolean bActive;
	private int iWidth, iHeight;

	@Override
	public void paint(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(0, 0, iWidth, iHeight);
		g.setColor(Color.lightGray);
		g.drawRect(0, 0, iWidth-1, iHeight-1);
		g.setColor(Color.black);
		g.drawRect(1, 1, iWidth-3, iHeight-3);
		
		if(bActive)
		{
			g.setColor(Color.gray);
			int[]xPoints={1,11,25,24,11,1};
			int[]yPoints={13,23,-1,-1,17,13};
			int nPoints=6;
			g.fillPolygon(xPoints, yPoints, nPoints);
			g.setColor(Color.black);
			int[]xPoints2={0,10,24,23,10,0};
			int[]yPoints2={14,24,0,0,18,14};
			int nPoints2=6;
			g.fillPolygon(xPoints2, yPoints2, nPoints2);
		}
	}


	public void setActive(boolean active) {
		bActive = active;
		repaint();
	}
	
	public void setSize(int iW, int iH)
	{
		super.setSize(iW, iH);
		iWidth = iW;
		iHeight = iH;
	}
	

}
