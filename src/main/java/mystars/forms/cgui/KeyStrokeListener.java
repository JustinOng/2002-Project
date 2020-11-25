package mystars.forms.cgui;

import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * This class listens for keypresses and closes the pane if escape is closed
 */
public class KeyStrokeListener implements WindowListener {
	/**
	 * Override {@code onInput} to close the base pane if escape is pressed to allow
	 * for "go back" behavior
	 */
	@Override
	public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
		if (keyStroke.getKeyType() == KeyType.Escape) {
			basePane.close();
		}
	}

	/**
	 * Prevents triggering of unintended action when pressing a key that may serve
	 * different functions in different contexts in the program
	 */
	@Override
	public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
		return;
	}

	/**
	 * When the user interface window changes position
	 */
	@Override
	public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
		return;
	}

	/**
	 * When the user interface window is resized
	 */
	@Override
	public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
		return;
	}
}
