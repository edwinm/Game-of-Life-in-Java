package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;
import org.bitstorm.gameoflife.uicontrol.GameOfLifeUserControlsEvent;

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
		GameOfLifeUserControlsEvent event = new GameOfLifeUserControlsEvent( this );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((CellGameUserControlsListener) e.nextElement()).nextButtonClicked(event);
		}
	}
}
