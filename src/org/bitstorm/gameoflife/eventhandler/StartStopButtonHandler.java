package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.uicontrol.GameOfLifeUserControlsEvent;
import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

public class StartStopButtonHandler implements ActionListener {
	private Vector listeners;
	
	public StartStopButtonHandler(Vector listener){
		this.listeners = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		startStopButtonClicked();
	}
	
	/**
	 * Called when the start/stop-button is clicked.
	 * Notify event-listeners.
	 */
	private void startStopButtonClicked() {
		GameOfLifeUserControlsEvent event = new GameOfLifeUserControlsEvent( this );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((CellGameUserControlsListener) e.nextElement()).startStopButtonClicked(event);
		}
	}
}
