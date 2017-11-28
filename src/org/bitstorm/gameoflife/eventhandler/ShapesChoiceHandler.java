package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;
import org.bitstorm.gameoflife.uicontrol.GameOfLifeUserControlsEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

public class ShapesChoiceHandler implements ItemListener{
	private Vector listeners;
	
	public ShapesChoiceHandler(Vector listener){
		this.listeners = listener;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		shapeSelected( (String) e.getItem() );
	}

	/**
	 * Called when a new shape from the shape pull down is selected.
	 * Notify event-listeners.
	 */
	private void shapeSelected( String shapeName ) {
		GameOfLifeUserControlsEvent event = GameOfLifeUserControlsEvent.getShapeSelectedEvent( this, shapeName );
		for (Enumeration e = listeners.elements(); e.hasMoreElements(); ){
			((CellGameUserControlsListener) e.nextElement()).shapeSelected( event );
		}
	}
}
