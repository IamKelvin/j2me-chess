/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
/**
 *
 * @author root
 */
public class ChessLayout {
	public ChessDrawer d;

	private static Image imgChess;
	private static final String[] IMAGE_NAME = {
		null, null, null, null, null, null, null, null,
		"wk", "wq", "wr", "wb", "wn", "wp", null, null,
		"bk", "bq", "br", "bb", "bn", "bp", null, null,
	};

	public Image[] imgPieces = new Image[24];
	private String	imagePiecePath;

	public int square_size;
	public int board_top, board_left, board_width, board_height;
	public int info_top, info_left, info_width, info_height;

	private ChessCanvas canvas;

	public ChessLayout (ChessCanvas canvas_) {
		canvas = canvas_;
		try {
			imgChess = Image.createImage("/org/openplanning/chess/images/franz.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(int w, int h) {
		adjustLayout(w, h);
		initPieceImages();

		// Set board on top left of screen
		board_top	 = 0;
		board_left   = 0;
		board_width  = 8 * square_size;
		board_height = 8 * square_size;
	}

	private void adjustLayout(int w, int h) {
		// "width" and "height" are Full-Screen values
		imagePiecePath = "/org/openplanning/chess/images/pieces/classic/";
		square_size = Math.min(w / 8, h / 8);
		if (false) {
			// Code Style
		} else if (square_size >= 41) {
			//squareSize = 41;
			imagePiecePath += "png45px/";
		} else if (square_size >= 29) {
			//squareSize = 29;
			imagePiecePath += "png30px/";
		} else if (square_size >= 21) {
			//squareSize = 21;
			imagePiecePath += "png30px/";
		} else {
			//squareSize = 16;
			imagePiecePath += "png30px/";
		}		
	}

	private void initPieceImages() {
		try {
			for (int pc = 0; pc < 24; pc ++) {
				if (IMAGE_NAME[pc] == null) {
					imgPieces[pc] = null;
				} else {
					imgPieces[pc] = Image.createImage(imagePiecePath + IMAGE_NAME[pc] + ".png");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void draw(Graphics g) {
		canvas.drawer.drawBackground(g);
		canvas.drawer.drawBoard(g);
		canvas.drawer.drawLastMove(g);
		canvas.drawer.drawPieces(g);
		canvas.drawer.drawCursor(g);
		canvas.drawer.drawInfo(g);
	}

}
