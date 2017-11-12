package org.bitstorm.handler;

import org.bitstorm.gameoflife.CellGridCanvas;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class CellGridCanvasComponentHandler implements ComponentListener {
	private CellGridCanvas canvas;
	
	public CellGridCanvasComponentHandler(CellGridCanvas canvas){
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
