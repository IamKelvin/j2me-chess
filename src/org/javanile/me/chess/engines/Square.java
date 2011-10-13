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
    
    public Square(int r, int c) {
        rank = r;
        file = c;
    }
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
    public Square shift(int dr,int dc) {
        return new Square(rank+dr,file+dc);
    }
    public Square shift(int[] d) {
        return new Square(rank+d[0],file+d[1]);
    }
    public String toString() {
        return ""+((char)(file+97))+((char)(56-rank));
    }
}
