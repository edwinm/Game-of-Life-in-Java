package org.bitstorm.handler;

import org.bitstorm.gameoflife.GameOfLifeControlsEvent;
import org.bitstorm.gameoflife.GameControlsListener;

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
		GameOfLifeControlsEvent event = new GameOfLifeControlsEvent( this );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((GameControlsListener) e.nextElement()).startStopButtonClicked(event);
		}
	}
}
