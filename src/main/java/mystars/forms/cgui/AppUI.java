package mystars.forms.cgui;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowManager;
import com.googlecode.lanterna.screen.Screen;

import mystars.exceptions.UIException;

/**
 * <h1>Class: AppUI</h1>
 * 
 * This class manages the overall user interface for the MySTARS program.
 */
public class AppUI extends MultiWindowTextGUI {
	/**
	 * Pass through constructor arguments to super
	 * 
	 * @param screen        The screen object.
	 * @param windowManager The window manager object.
	 * @param background    The background object.
	 */
	public AppUI(Screen screen, WindowManager windowManager, Component background) {
		super(screen, windowManager, background);
	}

	/**
	 * In our use case, since we only display one window at a time, we shoudn't have
	 * an active window at this point because 'addWindowAndWait' should only return
	 * once the window closes.
	 * 
	 * This handles the case where the application window has been closed (eg by
	 * clicking X), {@code addWindowAndWait} returns immediately and there is still an
	 * active window. We throw an exception which will bubble up to the main thread
	 * to terminate the application.
	 */
	@Override
	public WindowBasedTextGUI addWindowAndWait(Window window) {
		super.addWindowAndWait(window);

		if (getActiveWindow() != null) {
			throw new UIException("UI exited");
		}
		return this;
	}
}
