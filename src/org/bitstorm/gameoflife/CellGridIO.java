package org.bitstorm.gameoflife;

import org.bitstorm.gameoflife.cells.Shape;
import org.bitstorm.util.EasyFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface CellGridIO {
	void openShape() throws MalformedURLException;
	void openShape(String filename) throws MalformedURLException;
	void openShape(URL url);
	void openShape(EasyFile file) throws IOException;
	Shape makeShape(String name, String text);
	void saveShape();
	void setShape(Shape shape);
}
