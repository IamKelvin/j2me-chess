/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javanile.chessbit;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.javanile.common.*;

/**
 * @author cicciodarkast
 */
public class ChessBitMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    private ChessBitCanvas cbc;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command itemCommand;
    private Command itemCommand1;
    private Form main;
    private Form settings;
    private TextField textField;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public ChessBitMIDlet() {
	cbc = new ChessBitCanvas();
	cbc.setCommandListener(this);
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
	switchDisplayable(null, getMain());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
	Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
	if (alert == null) {
	    display.setCurrent(nextDisplayable);
	} else {
	    display.setCurrent(alert, nextDisplayable);
	}//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
	if (displayable == main) {//GEN-BEGIN:|7-commandAction|1|19-preAction
	    if (command == exitCommand) {//GEN-END:|7-commandAction|1|19-preAction
                // write pre-action user code here
		exitMIDlet();//GEN-LINE:|7-commandAction|2|19-postAction
                // write post-action user code here
	    } else if (command == itemCommand) {//GEN-LINE:|7-commandAction|3|23-preAction
		// write pre-action user code here
		openChessBitCanvas();//GEN-LINE:|7-commandAction|4|23-postAction
		// write post-action user code here
	    } else if (command == itemCommand1) {//GEN-LINE:|7-commandAction|5|33-preAction
		// write pre-action user code here
		switchDisplayable(null, getSettings());//GEN-LINE:|7-commandAction|6|33-postAction
		// write post-action user code here
	    }//GEN-BEGIN:|7-commandAction|7|7-postCommandAction
	}//GEN-END:|7-commandAction|7|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|8|
    //</editor-fold>//GEN-END:|7-commandAction|8|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
	if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
	    exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
	}//GEN-BEGIN:|18-getter|2|
	return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: main ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of main component.
     * @return the initialized component instance
     */
    public Form getMain() {
	if (main == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
	    main = new Form("ChessBit", new Item[] { });//GEN-BEGIN:|14-getter|1|14-postInit
	    main.addCommand(getExitCommand());
	    main.addCommand(getItemCommand());
	    main.addCommand(getItemCommand1());
	    main.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
	}//GEN-BEGIN:|14-getter|2|
	return main;
    }
    //</editor-fold>//GEN-END:|14-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of itemCommand component.
     * @return the initialized component instance
     */
    public Command getItemCommand() {
	if (itemCommand == null) {//GEN-END:|22-getter|0|22-preInit
	    // write pre-init user code here
	    itemCommand = new Command("Ciao", Command.ITEM, 0);//GEN-LINE:|22-getter|1|22-postInit
	    // write post-init user code here
	}//GEN-BEGIN:|22-getter|2|
	return itemCommand;
    }
    //</editor-fold>//GEN-END:|22-getter|2|
    //</editor-fold>



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: openChessBitCanvas ">//GEN-BEGIN:|26-entry|0|27-preAction
    /**
     * Performs an action assigned to the openChessBitCanvas entry-point.
     */
    public void openChessBitCanvas() {//GEN-END:|26-entry|0|27-preAction
	// write pre-action user code here
	Display.getDisplay(this).setCurrent(cbc);
	cbc.repaint();
//GEN-LINE:|26-entry|1|27-postAction
	// write post-action user code here
    }//GEN-BEGIN:|26-entry|2|
    //</editor-fold>//GEN-END:|26-entry|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand1 ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of itemCommand1 component.
     * @return the initialized component instance
     */
    public Command getItemCommand1() {
	if (itemCommand1 == null) {//GEN-END:|32-getter|0|32-preInit
	    // write pre-init user code here
	    itemCommand1 = new Command("Ciao1", Command.ITEM, 0);//GEN-LINE:|32-getter|1|32-postInit
	    // write post-init user code here
	}//GEN-BEGIN:|32-getter|2|
	return itemCommand1;
    }
    //</editor-fold>//GEN-END:|32-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: settings ">//GEN-BEGIN:|35-getter|0|35-preInit
    /**
     * Returns an initiliazed instance of settings component.
     * @return the initialized component instance
     */
    public Form getSettings() {
	if (settings == null) {//GEN-END:|35-getter|0|35-preInit
	    // write pre-init user code here
	    settings = new Form("Settings", new Item[] { getTextField() });//GEN-LINE:|35-getter|1|35-postInit
	    // write post-init user code here
	}//GEN-BEGIN:|35-getter|2|
	return settings;
    }
    //</editor-fold>//GEN-END:|35-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">//GEN-BEGIN:|37-getter|0|37-preInit
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTextField() {
	if (textField == null) {//GEN-END:|37-getter|0|37-preInit
	    // write pre-init user code here
	    textField = new TextField("Your name", null, 32, TextField.ANY);//GEN-LINE:|37-getter|1|37-postInit
	    // write post-init user code here
	}//GEN-BEGIN:|37-getter|2|
	return textField;
    }
    //</editor-fold>//GEN-END:|37-getter|2|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

}
