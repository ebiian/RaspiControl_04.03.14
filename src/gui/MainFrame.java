package gui;

import gui.widgets.panels.BooleanSwitchPanel;
import gui.widgets.panels.DoubleInputPanel;
import gui.widgets.panels.MarkPanel;
import gui.widgets.panels.ScreenPage;
import gui.widgets.panels.SingleInputPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import jetty.AppContextBuilder;
import jetty.JettyServer;
import jetty.ServerStartStopActionListner;
import jetty.Users;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.tw.pi.framebuffer.FrameBuffer;

//import piSchas.PiUmanaundaDoa;


import org.tw.pi.framebuffer.TestFrameBuffer;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import constants.Constants;
import data.DataHandler;
import data.types.Value;


public class MainFrame implements Observer {

	public static boolean bWithoutHardware = false;  //395
	public static boolean bInitValues;
	public boolean bManual = true, bAuto = false;
	public JFrame mainFrame;
	private static final JettyServer jettyServer = new JettyServer();
	private JLabel labelActualTime;
	private final List<ScreenPage> screenPages = new ArrayList<ScreenPage>();
	private SwitchTimer switchTimer1;
	private DataHandler newDataHandler;
	private GpioController gpioController;
	private ScreenPage panelPage1;
	private JLabel lableScrPageName;
	//GPIO-Pins
	private GpioPinDigitalOutput gpioPin_DO00;
	private GpioPinDigitalInput gpioPin_DI00;

	private static MainFrame INSTANCE;

	public static MainFrame getInstance() {
		return INSTANCE;
	}

	private DataHandler getNewDataHandler() {
		if (newDataHandler == null) {
			newDataHandler = new DataHandler();
		}
		return newDataHandler;
	}

	private ImageIcon hand_inactive;
	private ImageIcon hand_active;
	private ImageIcon auto_inactive;
	private ImageIcon auto_active;

	private JButton buttonManual, buttonAuto, buttonStartStopWebIF;

	// page 1
	DoubleInputPanel singleInputPanel;
	Value valueX1;
	Value valueX2;
	Value markX1X2;
	Value valueY1;
	Value valueY2;

	BooleanSwitchPanel booleanSwitchPanel_1;
	Value valueB1;

	MarkPanel markPanel;
	Value valueDi1;

	MarkPanel markPanel_1;
	Value valueDo1;
	Value valueDo2;

	BooleanSwitchPanel booleanSwitchPanel_2;
	Value valueB2;
	// page2

	Value valueTemp1;
	SingleInputPanel simpleInputPanel;

	Value valueTemp2;
	SingleInputPanel simpleInputPanel2;

	Value valueTemp3;
	Value valueTemp4;
	DoubleInputPanel doubleInputPanel;

	Value valueTimer1Start;
	Value valueTimer1Stop;
	Value valueTimer1Active;
	DoubleInputPanel doubleInputPanel2;

	Value valueBool1;
	BooleanSwitchPanel booleanSwitchPanel;

	Value valueActTemp1;
	SingleInputPanel simpleInputPanel3;

	Value valueTemp5;
	Value valueActTemp5;
	DoubleInputPanel doubleInputPanel3;

	Value digout00;
	MarkPanel markPanelDigout00;

	Value digin00;
	MarkPanel markPanelDigin00;

	// page 3
	DoubleInputPanel doubleInputPanel_1;

	MarkPanel markPanel_2;

