package gui.widgets.panels;

import gui.MainFrame;
import gui.widgets.inputdialogs.NumberInputDialog;
import gui.widgets.inputdialogs.TimeInputDialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.types.Value;

public class SingleInputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1201850147551866607L;
	private MouseListener ml;
	private final JPanel panel;
	private final JLabel labelText;
	private final JTextField textField;
	private final JLabel labelUnit;
	private Object variable;
	private Value newValue;
	private boolean bReadOnly=false;
	private boolean bNumberDialog = false, bTimeDialog = false;

	public SingleInputPanel() {
		super();

		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 700, 40);
		panel.setForeground(Color.DARK_GRAY);
		panel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.setName("SimpleInputPanel");
		panel.setLayout(null);

		labelText = new JLabel();
		labelText.setBounds(10, 14, 123, 15);
		labelText.setHorizontalAlignment(SwingConstants.LEFT);
		labelText.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelText.setText("longText");

		textField = new JTextField();
		textField.setFocusable(false);
		textField.setBounds(542, 11, 78, 21);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));

		labelUnit = new JLabel();
		labelUnit.setBounds(630, 14, 25, 15);
		labelUnit.setHorizontalAlignment(SwingConstants.LEFT);
		labelUnit.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelUnit.setText("unit");

		panel.add(labelText);
		panel.add(textField);
		panel.add(labelUnit);
		this.add(panel);

	}

	public void initPanel(Value value) {
		newValue = value;
		variable = newValue.getValue();
		labelText.setText(newValue.getLongText());
		labelUnit.setText(newValue.getUnitText());
		if (value.getValue() instanceof Integer
				|| value.getValue() instanceof Double) {
			bNumberDialog = true;
		}
		if (value.getValue() instanceof String) {
			bTimeDialog = true;
		}

		ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bNumberDialog)
					new NumberInputDialog(newValue, textField,
							MainFrame.getInstance().mainFrame);
				if (bTimeDialog)
					new TimeInputDialog(newValue, textField,
							MainFrame.getInstance().mainFrame);
			}
		};
		textField.addMouseListener(ml);

		if(value.isReadOnly())
			setReadOnly();

		setValue(variable);
	}

	public void setValue(Object value) {
		textField.setText(value + "");
	}

	public void setReadOnly() {
		bReadOnly = true;
		textField.setEditable(false);
		textField.setBackground(Color.lightGray);
		textField.setForeground(Color.darkGray);
		textField.removeMouseListener(ml);
	}

	public boolean isReadOnly() {
		return bReadOnly;
	}

	public String getVarName() {
		return labelText.getText();
	}

	public JTextField getInputField() {
		return textField;
	}

	public JLabel getTextLabel() {
		return labelText;
	}

	public JLabel getUnitLabel() {
		return labelUnit;
	}
}
