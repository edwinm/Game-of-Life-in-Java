package org.bitstorm.gameoflife;


import org.bitstorm.gameoflife.cells.CellGrid;
import org.bitstorm.gameoflife.cells.GameOfLifeGrid;
import org.bitstorm.gameoflife.cells.Shape;
import org.bitstorm.gameoflife.cells.ShapeException;
import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;
import org.bitstorm.gameoflife.ui.GameOfLifeUserControls;
import org.bitstorm.util.AlertBox;
import org.bitstorm.util.EasyFile;
import org.bitstorm.util.LineEnumerator;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

/**
 * File open and save operations for GameOfLifeGrid.
 */
class GameOfLifeGridIO implements CellGridIO{
	public final String FILE_EXTENSION = ".cells";
	private CellGrid grid;
	private String filename;
	private Frame frame;
	private AWTGameOfLife game;
	/**
	 * Contructor.
	 */
	public GameOfLifeGridIO( AWTGameOfLife game, Frame frame) {
		this.game = game;
		this.grid = game.getGameOfLifeGrid();
		this.frame = frame;
	}
	
	/**
	 * Load shape from disk
	 */
	@Override
	public void openShape() throws MalformedURLException {
		openShape( (String)null );
	}
	
	/**
	 * Load shape from disk
	 * @param filename filename to load shape from, or null when no filename given.
	 */
	@Override
	public void openShape( String filename ) throws MalformedURLException {
		openShape(new URL(filename));
	}
	
	/**
	 * Open shape from URL.
	 * @param url URL pointing to AWTGameOfLife-file
	 */
	@Override
	public void openShape( URL url ) {
		int col = 0;
		int row = 0;
		boolean cell;
		// Cope with different line endings ("\r\n", "\r", "\n")
		boolean nextLine = false;
		EasyFile file;
		String text;
		try {
			if ( url != null ) {
				file = new EasyFile( url );
				openShape( file );
			}
		} catch (FileNotFoundException e) {
			new AlertBox( frame, "URL not found", "Couldn't open this URL.\n"+e.getMessage());
		} catch (IOException e) {
			new AlertBox( frame, "URL read error", "Couldn't read this URL.\n"+e.getMessage());
		}
	}
	
	/**
	 * Use EasyFile object to read AWTGameOfLife-file from.
	 * @param file EasyFile-object
	 * @throws IOException
	 * @see org.bitstorm.util.EasyFile
	 */
	@Override
	public void openShape( EasyFile file ) throws IOException {
		Shape shape = makeShape( file.getFileName(), file.readText() );
		setShape( shape );
	}
	
	/**
	 * Set a shape and optionally resizes window.
	 * @param shape Shape to set
	 */
	@Override
	public void setShape( Shape shape ) {
		int width, height;
		Dimension shapeDim = new Dimension(shape.getWidth(), shape.getHeight());
		Dimension gridDim = new Dimension(this.grid.getCellCols(),this.grid.getCellRows());
		if ( shapeDim.width > gridDim.width || shapeDim.height > gridDim.height ) {
			// Window has to be made larger
			Toolkit toolkit = frame.getToolkit();
			Dimension screenDim =  toolkit.getScreenSize();
			Dimension frameDim = frame.getSize();
			int cellSize = game.getCellSize();
			// Calculate new window size
			width = frameDim.width + cellSize*(shapeDim.width - gridDim.width);
			height = frameDim.height + cellSize*(shapeDim.height - gridDim.height);
			// Does it fit on the screen?
			if ( width > screenDim.width || height > screenDim.height ) {
				// With current cellSize, it doesn't fit on the screen
				// GameOfLifeControls.SIZE_SMALL corresponds with GameOfLifeControls.SMALL
				int newCellSize = GameOfLifeUserControls.SIZE_SMALL;
				width = frameDim.width + newCellSize*shapeDim.width - cellSize*gridDim.width;
				height = frameDim.height + newCellSize*shapeDim.height - cellSize*gridDim.height;
				// a little kludge to prevent de window from resizing twice
				// setNewCellSize only has effect at the next resize
				((GameOfLifeAWTCellGrid)game.getGameOfLifeCanvas()).setAfterWindowResize( shape, newCellSize );
				// The UI has to be adjusted, too
				
				((GameOfLifeUserControls)game.getGameOfLifeCanvas()).setZoom( GameOfLifeUserControls.SMALL );
			} else {
				// Now resize the window (and optionally set the new cellSize)
				((GameOfLifeAWTCellGrid)game.getGameOfLifeCanvas()).setAfterWindowResize( shape, cellSize );
			}
			if ( width < 400 )
				width = 400;
			frame.setSize( width, height );
			return;
		}
		try {
			game.getGameOfLifeCanvas().setShape( shape );
		} catch (ShapeException e) {
			// ignore
		}
	}
	
