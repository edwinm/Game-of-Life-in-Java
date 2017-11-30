package org.bitstorm.gameoflife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitMenuItemListener implements ActionListener {
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
