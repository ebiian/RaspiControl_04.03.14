package gui.widgets.inputdialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import data.types.Value;

public class NumberInputDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005792355497770468L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldInput;
	private JLabel lblLimits;
	private JLabel lblVarName;
	private String sText="";
	private JTextField resultField;
	private boolean bInteger;
	private double dLowerLimit=0;
	private double dUpperLimit=0;
	private JLabel lblPlaus;
	private Value newValue;

	/**
	 * Create the dialog.
	 */
	public NumberInputDialog(Value val, JTextField field, JFrame parent) {
		newValue = val;
		dLowerLimit=newValue.getPlaus()[0];
		dUpperLimit=newValue.getPlaus()[1];
		resultField=field;
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);

		if(newValue.getValue() instanceof Integer){
			bInteger=true;
		}
		
		setAlwaysOnTop(true);
		setBounds(100, 100, 186, 355);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textFieldInput = new JTextField();
			textFieldInput.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					char c=arg0.getKeyChar();
					if((c+"").equals(","))
						c='.';
					sText+=c;
					if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					{
						ok_Action(textFieldInput.getText());
					}
					if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						cancel_Action();
					}
					if(arg0.getKeyCode() == KeyEvent.VK_DELETE)
					{
						delete_Character();
					}
				}
			});
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			panel.setForeground(Color.LIGHT_GRAY);
			panel.setBounds(0, 0, 186, 25);
			contentPanel.add(panel);
			
			lblVarName = new JLabel("New label");
			lblVarName.setFocusable(false);
			lblVarName.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(lblVarName);
			lblVarName.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVarName.setForeground(Color.LIGHT_GRAY);
			
			lblVarName.setText(newValue.getLongText());
			textFieldInput.setFont(new Font("Tahoma", Font.BOLD, 12));
			textFieldInput.setHorizontalAlignment(SwingConstants.TRAILING);
			textFieldInput.setBounds(13, 31, 158, 27);
			contentPanel.add(textFieldInput);
			textFieldInput.setColumns(10);
		}
		{
			lblLimits = new JLabel("Limits");
			lblLimits.setFocusable(false);
			lblLimits.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLimits.setBounds(85, 63, 83, 14);
			contentPanel.add(lblLimits);
		}
		{
			JButton btn1 = new JButton("1");
			btn1.setFocusable(false);
			btn1.setForeground(Color.DARK_GRAY);
			btn1.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="1";
					textFieldInput.setText(sText);
				}
			});
			btn1.setBounds(9, 85, 50, 45);
			contentPanel.add(btn1);
		}
		{
			JButton btn2 = new JButton("2");
			btn2.setFocusable(false);
			btn2.setForeground(Color.DARK_GRAY);
			btn2.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="2";
					textFieldInput.setText(sText);
				}
			});
			btn2.setBounds(65, 85, 50, 45);
			contentPanel.add(btn2);
		}
		{
			JButton btn3 = new JButton("3");
			btn3.setFocusable(false);
			btn3.setForeground(Color.DARK_GRAY);
			btn3.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="3";
					textFieldInput.setText(sText);
				}
			});
			btn3.setBounds(121, 85, 50, 45);
			contentPanel.add(btn3);
		}
		{
			JButton btn4 = new JButton("4");
			btn4.setFocusable(false);
			btn4.setForeground(Color.DARK_GRAY);
			btn4.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="4";
					textFieldInput.setText(sText);
				}
			});
			btn4.setBounds(9, 135, 50, 45);
			contentPanel.add(btn4);
		}
		{
			JButton btn5 = new JButton("5");
			btn5.setFocusable(false);
			btn5.setForeground(Color.DARK_GRAY);
			btn5.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="5";
					textFieldInput.setText(sText);
				}
			});
			btn5.setBounds(65, 135, 50, 45);
			contentPanel.add(btn5);
		}
		{
			JButton btn6 = new JButton("6");
			btn6.setFocusable(false);
			btn6.setForeground(Color.DARK_GRAY);
			btn6.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="6";
					textFieldInput.setText(sText);
				}
			});
			btn6.setBounds(121, 135, 50, 45);
			contentPanel.add(btn6);
		}
		{
			JButton btn7 = new JButton("7");
			btn7.setFocusable(false);
			btn7.setForeground(Color.DARK_GRAY);
			btn7.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="7";
					textFieldInput.setText(sText);
				}
			});
			btn7.setBounds(9, 188, 50, 45);
			contentPanel.add(btn7);
		}
		{
			JButton btn8 = new JButton("8");
			btn8.setFocusable(false);
			btn8.setForeground(Color.DARK_GRAY);
			btn8.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="8";
					textFieldInput.setText(sText);
				}
			});
			btn8.setBounds(65, 188, 50, 45);
			contentPanel.add(btn8);
		}
		{
			JButton btn9 = new JButton("9");
			btn9.setFocusable(false);
			btn9.setForeground(Color.DARK_GRAY);
			btn9.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="9";
					textFieldInput.setText(sText);
				}
			});
			btn9.setBounds(121, 188, 50, 45);
			contentPanel.add(btn9);
		}
		{
			JButton btnKomma = new JButton(",");
			btnKomma.setFocusable(false);
			btnKomma.setForeground(Color.DARK_GRAY);
			btnKomma.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnKomma.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+=".";
					textFieldInput.setText(sText);
				}
			});
			btnKomma.setBounds(9, 241, 50, 45);
			
			if(bInteger)
				btnKomma.setEnabled(false);
			contentPanel.add(btnKomma);
		}
		{
			JButton btn0 = new JButton("0");
			btn0.setFocusable(false);
			btn0.setForeground(Color.DARK_GRAY);
			btn0.setFont(new Font("Tahoma", Font.BOLD, 14));
			btn0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sText+="0";
					textFieldInput.setText(sText);
				}
			});
			btn0.setBounds(65, 241, 50, 45);
			contentPanel.add(btn0);
		}
		{
			JButton btnDelete = new JButton("<");
			btnDelete.setFocusable(false);
			btnDelete.setForeground(Color.DARK_GRAY);
			btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					delete_Character();
				}
			});
			btnDelete.setBounds(121, 241, 50, 45);
			contentPanel.add(btnDelete);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(9, 297, 162, 50);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("");
				cancelButton.setFocusable(false);
				cancelButton.setIcon(new ImageIcon(NumberInputDialog.class.getResource("/images/Cancel_button.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setBounds(0, 0, 70, 50);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("");
				okButton.setFocusable(false);
				okButton.setIcon(new ImageIcon(NumberInputDialog.class.getResource("/images/OK_button.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ok_Action(sText);
					}
				});
				okButton.setBounds(92, 0, 70, 50);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		lblPlaus = new JLabel("");
		lblPlaus.setForeground(Color.RED);
		lblPlaus.setBounds(13, 63, 83, 14);
		contentPanel.add(lblPlaus);
		if(bInteger)
			lblLimits.setText("("+(int)(dLowerLimit)+" ... "+(int)dUpperLimit+")");
		else
			lblLimits.setText("("+dLowerLimit+" ... "+dUpperLimit+")");
		setLocationRelativeTo(parent);
		setVisible(true);
	}




	private void ok_Action(String stxt)
	{
		if(checkValid(stxt))
		{
			resultField.setText(stxt);
			removeAll();
			dispose();						
		}
		else
		{
			stxt="";
			sText="";
			textFieldInput.setText(stxt);
			lblLimits.setForeground(Color.RED);
			lblPlaus.setText("Invalid value!");
		}		
	}
	
	private void cancel_Action()
	{
		dispose();
	}
	
	private void delete_Character()
	{
		if(sText.length()>0)
		{
			sText=sText.substring(0,sText.length()-1);
			textFieldInput.setText(sText);
		}
	}
	
	private boolean checkValid(String sResult)
	{
		try{
			if(bInteger)
			{
				int iResult=Integer.parseInt(sResult);
				if(iResult>=dLowerLimit && iResult<=dUpperLimit)
				{
					newValue.setValue(iResult);
					return true;
				}
			}else
			{
				double dResult = Double.parseDouble(sResult);
				if(dResult>=dLowerLimit && dResult<=dUpperLimit)
				{
					newValue.setValue(dResult);
					return true;
				}
				
			}
			
		}catch(Exception e){
			return false;
		}
		return false;
	}
}
