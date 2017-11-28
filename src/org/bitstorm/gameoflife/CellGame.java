package org.bitstorm.gameoflife;

import org.bitstorm.gameoflife.cells.Shape;
import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;

public interface CellGame extends CellGameUserControlsListener {
    void init();
    void start();
    void stop();
    void run();
    boolean isRunning();
    void nextGeneration();
    void setSpeed(int fps);
    void setShape(Shape shape);
    void setCellSize(int p);
    int getCellSize();
    void alert(String s);
}
