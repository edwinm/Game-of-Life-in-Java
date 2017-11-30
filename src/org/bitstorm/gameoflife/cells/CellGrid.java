/**
 * Interface to Game of Life Grid.
 * The GameOfLifeAWTCellGrid can deal with any grid, not only the Game of Life.
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 * @author Edwin Martin
 */

package org.bitstorm.gameoflife.cells;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Interface between GameOfLifeCanvas and AWTGameOfLife.
 * This way GameOfLifeCanvas is generic, independent of AWTGameOfLife.
 * It contains generic methods to operate on a cell grid.
 *
 * @author Edwin Martin
 */
public abstract class CellGrid {
	/**
	 * Get status of cell (alive or dead).
	 * @param col x-position
	 * @param row y-position
	 * @return living or not
	 */

	private int cellRows;
	private int cellCols;
	protected int generations;
	protected static Shape[] shapes;
	/**
	 * Contains the current, living shape.
	 * It's implemented as a hashtable. Tests showed this is 70% faster than Vector.
	 */
	protected Hashtable currentShape;
	protected Hashtable nextShape;
	/**
	 * Every cell on the grid is a Cell object. This object can become quite large.
	 */
	protected Cell[][] grid;
	protected CellGameRule rule = new NullRule();


	/**
	 * Contructs a GameOfLifeGrid.
	 *
	 * @param cellCols number of columns
	 * @param cellRows number of rows
	 */
	public CellGrid(int cellCols, int cellRows) {
		this.cellCols = cellCols;
		this.cellRows = cellRows;
		currentShape = new Hashtable();
		nextShape = new Hashtable();

		grid = new Cell[cellCols][cellRows];
		for ( int c=0; c<cellCols; c++)
			for ( int r=0; r<cellRows; r++ )
				grid[c][r] = new Cell( c, r );
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
			if ( this.rule.diesNext(cell)) {
				currentShape.remove( cell );
			}
		}
		// Bring out the new borns
		enumi = nextShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			// Here is the Game Of Life rule (2):
			if ( this.rule.bornsNext(cell) ) {
				setCell( cell.col, cell.row, true );
			}
		}
	}

	/**
	 * Adds a new neighbour to a cell.
	 *
	 * @param col Cell-column
	 * @param row Cell-row
	 */
	public synchronized void addNeighbour(int col, int row) {
		try {
			Cell cell = (Cell)nextShape.get( grid[col][row] );
			if ( cell == null ) {
				// Cell is not in hashtable, then add it
				Cell c = grid[col][row];
				c.neighbour = 1;
				nextShape.put(c, c);
			} else {
				// Else, increments neighbour count
				cell.neighbour++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		}
	}

	/**
	 * Get value of cell.
	 * @param col x-coordinate of cell
	 * @param row y-coordinate of cell
	 * @return value of cell
	 */
	public synchronized boolean getCell( int col, int row ) {
		try {
			return currentShape.containsKey(grid[col][row]);
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		}
		return false;
	}

	/**
	 * Set value of cell.
	 * @param col x-coordinate of cell
	 * @param row y-coordinate of cell
	 * @param c value of cell
	 */
	public synchronized void setCell( int col, int row, boolean c ) {
		try {
			Cell cell = grid[col][row];
			if ( c ) {
				currentShape.put(cell, cell);
			} else {
				currentShape.remove(cell);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore
		}
	}

	/**
	 * Get number of generations.
	 * @return number of generations
	 */
	public int getGenerations() {
		return generations;
	}
	
	/**
	 * Resize the cell grid.
	 * @param col new number of columns.
	 * @param row new number of rows.
	 */

	/**
	 * Resize grid. Reuse existing cells.
	 * @see CellGrid#resize(int, int)
	 */
	public synchronized void resize(int cellColsNew, int cellRowsNew) {
		if ( cellCols==cellColsNew && cellRows==cellRowsNew )
			return; // Not really a resize

		// Create a new grid	, reusing existing Cell's
		Cell[][] gridNew = new Cell[cellColsNew][cellRowsNew];
		for ( int c=0; c<cellColsNew; c++)
			for ( int r=0; r<cellRowsNew; r++ )
				if ( c < cellCols && r < cellRows )
					gridNew[c][r] = grid[c][r];
				else
					gridNew[c][r] = new Cell( c, r );

		// Copy existing shape to center of new shape
		int colOffset = (cellColsNew-cellCols)/2;
		int rowOffset = (cellRowsNew-cellRows)/2;
		Cell cell;
		Enumeration enumi;
		nextShape.clear();
		enumi = currentShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			int colNew = cell.col + colOffset;
			int rowNew = cell.row + rowOffset;
			try {
				nextShape.put( gridNew[colNew][rowNew], gridNew[colNew][rowNew] );
			} catch ( ArrayIndexOutOfBoundsException e ) {
				// ignore
			}
		}

		// Copy new grid and hashtable to working grid/hashtable
		grid = gridNew;
		currentShape.clear();
		enumi = nextShape.keys();
		while ( enumi.hasMoreElements() ) {
			cell = (Cell) enumi.nextElement();
			currentShape.put( cell, cell );
		}

		cellCols = cellColsNew;
		cellRows = cellRowsNew;
	}


	public int getCellRows(){
		return this.cellRows;
	}

	public int getCellCols(){
		return this.cellCols;
	}

	/**
	 * Get cell-enumerator. Enumerates over all living cells (type Cell).
	 * @return Enumerator over Cell.
	 * @see Cell
	 */
	public Enumeration getEnum() {
		return currentShape.keys();
	}

	/**
	 * Clears grid.
	 */
	public synchronized void clear() {
		generations = 0;
		currentShape.clear();
		nextShape.clear();
	}
}
