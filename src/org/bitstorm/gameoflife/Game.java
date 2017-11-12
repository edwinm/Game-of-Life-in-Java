package org.bitstorm.gameoflife;

public interface Game {
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
