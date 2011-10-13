/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanile.me.chess.engines;

/**
 *
 * @author cicciodarkast
 */
public class Node {
    private char[][] board;
    private int turn = 0;
    
    public Node() {      
        this(Constants.START_POSITION);
    }
    public Node(String fen) {
        parse_fen(fen);
    }
    public void set(Node node) {
        try {
            turn = node.getTurn();
            char[][] temp = node.getBoard();
            for(int r=0;r<12;r++) {
                for(int c=0;c<12;c++) {
                    board[r][c] = temp[r][c];
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void doMove(Move m) {
        switch(m.getKind()) {
            case Constants.MK_SIMPLE:
                setBoard(m.getTo(),getBoard(m.getFrom()));
                setBoard(m.getFrom(),'.');
                break;
        }
    }
    public void unMove(Move m) {
    
    }
    
    public void setBoard(Square s,char p) {
        board[s.getRank()+2][s.getFile()+2] = p;        
    }
    public char getBoard(Square s) {
        return board[s.getRank()+2][s.getFile()+2];        
    }
    public char getBoard(int r, int f) {
        return board[r+2][f+2];        
    }

    public boolean isWhiteTurn() {
        return turn==1;
    }
    public boolean isBlackTurn() {
        return turn==-1;
    }
    public int getTurn() {
        return turn;
    }
    public char[][] getBoard() {        
        return board;
    }
            
    public boolean isFreeOrOpponentSquare(Square s) {
        char p = getBoard(s);
        if (turn == -1) {
            if (p>96) {
                return true;
            } 
        } else {
        
        }
        return (p=='.');
    }
    
    private void parse_fen(String fen) {
        board = new char[12][12];
        
        for(int r=0;r<12;r++) {for(int c=0;c<12;c++) {board[r][c] = '#';}}
        for(int r=2;r<10;r++) {for(int c=2;c<10;c++) {board[r][c] = '.';}}
        
        int s = 0;
        int i = 0;
        int r = 0;
        int c = 0;
        char l;
        while(i<fen.length()) {
            l = fen.charAt(i);
            if (s==0) {
                if (l=='/') {
                    r++;
                    c=0;
                } else if ((l>='a')&&(l<='z')) {
                    board[r+2][c+2] = l;
                    c++;
                } else if ((l>='A')&&(l<='Z')) {     
                    board[r+2][c+2] = l;
                    c++;
                } else if ((l>='1')&&(l<='8')) {                
                    c = c + Integer.parseInt(""+l);
                } else {
                    s++;
                }
            } else if (s==1) {
                turn = 1;
            }
            i++;
        }
        
    }
    
    public String toString() {
        String output = "";
        for(int r=0;r<12;r++) {
            for(int c=0;c<12;c++) {
                output+=board[r][c];
            }
            output+="\n";
        }        
        return output;
    }
}
