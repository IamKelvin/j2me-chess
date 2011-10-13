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
        brain_node = new Node();
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
                        if (r==1) { moves.add(p, from, from.shift(Constants.DELTA(p,Constants.MK_BPD))); }
                        moves.add(p, from, from.shift(Constants.DELTA(p,Constants.MK_BPS)));
                        moves.add(p, from, from.shift(Constants.DELTA(p,Constants.MK_BPC1)));
                        moves.add(p, from, from.shift(Constants.DELTA(p,Constants.MK_BPC2)));
                    } else if ((p=='b')||(p=='r')||(p=='q')) {
                        int[] l = Constants.GAMMA(p);
                        for(int i=0;i<l.length;i++) {
                            Square to = from.shift(Constants.DELTA(p,l[i]));
                            while(node.isFreeOrOpponentSquare(to)) {
                                moves.add(p, from, to);
                                to = to.shift(Constants.DELTA(p,l[i]));
                            }
                        }                                        
                    }
                }
                if (node.isWhiteTurn()) {
                    if (p=='P') {
                        if (r==6) {
                            moves.add(p,from,from.shift(Constants.DELTA(p,Constants.MK_WPD)));
                        }
                        moves.add(p,from,from.shift(Constants.DELTA(p,Constants.MK_WPS)));
                        moves.add(p,from,from.shift(Constants.DELTA(p,Constants.MK_WPC1)));
                        moves.add(p,from,from.shift(Constants.DELTA(p,Constants.MK_WPC2)));                
                    } else if ((p=='B')||(p=='R')||(p=='Q')) {
                        int[] l = Constants.GAMMA(p);
                        for(int i=0;i<l.length;i++) {
                            Square to = from.shift(Constants.DELTA(p,l[i]));
                            while(node.isFreeOrOpponentSquare(to)) {
                                moves.add(p, from, to);
                                to = to.shift(Constants.DELTA(p,l[i]));
                            }
                        }                                                
                    }
                }
            }
        }
        return moves;
    }
    private Moves brain_getMoves() {
        return getMoves(brain_node);
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
    
    public void unMove(Node n, Move m) {   
    }
    public void unMove() {   
    }
    
    
    private void brain_doMove(Move m) {
        doMove(brain_node,m);
    }
    private void brain_unMove(Move m) {
        unMove(brain_node,m);
    }
    
    private int getValue(Node node) {
        int val = 0;
        for(int r=0;r<8;r++) {
            for(int c=0;c<8;c++) {
                Square from = new Square(r,c);        
                char p = node.getBoard(r,c);
                val += Constants.VALUE(p);
            }
        }
        return val;
    }
    
    public int brain_getValue() {
        return getValue(brain_node);
    }
    
    
    public void analize() {        
        AnalizeThread ct = new AnalizeThread();
        ct.start();
    }
    class AnalizeThread extends Thread {
        int count;
        Moves line;
        public void run() {
            try {
                line = new Moves();
                count=0;
                System.out.println("START!");                   
                brain_node.set(start_node);
                analize_loop(-9999,20);
                System.out.println(brain_node.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void analize_loop(int best,int deep) {            
            count++;
            System.out.println(count+". ("+deep+") "+best+" "+line.toString());                
            if (count<1000) {
                if (deep>0) {            
                    Moves m = brain_getMoves();
                    for(int i=0;i<m.size();i++) {
                        brain_doMove(m.at(i));             
                        line.move(m.at(i));
                        int val = brain_getValue();
                        if (val>=best) {
                            analize_loop(val,deep-1);
                        }
                        line.undo();
                        brain_unMove(m.at(i));
                    }                
                }
            }    
        }
    }
    
    public String toString() {
        return start_node.toString();
    }
}
