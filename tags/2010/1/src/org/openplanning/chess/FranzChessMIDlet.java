/*\
 *	FranzChess
 *	Clearlook & Mobile Application for Chess
 *
\*/
package org.openplanning.chess;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import org.openplanning.chess.sounds.AudioController;
import org.openplanning.chess.ui.ChessCanvas;
import org.openplanning.chess.motor.ChessThread;
import org.openplanning.chess.ui.MainForm;
import org.openplanning.chess.ui.OptionForm;

public class FranzChessMIDlet extends MIDlet {
	public static final boolean DEBUG = true;

	private boolean started = false;

	public MainForm		mainform	= new MainForm(this);
	public OptionForm	optionform	= new OptionForm(this);

	public Display display;

	public ChessCanvas canvas	= new ChessCanvas(this);
	public ChessThread thread	= new ChessThread(this);

	public AudioController ac	= new AudioController(this);
		
	public FranzChessMIDlet () {
		display = Display.getDisplay(this);
	}

	public void startApp() {
		if (!started) {
			thread.load_rsData();
			thread.init();

			mainform.load_from_thread();

			if (thread.have_rsData()) {
				canvas.init();
				display.setCurrent(canvas);
			} else {
				display.setCurrent(mainform);
			}
		} else {
			started = true;
		}
	}

	public void pauseApp() {
		// Do Nothing
	}

	public void destroyApp(boolean unc) {
		debugApp("Destroy");
		thread.save_rsData();
		started = false;
	}

	public void debugApp(String m) {
		if (DEBUG) {
			System.out.println(m);
		}
	}
}