package org.openplanning.chess.ui;

import org.openplanning.chess.motor.Position;
import org.openplanning.chess.motor.Util;
import org.openplanning.chess.motor.ChessThread;
import org.openplanning.chess.FranzChessMIDlet;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ChessCanvas extends Canvas {
	
	private static final int REPAINT_BOARD	= 0;
	private static final int REPAINT_INFO	= 1;

	public	static final int PHASE_LOADING	= 0;
	private static final int PHASE_WAITING	= 1;
	private static final int PHASE_THINKING = 2;
	private static final int PHASE_EXITTING = 3;
	private static final int PHASE_USERTURN	= 4;
	private static final int PHASE_EDITING	= 5;
	private static final int PHASE_ANALIZE	= 6;

	private static final String[] PHASE_STRING = {
		"LOADING",
		"WAITING",
		"THINKING",
		"EXITING",
		"USERTURN",
		"EDITING",
		"ANALIZE"
	};

	int pressed_key;

	public FranzChessMIDlet midlet;

	public ChessLayout layout = new ChessLayout(this);
	public ChessDrawer drawer = new ChessDrawer(this);
	
	public boolean	flipped		= false;
	public int		cursorX		= 0;
	public int		cursorY		= 0;
	public int		sqSelected	= 0;
	public int		mvLast		= 0;
	public int		animation	= 0;

	public int normalWidth		= getWidth();
	public int normalHeight		= getHeight();
	public int fullscreenWidth	= getWidth();
	public int fullscreenHeight	= getHeight();

	private Alert altAbout	= new Alert("About \"FrankChess\"", null, null, AlertType.INFO);
	private Alert altBack	= new Alert("FrankChess", "Abort Current Application?", null, AlertType.CONFIRMATION);
	private Alert altMsg	= new Alert("Attention!", "", null, AlertType.INFO);

	Command cmdBack			= new Command("Back",		Command.ITEM,	1);
	Command cmdRetract		= new Command("Retract",	Command.ITEM,	1);
	Command cmdAbout		= new Command("About",		Command.ITEM,	1);
	Command cmdBackOK		= new Command("OK",			Command.OK,		1);
	Command cmdBackCancel	= new Command("Cancel",		Command.CANCEL, 1);

	volatile int phase = PHASE_LOADING;

	private boolean started = false;
	
	public ChessCanvas(FranzChessMIDlet midlet_) {
		this.midlet = midlet_;
		setFullScreenMode(true);
		altAbout.setTimeout(Alert.FOREVER);
		altAbout.setString(midlet.getAppProperty("MIDlet-Description") + "\n\r\f" +
				"mobilechess.sourceforge.net Honor Products\n\r\f\n\r\f" +
				"(C) 2008-2009 mobilechess.sourceforge.net\n\r\f" +
				"This product accords with the GNU General Public License");
		altBack.setTimeout(Alert.FOREVER);
		altBack.addCommand(cmdBackOK);
		altBack.addCommand(cmdBackCancel);
		altBack.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (c == cmdBackOK) {
					midlet.thread.off();
					midlet.display.setCurrent(midlet.mainform);
				} else {
					midlet.display.setCurrent(ChessCanvas.this);
					phase = PHASE_LOADING;
					setFullScreenMode(true);
				}
			}
		});
		addCommand(cmdBack);
		addCommand(cmdRetract);
		addCommand(cmdAbout);

		setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (phase == PHASE_WAITING || phase == PHASE_EXITTING) {
					if (false) {
						// Code Style
					} else if (c == cmdBack) {
						back();
					} else if (c == cmdRetract) {
						retract();
					} else if (c == cmdAbout) {
						about();
					}
				}
			}
		});

		normalWidth			= getWidth();
		normalHeight		= getHeight();
		fullscreenWidth		= getWidth();
		fullscreenHeight	= getHeight();
	}

	public void init() {
		setFullScreenMode(true);
		cursorX		= 4;
		cursorY		= 6;
		sqSelected	= 0;
		mvLast		= 0;
		phase		= PHASE_LOADING;
	}

	protected void paint(Graphics g) {
		if (false) {
		} else if (phase == PHASE_LOADING) {
			adjustFullscreen();
			if (!started) {
				layout.init(fullscreenWidth, fullscreenHeight);
                started = true;				
			} 
			layout.draw(g);
			if (midlet.thread.now(ChessThread.PLAYER_HUMAN)) {
				phase = PHASE_USERTURN;
			} else {
				phase = PHASE_WAITING;
			}
		} else if (phase == PHASE_WAITING) {
			layout.draw(g);
			
		} else if (phase == PHASE_USERTURN) {
			layout.draw(g);

		} else if (phase == PHASE_THINKING) {
			/*
			int x, y;
			if (midlet.thread.flipped) {
				x = (Position.FILE_X(sqDst) < 8 ? left : right);
				y = (Position.RANK_Y(sqDst) < 4 ? top : bottom);
			} else {
				x = (Position.FILE_X(sqDst) < 8 ? right: left);
				y = (Position.RANK_Y(sqDst) < 4 ? bottom: top);
			}
			*/
		} else if (phase == PHASE_EXITTING) {
			/*
			g.setFont(fontLarge);
			g.setColor(0xff0000);
			g.drawString(message, width / 2, height / 2, Graphics.HCENTER + Graphics.BASELINE);
			*/
		}		
	}


	public void doRepaint() {
		repaint();
		serviceRepaints();
	}
	public void doRepaint(int area) {
		if (false) {
		} else if (area == REPAINT_BOARD) {
			repaint(
				layout.board_top,
				layout.board_left,
				layout.board_width,
				layout.board_height
			);
			serviceRepaints();
		} else if (area == REPAINT_INFO) {
			repaint(
				layout.info_top,
				layout.info_left,
				layout.info_width,
				layout.info_height
			);
			serviceRepaints();
		}
	}

	private void adjustFullscreen() {
		fullscreenWidth   = getWidth();
		fullscreenHeight  = getHeight();
		for (int i = 0; i < 10; i ++) {
			if (fullscreenWidth != normalWidth || fullscreenHeight != normalHeight) {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// Ignored
			}
			fullscreenWidth  = getWidth();
			fullscreenHeight = getHeight();
		}
	}
	
	protected void keyPressed(int code) {
		pressed_key = code;
		int deltaX = 0, deltaY = 0;
		int action = getGameAction(code);
		if (action == FIRE || code == KEY_NUM5) {
			clickSquare();			
		} else {
			switch (action) {
			case UP:
				deltaY = -1;
				break;
			case LEFT:
				deltaX = -1;
				break;
			case RIGHT:
				deltaX = 1;
				break;
			case DOWN:
				deltaY = 1;
				break;
			default:
				switch (code) {
					case KEY_NUM1:
						deltaX = -1;
						deltaY = -1;
						break;
					case KEY_NUM2:
						deltaY = -1;
						break;
					case KEY_NUM3:
						deltaX = 1;
						deltaY = -1;
						break;
					case KEY_NUM4:
						deltaX = -1;
						break;
					case KEY_NUM6:
						deltaX = 1;
						break;
					case KEY_NUM7:
						deltaX = -1;
						deltaY = 1;
						break;
					case KEY_NUM8:
						deltaY = 1;
						break;
					case KEY_NUM9:
						deltaX = 1;
						deltaY = 1;
						break;
				}
			}
			cursorX = (cursorX + deltaX + 8) % 8;
			cursorY = (cursorY + deltaY + 8) % 8;
		}
		doRepaint(REPAINT_BOARD);
	}

	protected void pointerPressed(int x, int y) {
		cursorX = Util.MIN_MAX(0, (x - layout.board_left) / layout.square_size, 7);
		cursorY = Util.MIN_MAX(0, (y - layout.board_top) / layout.square_size, 7);
		clickSquare();
		doRepaint(REPAINT_BOARD);
	}

	private void clickSquare() {
		int sq = Position.COORD_XY(cursorX + Position.FILE_LEFT, cursorY + Position.RANK_TOP);
		if (midlet.thread.flipped) {
			sq = Position.SQUARE_FLIP(sq);
		}
		int pc = midlet.thread.pos.squares[sq];
		
		if (false) {
		} else if (phase == PHASE_USERTURN) {
			if ((pc & Position.SIDE_TAG(midlet.thread.pos.sdPlayer)) != 0) {
				midlet.ac.playSound(0);
				mvLast = 0;
				sqSelected = sq;
			} else {
				if (sqSelected > 0) {
					int mv = Position.MOVE(sqSelected, sq);
					midlet.thread.motor.sendMove(mv);
					if (midlet.thread.motor.isvalid) {						
						sqSelected	= 0;
						mvLast		= mv;						
						if (midlet.thread.now(ChessThread.PLAYER_MOTOR)) {
							phase	= PHASE_WAITING;
						} else {
							phase	= PHASE_WAITING;
						}
						animateMove(sqSelected, sq);
					} else {
						showMessage("Illegal move!");
					}
				}
			}
		} else if (phase == PHASE_WAITING) {
			showMessage("Not your turn!");
		} else if (phase == PHASE_EXITTING) {
			midlet.display.setCurrent(midlet.mainform);
			return;
		}
	}

	void back() {
		if (phase == PHASE_WAITING) {
			midlet.display.setCurrent(altBack);
		} else {
			midlet.thread.rsData[0] = 0;
			midlet.display.setCurrent(midlet.mainform);
		}
	}

	void retract() {
		// Restore Retract Status
		System.arraycopy(midlet.thread.retractData, 0, midlet.thread.rsData, 0, ChessThread.RS_DATA_LEN);
		init();
		repaint();
		serviceRepaints();
	}

	void about() {
		Display.getDisplay(midlet).setCurrent(altAbout);
		phase = PHASE_LOADING;
		setFullScreenMode(true);
	}

	private void animateMove(int sqSrc, int sqDst) {
		doRepaint(REPAINT_BOARD);
	}

	public void showMessage(String message) {
		altMsg.setString(message);
		Display.getDisplay(midlet).setCurrent(altMsg);
		setFullScreenMode(true);
	}

	public String getPhase() {
		return PHASE_STRING[phase];
	}

	public void sendMove(int mv) {
		phase = PHASE_USERTURN;
		mvLast = mv;
	}
}