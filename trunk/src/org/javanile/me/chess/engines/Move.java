/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

/**
 *
 * @author cicciodarkast
 */
public class Move {
    private int kind;
    private Square from;
    private Square to;
    
    public Move(String m) {
        from = new Square(m.substring(0, 2));
        to = new Square(m.substring(2, 4));
        kind = Constants.MK_SIMPLE;
    }
    public int getKind() {
        return kind;
    }
    public Square getFrom() {
        return from;
    }
    public Square getTo() {
        return to;
    }       
}
