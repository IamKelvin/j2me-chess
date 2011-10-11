/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
/**
 *
 * @author root
 */
public class ChessThread {
	private static final String STORE_NAME = "FranzChess";

	static final int RS_DATA_LEN = 512;
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
	byte[]	rsData = new byte[RS_DATA_LEN];
	boolean flipped;
	int		level, sound;

	public ChessThread (MIDlet midlet_) {

	}

	public void clear_rsData() {
		for (int i = 0; i < RS_DATA_LEN; i ++) {
			rsData[i] = 0;
		}		
	}

	public void load_rsData() {
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
			// Ignored
		}
		flipped = (rsData[16] != 0);
		level = Util.MIN_MAX(0, rsData[18], 2);
		sound = Util.MIN_MAX(0, rsData[19], 5);
	}

	public void store_rsData() {
		rsData[16] = (byte) (flipped ? 1 : 0);
		rsData[18] = (byte) level;
		rsData[19] = (byte) sound;
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
			// Ignored
		}
	}

	public boolean isOpened() {
		return !(rsData[0] == 0);
	}


}
