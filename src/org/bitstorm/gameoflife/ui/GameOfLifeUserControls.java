/**
 * The control bar at the bottom.
 * Is put in a seperate object, so it can be replaced by another UI, e.g. on a J2ME phone. 
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 * @author Edwin Martin
 */
 
package org.bitstorm.gameoflife.ui;

import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;
import org.bitstorm.gameoflife.cells.Shape;
import org.bitstorm.gameoflife.cells.ShapeCollection;
import org.bitstorm.gameoflife.eventhandler.*;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Panel;
import java.util.Vector;


/**
 * GUI-controls of the Game of Life.
 * It contains controls like Shape, zoom and speed selector, next and start/stop-button.
 * It is a seperate class, so it can be replaced by another implementation for e.g. mobile phones or PDA's.
 * Communicates via the GameControlsListener.
 * @author Edwin Martin
 *
 */

public class GameOfLifeUserControls extends Panel implements CellGameUserControls {
	private Label genLabel;
	private final String genLabelText = "Generations: ";
	private final String nextLabelText = "Next";
	private final String startLabelText = "Start";
	private final String stopLabelText = "Stop";
	public static final String SLOW = "Slow";
	public static final String FAST = "Fast";
	public static final String HYPER = "Hyper";
	public static final String BIG = "Big";
	public static final String MEDIUM = "Medium";
	public static final String SMALL = "Small";
	public static final int SIZE_BIG = 11;
	public static final int SIZE_MEDIUM = 7;
	public static final int SIZE_SMALL = 3;
	private Button startstopButton;
	private Button nextButton;
	private Vector listeners;
	private Choice shapesChoice;
	private Choice zoomChoice;

	/**
	 * Contructs the controls.
	 */
	public GameOfLifeUserControls() {
		listeners = new Vector();

		// pulldown menu with shapes
		shapesChoice = new Choice();
	
		// Put names of shapes in menu
		Shape[] shapes = ShapeCollection.getShapes();
		for ( int i = 0; i < shapes.length; i++ )
			shapesChoice.addItem( shapes[i].getName() );

		// when shape is selected
		shapesChoice.addItemListener(
			new ShapesChoiceHandler(listeners)
		);
	
		// pulldown menu with speeds
		Choice speedChoice = new Choice();
	
		// add speeds
		speedChoice.addItem(SLOW);
		speedChoice.addItem(FAST);
		speedChoice.addItem(HYPER);
	
		// when item is selected
		speedChoice.addItemListener(
			new SpeedChoiceHandler(listeners)
		);
	
		// pulldown menu with speeds
		zoomChoice = new Choice();
	
		// add speeds
		zoomChoice.addItem(BIG);
		zoomChoice.addItem(MEDIUM);
		zoomChoice.addItem(SMALL);
	
		// when item is selected
		zoomChoice.addItemListener(
			new ZoomChoiceHandler(listeners)
		);
	
		// number of generations
		genLabel = new Label(genLabelText+"         ");
	
		// start and stop buttom
		startstopButton = new Button(startLabelText);
			
		// when start/stop button is clicked
		startstopButton.addActionListener(
			new StartStopButtonHandler(listeners)
		);
	
		// next generation button
		nextButton = new Button(nextLabelText);
			
		// when next button is clicked
		nextButton.addActionListener(
			new NextButtonHandler(listeners)
		);
	
		// create panel with controls
		this.add(shapesChoice);
		this.add(nextButton);
		this.add(startstopButton);
		this.add(speedChoice);
		this.add(zoomChoice);
		this.add(genLabel);
		this.validate();
	}
	

	/**
	 * Add listener for this control
	 * @param listener Listener object
	 */
	@Override
	public void addControlsListener(CellGameUserControlsListener listener ) {
		listeners.addElement( listener );
	}

	/**
	 * Remove listener from this control
	 * @param listener Listener object
	 */
	@Override
	public void removeControlsListener(CellGameUserControlsListener listener ) {
		listeners.removeElement( listener );
	}

	/**
	 * Set the number of generations in the control bar.
	 * @param generations number of generations
	 */
	@Override
	public void setGeneration( int generations ) {
		genLabel.setText(genLabelText + generations + "         ");
	}
	
	
	/**
	 * Start-button is activated.
	 */
	@Override
	public void start() {
		startstopButton.setLabel(stopLabelText);
		nextButton.setEnabled(false);
		shapesChoice.setEnabled(false);
	}

	/**
	 * Stop-button is activated.
	 */
	@Override
	public void stop() {
		startstopButton.setLabel(startLabelText);
		nextButton.setEnabled(true);
		shapesChoice.setEnabled(true);
	}
	
	/**
	 * Called when a new cell size from the zoom pull down is selected.
	 * Notify event-listeners.
	 */
	@Override
	public void setZoom( String n ) {
		zoomChoice.select(n);
	}


}