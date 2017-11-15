package org.bitstorm.handler;

import org.bitstorm.gameoflife.GameControlsListener;
import org.bitstorm.gameoflife.GameOfLifeControls;
import org.bitstorm.gameoflife.GameOfLifeControlsEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

public class ZoomChoiceHandler implements ItemListener {
	private Vector listeners;
	
	public ZoomChoiceHandler(Vector listner){
		this.listeners = listner;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		String arg = (String) e.getItem();
		if (GameOfLifeControls.BIG.equals(arg))
			zoomChanged(GameOfLifeControls.SIZE_BIG);
		else if (GameOfLifeControls.MEDIUM.equals(arg))
			zoomChanged(GameOfLifeControls.SIZE_MEDIUM);
		else if (GameOfLifeControls.SMALL.equals(arg))
			zoomChanged(GameOfLifeControls.SIZE_SMALL);
	}
	
	/**
	 * Called when a new zoom from the zoom pull down is selected.
	 * Notify event-listeners.
	 */
	private void zoomChanged( int zoom ) {
		GameOfLifeControlsEvent event = GameOfLifeControlsEvent.getZoomChangedEvent( this, zoom );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((GameControlsListener) e.nextElement()).zoomChanged(event);
		}
	}
}
