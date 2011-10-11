/*
ChessMIDlet.java - Source Code for Mobile Chess, Part III

Mobile Chess - a Chess Program for Java ME
Designed by Morning Yellow, Version: 1.03, Last Modified: Mar. 2008
Copyright (C) 2008 mobilechess.sourceforge.net

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package chess;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class FranzChessMIDlet extends MIDlet {
	private boolean started = false;

	Form form = new Form("FranzChess 0.1.2 - Clearlook and Mobile Chess Apps");

	ChessCanvas canvas	= new ChessCanvas(this);
	ChessThread thread	= new ChessThread(this);

	AudioController ac	= new AudioController(this);
	
	Command cmdStart	= new Command("Start", Command.OK, 1);
	Command cmdExit		= new Command("Exit", Command.BACK, 1);

	ChoiceGroup cgToMove = new ChoiceGroup("Player Takes", Choice.EXCLUSIVE,
				new String[] {"White", "Black"}, null);
	ChoiceGroup cgHandicap = new ChoiceGroup("White offers", Choice.POPUP,
				new String[] {"No Odds", "a Knight Odds", "a Rook Odds", "a Queen Odds"}, null);
	ChoiceGroup cgLevel = new ChoiceGroup("Level", Choice.POPUP,
				new String[] {"Beginner", "Amateur", "Expert"}, null);
	Gauge gSound = new Gauge("Sound Effect", true, 5, 0);
	TextField txtFen = new TextField("Startup FEN", null, 128, TextField.ANY);

	public FranzChessMIDlet () {

		form.append(cgToMove);
		form.append(cgLevel);
		form.append(gSound);

		form.addCommand(cmdStart);
		form.addCommand(cmdExit);

		//form.append(txtFen);
	
		form.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (false) {
					// Code Style
				} else if (c == cmdStart) {
					thread.flipped	= cgToMove.isSelected(1);
					thread.level	= cgLevel.getSelectedIndex();
					thread.sound	= gSound.getValue();
					canvas.load();
					Display.getDisplay(FranzChessMIDlet.this).setCurrent(canvas);
				} else if (c == cmdExit) {
					destroyApp(false);
					notifyDestroyed();
				}
			}
		});

		form.setItemStateListener(new ItemStateListener() {
			public void itemStateChanged(Item i) {
				if (false) {
					// Code Style
				} else if (i == cgHandicap) {
					txtFen.setString(Position.STARTUP_FEN[cgHandicap.getSelectedIndex()]);
				} else if (i == gSound) {
					thread.sound = gSound.getValue();
					ac.playSound(0);
				} 
			}
		});
	}

	public void startApp() {
		if (started) {
			return;
		} else {
			started = true;
		}
		thread.clear_rsData();
		thread.load_rsData();

		cgToMove.setSelectedIndex(thread.flipped ? 1 : 0, true);
		cgLevel.setSelectedIndex(thread.level, true);
		gSound.setValue(thread.sound);
		txtFen.setString(Position.STARTUP_FEN[0]);

		if (thread.isOpened()) {
			canvas.load();
			Display.getDisplay(this).setCurrent(canvas);
		} else {
			Display.getDisplay(this).setCurrent(form);
		}
	}

	public void pauseApp() {
		// Do Nothing
	}

	public void destroyApp(boolean unc) {
		thread.store_rsData();
		started = false;
	}
}