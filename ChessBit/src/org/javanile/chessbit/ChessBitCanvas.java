/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javanile.chessbit;

import javax.microedition.lcdui.*;

/**
 *
 * @author cicciodarkast
 */
public class ChessBitCanvas extends Canvas {
//---------------------------------------------------------
  //   fields

  /**
   * whether or not the screen should currently display the
   * "hello world" message.
   */
  boolean mySayHello = true;

  //-----------------------------------------------------
  //    initialization and game state changes

  /**
   * toggle the hello message.
   */
  void ChessBitCanvas() {
    mySayHello = !mySayHello;
    repaint();
  }

  //-------------------------------------------------------
  //  graphics methods

  /**
   * clear the screen and display the hello world message if appropriate.
   */
  public void paint(Graphics g) {
    // get the dimensions of the screen:
    int width = getWidth();
    int height = getHeight();
    // clear the screen (paint it white):
    g.setColor(0xffffff);
    // The first two args give the coordinates of the top
    // left corner of the rectangle.  (0,0) corresponds
    // to the top left corner of the screen.
    g.fillRect(0, 0, width, height);
    // display the hello world message if appropriate:.
    if(mySayHello) {
      Font font = g.getFont();
      int fontHeight = font.getHeight();
      int fontWidth = font.stringWidth("Hello World!");
      // set the text color to red:
      g.setColor(255, 0, 0);
      g.setFont(font);
      // write the string in the center of the screen
      g.drawString("Hello World!", (width - fontWidth)/2,
       (height - fontHeight)/2,
       g.TOP|g.LEFT);
    }
  }
}
