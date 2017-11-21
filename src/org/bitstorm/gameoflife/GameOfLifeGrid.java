/**
 * Copyright 1996-2004 Edwin Martin <edwin@bitstorm.nl>
 *
 * @author Edwin Martin
 */

package org.bitstorm.gameoflife;

//import java.awt.Dimension;
//import java.util.Hashtable;

/**
 * Contains the cellgrid, the current shape and the Game Of Life algorithm that changes it.
 *
 * @author Edwin Martin
 */
public class GameOfLifeGrid extends CellGrid {
    public GameOfLifeGrid(int cellCols, int cellRows) {
        super(cellCols, cellRows);
        this.rule = new GameOfLifeRule();
    }

    public GameOfLifeGrid(GameOfLifeRule gameOfLifeRule, int cellCols, int cellRows) {
        super(cellCols, cellRows);
        this.rule = gameOfLifeRule;
    }
}