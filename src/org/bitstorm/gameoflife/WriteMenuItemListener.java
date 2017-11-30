package org.bitstorm.gameoflife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteMenuItemListener implements ActionListener {
	private CellGridIO gridIO;
	public WriteMenuItemListener(CellGridIO gridIO){
		this.gridIO = gridIO;
	}
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		gridIO.saveShape();
	}
}
