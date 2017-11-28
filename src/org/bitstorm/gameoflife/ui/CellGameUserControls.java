package org.bitstorm.gameoflife.ui;

import org.bitstorm.gameoflife.uicontrol.CellGameUserControlsListener;

public interface CellGameUserControls {
	void addControlsListener(CellGameUserControlsListener listener);
	void removeControlsListener( CellGameUserControlsListener listener);
	void start();
	void stop();
	void setZoom(String n);
	void setGeneration(int generation);
}
