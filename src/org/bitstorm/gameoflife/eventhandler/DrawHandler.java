package org.bitstorm.gameoflife.eventhandler;

import org.bitstorm.gameoflife.ui.CellGridDrawer;
import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawHandler implements MouseListener {
	private CellGridDrawer canvas;
	
	public DrawHandler(CellGridDrawer canvas){
		this.canvas = canvas;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		canvas.saveCellUnderMouse(e.getX(), e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		canvas.draw(e.getX(), e.getY());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	
	}
}
