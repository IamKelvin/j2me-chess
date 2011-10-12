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
        return getMoves(start_node);
    }
    public Moves getMoves(Node node) {        
        Moves moves = new Moves();
        for(int r=0;r<8;r++) {
            for(int c=0;c<8;c++) {
                Square from = new Square(r,c);        
                char p = node.getBoard(r,c);
                if (node.isBlackTurn()) {
                    if (p=='p') {
                        moves.add(p,from,from.shift(2, 0));
                        moves.add(p,from,from.shift(1, 0));
                        moves.add(p,from,from.shift(1, 1));
                        moves.add(p,from,from.shift(1,-1));
                    } else if (p=='b') {
                    
                    }
                }
                if (node.isWhiteTurn()) {
                    if (p=='P') {
                        moves.add(p,from,from.shift(-2, 0));
                        moves.add(p,from,from.shift(-1, 0));
                        moves.add(p,from,from.shift(-1, 1));
                        moves.add(p,from,from.shift(-1,-1));                
                    } else if (p=='B') {
                        while() {
                            moves.add(p, from, from.shift(r, c));
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    
    public void doMove(Node n, Move m) {
        n.doMove(m);
    }
    public void doMove(Move m) {
        doMove(start_node,m);
    }
    public void doMove(String m) {
        doMove(start_node,new Move(m));
    }
    
    public void unMove() {   
    }
    
    
    private void brain_doMove(Move m) {
        doMove(brain_node,m);
    }
    private void brain_unMove(Move m) {
    
    }
    
    public String toString() {
        return start_node.toString();
    }
}