	private final List<String[]> users = new ArrayList<String[]>();
	
/*	private static LcdPanel INSTANCE1;
	
	public static LcdPanel getInstance() {
	      if(INSTANCE1 == null) {
	    	  INSTANCE1 = new LcdPanel();
	       }
		return INSTANCE1;
	}	
*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ContextHandlerCollection contexts = new ContextHandlerCollection();
				contexts.setHandlers(new Handler[] { new AppContextBuilder()
						.buildWebAppContext() });
				jettyServer.setHandler(contexts);
				try {
					MainFrame window = new MainFrame();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			// @Override
			@Override
			public void run() {
				if (jettyServer.isStarted()) {
					try {
						jettyServer.stop();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		}, "Stop Jetty Hook"));
		
		
		
	}


	/**
	 * Create the application.
	 */
	public MainFrame() {
		if (!bWithoutHardware)
			initGpio();
		getNewDataHandler();
		// NewDataHandler newDataHandler = new NewDataHandler();
		// initialize components
		initialize();
		DataHandler.getInstance().setActScreenPage(panelPage1); //initialize webinterface start page...
		lableScrPageName.setText(panelPage1.getPageName());
		DataHandler.getInstance().setAvailableScreenPages(screenPages);
		// assign fields an values
		initValues();

		// display actual time
		TimerTask task = new TimerTask() {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd.MM.yyyy  HH:mm:ss");

			@Override
			public void run() {
				date.setTime(System.currentTimeMillis());
				labelActualTime.setText(dateFormat.format(date));
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 0, 1000);

		// other init actions
		switchToManualMode();
		initWebIfUsers();
		
		LcdPanel.getInstanceLcdPanel().LcdPanelTest();  //395

		if (INSTANCE == null)
			INSTANCE = this;
				
	}

	private void initGpio() {
		try {
			gpioController = GpioFactory.getInstance();
		} catch (Exception e) {
			gpioController = null;
		}
	}

	private void showPage(int iNumber) {
		Iterator<ScreenPage> it = screenPages.iterator();
		ScreenPage page;
		while (it.hasNext()) {
			page = it.next();
			if (page.getPageNumber()==iNumber) {
				lableScrPageName.setText(page.getPageName());
				page.setVisible(true);
			} else {
				page.setVisible(false);
			}
		}
		mainFrame.invalidate();
		mainFrame.repaint();
	}

	private void initWebIfUsers() {
		users.add(Users.webIFuser1);
		users.add(Users.webIFuser2);
		users.add(Users.webIFuser3);
	}

	public List<String[]> getUsers() {
		return users;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		hand_inactive = new ImageIcon(
				MainFrame.class.getResource("/images/hand_inactive.png"));
		hand_active = new ImageIcon(
				MainFrame.class.getResource("/images/hand_active.png"));
		auto_inactive = new ImageIcon(
				MainFrame.class.getResource("/images/auto_inactive.png"));
		auto_active = new ImageIcon(
				MainFrame.class.getResource("/images/auto_active.png"));
		// MainFrame
		mainFrame = new JFrame();
		mainFrame.setTitle("piControl");
		mainFrame.setResizable(false);
		mainFrame.setPreferredSize(new Dimension(800, 480));
		mainFrame.setMinimumSize(new Dimension(800, 480));
		mainFrame.setBounds(100, 100, 808, 510);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
						

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.GRAY);
		topPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		topPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.setBounds(0, 0, 800, 27);
		mainFrame.getContentPane().add(topPanel);
		topPanel.setLayout(null);

		labelActualTime = new JLabel("time");
		labelActualTime.setHorizontalAlignment(SwingConstants.RIGHT);
		labelActualTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelActualTime.setForeground(Color.LIGHT_GRAY);
		labelActualTime.setBorder(null);
		labelActualTime.setBounds(572, 2, 218, 25);
		topPanel.add(labelActualTime);
		
		lableScrPageName = new JLabel("screenPageName");
		lableScrPageName.setForeground(Color.BLACK);
		lableScrPageName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lableScrPageName.setBounds(10, 2, 449, 25);
		topPanel.add(lableScrPageName);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GRAY);
		bottomPanel.setLayout(null);
		bottomPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bottomPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		bottomPanel.setBounds(0, 432, 800, 48);
		mainFrame.getContentPane().add(bottomPanel);

