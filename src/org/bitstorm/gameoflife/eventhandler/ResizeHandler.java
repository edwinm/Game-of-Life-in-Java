package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeHandler implements ComponentListener {
	private GameOfLifeAWTCellGrid canvas;
	
	public ResizeHandler(GameOfLifeAWTCellGrid canvas){
		this.canvas = canvas;
	}
	@Override
	public void componentResized(ComponentEvent e) {
		canvas.resized();
		canvas.repaint();
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
	
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
	
	}
}
