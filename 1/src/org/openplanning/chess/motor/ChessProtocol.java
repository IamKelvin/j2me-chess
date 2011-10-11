/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.motor;

/**
 *
 * @author root
 */
public class ChessProtocol {
	private static final int RESP_CLICK		= 0;
	private static final int RESP_ILLEGAL	= 1;
	private static final int RESP_MOVE		= 2;
	private static final int RESP_MOVE2		= 3;
	private static final int RESP_CAPTURE	= 4;
	private static final int RESP_CAPTURE2	= 5;
	private static final int RESP_SPECIAL	= 6;
	private static final int RESP_SPECIAL2	= 7;
	private static final int RESP_CHECK		= 8;
	private static final int RESP_CHECK2	= 9;
	private static final int RESP_WIN		= 10;
	private static final int RESP_DRAW		= 11;
	private static final int RESP_LOSS		= 12;
}
