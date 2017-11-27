package org.bitstorm.gameoflife.cells;

public interface CellGameRule {
    boolean diesNext(Cell c);
    boolean bornsNext(Cell c);
}
