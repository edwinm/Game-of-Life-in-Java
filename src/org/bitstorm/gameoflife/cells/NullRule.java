package org.bitstorm.gameoflife.cells;

public class NullRule implements CellGameRule {

    @Override
    public boolean diesNext(Cell c) {
        return false;
    }

    @Override
    public boolean bornsNext(Cell c) {
        return false;
    }
}
