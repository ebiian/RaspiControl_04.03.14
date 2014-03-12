package gui.widgets.inputdialogs;

import gui.widgets.BooleanSwitch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import constants.Constants;
import data.types.Value;

public class BooleanInputDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005792355497770468L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblVarName;
	private Value value;
	private boolean bActualSelection;
	private BooleanSwitch bSwitch;
	
	/**
	 * Create the dialog.
	 */
	public BooleanInputDialog(Value val, BooleanSwitch bSwit,JFrame parent) {
		setUndecorated(true);
		bSwitch=bSwit;
		value = val;
		bActualSelection=(boolean)val.getValue();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		
		setAlwaysOnTop(true);
		setBounds(100, 100, 187, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			panel.setForeground(Color.LIGHT_GRAY);
			panel.setBounds(0, 0, 186, 25);
			contentPanel.add(panel);
			
			lblVarName = new JLabel("New label");
			lblVarName.setFocusable(false);
			lblVarName.setHorizontalAlignment(SwingConstants.CENTER);
			lblVarName.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVarName.setForeground(Color.LIGHT_GRAY);
			lblVarName.setText(val.getLongText());
			panel.add(lblVarName);

			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(13, 76, 162, 50);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("");
				cancelButton.setFocusable(false);
				cancelButton.setIcon(new ImageIcon(BooleanInputDialog.class.getResource("/images/Cancel_button.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel_Action();
					}
				});
				cancelButton.setBounds(0, 0, 70, 50);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("");
				okButton.setFocusable(false);
				okButton.setIcon(new ImageIcon(BooleanInputDialog.class.getResource("/images/OK_button.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ok_Action();
					}
				});
				okButton.setBounds(92, 0, 70, 50);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JLabel lblActivateDeactivate;
			if(bActualSelection)
				lblActivateDeactivate= new JLabel(Constants.BOOL_DEACTIVATE);
			else
				lblActivateDeactivate= new JLabel(Constants.BOOL_ACTIVATE);
			lblActivateDeactivate.setForeground(Color.DARK_GRAY);
			lblActivateDeactivate.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblActivateDeactivate.setBounds(13, 36, 162, 24);
			contentPanel.add(lblActivateDeactivate);
		}
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	private void ok_Action()
	{
			value.setValue(!bActualSelection);
			bSwitch.setActive(!bActualSelection);
			removeAll();
			dispose();						
	}
	
	private void cancel_Action()
	{
		value.setValue(bActualSelection);
		removeAll();
		dispose();
	}
	
	
}
