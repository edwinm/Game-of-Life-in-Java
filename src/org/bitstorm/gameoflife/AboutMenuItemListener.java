package org.bitstorm.gameoflife;

import org.bitstorm.util.AboutDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class AboutMenuItemListener implements ActionListener {
	private Frame frame;
	public AboutMenuItemListener(Frame frame){
		this.frame = frame;
	}
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		
		Properties properties = System.getProperties();
		String jvmProperties = "Java VM "+properties.getProperty("java.version")+" from "+properties.getProperty("java.vendor");
		Point p = frame.getLocation();
		new AboutDialog( frame, "About the Game of Life", new String[] {"Version 1.5 - Copyright 1996-2004 Edwin Martin", "http://www.bitstorm.org/gameoflife/", jvmProperties}, "about.jpg", p.x+100, p.y+60 );
		
	}
}
