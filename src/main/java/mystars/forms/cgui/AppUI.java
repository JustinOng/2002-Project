package mystars.forms.cgui;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowManager;
import com.googlecode.lanterna.screen.Screen;

import mystars.exceptions.UIException;

public class AppUI extends MultiWindowTextGUI {
	public AppUI(Screen screen, WindowManager windowManager, Component background) {
		super(screen, windowManager, background);
	}

	@Override
	public WindowBasedTextGUI addWindowAndWait(Window window) {
		super.addWindowAndWait(window);

		if (getActiveWindow() != null) {
			/*
			 * in our use case, since we only display one window at a time, we shoudn't have
			 * an active window at this point because addWindowAndWait should only return
			 * once the window closes
			 * 
			 * This handles the case where the application window has been closed (eg by X),
			 * addWindowAndWait returns immediately and there is still an active window. We
			 * throw an exception which will bubble up to the main thread to terminate the
			 * application
			 */
			throw new UIException("UI exited");
		}
		return this;
	}
}
