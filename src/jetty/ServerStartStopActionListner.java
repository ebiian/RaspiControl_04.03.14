package jetty;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ServerStartStopActionListner implements ActionListener {

	private final JettyServer jettyServer;
	private final ImageIcon webif_active = new ImageIcon(
			ServerStartStopActionListner.class
					.getResource("/images/webif_active.png"));
	private final ImageIcon webif_inactive = new ImageIcon(
			ServerStartStopActionListner.class
					.getResource("/images/webif_inactive.png"));

	// private JLabel lblState;

	public ServerStartStopActionListner(JettyServer jettyServer) {// , JLabel
		// serverState)
		// {
		this.jettyServer = jettyServer;
		// this.lblState = serverState;
		// lblState.setText("Web Interface is stopped!");

	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JButton btnStartStop = (JButton) actionEvent.getSource();

		if (jettyServer.isStarted()) {
			btnStartStop.setText("<html>Stopping<br>WebIF...</html>");
			btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				jettyServer.stop();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			btnStartStop.setText("<html>Start<br>WebIF</html>");
			btnStartStop.setIcon(webif_inactive);
			// lblState.setText("Web Interface is stopped!");
			btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (jettyServer.isStopped()) {
			btnStartStop.setText("<html>Starting<br>WebIF...</html>");
			btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				jettyServer.start();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			btnStartStop.setText("<html>Stopp<br>WebIF</html>");
			btnStartStop.setIcon(webif_active);
			// lblState.setText("Web Interface is running...");
			btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
