package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.ui.GameOfLifeUserControls;
import org.bitstorm.gameoflife.uicontrol.GameOfLifeUserControlsEvent;
import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

public class SpeedChoiceHandler implements ItemListener {
	private Vector listeners;
	
	public SpeedChoiceHandler(Vector listener){
		this.listeners = listener;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		String arg = (String) e.getItem();
		if (GameOfLifeUserControls.SLOW.equals(arg)) // slow
			speedChanged(1000);
		else if (GameOfLifeUserControls.FAST.equals(arg)) // fast
			speedChanged(100);
		else if (GameOfLifeUserControls.HYPER.equals(arg)) // hyperspeed
			speedChanged(10);
	}
	
	/**
	 * Called when a new speed from the speed pull down is selected.
	 * Notify event-listeners.
	 */
	private void speedChanged( int speed ) {
		GameOfLifeUserControlsEvent event = GameOfLifeUserControlsEvent.getSpeedChangedEvent( this, speed );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ) {
			((CellGameUserControlsListener) e.nextElement()).speedChanged(event);
		}
	}
}
