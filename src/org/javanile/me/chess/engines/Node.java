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
    private Square focus_square;
            
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
    public void setFocusSquare(Square s) {
        focus_square = s;
    }
    
    public void doMove(Move m) {
        switch(m.getKind()) {
            case Constants.MK_SIMPLE:
            default:
                setBoard(m.getTo(),getBoard(m.getFrom()));
                setBoard(m.getFrom(),'.');
                break;
        }
        changeTurn();        
    }
    public void unMove(Move m) {
		switch(m.getKind()) {
            case Constants.MK_SIMPLE:
            default:
                setBoard(m.getTo(),m.getCaptured());
                setBoard(m.getFrom(),m.getPiece());
                break;
        }
        changeTurn();        
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
    public boolean isBlackPawn() {
        return getBoard(focus_square)=='p';
    }
    public boolean isWhitePawn() {
        return getBoard(focus_square)=='P';
    }
    public boolean isBlackPawnRank() {
        return focus_square.getRank()==1;
    }
    public boolean isWhitePawnRank() {
        return focus_square.getRank()==6;
    }
	public boolean isFileH() {
		return focus_square.getFile()==7;
	}
	public boolean isFileA() {
		return focus_square.getFile()==0;
	}
			
    private void changeTurn() {
        turn = -turn;
    }
    public int getTurn() {
        return turn;
    }
    public char[][] getBoard() {        
        return board;
    }
    public Move getMove(Square from, Square to) {
        return new Move(getBoard(from),from,to,getBoard(to),Constants.MK_SIMPLE);
    }
    public Move getMove(int mk) {
        Square from = focus_square;
        Square to = from.shift(Constants.DELTA(getBoard(from), mk));
        return new Move(getBoard(from),from,to,getBoard(to),mk);
    }    
    public Move getMove(Square to, int mk) {
        Square from = focus_square;
        return new Move(getBoard(from),from,to,getBoard(to),mk);
    }    
    public boolean isFreeOrOpponentSquare(Square s) {
        char p = getBoard(s);
        if (turn == -1) {
			if ((p=='.')||(p=='P')||(p=='N')||(p=='B')||(p=='R')||(p=='Q')||(p=='K')) return true;		    
        } else {
			if ((p=='.')||(p=='p')||(p=='n')||(p=='b')||(p=='r')||(p=='q')||(p=='k')) return true;		
        }
        return false;
    }
    public boolean haveOpponentPieceIn(Square to) {
		if (getBoard(to)!='.') {
			return (getPieceColor(focus_square)!=getPieceColor(to));
		}
		return false;
	}
	public int getPieceColor(Square s) {
		char p = getBoard(s);
		if ((p=='p')||(p=='n')||(p=='b')||(p=='r')||(p=='q')||(p=='k')) return -1;
		if ((p=='P')||(p=='N')||(p=='B')||(p=='R')||(p=='Q')||(p=='K')) return  1;
		return 0;
	}
	public boolean isFreeSquare(Square s) {
		return getBoard(s)=='.';
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
