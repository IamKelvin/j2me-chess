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
    private Node	start_node;
    private Node	brain_node;
	private Moves	start_line;
	private Moves	brain_line;
	
        
	private Moves game;
    
    public Engine() {
		game = new Moves();
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
                node.setFocusSquare(from);
                
                if (node.isWhiteTurn()) {
                    if (node.isWhitePawn()) {
                        if (node.isWhitePawnRank()) {
                            moves.add(node.getMove(Constants.MK_WPD));
                        }
						if (!node.isFileH()) {
							Move m = node.getMove(Constants.MK_WPCE);
							if (node.haveOpponentPieceIn(m.getTo())) {
								moves.add(m);
							}
						}
						if (!node.isFileA()) {						
							Move m = node.getMove(Constants.MK_WPCW);
							if (node.haveOpponentPieceIn(m.getTo())) {
								moves.add(m);
							}
						}
						Move m = node.getMove(Constants.MK_WPS);
						if (node.isFreeSquare(m.getTo())) {
							moves.add(m);
						}
                        
                    } else if ((p=='B')||(p=='R')||(p=='Q')) {
                        int[] l = Constants.GAMMA(p);
                        for(int i=0;i<l.length;i++) {
                            Square to = from.shift(Constants.DELTA(p,l[i]));
                            while(node.isFreeOrOpponentSquare(to)) {
                                moves.add(node.getMove(to,l[i]));
                                to = to.shift(Constants.DELTA(p,l[i]));
                            }
                        }                                                
                    }
                } else {
                    if (node.isBlackPawn()) {
                        if (node.isBlackPawnRank()) { 
                            moves.add(node.getMove(Constants.MK_BPD)); 
                        }                        
						if (!node.isFileH()) {
							moves.add(node.getMove(Constants.MK_BPCE));
						}
                        if (!node.isFileA()) {
							moves.add(node.getMove(Constants.MK_BPCW));
						}
						moves.add(node.getMove(Constants.MK_BPS));
                        
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
		game.move(m);
        doMove(start_node,m);
    }
    public void doMove(String m) {
        doMove(start_node.getMove(m));
    }
    
    public void unMove(Node n, Move m) { 
		n.unMove(m);
    }
    public void unMove() {
		start_node.unMove(game.last());
		game.undo();
    }
    
    private void brain_doMove(Move m) {
		brain_line.move(m);
        doMove(brain_node,m);
    }
    private void brain_unMove(Move m) {
        brain_line.undo();   
		unMove(brain_node,m);
    }
    
    private int getValue(Node node) {
        int val = 0;
        for(int r=0;r<8;r++) {
            for(int c=0;c<8;c++) {
                Square from = new Square(r,c);        
                node.setFocusSquare(from);
				char p = node.getBoard(r,c);
                val += Constants.VALUE(p);
				if (node.isWhitePiece()) {
					val+=r+c;
				}
				if (node.isBlackPiece()) {
					val-=r+c;
				}
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
        public void run() {
            try {
                brain_line = new Moves();
                count=0;
                System.out.println("START!");                   
                brain_node.set(start_node);
                int exit = analize_loop(5,-100000,100000);
                System.out.println(brain_node.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private int analize_loop(int deep, int alfa, int beta) {            
            count++;
            if (count<1000) {
                if (deep>0) {            
                    Moves m = brain_getMoves();
                    for(int i=0;i<m.size();i++) {
                        brain_doMove(m.at(i));     
						int zeta = analize_loop(deep-1,-beta,-alfa);
                        System.out.println(count+". ("+deep+") ["+alfa+":"+beta+"] "+zeta+" "+brain_line.toString());      			
    
						alfa = Math.max(alfa, -zeta);
						brain_unMove(m.at(i));
						if (alfa>=beta) {
							return beta;
                        }
                    } 
					return alfa;
                }
            } 
			return brain_getValue();
        }
    }
    
    public String toString() {
        return start_node.toString();
    }
}
