package gui.widgets.panels;

import gui.MainFrame;
import gui.widgets.BooleanSwitch;
import gui.widgets.inputdialogs.BooleanInputDialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.types.Value;

public class BooleanSwitchPanel extends JPanel {

	private static final long serialVersionUID = -695054119642765418L;
	private MouseListener ml;
	private final JPanel panel;
	private final JLabel labelText;
	private final BooleanSwitch bSwitch;
	private boolean bSwitchActive;
	private Value newValue;
	private boolean bVariable;

	public BooleanSwitchPanel() {
		super();
		

		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 723, 40);
		panel.setForeground(Color.DARK_GRAY);
		panel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.setName("BooleanSwitchPanel");
		panel.setLayout(null);

		labelText = new JLabel();
		labelText.setBounds(10, 14, 123, 15);
		labelText.setHorizontalAlignment(SwingConstants.LEFT);
		labelText.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelText.setText("longText");

		bSwitch = new BooleanSwitch();
//		bSwitch.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				new NewInputBooleanDialog(value, MainFrame.getInstance().mainFrame);
//			}
//		});
		bSwitch.setLocation(600, 10);
		bSwitch.setSize(25, 25);

		panel.add(labelText);
		panel.add(bSwitch);
		this.add(panel);

	}

	public void initPanel(Value value) {
		newValue = value;
		bVariable = (boolean)newValue.getValue();
		labelText.setText(newValue.getLongText());
		ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new BooleanInputDialog(newValue, bSwitch, MainFrame.getInstance().mainFrame);
			}
		};
		bSwitch.addMouseListener(ml);
		setSwitchActive(bVariable);
	}

	public void setLongText(String longText) {
		labelText.setText(longText);
	}

	public void setSwitchActive(boolean switchActive) {
		bSwitchActive = switchActive;
		bSwitch.setActive(bSwitchActive);
	}

	public boolean isSwitchActive() {
		return bSwitchActive;
	}
	
	public BooleanSwitch getSwitch() {
		return bSwitch;
	}

}
