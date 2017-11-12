package org.bitstorm.handler;

import org.bitstorm.gameoflife.CellGridCanvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CellGridCanvasMouseMotionHandler implements MouseMotionListener {
	private CellGridCanvas canvas;
	
	public CellGridCanvasMouseMotionHandler(CellGridCanvas canvas){
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
