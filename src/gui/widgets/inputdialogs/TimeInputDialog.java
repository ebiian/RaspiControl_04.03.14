package gui.widgets.inputdialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import data.types.Value;

public class TimeInputDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2289762069609848478L;
	private final JPanel contentPanel = new JPanel();
	private final JTextField textFieldHour;
	private final JTextField textFieldMinute;
	private final JTextField textFieldSecond;
	private int iHour, iMinute, iSecond;
	private final JTextField resultField;
	private final Value value;

	/**
	 * Create the dialog.
	 */
	public TimeInputDialog(Value val, JTextField field, JFrame parent) {
		value = val;
		try {
			String t[] = ((String) val.getValue()).split(":");
			iHour = Integer.parseInt(t[0]);
			iMinute = Integer.parseInt(t[1]);
			iSecond = Integer.parseInt(t[2]);
		} catch (Exception e) {
			iHour = 0;
			iMinute = 0;
			iSecond = 0;
		}
		resultField = field;
		setUndecorated(true);
		setBounds(100, 100, 191, 259);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(null);
			buttonPane.setBounds(10, 191, 170, 57);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("");
				okButton.setIcon(new ImageIcon(TimeInputDialog.class
						.getResource("/images/OK_button.png")));
				okButton.setFocusPainted(false);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String sText;
						if (iHour < 10)
							sText = "0" + iHour + ":";
						else
							sText = iHour + ":";
						if (iMinute < 10)
							sText += "0" + iMinute + ":";
						else
							sText += iMinute + ":";
						if (iSecond < 10)
							sText += "0" + iSecond;
						else
							sText += iSecond;
						value.setValue(sText);

						resultField.setText(sText);
						removeAll();
						dispose();
					}
				});
				okButton.setBounds(100, 3, 70, 50);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("");
				cancelButton.setIcon(new ImageIcon(TimeInputDialog.class
						.getResource("/images/Cancel_button.png")));
				cancelButton.setFocusPainted(false);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						removeAll();
						dispose();
					}
				});
				cancelButton.setBounds(0, 3, 70, 50);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		textFieldHour = new JTextField();
		textFieldHour.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldHour.setFocusable(false);
		textFieldHour.setHorizontalAlignment(SwingConstants.CENTER);
		if (iHour < 10)
			textFieldHour.setText("0" + iHour);
		else
			textFieldHour.setText(iHour + "");

		textFieldHour.setBounds(22, 104, 26, 20);
		contentPanel.add(textFieldHour);
		textFieldHour.setColumns(10);

		textFieldMinute = new JTextField();
		textFieldMinute.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldMinute.setFocusable(false);
		if (iMinute < 10)
			textFieldMinute.setText("0" + iMinute);
		else
			textFieldMinute.setText(iMinute + "");
		textFieldMinute.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMinute.setColumns(10);
		textFieldMinute.setBounds(83, 104, 26, 20);
		contentPanel.add(textFieldMinute);

		textFieldSecond = new JTextField();
		textFieldSecond.setFont(new Font("Tahoma", Font.BOLD, 12));
		textFieldSecond.setFocusable(false);
		if (iSecond < 10)
			textFieldSecond.setText("0" + iSecond);
		else
			textFieldSecond.setText(iSecond + "");
		textFieldSecond.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldSecond.setColumns(10);
		textFieldSecond.setBounds(142, 104, 26, 20);
		contentPanel.add(textFieldSecond);

		JLabel label = new JLabel(":");
		label.setBounds(64, 107, 9, 14);
		contentPanel.add(label);

		JLabel label_1 = new JLabel(":");
		label_1.setBounds(121, 107, 9, 14);
		contentPanel.add(label_1);

		JButton buttonHourPlus = new JButton("+");
		buttonHourPlus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonHourPlus.setFocusPainted(false);
		buttonHourPlus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(true, true, false, false, iHour);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(true, true, false, false, iHour);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});
		buttonHourPlus.setBounds(10, 48, 50, 45);
		contentPanel.add(buttonHourPlus);

		JButton buttonMinutePlus = new JButton("+");
		buttonMinutePlus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonMinutePlus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(true, false, true, false, iMinute);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(true, false, true, false, iMinute);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});
		buttonMinutePlus.setFocusPainted(false);
		buttonMinutePlus.setBounds(70, 48, 50, 45);
		contentPanel.add(buttonMinutePlus);

		JButton buttonSecondPlus = new JButton("+");
		buttonSecondPlus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonSecondPlus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(true, false, false, true, iSecond);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(true, false, false, true, iSecond);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});
		buttonSecondPlus.setFocusPainted(false);
		buttonSecondPlus.setBounds(130, 48, 50, 45);
		contentPanel.add(buttonSecondPlus);

		JButton buttonHourMinus = new JButton("-");
		buttonHourMinus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonHourMinus.setFocusPainted(false);
		buttonHourMinus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(false, true, false, false, iHour);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(false, true, false, false, iHour);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});

		buttonHourMinus.setBounds(10, 135, 50, 45);
		contentPanel.add(buttonHourMinus);

		JButton buttonMinuteMinus = new JButton("-");
		buttonMinuteMinus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonMinuteMinus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(false, false, true, false, iMinute);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(false, false, true, false, iMinute);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});
		buttonMinuteMinus.setFocusPainted(false);
		buttonMinuteMinus.setBounds(70, 135, 50, 45);
		contentPanel.add(buttonMinuteMinus);

		JButton buttonSecondMinus = new JButton("-");
		buttonSecondMinus.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonSecondMinus.addMouseListener(new MouseAdapter() {
			Timer repeatTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				countUpDown(false, false, false, true, iSecond);
				repeatTimer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						countUpDown(false, false, false, true, iSecond);
					}
				});
				repeatTimer.setInitialDelay(1000); // start repeating only after
													// 1 second
				repeatTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repeatTimer.stop();
			}
		});
		buttonSecondMinus.setFocusPainted(false);
		buttonSecondMinus.setBounds(130, 135, 50, 45);
		contentPanel.add(buttonSecondMinus);

		JLabel lblNewLabel = new JLabel("Stunde");
		lblNewLabel.setBounds(16, 33, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Minute");
		lblNewLabel_1.setBounds(77, 33, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Sekunde");
		lblNewLabel_2.setBounds(134, 33, 46, 14);
		contentPanel.add(lblNewLabel_2);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 191, 25);
		contentPanel.add(panel);

		JLabel lblZeitEinstellen = new JLabel(val.getVarName());
		lblZeitEinstellen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZeitEinstellen.setForeground(Color.LIGHT_GRAY);
		panel.add(lblZeitEinstellen);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setVisible(true);

	}

	private void countUpDown(boolean bUp, boolean bHour, boolean bMinute,
			boolean bSecond, int iValue) {
		if (bHour) {
			if (bUp) {
				if (iValue == 23)
					iValue = 0;
				else
					iValue++;
			} else {
				if (iValue == 0)
					iValue = 23;
				else
					iValue--;
			}
			iHour = iValue;
			if (iHour < 10)
				textFieldHour.setText("0" + iHour);
			else
				textFieldHour.setText(iHour + "");
		}
		if (bMinute) {
			if (bUp) {
				if (iValue == 59)
					iValue = 0;
				else
					iValue++;
			} else {
				if (iValue == 0)
					iValue = 59;
				else
					iValue--;
			}
			iMinute = iValue;
			if (iMinute < 10)
				textFieldMinute.setText("0" + iMinute);
			else
				textFieldMinute.setText(iMinute + "");
		}
		if (bSecond) {
			if (bUp) {
				if (iValue == 59)
					iValue = 0;
				else
					iValue++;
			} else {
				if (iValue == 0)
					iValue = 59;
				else
					iValue--;
			}
			iSecond = iValue;
			if (iSecond < 10)
				textFieldSecond.setText("0" + iSecond);
			else
				textFieldSecond.setText(iSecond + "");

		}
	}
}
