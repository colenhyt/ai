package cl.util.mail;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;



/*
 ** JSO1.0, by Allen Huang,2007-7-4
 */
public class GoogleMailBox implements MailBox{
	static Logger log = Logger.getLogger("GoogleMailBox.java");
	private final String host,from,pwd,smtpStr,charSet;
	private final Properties properties;
	
	public GoogleMailBox(){
		host="smtp.gmail.com";
		from="bikeeadmin@gmail.com";
		pwd="allenhyt";
		charSet="utf-8";
		smtpStr="smtp";
		properties=System.getProperties(); 
		properties.put("mail.smtp.host",host); 
		properties.put("mail.smtp.auth","true"); 
		properties.put("mail.from",from);
		properties.put("mail.smtp.starttls.enable","true"); 
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.starttls.enable","true"); 
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.quitwait", "false");
//		properties.put("mail.smtp.port", "25"); 

		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
	}

	public String getFrom() {
		return from;
	}

	public String getHost() {
		return host;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPwd() {
		return pwd;
	}

	public String getCharSet() {
		return charSet;
	}

	public String getSmtpStr() {
		return smtpStr;
	}

	public int getPort() {
		return 25;
	}

}

