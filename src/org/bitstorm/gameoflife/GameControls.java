package org.bitstorm.gameoflife;

public interface GameControls {
	void addControlsListener(GameControlsListener listener);
	void removeControlsListener( GameControlsListener listener);
	void start();
	void stop();
	void setZoom(String n);
	void setGeneration(int generation);
}
