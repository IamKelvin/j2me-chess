/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.ItemStateListener;

import org.openplanning.chess.FranzChessMIDlet;
import org.openplanning.chess.motor.Position;
/**
 *
 * @author root
 */
public class MainForm extends Form {
	public FranzChessMIDlet midlet;

	Command cmdStart	= new Command("Start", Command.OK, 1);
	Command cmdExit		= new Command("Exit", Command.BACK, 1);

	ChoiceGroup cgGameType = new ChoiceGroup(
		"New",
		Choice.EXCLUSIVE,
		new String[] {
			"Human vs. FranzChess",
			"FranzChess vs. Human",
		},
		null
	);

	ChoiceGroup cgGameRule = new ChoiceGroup(
		"Option",
		Choice.EXCLUSIVE,
		new String[] {
			"Blitz 5 min.",
			"Blitz 10 min.",
		},
		null
	);
	
	StringItem okButton = new StringItem("OK","OK",Item.BUTTON);

	ChoiceGroup cgToMove = new ChoiceGroup("Player Takes", Choice.EXCLUSIVE,
				new String[] {"White", "Black"}, null);
	ChoiceGroup cgHandicap = new ChoiceGroup("White offers", Choice.POPUP,
				new String[] {"No Odds", "a Knight Odds", "a Rook Odds", "a Queen Odds"}, null);
	ChoiceGroup cgLevel = new ChoiceGroup("Level", Choice.POPUP,
				new String[] {"Beginner", "Amateur", "Expert"}, null);
	Gauge gSound = new Gauge("Sound Effect", true, 5, 0);

	public TextField txtFen = new TextField("Startup FEN", null, 128, TextField.ANY);
	//form.append(txtFen);

	public MainForm(FranzChessMIDlet midlet_) {
		super("FranzChess 0.1.2\nClearlook and Mobile Chess Apps");

		midlet = midlet_;

		this.append(cgGameType);
		this.append(cgGameRule);
		this.append(okButton);

		this.append(cgToMove);


		this.append(cgLevel);
		this.append(gSound);

		this.addCommand(cmdStart);
		this.addCommand(cmdExit);

		this.setCommandListener(new CommandListener() {
			public void commandAction(Command c, Displayable d) {
				if (false) {
					// Code Style
				} else if (c == cmdStart) {
					midlet.thread.flipped	= cgToMove.isSelected(1);
					midlet.thread.level	= cgLevel.getSelectedIndex();
					midlet.thread.sound	= gSound.getValue();
					midlet.canvas.init();
					midlet.thread.init();
					midlet.display.setCurrent(midlet.canvas);					
				} else if (c == cmdExit) {
					midlet.destroyApp(false);
					midlet.notifyDestroyed();
				}
			}
		});

		this.setItemStateListener(new ItemStateListener() {
			public void itemStateChanged(Item i) {
				if (false) {
					// Code Style
				} else if (i == cgHandicap) {
					txtFen.setString(Position.STARTUP_FEN[cgHandicap.getSelectedIndex()]);
				} else if (i == gSound) {
					midlet.thread.sound = gSound.getValue();
					midlet.ac.playSound(0);
				}
			}
		});
	}

	public void load_from_thread() {
		cgToMove.setSelectedIndex(midlet.thread.flipped ? 1 : 0, true);
		cgLevel.setSelectedIndex(midlet.thread.level, true);
		gSound.setValue(midlet.thread.sound);
		txtFen.setString(Position.STARTUP_FEN[0]);
	}
}
