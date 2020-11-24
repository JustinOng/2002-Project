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
	 * Command line arguments that can be passed to the application:<br>
	 * --config=/path/to/file - path to properties file containing parameters for
	 * the application<br>
	 * --load-indexes=/path/to/file - path to txt file containing course
	 * information. See {@link MySTARS#loadIndexes(String)} for format
	 * information<br>
	 * --create-admin - create admin account. See {@link MySTARS#createAdmin()} for
	 * more information --create-students - create student accounts. See
	 * {@link MySTARS#createStudents()} for more information
	 * 
	 * @param args Command line arguments to this application.
	 * @throws IOException on UI exception
	 */
	public static void main(String[] args) throws IOException {
		String configFile = "app.properties";
		String indexDataFile = null;

		boolean createAdmin = false;
		boolean createStudents = false;
		int maxAUs = -1;
		int telnetPort = -1;

		for (String arg : args) {
			if (arg.startsWith("--config=")) {
				configFile = arg.replaceFirst("--config=", "");
			} else if (arg.startsWith("--load-indexes=")) {
				indexDataFile = arg.replaceFirst("--load-indexes=", "");
			} else if (arg.startsWith("--create-admin")) {
				createAdmin = true;
			} else if (arg.startsWith("--create-students")) {
				createStudents = true;
			} else if (arg.startsWith("--telnet=")) {
				telnetPort = Integer.parseInt(arg.replaceFirst("--telnet=", ""));
			}
		}
		INotifyStudent notifier = null;

		try {
			Properties config = new Properties();
			config.load(new FileInputStream(configFile));

			maxAUs = Integer.parseInt(config.getProperty("maxAUs", "-1"));

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

		MySTARS app = new MySTARS(new ConsoleGraphicUserInterface(telnetPort), notifier);
		app.start();

		if (indexDataFile != null) {
			app.loadIndexes(indexDataFile);
		}

		if (createAdmin) {
			app.createAdmin();
		}

		if (createStudents) {
			app.createStudents();
		}

		if (maxAUs == -1) {
			System.out.println("Defaulting max AUs to 21 because no value specified");
			app.setMaxAUs(21);
		} else {
			app.setMaxAUs(maxAUs);
		}

		app.loop();
	}

	/**
	 * Creates the object to be used for sending email notifications to students.
	 * {@code config} is expected to contain the following properties:<br>
	 * <ul>
	 * <li>{@code username}: username of email account
	 * <li>{@code password}: password of email account
	 * <li>{@code smtpHost}: Hostname of the SMTP server to connect to
	 * <li>{@code smtpPort}: Port of the SMTP server to connect to
	 * </ul>
	 * 
	 * If any of the above properties are not provided, null is returned.
	 * 
	 * @param config Configuration parameters
	 * @return Configured object
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
