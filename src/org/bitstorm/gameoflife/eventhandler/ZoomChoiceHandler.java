package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;
import org.bitstorm.gameoflife.ui.GameOfLifeUserControls;
import org.bitstorm.gameoflife.uicontrol.GameOfLifeUserControlsEvent;

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
		if (GameOfLifeUserControls.BIG.equals(arg))
			zoomChanged(GameOfLifeUserControls.SIZE_BIG);
		else if (GameOfLifeUserControls.MEDIUM.equals(arg))
			zoomChanged(GameOfLifeUserControls.SIZE_MEDIUM);
		else if (GameOfLifeUserControls.SMALL.equals(arg))
			zoomChanged(GameOfLifeUserControls.SIZE_SMALL);
	}
	
	/**
	 * Called when a new zoom from the zoom pull down is selected.
	 * Notify event-listeners.
	 */
	private void zoomChanged( int zoom ) {
		GameOfLifeUserControlsEvent event = GameOfLifeUserControlsEvent.getZoomChangedEvent( this, zoom );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((CellGameUserControlsListener) e.nextElement()).zoomChanged(event);
		}
	}
}
