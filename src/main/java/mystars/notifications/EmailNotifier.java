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

public class EmailNotifier implements INotifyStudent {
	private Properties props = new Properties();
	private String username;
	private String password;

	public EmailNotifier(String username, String password, String smtpHost, String smtpPort) {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		this.username = username;
		this.password = password;
	}

	/**
	 * Notify {@code student} with the given {@code title} and {@code message}
	 * 
	 * @returns {@code true} if succesful
	 * @returns {@code false} otherwise
	 */
	@Override
	public boolean notify(Student student, String title, String message) {
		return sendEmail(student.getEmail(), title, message);
	}

	/**
	 * Sends an email to {@code email} with subject {@code subject} and body
	 * {@code body}
	 * 
	 * @param email Email address to send to
	 * @param title Title of the email
	 * @param body  Body of the email
	 * 
	 * @return {@code true} if successful
	 * @return {@code false} otherwise
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // to be added an email addr
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
