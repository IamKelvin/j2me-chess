/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import org.openplanning.chess.motor.Position;
import javax.microedition.lcdui.*;
/**
 *
 * @author root
 */
public class ChessDrawer {
	private ChessCanvas canvas;

	private static Font fontInfo = Font.getFont(
		Font.FACE_MONOSPACE,
		Font.STYLE_PLAIN,
		Font.SIZE_SMALL
	);

	private static Font fontSmall = Font.getFont(
		Font.FACE_SYSTEM,
		Font.STYLE_BOLD,
		Font.SIZE_SMALL
	);

	public ChessDrawer(ChessCanvas canvas_) {
		canvas = canvas_;
	}

	private int sqX(int sq) {
		int sqFlipped = (canvas.flipped ? Position.SQUARE_FLIP(sq) : sq);
		int sqX = canvas.layout.board_left + (Position.FILE_X(sqFlipped) - Position.FILE_LEFT) * canvas.layout.square_size;
		return sqX;
	}

	private int sqY(int sq) {
		int sqFlipped = (canvas.flipped ? Position.SQUARE_FLIP(sq) : sq);
		int sqY = canvas.layout.board_top + (Position.RANK_Y(sqFlipped) - Position.RANK_TOP) * canvas.layout.square_size;
		return sqY;
	}

	public void drawPieceSquare(Graphics g, Image image, int sq) {
		int sqX = sqX(sq);
		int sqY = sqY(sq);
		g.drawImage(image, sqX, sqY, Graphics.LEFT + Graphics.TOP);
	}

    public void drawVoidSquare(Graphics g, int sq) {
		int sqX = sqX(sq);
		int sqY = sqY(sq);

		if (((Position.RANK_Y(sq)+Position.FILE_X(sq)) % 2) != 0) {
			g.setColor(0x4D6D92);
		} else {
			g.setColor(0xECECD7);
		}
		g.fillRect(sqX, sqY, canvas.layout.square_size, canvas.layout.square_size);
	}

    public void drawCursorSquare(Graphics g, int sq) {
		int sqX = sqX(sq);
		int sqY = sqY(sq);

		g.setColor(0x333333);
		g.drawRect(sqX,		sqY,	canvas.layout.square_size-1,	canvas.layout.square_size-1);
		g.drawRect(sqX+1,	sqY+1,	canvas.layout.square_size-3,	canvas.layout.square_size-3);
		g.drawRect(sqX+2,	sqY+2,	canvas.layout.square_size-5,	canvas.layout.square_size-5);
	}

	public void drawSrcSquare(Graphics g, int sq) {
		int sqX = sqX(sq);
		int sqY = sqY(sq);

		g.setColor(0x99ff99);
		g.fillRect(sqX, sqY, canvas.layout.square_size, canvas.layout.square_size);
	}

	public void drawDstSquare(Graphics g, int sq) {
		int sqX = sqX(sq);
		int sqY = sqY(sq);

		g.setColor(0x66ff33);
		g.fillRect(sqX, sqY, canvas.layout.square_size, canvas.layout.square_size);
	}

	public void drawBackground(Graphics g) {
		// Background
		g.setColor(0xFFFFFF);
		g.fillRect(0,0,canvas.fullscreenWidth,canvas.fullscreenHeight);
	}

	public void drawBoard(Graphics g) {
		// Borad
		for (int sq = 0; sq < 128; sq ++) {
			if (Position.IN_BOARD(sq)) {
				drawVoidSquare(g, sq);
			}
		}
	}

	public void drawLastMove(Graphics g) {
		// Last move decoration
		int sqSrc = 0;
		int sqDst = 0;
		if (canvas.mvLast > 0) {
			sqSrc = Position.SRC(canvas.mvLast);
			sqDst = Position.DST(canvas.mvLast);
			drawSrcSquare(g, sqSrc);
			drawDstSquare(g, sqDst);
		} else if (canvas.sqSelected > 0) {
			drawSrcSquare(g, canvas.sqSelected);
		}
	}

	public void drawPieces(Graphics g) {
		// Piece in board
        for (int sq = 0; sq < 128; sq ++) {
			if (Position.IN_BOARD(sq)) {
				int pc = canvas.midlet.thread.pos.squares[sq];
				if (pc > 0) {
					drawPieceSquare(g, canvas.layout.imgPieces[pc], sq);
				}
			}
		}
	}

	public void drawCursor(Graphics g) {
		// Cursor on board
		int sq = Position.COORD_XY(canvas.cursorX + Position.FILE_LEFT, canvas.cursorY + Position.RANK_TOP);
		if (canvas.flipped) {
			sq = Position.SQUARE_FLIP(sq);
		}
		drawCursorSquare(g, sq);
	}

	public void drawInfo(Graphics g) {
		g.setFont(fontInfo);

		String statusbar = "() - "+String.valueOf(canvas.theKeyPressed);
		g.drawString(
			statusbar,
			canvas.fullscreenWidth,
			canvas.fullscreenHeight,
			Graphics.BOTTOM + Graphics.RIGHT
		);
		g.drawString(
			canvas.midlet.thread.motor.thinkStringReturn(),
			0,
			canvas.fullscreenHeight,
			Graphics.BOTTOM + Graphics.LEFT
		);
		g.drawString(
			canvas.getPhase(),
			20,
			canvas.fullscreenHeight,
			Graphics.BOTTOM + Graphics.LEFT
		);
		g.drawString(
			canvas.midlet.thread.getPlayer(),
			100,
			canvas.fullscreenHeight,
			Graphics.BOTTOM + Graphics.LEFT
		);
	}
}
