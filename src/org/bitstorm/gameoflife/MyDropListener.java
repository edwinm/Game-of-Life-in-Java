package org.bitstorm.gameoflife;


import org.bitstorm.gameoflife.cells.GameOfLifeGrid;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.net.URL;

/**
 * Handles drag and drops to the canvas.
 *
 * This class does handle the dropping of files and URL's to the canvas.
 * The code is based on the dnd-code from the book Professional Java Programming by Brett Spell.
 *
 * @author Edwin Martin
 *
 */
class MyDropListener implements DropTargetListener {
	private final DataFlavor urlFlavor = new DataFlavor("application/x-java-url; class=java.net.URL", "Game of Life URL");
	private CellGridIO gridIO;
	private CellGame game;
	public MyDropListener(AWTGameOfLife game, CellGridIO gridIO){
		this.gridIO = gridIO;
		this.game = game;
	}
	
	/**
	 * The canvas only supports Files and URL's
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent event) {
		if ( event.isDataFlavorSupported( DataFlavor.javaFileListFlavor ) || event.isDataFlavorSupported( urlFlavor ) ) {
			return;
		}
		event.rejectDrag();
	}
	
	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent event) {
	}
	
	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent event) {
	}
	
	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(DropTargetDragEvent event) {
	}
	
	/**
	 * The file or URL has been dropped.
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent event) {
		// important to first try urlFlavor
		if ( event.isDataFlavorSupported( urlFlavor ) ) {
			try {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				Transferable trans = event.getTransferable();
				URL url = (URL)( trans.getTransferData( urlFlavor ) );
				String urlStr = url.toString();
				gridIO.openShape( url );
				game.reset();
				event.dropComplete(true);
			} catch (Exception e) {
				event.dropComplete(false);
			}
		} else if ( event.isDataFlavorSupported( DataFlavor.javaFileListFlavor ) ) {
			try {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				Transferable trans = event.getTransferable();
				java.util.List list = (java.util.List)( trans.getTransferData( DataFlavor.javaFileListFlavor ) );
				File droppedFile = (File) list.get(0); // More than one file -> get only first file
				gridIO.openShape( droppedFile.getPath() );
				game.reset();
				event.dropComplete(true);
			} catch (Exception e) {
				event.dropComplete(false);
			}
		}
	}
}
