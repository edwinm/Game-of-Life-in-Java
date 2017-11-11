/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 * @author Edwin Martin
 */

package org.bitstorm.gameoflife;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Contains the cellgrid, the current shape and the Game Of Life algorithm that changes it.
 *
 * @author Edwin Martin
 */
public class GameOfLifeGrid extends CellGrid {
	public GameOfLifeGrid(int cellCols, int cellRows) {
		super(cellCols,cellRows);
	}

	/**
	 * Create next generation of shape.
	 */
	public synchronized void next() {
		Cell cell;
		int col, row;
		int neighbours;
		Enumeration enumi;

		generations++;
		nextShape.clear();

		// Reset cells
		enumi = currentShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			cell.neighbour = 0;
		}
		// Add neighbours
		// You can't walk through an hashtable and also add elements. Took me a couple of ours to figure out. Argh!
		// That's why we have a hashNew hashtable.
		enumi = currentShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			col = cell.col;
			row = cell.row;
			addNeighbour( col-1, row-1 );
			addNeighbour( col, row-1 );
			addNeighbour( col+1, row-1 );
			addNeighbour( col-1, row );
			addNeighbour( col+1, row );
			addNeighbour( col-1, row+1 );
			addNeighbour( col, row+1 );
			addNeighbour( col+1, row+1 );
		}
		
		// Bury the dead
		// We are walking through an enum from we are also removing elements. Can be tricky.
		enumi = currentShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			// Here is the Game Of Life rule (1):
			if ( cell.neighbour != 3 && cell.neighbour != 2 ) {
				currentShape.remove( cell );
			}
		}
		// Bring out the new borns
		enumi = nextShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			// Here is the Game Of Life rule (2):
			if ( cell.neighbour == 3 ) {
				setCell( cell.col, cell.row, true );
			}
		}
	}
}