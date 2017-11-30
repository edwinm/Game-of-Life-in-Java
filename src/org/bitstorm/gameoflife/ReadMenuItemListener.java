package org.bitstorm.gameoflife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class ReadMenuItemListener implements ActionListener {
	private AWTGameOfLife game;
	private CellGridIO gridIO;
	public ReadMenuItemListener(AWTGameOfLife game, CellGridIO gridIO){
		this.game = game;
		this.gridIO = gridIO;
	}
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		try {
			gridIO.openShape();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		game.reset();
	}
}
