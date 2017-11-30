package org.bitstorm.gameoflife;


import org.bitstorm.util.AboutDialog;
import org.bitstorm.util.TextFileDialog;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * The window with the applet. Extra is the menu bar.
 *
 * @author Edwin Martin
 */
class AppletFrame extends Frame {
	private final AWTGameOfLife applet;
	/**
	 * Constructor.
	 * @param title title of window
	 * @param applet applet to show
	 */
	public AppletFrame(String title, StandaloneGameOfLife applet) throws MalformedURLException {
		super( title );
		this.applet = applet;
		
		URL iconURL = this.getClass().getResource("icon.gif");
		Image icon = Toolkit.getDefaultToolkit().getImage( iconURL );
		this.setIconImage( icon );
		
		enableEvents(Event.WINDOW_DESTROY);
		
		MenuBar menubar = new MenuBar();
		Menu fileMenu = new Menu("File", true);
		MenuItem readMenuItem = new MenuItem( "Open...");
		readMenuItem.addActionListener(new ReadMenuItemListener(applet, applet.getGameOfLifeGridIO()));
		MenuItem writeMenuItem = new MenuItem( "Save...");
		writeMenuItem.addActionListener(new WriteMenuItemListener(applet.getGameOfLifeGridIO()));
		MenuItem quitMenuItem = new MenuItem( "Exit");
		quitMenuItem.addActionListener(new QuitMenuItemListener());
		Menu helpMenu = new Menu("Help", true);
		MenuItem manualMenuItem = new MenuItem( "Manual");
		manualMenuItem.addActionListener(new ManualMenuItemListener(this));
		MenuItem licenseMenuItem = new MenuItem( "License");
		licenseMenuItem.addActionListener(new LicenseMenuItemListener(this));
		MenuItem aboutMenuItem = new MenuItem( "About");
		aboutMenuItem.addActionListener(new AboutMenuItemListener(this));
		fileMenu.add(readMenuItem);
		fileMenu.add(writeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);
		helpMenu.add(manualMenuItem);
		helpMenu.add(licenseMenuItem);
		helpMenu.add(aboutMenuItem);
		menubar.add(fileMenu);
		menubar.add(helpMenu);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints appletContraints = new GridBagConstraints();
		setLayout(gridbag);
		appletContraints.fill = GridBagConstraints.BOTH;
		appletContraints.weightx = 1;
		appletContraints.weighty = 1;
		gridbag.setConstraints(applet, appletContraints);
		setMenuBar(menubar);
		setResizable(true);
		add(applet);
		Toolkit screen = getToolkit();
		Dimension screenSize = screen.getScreenSize();
		// Java in Windows opens windows in the upper left corner, which is ugly! Center instead.
		if ( screenSize.width >= 640 && screenSize.height >= 480 )
			setLocation((screenSize.width-550)/2, (screenSize.height-400)/2);
		applet.init( this );
		applet.start();
		pack();
		// Read shape after initialization
		applet.readShape();
		// Bring to front. Sometimes it stays behind other windows.
		setVisible(true);
		toFront();
	}
	
	/**
	 * Process close window button.
	 * @see java.awt.Component#processEvent(java.awt.AWTEvent)
	 */
	public void processEvent( AWTEvent e ) {
		if ( e.getID() == Event.WINDOW_DESTROY )
			System.exit(0);
	}
	
	/**
	 * Show about dialog.
	 */
	private void showAboutDialog() {
		Properties properties = System.getProperties();
		String jvmProperties = "Java VM "+properties.getProperty("java.version")+" from "+properties.getProperty("java.vendor");
		Point p = getLocation();
		new AboutDialog( this, "About the Game of Life", new String[] {"Version 1.5 - Copyright 1996-2004 Edwin Martin", "http://www.bitstorm.org/gameoflife/", jvmProperties}, "about.jpg", p.x+100, p.y+60 );
	}
	
	/**
	 * Show manual.
	 */
	private void showManualDialog() {
		Point p = getLocation();
		new TextFileDialog( this, "Game of Life Manual", "manual.txt",  p.x+60, p.y+60 );
	}
	
	/**
	 * Show license.
	 */
	private void showLicenseDialog() {
		Point p = getLocation();
		new TextFileDialog( this, "Game of Life License", "license.txt", p.x+60, p.y+60 );
	}
	
	/**
	 * Get StandaloneGameOfLife object.
	 *
	 * @return StandaloneGameOfLife
	 */
	private StandaloneGameOfLife getStandaloneGameOfLife() {
		return (StandaloneGameOfLife) applet;
	}
}
