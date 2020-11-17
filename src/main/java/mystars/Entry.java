package mystars;

import java.io.IOException;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

/**
 * <h1>Class: Entry</h1>
 * 
 * This class defines the entry point into the MySTARS program.
 */
public class Entry
{
	/**
	 * Main method which creates the MySTARS application object and initializes it.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		MySTARS app = new MySTARS(new ConsoleGraphicUserInterface());
		app.start();
	}
}
