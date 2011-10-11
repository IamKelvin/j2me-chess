/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

/**
 *
 * @author cicciodarkast
 */
public class Square {
    private int rank;
    private int file;
    
    public Square(String s) {
        rank = 56-s.charAt(1);
        file = s.charAt(0)-97;
    }
    public int getRank() {
        return rank;
    }
    public int getFile() {
        return file;
    }
}
