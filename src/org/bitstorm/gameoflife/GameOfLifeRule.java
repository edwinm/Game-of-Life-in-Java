package org.bitstorm.gameoflife;

public class GameOfLifeRule implements GameRule{
    @Override
    public boolean diesNext(Cell c){
        return c.neighbour != 3 && c.neighbour != 2;
    }
    @Override
    public boolean bornsNext(Cell c){
        return c.neighbour == 3;
    }
}
