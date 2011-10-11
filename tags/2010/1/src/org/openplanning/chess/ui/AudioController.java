/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author root
 */
public class AudioController {
	FranzChessMIDlet midlet;

	static final String[] SOUND_NAME = {
		"click", "illegal", "move", "move2", "capture", "capture2",
		"special", "special2", "check", "check2", "win", "draw", "loss",
	};
	
	public AudioController(FranzChessMIDlet midlet_) {
		midlet = midlet_;
	}

	Player createPlayer(String name, String type) {
		InputStream in = getClass().getResourceAsStream(name);
		try {
			return Manager.createPlayer(in, type);
			// If creating "Player" succeeded, no need to close "InputStream".
		} catch (Exception e) {
			try {
				in.close();
			} catch (Exception e2) {
				// Ignored
			}
			return null;
		}
	}

	void playSound(int response) {
		if (midlet.thread.sound == 0) {
			return;
		}
		final int i = response;
		new Thread() {
			public void run() {
				Player p = createPlayer("/sounds/" + SOUND_NAME[i] + ".wav", "audio/x-wav");
				if (p == null) {
					return;
				}
				try {
					p.realize();
					VolumeControl vc = (VolumeControl) p.getControl("VolumeControl");
					if (vc != null) {
						vc.setLevel(midlet.thread.sound * 10);
					}
					long t = p.getDuration();
					p.start();
					if (t != Player.TIME_UNKNOWN) {
						sleep(t / 1000 + 1);
					}
					while (p.getState() == Player.STARTED) {
						sleep(100);
					}
				} catch (Exception e) {
					// Ignored
				}
				p.close();
			}
		}.start();
	}
}
