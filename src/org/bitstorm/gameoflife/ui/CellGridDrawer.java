package org.bitstorm.gameoflife.ui;

import org.bitstorm.gameoflife.cells.Shape;
import org.bitstorm.gameoflife.cells.ShapeException;

public interface CellGridDrawer {
    void setCellSize(int cellSize);
    void resized();
    void draw(int x, int y);
    void setShape (Shape shape) throws ShapeException;
    void saveCellUnderMouse(int x, int y);
}
