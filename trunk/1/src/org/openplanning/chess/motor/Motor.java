/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.motor;

import java.util.Vector;

import org.openplanning.chess.FranzChessMIDlet;
/**
 *
 * @author root
 */
public class Motor implements Runnable {

	public boolean	isvalid = false;
	public String	message = "";

	public long trigger_last = 0;
	
	private static final String	thinkString = "-\\|/";
	private int					thinkStatus = 0;

	public int controller = 0;
	private ChessThread thread;
    private boolean quit = false;

	private Vector queue = new Vector();

	public Motor(ChessThread thread_) {
		thread = thread_;
		new Thread( this ).start();
	}

    public void run(){
		System.out.println("Stara");
		Object o;

		while( !quit ){
			o = null;

			synchronized( queue ){
				if( queue.size() > 0 ){
					o = queue.elementAt( 0 );
					queue.removeElementAt( 0 );
				} else {
					/*
					try {
						//queue.wait();
					}
					catch( InterruptedException e ){
					}
					*/
				}
			}

			synchronized( thread ){
				if( thread.now(thread.PLAYER_MOTOR) ){
					//thread.midlet.debugApp("FranzTurn");
					thread.midlet.canvas.sendMove(0);
				} else {
					/*
					try {
						//queue.wait();
					}
					catch( InterruptedException e ){
					}
					*/
				}
			}

			if( o != null ){
			// do something
			}

			long trigger_now = System.currentTimeMillis();
			if ((trigger_now-trigger_last)>333) {
				thinkStringAnimate();
				thread.midlet.canvas.doRepaint();
				trigger_last = trigger_now;
				//thread.midlet.debugApp("Trigger:"+trigger_last);
			}
		}
    }

    public boolean addToQueue( Object o ){
		synchronized( queue ){
			if( !quit ){
				queue.addElement( o );
				queue.notify();
				System.out.println( o );
				return true;
			}
			return false;
		}
    }

    public void quit(){
		synchronized( queue ){
			quit = true;
			queue.notify();
		}
    }

	private void thinkStringAnimate() {
		thinkStatus = (thinkStatus<(thinkString.length()-1)) ? thinkStatus+1 : 0;
	}
	public String thinkStringReturn() {
		return (thinkString.charAt(thinkStatus))+"";
	}
	
	/** Player Move Result */
	private boolean getResult() {
		return getResult(-1);
	}

	/** Computer Move Result */
	private boolean getResult(int response) {
		if (thread.pos.isMate()) {
			if (thread.pos.inCheck()) {				
				message = (response < 0 ? "You Win!" : "You Lose!");
			} else {				
				message = "Draw by Stalemate!";
			}
			return true;
		}
		if (thread.pos.isRep(2)) {			
			message = "Draw by Repetition!";
			return true;
		}
		if (thread.pos.moveNum > 100) {
			message = "Draw by 50-Move Rule!";
			return true;
		}
		if (response >= 0) {			
			// Backup Retract Status
			System.arraycopy(thread.rsData, 0, thread.retractData, 0, ChessThread.RS_DATA_LEN);
			// Backup Record-Score Data
			thread.rsData[0] = 1;
			System.arraycopy(thread.pos.squares, 0, thread.rsData, 256, 128);
			thread.rsData[384] = (byte) thread.pos.castlingBits();
			thread.rsData[385] = (byte) thread.pos.enPassantSquare();
		}
		return false;
	}

	public void sendMove(int mv) {
		doMove(mv);
	}

	private void doMove(int mv) {
		if (thread.pos.legalMove(mv)) {
			int pcSrc = thread.pos.squares[Position.SRC(mv)];
			if (thread.pos.makeMove(mv)) {
				if (thread.pos.captured() || Position.PIECE_TYPE(pcSrc) == Position.PIECE_PAWN) {
					thread.pos.setIrrev();
				}
				isvalid = true;
				return;
			}
			isvalid = true;
			return;
		}
		isvalid = false;
		return;
	}
	boolean responseMove() {
		/*
		if (getResult()) {
			return false;
		}
		phase = PHASE_THINKING;
		repaint();
		serviceRepaints();
		mvLast = thread.search.searchMain(1000 << (thread.level << 1));
		int pc = thread.pos.squares[Position.SRC(mvLast)];
		thread.pos.makeMove(mvLast);
		int response = thread.pos.inCheck() ? RESP_CHECK2 : thread.pos.specialMove() ?
				RESP_SPECIAL2 : thread.pos.captured() ? RESP_CAPTURE2 : RESP_MOVE2;
		if (thread.pos.captured() || Position.PIECE_TYPE(pc) == Position.PIECE_PAWN) {
			thread.pos.setIrrev();
		}
		phase = PHASE_WAITING;
		repaint();
		serviceRepaints();*/
		//return !getResult(response);
		return false;
	}

}
