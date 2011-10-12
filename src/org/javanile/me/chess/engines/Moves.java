/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

import java.util.Vector;
/**
 *
 * @author cicciodarkast
 */
public class Moves {
    private Vector moves;
    public Moves() {
        moves = new Vector();        
    }
    public void add(char p,Square from, Square to) {
        moves.addElement(new Move(p, from, to));
    }
    public String toString() {
        String output = "";
        for(int i=0;i<moves.size();i++) {
            output+=((Move)moves.elementAt(i)).toString()+" ";
        }        
        return output;
    }
}                               
