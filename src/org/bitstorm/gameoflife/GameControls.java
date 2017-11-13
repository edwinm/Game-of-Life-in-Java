package org.bitstorm.gameoflife;

public interface GameControls {
	void addControlsListener(GameOfLifeControlsListener listener);
	void removeControlsListener( GameOfLifeControlsListener listener);
	void start();
	void stop();
	void setZoom(String n);
	void setGeneration(int generation);
}
