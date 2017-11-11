package org.bitstorm.gameoflife;

public interface GameRule {
    boolean diesNext(Cell c);
    boolean bornsNext(Cell c);
}
