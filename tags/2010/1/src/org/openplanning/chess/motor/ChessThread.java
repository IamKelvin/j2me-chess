/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.motor;

import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

import org.openplanning.chess.FranzChessMIDlet;
/**
 *
 * @author root
 */
public class ChessThread {
	private static final String STORE_NAME = "FranzChess";

	public static final int RS_DATA_LEN = 512;
	/**
	 * 0: Status, 0 = Normal Exit, 1 = Game Saved<br>
	 * 16: Player, 0 = Red, 1 = Black (Flipped)<br>
	 * 17: Handicap, 0 = None, 1 = Knight, 2 = Rook, 3 = Queen<br>
	 * 18: Level, 0 = Beginner, 1 = Amateur, 2 = Expert<br>
	 * 19: Sound Level, 0 = Mute, 5 = Max<br>
	 * 20: Music Level, 0 = Mute, 5 = Max<br>
	 * 256-383: Squares<br>
	 * 384: Castling Bits<br>
	 * 385: En-Passant Square
	 */
	public byte[]	rsData = new byte[RS_DATA_LEN];
	public byte[]	retractData = new byte[ChessThread.RS_DATA_LEN];
	public boolean  flipped;
	public int		level, sound;

	public Position	pos			= new Position();
	public Position	pos_showed	= new Position();
	public Search	search		= new Search(pos, 12);

	public Motor	motor;

	public int		white = 0;
	public int		black = 1;
	public int		turn  = 0;

	public static final int PLAYER_HUMAN	= 0;
	public static final int PLAYER_MOTOR	= 1;
	public static final int PLAYER_INTERNET	= 2;
	public static final int PLAYER_BLUETHOOT= 3;

	public static final String[] PLAYER = {"HUMAN","MOTOR","INTERNET","BLUETHOOT"};
	public static final String[] PLAYER_COLOR = {"WHITE","BLACK"};


	public boolean now(int player) {
		if (pos.sdPlayer > 0) {
			return (player == black);
		}
		return (player == white);
	}

	public FranzChessMIDlet midlet;

	public String getPlayer() {
		return PLAYER_COLOR[pos_showed.sdPlayer]+"("+PLAYER[white]+":"+PLAYER[black]+")";
	}

	public ChessThread (FranzChessMIDlet midlet_) {
		midlet = midlet_;
		motor = new Motor(this);
	}

	public void zero_rsData() {
		for (int i = 0; i < RS_DATA_LEN; i ++) {
			rsData[i] = 0;
		}		
	}

	public void load_rsData() {
		zero_rsData();
		
		rsData[19] = 3;
		rsData[20] = 2;
		try {
			RecordStore rs = RecordStore.openRecordStore(STORE_NAME, true);
			RecordEnumeration re = rs.enumerateRecords(null, null, false);
			if (re.hasNextElement()) {
				int recordId = re.nextRecordId();
				if (rs.getRecordSize(recordId) == RS_DATA_LEN) {
					rsData = rs.getRecord(recordId);
				} else {
					rs.setRecord(recordId, rsData, 0, RS_DATA_LEN);
				}
			} else {
				rs.addRecord(rsData, 0, RS_DATA_LEN);
			}
			rs.closeRecordStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
		flipped = (rsData[16] != 0);
		level = Util.MIN_MAX(0, rsData[18], 2);
		sound = Util.MIN_MAX(0, rsData[19], 5);
	}

	public void save_rsData() {
		rsData[0]	= (byte) 1;
		rsData[16]	= (byte) (flipped ? 1 : 0);
		rsData[18]	= (byte) level;
		rsData[19]	= (byte) sound;
		try {
			RecordStore rs = RecordStore.openRecordStore(STORE_NAME, true);
			RecordEnumeration re = rs.enumerateRecords(null, null, false);
			if (re.hasNextElement()) {
				int recordId = re.nextRecordId();
				rs.setRecord(recordId, rsData, 0, RS_DATA_LEN);
			} else {
				rs.addRecord(rsData, 0, RS_DATA_LEN);
			}
			rs.closeRecordStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public boolean have_rsData() {
		return (rsData[0] != 0);
	}

	public void init() {
		if (midlet.thread.rsData[0] == 0) {
			// pos.fromFen(Position.STARTUP_FEN[midlet.handicap]);
			midlet.thread.pos.fromFen(midlet.mainform.txtFen.getString());
			midlet.thread.pos_showed.fromFen(midlet.mainform.txtFen.getString());

		} else {
			
			// Restore Record-Score Data
			midlet.thread.pos.clearBoard();
			for (int sq = 0; sq < 128; sq ++) {
				int pc = midlet.thread.rsData[sq + 256];
				if (pc > 0) {
					midlet.thread.pos.addPiece(sq, pc);
					midlet.thread.pos_showed.addPiece(sq, pc);
				}
			}
			if (midlet.thread.flipped) {
				midlet.thread.pos.changeSide();
				midlet.thread.pos_showed.changeSide();
			}
			midlet.thread.pos.setIrrev(midlet.thread.rsData[384], midlet.thread.rsData[385] & 255);
			midlet.thread.pos_showed.setIrrev(midlet.thread.rsData[384], midlet.thread.rsData[385] & 255);
		}

		// Backup Retract Status
		System.arraycopy(midlet.thread.rsData, 0, midlet.thread.retractData, 0, ChessThread.RS_DATA_LEN);


		// Call "responseMove()" if Computer Moves First
		/*
		if ((midlet.thread.pos.sdPlayer == 0 ? midlet.thread.flipped : !midlet.thread.flipped) && !midlet.thread.pos.isMate()) {
			new Thread() {
				public void run() {
					while (midlet.canvas.phase == ChessCanvas.PHASE_LOADING) {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							// Ignored
						}
					}
					midlet.canvas.responseMove();
				}
			}.start();
		}
		*/
	}

	public void off() {
		rsData[0] = 0;
	}
	public void on() {
		rsData[0] = 0;
	}
	public void standby() {
		rsData[0] = 0;
	}
}
