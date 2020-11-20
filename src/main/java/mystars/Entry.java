package mystars;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import mystars.forms.cgui.ConsoleGraphicUserInterface;
import mystars.notifications.EmailNotifier;
import mystars.notifications.INotifyStudent;

/**
 * <h1>Class: Entry</h1>
 * 
 * This class defines the entry point into the MySTARS program.
 */
public class Entry {
	/**
	 * Main method which creates the MySTARS application object and initializes it.
	 * Command line arguments passed to this application when initializing it include:
	 * - The configuration file containing the information on the student's emails.
	 * 
	 * @param args Command line arguments to this application.
	 */
	public static void main(String[] args) throws IOException {
		String configFile = "app.properties";

		if (args.length > 0) {
			configFile = args[0];
		}
		INotifyStudent notifier = null;

		try {
			Properties config = new Properties();
			config.load(new FileInputStream(configFile));

			notifier = createNotifier(config);

			if (notifier == null) {
				System.err.println(String.format("Unable to find appropriate properties in %s", configFile));
			}
		} catch (FileNotFoundException e) {
			System.err.println(String.format("Unable to find %s", configFile));
		}
		
		if (notifier == null) {
			System.err.println("Email notifications are disabled");
		}

		MySTARS app = new MySTARS(new ConsoleGraphicUserInterface(), notifier);
		app.start();
	}

	/**
	 * Creates a notification for students and sends it to their email.
	 * 
	 * @param config	
	 * @return			An email notification to the student with required port parameters.
	 */
	public static INotifyStudent createNotifier(Properties config) {
		String username = config.getProperty("username", null);
		String password = config.getProperty("password", null);
		String smtpHost = config.getProperty("smtpHost", null);
		String smtpPort = config.getProperty("smtpPort", null);

		// If any of the parameters are not configured.
		if (username == null || password == null || smtpHost == null || smtpPort == null) {
			return null;
		}
		return new EmailNotifier(username, password, smtpHost, smtpPort);
	}
}
