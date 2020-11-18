package mystars.notifications;

import mystars.entities.Student;

/**
 * Represents a class that is capable of sending a notification to a student
 */
public interface INotifyStudent {
	/**
	 * Send {@code message} with {@code title} to {@code student}
	 * 
	 * @param student target to send notification to
	 * @param title   title of the notification
	 * @param message message of the notification
	 * @return {@code true} if notified successfully
	 * @return {@code false} otherwise
	 */
	public boolean notify(Student student, String title, String message);
}
