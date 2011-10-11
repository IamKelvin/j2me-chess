/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openplanning.chess.ui;

import javax.microedition.lcdui.Form;

import org.openplanning.chess.FranzChessMIDlet;

/**
 *
 * @author root
 */
public class OptionForm extends Form{
	public FranzChessMIDlet midlet;

	public OptionForm(FranzChessMIDlet midlet_) {
		super("Game Option");
		midlet = midlet_;
	}
}
