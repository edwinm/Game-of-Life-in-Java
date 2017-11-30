package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CellGridCanvasMouseMotionHandler implements MouseMotionListener {
	private GameOfLifeAWTCellGrid canvas;
	
	public CellGridCanvasMouseMotionHandler(GameOfLifeAWTCellGrid canvas){
		this.canvas = canvas;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		canvas.draw(e.getX(), e.getY());
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	
	}
}
