package models;

import java.io.File;
import java.net.URL;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import play.db.jpa.Model;
import play.libs.Mail;

public class Emailer  extends Model {

	public static void sendNewsletterTo(String emailAddressDestination, String emailAddressSource, File fileURL) throws EmailException{	
		HtmlEmail email = new HtmlEmail();
		email.addTo(emailAddressDestination);
		email.setFrom(emailAddressSource, "User");
		email.setSubject("Journal Newsletter");
		// embed content
		String cid = email.embed(fileURL);
		// set the html message
		email.setHtmlMsg("Attached is a copy of our Newsletter");
		// set the alternative message
		email.setTextMsg("Attached is a copy og our Newsletter");
		Mail.send(email); 
	}
	
	public static void sendEmailTo(String emailAddressDestination, String emailAddressSource, String message, String title) throws EmailException{	
		HtmlEmail email = new HtmlEmail();
		email.addTo(emailAddressDestination);
		email.setFrom(emailAddressSource);
		email.setSubject(title);
		// set the html message
		email.setHtmlMsg(message);
		// set the alternative message
		email.setTextMsg(message);
		Mail.send(email); 
	}
}
