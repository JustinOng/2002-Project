package mystars;

import java.io.IOException;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

public class Entry
{
	public static void main(String[] args) throws IOException
	{
		MySTARS app = new MySTARS(new ConsoleGraphicUserInterface());
		app.start();
	}
}
