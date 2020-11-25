package mystars.notifications;

import mystars.entities.Student;

/**
 * Represents a class that sends a notification to a student.
 */
public interface INotifyStudent {
	/**
	 * Send a {@code message} with the {@code title} to a {@code student}.
	 * 
	 * @param student The target to send notification to.
	 * @param title   The title of the notification.
	 * @param message The message of the notification.
	 * @return {@code true} If the student is notified successfully, or
	 *         {@code false} otherwise
	 */
	public boolean notify(Student student, String title, String message);
}
