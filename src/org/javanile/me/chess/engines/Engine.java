/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

/**
 *
 * @author cicciodarkast
 */
public class Engine {
    private Node start_node;
    private Node brain_node;
    
    public Engine() {
        start_node = new Node();
    }
    
    public Moves getMoves() {
        Moves moves = new Moves();
        return moves;
    }
    
    public void doMove(Move m) {
    
    }
    
    public void unMove() {   
    }
    
    
    private void brain_doMove(Move m) {
    
    }
    private void brain_unMove(Move m) {
    
    }
}
