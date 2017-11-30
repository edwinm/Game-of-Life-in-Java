/**
 * Game of Life v1.4 Standalone version
 * The standalone version extends the applet version.
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 *
 */

package org.bitstorm.gameoflife;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.net.MalformedURLException;

import org.bitstorm.gameoflife.cells.GameOfLifeGrid;
import org.bitstorm.gameoflife.cells.ShapeCollection;
import org.bitstorm.gameoflife.cells.ShapeException;
import org.bitstorm.gameoflife.ui.GameOfLifeAWTCellGrid;
import org.bitstorm.gameoflife.ui.GameOfLifeUserControls;
import org.bitstorm.util.AlertBox;

/**
 * Turns AWTGameOfLife applet into application.
 * It adds a menu, a window, drag-n-drop etc.
 * It can be run stand alone.
 * 
 * @author Edwin Martin
 */
public class StandaloneGameOfLife extends AWTGameOfLife {
	private Frame appletFrame;
	private String[] args;
	private CellGridIO gridIO;

	public StandaloneGameOfLife(String[] args){
		this.args = args;
	}
	
	/**
	 * Initialize UI.
	 * @param parent Parent frame.
	 * @see java.applet.Applet#init()
	 */
	public void init( Frame parent ) {
		appletFrame = parent;
		getParams();

		// set background colour
		setBackground(new Color(0x999999));

		// TODO: casten naar interface
		// create StandAloneGameOfLifeGrid
		gameOfLifeGrid = new GameOfLifeGrid(cellCols, cellRows);
		gridIO = new GameOfLifeGridIO( this, appletFrame);

		// create GameOfLifeCanvas
		gameOfLifeCanvas = new GameOfLifeAWTCellGrid(gameOfLifeGrid, cellSize);

		try {
			// Make GameOfLifeCanvas a drop target
			DropTarget dt = new DropTarget( (GameOfLifeAWTCellGrid)gameOfLifeCanvas, DnDConstants.ACTION_COPY_OR_MOVE, new MyDropListener(this, gridIO) );
		} catch (NoClassDefFoundError e) {
			// Ignore. Older Java version don't support dnd
		}

		// create GameOfLifeControls
		controls = new GameOfLifeUserControls();
		controls.addControlsListener( this );

		// put it all together
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints canvasContraints = new GridBagConstraints();
        setLayout(gridbag);
        canvasContraints.fill = GridBagConstraints.BOTH;
        canvasContraints.weightx = 1;
        canvasContraints.weighty = 1;
        canvasContraints.gridx = GridBagConstraints.REMAINDER;
        canvasContraints.gridy = 0;
        canvasContraints.anchor = GridBagConstraints.CENTER;
        gridbag.setConstraints((GameOfLifeAWTCellGrid)gameOfLifeCanvas, canvasContraints);
        add((GameOfLifeAWTCellGrid)gameOfLifeCanvas);
        GridBagConstraints controlsContraints = new GridBagConstraints();
        canvasContraints.gridx = GridBagConstraints.REMAINDER;
        canvasContraints.gridy = 1;
        controlsContraints.gridx = GridBagConstraints.REMAINDER;
        gridbag.setConstraints((GameOfLifeUserControls)controls, controlsContraints);
        add((GameOfLifeUserControls)controls);
		setVisible(true);
		validate();
	}

	/**
	 * Set the shape.
	 *
	 * This is not done in init(), because the window resize in GameOfLifeGridIO.setShape(Shape)
	 * needs a fully opened window to do new size calculations.
	 */
	public void readShape() throws MalformedURLException {
	    if ( args.length > 0 ) {
	    	gridIO.openShape(args[0]);
			reset();
	    } else {
			try {
				setShape( ShapeCollection.getShapeByName( "Glider" ) );
			} catch (ShapeException e) {
				// Ignore. It's not going to happen here.
			}
	    }
	}

    /**
     * Override method, called by applet.
	 * @see java.applet.Applet#getParameter(java.lang.String)
	 */
	public String getParameter( String parm ) {
        return System.getProperty( parm );
    }

	/**
	 * Shows an alert
	 * @param s text to show
	 */
	public void alert( String s ) {
		new AlertBox( appletFrame, "Alert", s );
	}
	
	/**
	 * Do not use showStatus() of the applet.
	 * @see java.applet.Applet#showStatus(java.lang.String)
	 */
	public void showStatus( String s ) {
		// do nothing
	}
	
	/**
	 * get GameOfLifeGridIO
	 * @return GameOfLifeGridIO object
	 */
	protected CellGridIO getGameOfLifeGridIO() {
		return gridIO;
	}

}


