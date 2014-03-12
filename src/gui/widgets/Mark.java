package gui.widgets;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/*******************************************************************************
 * Class for displaying a mark
 */
public class Mark extends JPanel {
	public Mark() {
	}

	private static final long serialVersionUID = -695054119642765418L;
	private Color activeColor = Color.red, fillColor = Color.lightGray;
	private boolean bActive;

	@Override
	public void paint(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(0, 0, 17, 17);
		g.setColor(Color.lightGray);
		g.drawRect(0, 0, 16, 16);
		g.setColor(Color.gray);
		g.drawRect(1, 1, 15, 15);
	}

	public void setActiveColor(Color col) {
		activeColor = col;
		repaint();
	}

	public void setActive(boolean active) {
		bActive = active;
		if (bActive) {
			fillColor = activeColor;
		} else {
			fillColor = Color.lightGray;
		}
		repaint();
	}

}
