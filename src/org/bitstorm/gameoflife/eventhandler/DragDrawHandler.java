package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.ui.CellGridDrawer;
import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DragDrawHandler implements MouseMotionListener {
	private CellGridDrawer canvas;
	
	public DragDrawHandler(CellGridDrawer canvas){
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