	/**
	 * "Draw" the shape on the grid. (Okay, it's not really drawing).
	 * The lines of text represent the cells of the shape.
	 *
	 * @param name name of shape
	 * @param text lines of text
	 */
	@Override
	public Shape makeShape( String name, String text ) {
		int col = 0;
		int row = 0;
		boolean cell;
		// Cope with different line endings ("\r\n", "\r", "\n")
		int[][] cellArray;
		Vector cells = new Vector<int[]>();
		
		if ( text.length() == 0 )
			return null;
		
		grid.clear();
		
		Enumeration enumi = new LineEnumerator( text );
		while ( enumi.hasMoreElements() ) {
			String line = (String) enumi.nextElement();
			if ( line.startsWith("#") || line.startsWith("!") )
				continue;
			
			char[] ca = line.toCharArray();
			for ( col=0; col < ca.length; col++ ) {
				switch( ca[col] ) {
					case '*':
					case 'O':
					case 'o':
					case 'X':
					case 'x':
					case '1':
						cell = true;
						break;
					default:
						cell = false;
						break;
				}
				if ( cell )
					cells.addElement(new int[] {col, row});
			}
			row++;
		}
		
		cellArray = new int[cells.size()][];
		for ( int i=0; i<cells.size(); i++ )
			cellArray[i] = (int[]) cells.get(i);
		return new Shape( name, cellArray );
	}
	
	/**
	 * Write shape to disk.
	 */
	@Override
	public void saveShape() {
		int colEnd = 0;
		int rowEnd = 0;
		Dimension dim = new Dimension(grid.getCellCols(),grid.getCellRows());
		int colStart = dim.width;
		int rowStart = dim.height;
		
		String lineSeperator = System.getProperty( "line.separator" );
		StringBuffer text = new StringBuffer("!Generator: Game of Life (http://www.bitstorm.org/gameoflife/)"+lineSeperator+"!Variation: 23/3"+lineSeperator+"!"+lineSeperator);
		
		for ( int row = 0; row < dim.height; row++ ) {
			for ( int col = 0; col < dim.width; col++ ) {
				if ( grid.getCell( col, row ) ) {
					if ( row < rowStart )
						rowStart = row;
					if ( col < colStart )
						colStart = col;
					if ( row > rowEnd )
						rowEnd = row;
					if ( col > colEnd )
						colEnd = col;
				}
			}
		}
		
		for ( int row = rowStart; row <= rowEnd; row++ ) {
			for ( int col = colStart; col <= colEnd; col++ ) {
				text.append( grid.getCell( col, row ) ? 'O' : '-' );
			}
			text.append( lineSeperator );
		}
		EasyFile file;
		try {
			file = new EasyFile( frame, "Save Game of Life file" );
			file.setFileName( filename );
			file.setFileExtension( FILE_EXTENSION );
			file.writeText( text.toString() );
		} catch (FileNotFoundException e) {
			new AlertBox( frame, "File error", "Couldn't open this file.\n"+e.getMessage());
		} catch (IOException e) {
			new AlertBox( frame, "File error", "Couldn't write to this file.\n"+e.getMessage());
		}
	}
}
