package gui.widgets.panels;

import gui.widgets.Mark;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;

import data.types.Value;

public class MarkPanel extends JPanel {

	private static final long serialVersionUID = -695054119642765418L;
	private final JPanel panel;
	private final JLabel labelText;
	private final Mark mark;
	private boolean bMarkActive;
	private Value newValue;
	private boolean bVariable;
	private GpioPin gpioPin;
	private Color activeColor;
	
	public MarkPanel(Color markColor) {
		super();
		
		activeColor=markColor;
		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 723, 40);
		panel.setForeground(Color.DARK_GRAY);
		panel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.setName("SimpleInputPanel");
		panel.setLayout(null);

		labelText = new JLabel();
		labelText.setBounds(10, 14, 366, 15);
		labelText.setHorizontalAlignment(SwingConstants.LEFT);
		labelText.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelText.setText("longText");

		mark = new Mark();
		mark.setLocation(600, 10);
		mark.setSize(17, 17);
		mark.setActiveColor(markColor);

		panel.add(labelText);
		panel.add(mark);
		this.add(panel);

	}

	public void initPanel(Value value) {
		newValue = value;
		bVariable = (boolean)newValue.getValue();
		labelText.setText(newValue.getLongText());
		setMarkActive(bVariable);
		gpioPin = value.getGpioPin();
		
	}


	public void setLongText(String longText) {
		labelText.setText(longText);
	}

	public void setMarkActive(boolean markActive) {
		bMarkActive = markActive;
		mark.setActive(bMarkActive);
		if(gpioPin!=null)
		{
			if (gpioPin instanceof GpioPinDigitalOutput || gpioPin instanceof GpioPinDigitalInput)
			{
				if(gpioPin.getMode() == PinMode.DIGITAL_OUTPUT) //nur wenn´s a DO ist -> setzen
				{
					if(bMarkActive)
						((GpioPinDigitalOutput) gpioPin).high();
					else
						((GpioPinDigitalOutput) gpioPin).low();
				}
			}
		}
		
	}

	public boolean isMarkActive() {
		return bMarkActive;
	}
	public Color getColor()
	{
		return activeColor;
	}
}