		JButton buttonExit = new JButton("");
		buttonExit.setBackground(Color.GRAY);
		buttonExit.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonExit.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/Exit.png")));
		buttonExit.setFocusable(false);
		buttonExit.setFocusTraversalKeysEnabled(false);
		buttonExit.setFocusPainted(false);
		buttonExit.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonExit.addMouseListener(new MouseAdapter() {
			javax.swing.Timer exitDelayTimer;

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (bAuto)
					switchToManualMode();// stop automatic mode
				exitDelayTimer = new javax.swing.Timer(100,
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (gpioController != null) {
									gpioController.shutdown();
								}
								System.exit(0);
							}
						});
				exitDelayTimer.setInitialDelay(2000); // button has to be
														// pressed 2 seconds to
														// exit...
				exitDelayTimer.start();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				exitDelayTimer.stop();
			}
		});

		buttonExit.setForeground(Color.LIGHT_GRAY);
		buttonExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonExit.setBounds(656, 3, 70, 43);
		bottomPanel.add(buttonExit);

		buttonStartStopWebIF = new JButton("<html>Start<br>WebIF</html>");
		buttonStartStopWebIF.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonStartStopWebIF.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/webif_inactive.png")));
		buttonStartStopWebIF.setFocusTraversalKeysEnabled(false);
		buttonStartStopWebIF.setFocusable(false);
		buttonStartStopWebIF.setFocusPainted(false);
		buttonStartStopWebIF.setBorder(new SoftBevelBorder(BevelBorder.RAISED,
				null, null, null, null));
		buttonStartStopWebIF.setBackground(Color.GRAY);
		buttonStartStopWebIF.setForeground(Color.DARK_GRAY);
		buttonStartStopWebIF.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonStartStopWebIF.setBounds(584, 3, 70, 43);
		buttonStartStopWebIF
				.addActionListener(new ServerStartStopActionListner(jettyServer));
		bottomPanel.add(buttonStartStopWebIF);

		buttonManual = new JButton("");
		buttonManual.setBackground(Color.GRAY);
		buttonManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchToManualMode();
			}
		});
		buttonManual.setForeground(Color.LIGHT_GRAY);
		buttonManual
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		buttonManual.setPreferredSize(new Dimension(2, 2));
		buttonManual.setIcon(hand_inactive);
		buttonManual.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonManual.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonManual.setFocusable(false);
		buttonManual.setFocusTraversalKeysEnabled(false);
		buttonManual.setFocusPainted(false);
		buttonManual.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonManual.setBounds(10, 3, 70, 43);
		bottomPanel.add(buttonManual);

		buttonAuto = new JButton("");
		buttonAuto.setBackground(Color.GRAY);
		buttonAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchToAutoMode();
			}
		});
		buttonAuto.setForeground(Color.LIGHT_GRAY);
		buttonAuto.setIcon(auto_inactive);
		buttonAuto.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonAuto.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonAuto.setFocusable(false);
		buttonAuto.setFocusTraversalKeysEnabled(false);
		buttonAuto.setFocusPainted(false);
		buttonAuto.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonAuto.setBounds(82, 3, 70, 43);
		bottomPanel.add(buttonAuto);
		
		JPanel panel = new JPanel();
		panel.setBounds(733, 28, 67, 404);
		mainFrame.getContentPane().add(panel);
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel.setBackground(Color.GRAY);
		panel.setLayout(null);

		JButton buttonPage1 = new JButton("Page 1");
		buttonPage1.setSize(new Dimension(65, 25));
		buttonPage1.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage1.setMaximumSize(new Dimension(65, 30));
		buttonPage1.setMinimumSize(new Dimension(65, 30));
		buttonPage1.setPreferredSize(new Dimension(65, 30));
		buttonPage1.setForeground(Color.LIGHT_GRAY);
		buttonPage1.setBackground(Color.GRAY);
		buttonPage1.setBounds(0, 0, 66, 45);
		panel.add(buttonPage1);
		buttonPage1.setFocusable(false);
		buttonPage1.setFocusTraversalKeysEnabled(false);
		buttonPage1.setFocusPainted(false);
		buttonPage1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonPage1.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton buttonPage2 = new JButton("Page 2");
		buttonPage2.setSize(new Dimension(65, 25));
		buttonPage2.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage2.setMaximumSize(new Dimension(65, 30));
		buttonPage2.setMinimumSize(new Dimension(65, 30));
		buttonPage2.setPreferredSize(new Dimension(65, 30));
		buttonPage2.setForeground(Color.LIGHT_GRAY);
		buttonPage2.setBackground(Color.GRAY);
		buttonPage2.setBounds(0, 45, 66, 45);
		panel.add(buttonPage2);
		buttonPage2.setFocusable(false);
		buttonPage2.setFocusTraversalKeysEnabled(false);
		buttonPage2.setFocusPainted(false);
		buttonPage2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonPage2.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton buttonPage3 = new JButton("Page 3");
		buttonPage3.setSize(new Dimension(65, 25));
		buttonPage3.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage3.setMaximumSize(new Dimension(65, 30));
		buttonPage3.setMinimumSize(new Dimension(65, 30));
		buttonPage3.setPreferredSize(new Dimension(65, 30));
		buttonPage3.setForeground(Color.LIGHT_GRAY);
		buttonPage3.setBackground(Color.GRAY);
		buttonPage3.setBounds(0, 90, 66, 45);
		panel.add(buttonPage3);
		buttonPage3.setFocusable(false);
		buttonPage3.setFocusTraversalKeysEnabled(false);
		buttonPage3.setFocusPainted(false);
		buttonPage3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,
				null, null, null));
		buttonPage3.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton buttonPage4 = new JButton("Page 4");
		buttonPage4.setEnabled(false);
		buttonPage4.setSize(new Dimension(65, 25));
		buttonPage4.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage4.setMaximumSize(new Dimension(65, 30));
		buttonPage4.setMinimumSize(new Dimension(65, 30));
		buttonPage4.setPreferredSize(new Dimension(65, 30));
		buttonPage4.setForeground(Color.LIGHT_GRAY);
		buttonPage4.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage4.setFocusable(false);
		buttonPage4.setFocusTraversalKeysEnabled(false);
		buttonPage4.setFocusPainted(false);
		buttonPage4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage4.setBackground(Color.GRAY);
		buttonPage4.setBounds(0, 135, 66, 45);
		panel.add(buttonPage4);

		JButton buttonPage5 = new JButton("Page 5");
		buttonPage5.setEnabled(false);
		buttonPage5.setSize(new Dimension(65, 25));
		buttonPage5.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage5.setMaximumSize(new Dimension(65, 30));
		buttonPage5.setMinimumSize(new Dimension(65, 30));
		buttonPage5.setPreferredSize(new Dimension(65, 30));
		buttonPage5.setForeground(Color.LIGHT_GRAY);
		buttonPage5.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage5.setFocusable(false);
		buttonPage5.setFocusTraversalKeysEnabled(false);
		buttonPage5.setFocusPainted(false);
		buttonPage5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage5.setBackground(Color.GRAY);
		buttonPage5.setBounds(0, 180, 66, 45);
		panel.add(buttonPage5);

		JButton buttonPage6 = new JButton("Page 6");
		buttonPage6.setEnabled(false);
		buttonPage6.setSize(new Dimension(65, 25));
		buttonPage6.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage6.setMaximumSize(new Dimension(65, 30));
		buttonPage6.setMinimumSize(new Dimension(65, 30));
		buttonPage6.setPreferredSize(new Dimension(65, 30));
		buttonPage6.setForeground(Color.LIGHT_GRAY);
		buttonPage6.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage6.setFocusable(false);
		buttonPage6.setFocusTraversalKeysEnabled(false);
		buttonPage6.setFocusPainted(false);
		buttonPage6.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage6.setBackground(Color.GRAY);
		buttonPage6.setBounds(0, 225, 66, 45);
		panel.add(buttonPage6);

		JButton buttonPage7 = new JButton("Page 7");
		buttonPage7.setEnabled(false);
		buttonPage7.setSize(new Dimension(65, 25));
		buttonPage7.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage7.setMaximumSize(new Dimension(65, 30));
		buttonPage7.setMinimumSize(new Dimension(65, 30));
		buttonPage7.setPreferredSize(new Dimension(65, 30));
		buttonPage7.setForeground(Color.LIGHT_GRAY);
		buttonPage7.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage7.setFocusable(false);
		buttonPage7.setFocusTraversalKeysEnabled(false);
		buttonPage7.setFocusPainted(false);
		buttonPage7.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage7.setBackground(Color.GRAY);
		buttonPage7.setBounds(0, 270, 66, 45);
		panel.add(buttonPage7);

		JButton buttonPage8 = new JButton("Page 8");
		buttonPage8.setEnabled(false);
		buttonPage8.setSize(new Dimension(65, 25));
		buttonPage8.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage8.setMaximumSize(new Dimension(65, 30));
		buttonPage8.setMinimumSize(new Dimension(65, 30));
		buttonPage8.setPreferredSize(new Dimension(65, 30));
		buttonPage8.setForeground(Color.LIGHT_GRAY);
		buttonPage8.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage8.setFocusable(false);
		buttonPage8.setFocusTraversalKeysEnabled(false);
		buttonPage8.setFocusPainted(false);
		buttonPage8.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage8.setBackground(Color.GRAY);
		buttonPage8.setBounds(0, 315, 66, 45);
		panel.add(buttonPage8);

		JButton buttonPage9 = new JButton("Page 9");
		buttonPage9.setEnabled(false);
		buttonPage9.setSize(new Dimension(65, 25));
		buttonPage9.setPreferredSize(new Dimension(65, 30));
		buttonPage9.setMinimumSize(new Dimension(65, 30));
		buttonPage9.setMaximumSize(new Dimension(65, 30));
		buttonPage9.setForeground(Color.LIGHT_GRAY);
		buttonPage9.setFont(new Font("Tahoma", Font.BOLD, 10));
		buttonPage9.setFocusable(false);
		buttonPage9.setFocusTraversalKeysEnabled(false);
		buttonPage9.setFocusPainted(false);
		buttonPage9.setBounds(new Rectangle(0, 0, 65, 30));
		buttonPage9.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null,

		null, null, null));
		buttonPage9.setBackground(Color.GRAY);
		buttonPage9.setBounds(0, 360, 66, 45);
		panel.add(buttonPage9);
		buttonPage3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPage(3);
			}
		});
		buttonPage2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPage(2);
			}
		});
		buttonPage1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPage(1);
			}
		});
	
		
		//SCREENPAGES
		// Page1
		panelPage1 = new ScreenPage();
		panelPage1.setPageName("Seite 1");
		panelPage1.setPageNumber(1);
		panelPage1.setPageButton(buttonPage1);
		panelPage1.setBounds(0, 28, 733, 403);
		screenPages.add(panelPage1);
		mainFrame.getContentPane().add(panelPage1);
		panelPage1.setLayout(null);
								
		singleInputPanel = new DoubleInputPanel();
		singleInputPanel.setBounds(10, 11, 699, 39);
		panelPage1.add(singleInputPanel);
		
		markPanel_1 = new MarkPanel(Color.red);
		markPanel_1.setBounds(10, 87, 699, 39);
		panelPage1.add(markPanel_1);
		
		markPanel = new MarkPanel(Color.green);
		markPanel.setBounds(10, 50, 699, 39);
		panelPage1.add(markPanel);
				
		ScreenPage panelPage2 = new ScreenPage();
		panelPage2.setForeground(Color.DARK_GRAY);
		panelPage2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelPage2.setPageName("Seite 2");
		panelPage2.setPageNumber(2);
		panelPage2.setPageButton(buttonPage2);
		panelPage2.setBounds(0, 28, 733, 403);
		screenPages.add(panelPage2);
		mainFrame.getContentPane().add(panelPage2);
		panelPage2.setLayout(null);
		
		simpleInputPanel = new SingleInputPanel();
		simpleInputPanel.getTextLabel().setLocation(10, 14);
		simpleInputPanel.getInputField().setLocation(538, 11);
		simpleInputPanel.setBounds(10, 11, 700, 40);
		simpleInputPanel.setName("sautrottl");
		panelPage2.add(simpleInputPanel);
				
		simpleInputPanel2 = new SingleInputPanel();
		simpleInputPanel2.getTextLabel().setLocation(10, 14);
		simpleInputPanel2.getUnitLabel().setLocation(630, 14);
		simpleInputPanel2.getInputField().setLocation(538, 11);
		simpleInputPanel2.setBounds(10, 49, 700, 40);
		panelPage2.add(simpleInputPanel2);
		
		markPanelDigout00 = new MarkPanel(Color.red);
		markPanelDigout00.setBounds(10, 89, 700, 40);
		panelPage2.add(markPanelDigout00);
								
		markPanelDigin00 = new MarkPanel(Color.green);
		markPanelDigin00.setBounds(10, 327, 700, 40);
		panelPage2.add(markPanelDigin00);
										
		doubleInputPanel = new DoubleInputPanel();
		doubleInputPanel.getTextLabel().setLocation(10, 14);
		doubleInputPanel.getUnitLabel2().setLocation(630, 14);
		doubleInputPanel.getInputField2().setLocation(538, 11);
		doubleInputPanel.setBounds(10, 129, 700, 40);
		panelPage2.add(doubleInputPanel);

		doubleInputPanel2 = new DoubleInputPanel();
		doubleInputPanel2.getTextLabel().setLocation(10, 14);
		doubleInputPanel2.getUnitLabel2().setLocation(630, 14);
		doubleInputPanel2.getInputField2().setLocation(538, 11);
		doubleInputPanel2.setBounds(10, 170, 700, 40);
		panelPage2.add(doubleInputPanel2);
				
		booleanSwitchPanel = new BooleanSwitchPanel();
		booleanSwitchPanel.getSwitch().setLocation(595, 10);
		booleanSwitchPanel.setBounds(10, 209, 700, 40);
		panelPage2.add(booleanSwitchPanel);
		
		simpleInputPanel3 = new SingleInputPanel();
		simpleInputPanel3.getTextLabel().setLocation(10, 14);
		simpleInputPanel3.getUnitLabel().setLocation(630, 14);
		simpleInputPanel3.getInputField().setLocation(538, 11);
		simpleInputPanel3.setBounds(10, 247, 700, 40);
		panelPage2.add(simpleInputPanel3);
				
		doubleInputPanel3 = new DoubleInputPanel();
		doubleInputPanel3.getUnitLabel2().setLocation(630, 14);
		doubleInputPanel3.setBounds(10, 287, 700, 40);
		panelPage2.add(doubleInputPanel3);

		// page3
		ScreenPage panelPage3 = new ScreenPage();
		panelPage3.setPageName("Seite 3");
		panelPage3.setPageNumber(3);
		panelPage1.setPageButton(buttonPage3);
		panelPage3.setBounds(0, 28, 733, 403);
		screenPages.add(panelPage3);
		mainFrame.getContentPane().add(panelPage3);
		panelPage3.setLayout(null);

		doubleInputPanel_1 = new DoubleInputPanel();
		doubleInputPanel_1.setBounds(10, 11, 699, 39);
		panelPage3.add(doubleInputPanel_1);

		markPanel_2 = new MarkPanel(Color.RED);
		markPanel_2.setBounds(10, 49, 699, 39);
		panelPage3.add(markPanel_2);

		booleanSwitchPanel_1 = new BooleanSwitchPanel();
		booleanSwitchPanel_1.setBounds(0, 40, 699, 39);

	}

	public void switchToManualMode() {
		buttonAuto.setIcon(auto_inactive);
		buttonManual.setIcon(hand_active);
		DataHandler.getInstance().setOpMode(Constants.OPMODE_MANUAL);
		bAuto = false;
		stopTimers();
	}

	public void switchToAutoMode() {
		buttonAuto.setIcon(auto_active);
		buttonManual.setIcon(hand_inactive);
		DataHandler.getInstance().setOpMode(Constants.OPMODE_AUTOMATIC);
		bManual = false;
		bAuto = true;
		startTimers();
	}

	private void startTimers() {
		int hStart, hStop;
		String[] t = ((String) valueTimer1Start.getValue()).split(":");
		Calendar start = Calendar.getInstance();
		hStart = Integer.parseInt(t[0]);
		start.set(Calendar.HOUR_OF_DAY, hStart);
		start.set(Calendar.MINUTE, Integer.parseInt(t[1]));
		start.set(Calendar.SECOND, Integer.parseInt(t[2]));
		t = ((String) valueTimer1Stop.getValue()).split(":");
		Calendar stop = Calendar.getInstance();
		hStop = Integer.parseInt(t[0]);
		if (hStart > hStop) {
			stop.add(Calendar.DAY_OF_MONTH, 1);
		}
		stop.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
		stop.set(Calendar.MINUTE, Integer.parseInt(t[1]));
		stop.set(Calendar.SECOND, Integer.parseInt(t[2]));
		if (switchTimer1 == null) {
			switchTimer1 = new SwitchTimer(valueTimer1Active, start.getTime(),
					stop.getTime());
		}
	}

	private void stopTimers() {
		if (switchTimer1 != null) {
			switchTimer1.stopTimer();
			switchTimer1 = null;
		}
	}

	private void initValues() {
		bInitValues = true;

		List<Value> values = new ArrayList<Value>(); //

		// Register all variables:
		// 1) new value
		// 2) add to List (for reading)
		// 3) add observer

		// PAGE1 variables
		valueX1 = new Value("valueX1", "Schas1", new Integer(0), 0.0, 999.9,
				"Stk", singleInputPanel, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueX1);
		valueX1.addObserver(this);
		valueX2 = new Value("valueX2", "Schas1.2", new Integer(0), 0.0, 999.9,
				"Stk", singleInputPanel, Constants.PANEL_VARIABLE_2,
				Constants.READWRITE, null);
		values.add(valueX2);
		valueX2.addObserver(this);
		markX1X2 = new Value("markX1X2", "OK", new Boolean(true), 0.0, 0.0, "",
				singleInputPanel, Constants.PANEL_MARK, Constants.READONLY,
				null);
		values.add(markX1X2);
		markX1X2.addObserver(this);

		valueY1 = new Value("valueY1", "schas 3", (new Integer(0)), 0.0, 100.0,
				"Stk", doubleInputPanel_1, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueY1);
		valueY1.addObserver(this);
		valueY2 = new Value("valueY2", "schas 3.2", (new Integer(0)), 0.0,
				100.0, "Stk", doubleInputPanel_1, Constants.PANEL_VARIABLE_2,
				Constants.READWRITE, null);
		values.add(valueY2);
		valueY2.addObserver(this);

		valueDi1 = new Value("valueDi1", "Schas 5", (new Boolean(false)), 0.0,
				0.0, "", markPanel, Constants.PANEL_VARIABLE_1,
				Constants.READONLY, null);
		values.add(valueDi1);
		valueDi1.addObserver(this);

		valueDo1 = new Value("valueDo1", "Schas 6", (new Boolean(false)), 0.0,
				0.0, "", markPanel_1, Constants.PANEL_VARIABLE_1,
				Constants.READONLY, null);
		values.add(valueDo1);
		valueDo1.addObserver(this);
		valueDo2 = new Value("valueDo2", "Schas 7", (new Boolean(false)), 0.0,
				0.0, "", markPanel_2, Constants.PANEL_VARIABLE_1,
				Constants.READONLY, null);
		values.add(valueDo2);
		valueDo2.addObserver(this);

		// PAGE2 variables
		valueTemp1 = new Value("valueTemp1", "Temperatur 1", (new Integer(0)),
				2.0, 25.0, "°C", simpleInputPanel, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueTemp1);
		valueTemp1.addObserver(this);// observertest

		valueTemp2 = new Value("valueTemp2", "Temperatur 2", (new Double(0)),
				0.3, 99.7, "°C", simpleInputPanel2, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueTemp2);
		valueTemp2.addObserver(this);// observertest

		valueTemp3 = new Value("valueTemp3", "Temperatur 3", (new Double(0)),
				0.0, 100.0, "°C", doubleInputPanel, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueTemp3);
		valueTemp3.addObserver(this);// observertest
		valueTemp4 = new Value("valueTemp4", "Temperatur 4", (new Double(0)),
				0.0, 200.0, "°C", doubleInputPanel, Constants.PANEL_VARIABLE_2,
				Constants.READWRITE, null);
		values.add(valueTemp4);
		valueTemp4.addObserver(this);// observertest

		valueTimer1Start = new Value("valueTimer1Start", "Timer 1 start",
				(new String("00:00:00")), 0.0, 0.0, "Uhr", doubleInputPanel2,
				Constants.PANEL_VARIABLE_1, Constants.READWRITE, null);
		values.add(valueTimer1Start);
		valueTimer1Start.addObserver(this);// observertest
		valueTimer1Stop = new Value("valueTimer1Stop", "Timer 1 stopp",
				(new String("00:00:00")), 0.0, 0.0, "Uhr", doubleInputPanel2,
				Constants.PANEL_VARIABLE_2, Constants.READWRITE, null);
		values.add(valueTimer1Stop);
		valueTimer1Stop.addObserver(this);// observertest
		valueTimer1Active = new Value("valueTimer1Active", "Timer 1 aktiv",
				(new Boolean(false)), 0.0, 0.0, "", doubleInputPanel2,
				Constants.PANEL_MARK, Constants.READONLY, null);
		values.add(valueTimer1Active);
		valueTimer1Active.addObserver(this);// observertest

		valueBool1 = new Value("valueBool1", "Schalter 1", (new Boolean(true)),
				0.0, 0.0, "", booleanSwitchPanel, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueBool1);
		valueBool1.addObserver(this);// observertest

		valueActTemp1 = new Value("valueActTemp1", "Messwert 1", (new Double(
				20.5)), 0.0, 0.0, "°C", simpleInputPanel3,
				Constants.PANEL_VARIABLE_1, Constants.READONLY, null);
		values.add(valueActTemp1);
		valueActTemp1.addObserver(this);// observertest

		valueTemp5 = new Value("valueTemp5", "Sollwert xy", (new Integer(0)),
				2.0, 25.0, "°C", doubleInputPanel3, Constants.PANEL_VARIABLE_1,
				Constants.READWRITE, null);
		values.add(valueTemp5);
		valueTemp5.addObserver(this);// observertest
		valueActTemp5 = new Value("valueActTemp5", "Istwert xy", (new Double(
				20.5)), 0.0, 0.0, "°C", doubleInputPanel3,
				Constants.PANEL_VARIABLE_2, Constants.READONLY, null);
		values.add(valueActTemp5);
		valueActTemp5.addObserver(this);// observertest
/*
		//DIGOUT00
		if (gpioController != null) {
			gpioPin_DO00 = gpioController.provisionDigitalOutputPin(
					RaspiPin.GPIO_04, "Dogout 00", PinState.LOW);
		} else {
			gpioPin_DO00 = null;
		}
		digout00 = new Value("digout00", "Digitaler Ausgang 0", (new Boolean(
				false)), 0.0, 0.0, "", markPanelDigout00,
				Constants.PANEL_VARIABLE_1, Constants.READONLY, gpioPin_DO00);
		values.add(digout00);
		digout00.addObserver(this);// observertest


		//DIGIN00
		if (gpioController != null) {
			gpioPin_DI00 = gpioController.provisionDigitalInputPin(
					RaspiPin.GPIO_05, "Digin 00", PinPullResistance.PULL_DOWN);
			gpioPin_DI00.addListener(new GpioPinListenerDigital() {
	            @Override
	            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	                //System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
	                digin00.setValue(event.getState().isHigh());
	            }
	            
	        });
		} else {
			gpioPin_DI00 = null;
		}
		digin00 = new Value("digin00", "Digitaler Eingang 0", (new Boolean(
				false)), 0.0, 0.0, "", markPanelDigin00,
				Constants.PANEL_VARIABLE_1, Constants.READONLY, gpioPin_DI00);
		values.add(digin00);
		digin00.addObserver(this);// observertest
		
*/		

		// Read all values from stored file
		if (getNewDataHandler().paramFileExists()) {
			List<String> paramLines = new ArrayList<String>();
			paramLines = getNewDataHandler().readParams();
			String[] lineParts;
			String sVarName, sVarType;
			Object varVal = null;
			double dPlausMin, dPlausMax;
			for (String line : paramLines) {
				lineParts = line.split(";");
				sVarName = lineParts[0];
				sVarType = lineParts[2];

				switch (sVarType) {
				case Constants.VARTYPE_INT:
					varVal = Integer.parseInt(lineParts[3]);
					break;
				case Constants.VARTYPE_DOUBLE:
					varVal = Double.parseDouble(lineParts[3]);
					break;
				case Constants.VARTYPE_BOOLEAN:
					varVal = lineParts[3];
					switch ((String) varVal) {
					case Constants.BOOLEAN_TRUE:
						varVal = true;
						break;
					case Constants.BOOLEAN_FALSE:
						varVal = false;
						break;
					}
					break;
				case Constants.VARTYPE_STRING:
					varVal = lineParts[3];
					break;
				}
				dPlausMin = Double.parseDouble(lineParts[4]);
				dPlausMax = Double.parseDouble(lineParts[5]);

				for (Value val : values) {
					if (sVarName.equals(val.getVarName())) {
						val.setValue(varVal);
						val.setPlaus(dPlausMin, dPlausMax);
					}
				}
			}
		}
		bInitValues = false;

		// kennung, auf welcher bildschirmseite ein anzeigeelement liegt ist.
		int iPage = 0;
		for (ScreenPage page : screenPages) {
			iPage++;
			for (int i = 0; i < page.getComponentCount(); i++) {
				page.getComponent(i).setName(Constants.PAGE + iPage);
			}

		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Value-observer: " + ((Value) arg0).getVarName()
				+ " changed to " + arg1);
		
		
		//timer 1 sets digout00...
		if (((Value) arg0).getVarName().equals(valueTimer1Active.getVarName())) {
			digout00.setValue(((Value) arg0).getValue());
		}
		if ( (bAuto)&&( ((Value) arg0).getVarName().equals(valueTimer1Start.getVarName()) || ((Value) arg0).getVarName().equals(valueTimer1Stop.getVarName())) ) {
			//timer restarten..
			Thread tRestartTimer = new Thread(new Runnable()
			{
			    public void run()
			    {
			        SwingUtilities.invokeLater(new Runnable()
			        {
			            public void run()
			            {
							stopTimers();
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							startTimers();
			            }
			        });
			    }
			});
			tRestartTimer.start();		
		}
		
	}

//lcd test ----------------------------------------------------------------------------------	


}
