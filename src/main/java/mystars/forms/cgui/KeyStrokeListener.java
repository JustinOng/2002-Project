package mystars.forms.cgui;

import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * <h1>Class: KeyStrokeListener</h1>
 * 
 * This class listens for keypresses and closes the pane if escape is closed
 */
public class KeyStrokeListener implements WindowListener {
	/**
	 * When the user enters keystrokes.
	 */
	public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
		if (keyStroke.getKeyType() == KeyType.Escape) {
			basePane.close();
		}
	}

	public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
		return;
	}

	public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
		return;
	}

	public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
		 return;
	}
}
