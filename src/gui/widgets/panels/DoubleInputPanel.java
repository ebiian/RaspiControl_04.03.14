package gui.widgets.panels;

import gui.MainFrame;
import gui.widgets.Mark;
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

import constants.Constants;
import data.types.Value;

public class DoubleInputPanel extends JPanel {

	private static final long serialVersionUID = -1201850147551866607L;
	private MouseListener ml;
	private MouseListener ml2;
	private final JPanel panel;
	private final JLabel labelText;
	private final JLabel labelText2;
	private final JTextField textField;
	private final JTextField textField2;
	private final JLabel labelUnit;
	private final JLabel labelUnit2;
	private final Mark mark;
	private Object variable;
	private Object variable2;
	private Value newValue;
	private Value newValue2;
	private boolean bReadOnly;
	private boolean bReadOnly2;
	private boolean bNumberDialog = false, bTimeDialog = false;
	private boolean bNumberDialog2 = false, bTimeDialog2 = false;

	public DoubleInputPanel() {
		super();

		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 723, 40);
		panel.setForeground(Color.DARK_GRAY);
		panel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.setName("SimpleInputPanel");
		panel.setLayout(null);

		labelText = new JLabel();
		labelText.setBounds(10, 14, 161, 15);
		labelText.setHorizontalAlignment(SwingConstants.LEFT);
		labelText.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelText.setText("longText");

		labelText2 = new JLabel();
		labelText2.setBounds(347, 14, 181, 15);
		labelText2.setHorizontalAlignment(SwingConstants.LEFT);
		labelText2.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelText2.setText("longText2");

		textField = new JTextField();
		textField.setFocusable(false);
		textField.setBounds(181, 11, 78, 21);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));

		textField2 = new JTextField();
		textField2.setFocusable(false);
		textField2.setBounds(538, 11, 78, 21);
		textField2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField2.setFont(new Font("Tahoma", Font.BOLD, 12));

		labelUnit = new JLabel();
		labelUnit.setBounds(269, 14, 32, 15);
		labelUnit.setHorizontalAlignment(SwingConstants.LEFT);
		labelUnit.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelUnit.setText("unit");

		labelUnit2 = new JLabel();
		labelUnit2.setBounds(626, 14, 41, 15);
		labelUnit2.setHorizontalAlignment(SwingConstants.LEFT);
		labelUnit2.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelUnit2.setText("unit2");

		mark = new Mark();
		mark.setLocation(670, 14);
		mark.setSize(15, 15);
		mark.setActiveColor(Constants.MARK_COLOR_TOLERANCE);
		mark.setVisible(false);

		panel.add(labelText);
		panel.add(textField);
		panel.add(labelUnit);
		panel.add(labelText2);
		panel.add(textField2);
		panel.add(labelUnit2);
		panel.add(mark);
		this.add(panel);

	}

	public void initPanelVar1(Value value) {
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

	public void initPanelVar2(Value value2) {
		newValue2 = value2;
		variable2 = newValue2.getValue();
		labelText2.setText(newValue2.getLongText());
		labelUnit2.setText(newValue2.getUnitText());
		if (value2.getValue() instanceof Integer
				|| value2.getValue() instanceof Double) {
			bNumberDialog2 = true;
		}
		if (value2.getValue() instanceof String) {
			bTimeDialog2 = true;
		}

		ml2 = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (bNumberDialog2)
					new NumberInputDialog(newValue2, textField2,
							MainFrame.getInstance().mainFrame);
				if (bTimeDialog2)
					new TimeInputDialog(newValue2, textField2,
							MainFrame.getInstance().mainFrame);
			}
		};
		textField2.addMouseListener(ml2);

		if(value2.isReadOnly())
			setReadOnly2();
		
		setValue2(variable2);
	}

	public void initPanelMark(Value value) {
		if (value!=null)
			setMarkVisible(true);
	}

	public void setValue(Object value) {
		textField.setText(value + "");
	}

	public void setValue2(Object value2) {
		textField2.setText(value2 + "");
	}

	public void setReadOnly() {
		bReadOnly = true;
		textField.setEditable(false);
		textField.setBackground(Color.lightGray);
		textField.setForeground(Color.darkGray);
		textField.removeMouseListener(ml);
	}

	public void setReadOnly2() {
		bReadOnly2 = true;
		textField2.setEditable(false);
		textField2.setBackground(Color.lightGray);
		textField2.setForeground(Color.darkGray);
		textField2.removeMouseListener(ml2);
	}

	public void setMarkVisible(boolean visible) {
		mark.setVisible(visible);;
	}

	public boolean isReadOnly() {
		return bReadOnly;
	}

	public boolean isReadOnly2() {
		return bReadOnly2;
	}

	public String getVarName() {
		return labelText.getText();
	}

	public String getVarName2() {
		return labelText2.getText();
	}

	public JTextField getInputField() {
		return textField;
	}

	public JTextField getInputField2() {
		return textField2;
	}

	public JLabel getTextLabel() {
		return labelText;
	}

	public JLabel getTextLabel2() {
		return labelText2;
	}

	public JLabel getUnitLabel() {
		return labelUnit;
	}

	public JLabel getUnitLabel2() {
		return labelUnit2;
	}
	public Mark getPanelMark() {
		return mark;
	}

}
