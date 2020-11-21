package mystars.notifications;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mystars.entities.Student;

/**
 * <h1>Class: EmailNotifier</h1>
 * 
 * This class sends an email to the student to notify them of changes to their
 * course registration.
 */
public class EmailNotifier implements INotifyStudent {
	private Properties props = new Properties();
	private String username;
	private String password;

	/**
	 * This class sets the parameters for the email notifier object,
	 * 
	 * @param username The student's username.
	 * @param password The student's password.
	 * @param smtpHost The SMTP hostname.
	 * @param smtpPort The SMTP port number.
	 */
	public EmailNotifier(String username, String password, String smtpHost, String smtpPort) {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		this.username = username;
		this.password = password;
	}

	/**
	 * Notify a student {@code student} with the given title {@code title} and
	 * message {@code message}.
	 * 
	 * @return {@code true} If the notification was successful, or {@code false}
	 *         otherwise
	 */
	@Override
	public boolean notify(Student student, String title, String message) {
		return sendEmail(student.getEmail(), title, message);
	}

	/**
	 * Sends an email to an address {@code email} with the subject {@code subject}
	 * and body {@code body}
	 * 
	 * @param email   The email address to send to.
	 * @param subject The title of the email.
	 * @param body    The body of the email.
	 * 
	 * @return {@code true} If the email was sent successfully, or {@code false}
	 *         otherwise
	 */
	public boolean sendEmail(String email, String subject, String body) {
		System.out.println(String.format("Sending email to %s with subject=%s, body=%s", email, subject, body));
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(username));
			// An email address is to be added here.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			return true;
		} catch (MessagingException e) {
			System.err.println("Failed to send email");
			e.printStackTrace();
		}
		return false;
	}
}
