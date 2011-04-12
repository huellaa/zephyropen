package zephyropen.util;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


//
// ref: http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
//
public class SendMAIL {
	
	// take this from properties 
	private static final int SMTP_HOST_PORT = 587;
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_AUTH_USER = "brad.zdanivsky@gmail.com";
	private static final String SMTP_AUTH_PWD = "";

	/* test driver */
	public static void main(String[] args) throws Exception {
		
		if (sendMessage("Important Event", "testing attachment", ".classpath"))
			System.out.println("email sent");
		else
			System.out.println("email failed, check your settings");
	}

	/**
t	 * 
	 * Send yourself an error message from the robot. This method requires a
	 * Gmail user account.
	 * 
	 * @param text
	 *            is the message body to form the email body
	 * @return True if mail was sent successfully
	 */
	public static boolean sendMessage(final String sub, final String text) {
		try {
			Properties props = new Properties();
			props.put("mail.smtps.host", SMTP_HOST_NAME);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			Session mailSession = Session.getDefaultInstance(props);
			Transport transport = mailSession.getTransport("smtp");
			// mailSession.setDebug(true);

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(sub);
			message.setContent(text, "text/plain");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(SMTP_AUTH_USER));

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}

		// all good
		return true;
	}
	
	public static boolean sendMessage(final String sub, final String text, final String path) {
		
		try{
		
			Properties props = new Properties();
			props.put("mail.smtps.host", SMTP_HOST_NAME);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			
			Session mailSession = Session.getDefaultInstance(props);
			Transport transport = mailSession.getTransport("smtp");
			mailSession.setDebug(true);
	
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(sub);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(SMTP_AUTH_USER));
	    	 
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(text);
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	
	        // Part two is attachment
	        messageBodyPart = new MimeBodyPart();
	        DataSource source = new FileDataSource(path);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(path);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        
	        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
	       
	    }catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
    
	    // all well
	    return true;
	}

}