package org.bitstorm.handler;

import org.bitstorm.gameoflife.GameOfLifeControlsEvent;
import org.bitstorm.gameoflife.GameOfLifeControlsListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

public class NextButtonHandler implements ActionListener{
	private Vector listeners;
	
	public NextButtonHandler(Vector listener){
		this.listeners = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		nextButtonClicked();
	}
	
	/**
	 * Called when the next-button is clicked.
	 * Notify event-listeners.
	 */
	private void nextButtonClicked() {
		GameOfLifeControlsEvent event = new GameOfLifeControlsEvent( this );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((GameOfLifeControlsListener) e.nextElement()).nextButtonClicked(event);
		}
	}
}
