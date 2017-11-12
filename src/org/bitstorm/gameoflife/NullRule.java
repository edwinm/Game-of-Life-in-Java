package org.bitstorm.gameoflife;

public class NullRule implements GameRule{

    @Override
    public boolean diesNext(Cell c) {
        return false;
    }

    @Override
    public boolean bornsNext(Cell c) {
        return false;
    }
}
